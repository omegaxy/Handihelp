package com.example.sikanla.maquettehandi.network;

import android.content.Context;
import android.util.Log;

import com.example.sikanla.maquettehandi.Model.Contact;
import com.example.sikanla.maquettehandi.identification.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sikanla on 15/05/2017.
 */

public class MessageRequester {
    public interface MessagesCB {
        void getArrayContacts(ArrayList<Contact> s, Boolean success);

        void getArrayMessages(ArrayList<String> arrayList);
    }

    public interface SendMessagesCB {
        void onMessageReceived(Boolean success);

    }

    public void getContacts(Context context, final MessagesCB messagesCB) {
        User user = new User();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", user.getAPIKEY());
        Map<String, String> parameters = new HashMap<>();
        new AllRequest(context, parameters, headers, "/messages", AllRequest.GET, new AllRequest.CallBackConnector() {
            @Override
            public void CallBackOnConnect(String response, Boolean success) {
                ArrayList<Contact> arrayList = new ArrayList<>();
                if (success) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = new JSONArray(jsonObject.get("contacts").toString());

                        if (jsonObject.get("error").toString() == "false") {
                            messagesCB.getArrayContacts(fromJson(jsonArray), true);

                        } else
                            messagesCB.getArrayContacts(arrayList, false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    messagesCB.getArrayContacts(arrayList, false);

                }
            }
        });
    }

    // Factory method to convert an array of JSON objects into a list of objects
    public static ArrayList<Contact> fromJson(JSONArray jsonObjects) {
        ArrayList<Contact> contacts = new ArrayList<Contact>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                //todo
                contacts.add(new Contact(jsonObjects.getJSONObject(i).getString("contact_firstname"),
                        jsonObjects.getJSONObject(i).getString("contact_surname"),
                        jsonObjects.getJSONObject(i).getString("contact_id")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return contacts;
    }


    public void sendMessage(Context context, String message, String idReceiver, final SendMessagesCB sendMessagesCB) {
        User user = new User();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", user.getAPIKEY());
        Map<String, String> parameters = new HashMap<>();
        parameters.put("message", message);
        parameters.put("id_receiver", idReceiver);
        new AllRequest(context, parameters, headers, "/message/user", AllRequest.POST, new AllRequest.CallBackConnector() {
            @Override
            public void CallBackOnConnect(String response, Boolean success) {
                if (success) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.get("error").toString() == "false") {
                            sendMessagesCB.onMessageReceived(true);
                        } else
                            sendMessagesCB.onMessageReceived(false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    sendMessagesCB.onMessageReceived(false);

                }
            }
        });
    }
}

