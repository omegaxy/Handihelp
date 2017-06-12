package com.example.sikanla.maquettehandi.network;

import android.content.Context;

import com.example.sikanla.maquettehandi.Model.InstantRequest;
import com.example.sikanla.maquettehandi.Model.PlannedRequest;
import com.example.sikanla.maquettehandi.Model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sikanla on 10/06/2017.
 */

public class InstantRequester {
    public interface InstantCB {
        void onInstantCB(boolean success);
    }

    public interface InstantRequestCB {
        void getArrayInstantRequest(ArrayList<InstantRequest> s, Boolean success);
    }


    public void updatePosition(Context context, String longi, String lat, final InstantCB instantCB) {
        User user = new User();
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", user.getAPIKEY());
        Map<String, String> parameters = new HashMap<>();
        parameters.put("longi", longi);
        parameters.put("lat", lat);
        AllRequest.getInstance(context)
                .sendRequest(AllRequest.POST, parameters, headers, "/position", new AllRequest.CallBackConnector() {
                    @Override
                    public void CallBackOnConnect(String response, Boolean success) {
                        if (success) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.get("error").toString() == "false") {
                                    instantCB.onInstantCB(true);
                                } else
                                    instantCB.onInstantCB(false);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            instantCB.onInstantCB(false);

                        }
                    }
                });
    }


    public void sendInstantRequest(Context context, String help_category, String description,
                                   String longi, String lat, final InstantCB instantCB) {
        User user = new User();
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", user.getAPIKEY());
        Map<String, String> parameters = new HashMap<>();
        parameters.put("help_category", help_category);
        parameters.put("description", description);
        parameters.put("longi", longi);
        parameters.put("lat", lat);
        AllRequest.getInstance(context)
                .sendRequest(AllRequest.POST, parameters, headers, "/instantrequest", new AllRequest.CallBackConnector() {
                    @Override
                    public void CallBackOnConnect(String response, Boolean success) {
                        if (success) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.get("error").toString() == "false") {
                                    instantCB.onInstantCB(true);
                                } else
                                    instantCB.onInstantCB(false);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            instantCB.onInstantCB(false);

                        }
                    }
                });
    }

    public void getInstantRequests(Context context, final InstantRequestCB instantCB) {
        User user = new User();
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", user.getAPIKEY());
        Map<String, String> parameters = new HashMap<>();
        AllRequest.getInstance(context)
                .sendRequest(AllRequest.GET, parameters, headers, "/allinstantrequest", new AllRequest.CallBackConnector() {
                    @Override
                    public void CallBackOnConnect(String response, Boolean success) {
                        ArrayList<InstantRequest> arrayList = new ArrayList<>();

                        if (success) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonArray = new JSONArray(jsonObject.get("planned_requests").toString());

                                if (jsonObject.get("error").toString() == "false") {
                                    instantCB.getArrayInstantRequest(fromJson(jsonArray), true);

                                } else
                                    instantCB.getArrayInstantRequest(arrayList, false);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            instantCB.getArrayInstantRequest(arrayList, false);

                        }
                    }
                });
    }

    private ArrayList<InstantRequest> fromJson(JSONArray jsonObjects) {
        ArrayList<InstantRequest> instantRequests = new ArrayList<InstantRequest>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                instantRequests.add(new InstantRequest(jsonObjects.getJSONObject(i).getString("help_category"),
                        jsonObjects.getJSONObject(i).getString("description"),
                        jsonObjects.getJSONObject(i).getString("close_users"),
                        jsonObjects.getJSONObject(i).getString("createdAt"),
                        jsonObjects.getJSONObject(i).getString("id"),
                        jsonObjects.getJSONObject(i).getString("id_instant")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return instantRequests;
    }

}
