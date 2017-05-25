package com.example.sikanla.maquettehandi.network;

import android.content.Context;
import android.util.Log;

import com.example.sikanla.maquettehandi.Model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sikanla on 25/05/2017.
 */

public class FriendRequester {

    public interface AddFriendCB {
        void onFriendAdded(Boolean success);

    }

    public interface GetFriendCB {
        void getArrayFriends(ArrayList<String> arrayList, Boolean success);

    }

    public void addFriend(Context context, String id_friend, final AddFriendCB addFriendCB) {
        User user = new User();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", user.getAPIKEY());
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id_friend", id_friend);
        new AllRequest(context, parameters, headers, "/user/friend", AllRequest.POST, new AllRequest.CallBackConnector() {
            @Override
            public void CallBackOnConnect(String response, Boolean success) {
                if (success) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.get("error").toString() == "false") {
                            addFriendCB.onFriendAdded(true);
                        } else
                            addFriendCB.onFriendAdded(false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    addFriendCB.onFriendAdded(false);

                }
            }
        });
    }

    public void getFriends(Context context, final GetFriendCB getFriendCB) {
        User user = new User();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", user.getAPIKEY());
        Map<String, String> parameters = new HashMap<>();
        new AllRequest(context, parameters, headers, "/friends", AllRequest.GET, new AllRequest.CallBackConnector() {
            @Override
            public void CallBackOnConnect(String response, Boolean success) {
                ArrayList<String> arrayList = new ArrayList<>();

                if (success) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = new JSONArray(jsonObject.get("friends").toString());

                        if (jsonObject.get("error").toString() == "false") {
                            getFriendCB.getArrayFriends(fromJson(jsonArray), true);

                        } else
                            getFriendCB.getArrayFriends(fromJson(jsonArray), false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    getFriendCB.getArrayFriends(arrayList, false);

                }
            }
        });
    }

    // Factory method to convert an array of JSON objects into a list of objects
    private ArrayList<String> fromJson(JSONArray jsonObjects) {
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                strings.add(jsonObjects.getJSONObject(i).getString("id_friend"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return strings;
    }
}
