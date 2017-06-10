package com.example.sikanla.maquettehandi.network;

import android.content.Context;

import com.example.sikanla.maquettehandi.Model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sikanla on 10/06/2017.
 */

public class InstantRequester {
    public interface PostInstantCB {
        void onInstantCB(boolean success);
    }

    public void sendInstantRequest(Context context, String help_category, String description,
                                   String longi, String lat, final PostInstantCB postInstantCB) {
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
                                    postInstantCB.onInstantCB(true);
                                } else
                                    postInstantCB.onInstantCB(false);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            postInstantCB.onInstantCB(false);

                        }
                    }
                });
    }
}
