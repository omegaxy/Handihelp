package com.example.sikanla.maquettehandi.network;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.sikanla.maquettehandi.Model.ResponsePlanned;
import com.example.sikanla.maquettehandi.Model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sikanla on 09/06/2017.
 */

public class UserRequester {

    public interface UserRequestCB {
        void onRequest(Boolean success);
    }

    public interface GetUserCB {
        void getUser(String firstName, String surname, String age, Boolean success);
    }

    public interface GetRatingsCB {
        void getRatings(String ratings, ArrayList<String> comments, Boolean success);
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

    public void getUser(Context context, String userId, final GetUserCB getUserCB) {
        User user = new User();
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", user.getAPIKEY());
        Map<String, String> parameters = new HashMap<>();
        AllRequest.getInstance(context)
                .sendRequest(AllRequest.GET, parameters, headers, "/user/" + userId, new AllRequest.CallBackConnector() {
                    @Override
                    public void CallBackOnConnect(String response, Boolean success) {
                        if (success) {

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.get("error").toString() == "false") {
                                    getUserCB.getUser(jsonObject.getString("firstname"),
                                            jsonObject.getString("surname"), jsonObject.getString("birth_year"), true);
                                } else
                                    getUserCB.getUser("", "", "", false);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            getUserCB.getUser("", "", "", false);

                        }
                    }
                });
    }

    public void deleteUser(Context context, String email, String password, final UserRequestCB userRequestCB) {
        final User user = new User();
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", user.getAPIKEY());
        Map<String, String> parameters = new HashMap<>();
        parameters.put("email", email);
        parameters.put("password", password);
        AllRequest.getInstance(context)
                .sendRequest(AllRequest.POST, parameters, headers, "/user/delete", new AllRequest.CallBackConnector() {
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

    private void getRatings(Context context, String userId, final GetRatingsCB getRatingsCB) {
        User user = new User();
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", user.getAPIKEY());
        Map<String, String> parameters = new HashMap<>();
        AllRequest.getInstance(context)
                .sendRequest(AllRequest.GET, parameters, headers, "/user/rating/" + userId, new AllRequest.CallBackConnector() {
                    @Override
                    public void CallBackOnConnect(String response, Boolean success) {
                        if (success) {

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonArray = new JSONArray(jsonObject.get("users").toString());

                                if (jsonObject.get("error").toString() == "false") {
                                    getRatingsCB.getRatings(getUserRating(jsonArray),
                                            getUserComments(jsonArray), true);
                                } else
                                    getRatingsCB.getRatings(null, null, false);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            getRatingsCB.getRatings(null, null, false);

                        }
                    }
                });
    }

    // Factory method to convert an array of JSON objects into a list of objects
    private String getUserRating(JSONArray jsonObjects) {
        float sum = 0;
        int count = 0;
        User user = new User();

        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                if (user.getUserId() == jsonObjects.getJSONObject(i).getString("id_helper")) {
                    sum += Float.parseFloat(jsonObjects.getJSONObject(i).getString("rating_given_helper"));
                    count+=1;
                } else {
                    sum += Float.parseFloat(jsonObjects.getJSONObject(i).getString("rating_given_helped"));
                    count+=1;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return String.valueOf(sum/count);
    }

    private ArrayList<String> getUserComments(JSONArray jsonObjects) {
        ArrayList<String> comments = new ArrayList<>();
        User user = new User();

        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                if (user.getUserId() == jsonObjects.getJSONObject(i).getString("id_helper")) {
                    comments.add(jsonObjects.getJSONObject(i).getString("comment_given_helper"));
                } else {
                    comments.add(jsonObjects.getJSONObject(i).getString("comment_given_helped"));

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return comments;
    }


}
