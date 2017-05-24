package com.example.sikanla.maquettehandi.network;

import android.content.Context;

import com.example.sikanla.maquettehandi.Model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sikanla on 25/05/2017.
 */

public class FriendRequester {

    public interface AddFriendCB {
        void onFriendAdded(Boolean success);

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
}
