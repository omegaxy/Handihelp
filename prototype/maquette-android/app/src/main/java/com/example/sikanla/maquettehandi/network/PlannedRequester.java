package com.example.sikanla.maquettehandi.network;

import android.content.Context;
import android.util.Log;

import com.example.sikanla.maquettehandi.Model.PlannedRequest;
import com.example.sikanla.maquettehandi.identification.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sikanla on 10/05/2017.
 */

public class PlannedRequester {

    public interface PlannedRequestCB {
        void getArrayPlannedRequest(ArrayList<PlannedRequest> s, Boolean success);
    }

    public void getPlannedRequest(Context context, final PlannedRequestCB plannedRequestCB) {
        User user = new User();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", user.getAPIKEY());
        Map<String, String> parameters = new HashMap<>();
        new AllRequest(context, parameters, headers, "/allplannedrequest", AllRequest.GET, new AllRequest.CallBackConnector() {
            @Override
            public void CallBackOnConnect(String response, Boolean success) {
                Log.e("callback1", success.toString());
                ArrayList<PlannedRequest> arrayList = new ArrayList<>();

                if (success) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = new JSONArray(jsonObject.get("planned_requests").toString());

                        if (jsonObject.get("error").toString() == "false") {
                            plannedRequestCB.getArrayPlannedRequest(fromJson(jsonArray), true);

                        } else
                            plannedRequestCB.getArrayPlannedRequest(arrayList, false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    plannedRequestCB.getArrayPlannedRequest(arrayList, false);

                }
            }
        });
    }

    // Factory method to convert an array of JSON objects into a list of objects
    public static ArrayList<PlannedRequest> fromJson(JSONArray jsonObjects) {
        ArrayList<PlannedRequest> plannedRequests = new ArrayList<PlannedRequest>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                //todo
                plannedRequests.add(new PlannedRequest(jsonObjects.getJSONObject(i).getString("help_category"),
                        jsonObjects.getJSONObject(i).getString("description"),
                        jsonObjects.getJSONObject(i).getString("scheduled_at"),
                        jsonObjects.getJSONObject(i).getString("localisation"),
                        jsonObjects.getJSONObject(i).getString("id")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return plannedRequests;
    }
}
