package com.example.sikanla.maquettehandi.network;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.sikanla.maquettehandi.Model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sikanla on 09/06/2017.
 */

public class UserRequester {

    public interface UserRequestCB {
        void onRequest(Boolean success);
    }


    public void modifyPassword(Context context, String password, final UserRequestCB userRequestCB) {
        final User user = new User();
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", user.getAPIKEY());
        Map<String, String> parameters = new HashMap<>();
        parameters.put("password", password);
        AllRequest.getInstance(context)
                .sendRequest(AllRequest.PUT, parameters, headers, "/user/password", new AllRequest.CallBackConnector() {
                    @Override
                    public void CallBackOnConnect(String response, Boolean success) {
                        if (success) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.get("error").toString() == "false") {
                                    userRequestCB.onRequest(true);
                                } else
                                    userRequestCB.onRequest(false);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            userRequestCB.onRequest(false);

                        }
                    }
                });
    }


    public void saveAndroidIdtoServer(final Context context) {

        SharedPreferences prefs = context.getSharedPreferences(User.MY_PREFS_NAME, User.MODE_PRIVATE);
        String str = prefs.getString("androidid", "");
        User user = new User();

        Map<String, String> parameters = new HashMap<>();
        parameters.put("androidid", str);
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", user.getAPIKEY());
        AllRequest.getInstance(context)
                .sendRequest(AllRequest.POST, parameters, headers, "/user/androidid", new AllRequest.CallBackConnector() {
                    @Override
                    public void CallBackOnConnect(String response, Boolean success) {

                    }
                });
    }

    public void deleteAndroidIFromServer(final Context context) {
        User user = new User();
        Map<String, String> parameters = new HashMap<>();
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", user.getAPIKEY());
        AllRequest.getInstance(context)
                .sendRequest(AllRequest.PUT, parameters, headers, "/user/androidid", new AllRequest.CallBackConnector() {
                    @Override
                    public void CallBackOnConnect(String response, Boolean success) {

                    }
                });
    }


}
