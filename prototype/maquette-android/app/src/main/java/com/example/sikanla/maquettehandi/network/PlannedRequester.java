package com.example.sikanla.maquettehandi.network;

import android.content.Context;

import com.example.sikanla.maquettehandi.Model.PlannedRequest;
import com.example.sikanla.maquettehandi.Model.ResponsePlanned;
import com.example.sikanla.maquettehandi.Model.User;

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

    public interface ResponsePlannedCB {
        void onResponsePlanned(ArrayList<ResponsePlanned> s, Boolean success);
    }


    public interface GetUserCB {
        void getUser(String firstName, String surname, String age, Boolean success);
    }

    public interface PostPlannedCB {
        void onPlannedPosted(Boolean success);
    }

    public void getPlannedRequest(Context context, final PlannedRequestCB plannedRequestCB) {
        User user = new User();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", user.getAPIKEY());
        Map<String, String> parameters = new HashMap<>();
        new AllRequest(context, parameters, headers, "/allplannedrequest", AllRequest.GET, new AllRequest.CallBackConnector() {
            @Override
            public void CallBackOnConnect(String response, Boolean success) {
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

    public void getMyPlannedRequest(Context context, final PlannedRequestCB plannedRequestCB) {
        User user = new User();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", user.getAPIKEY());
        Map<String, String> parameters = new HashMap<>();
        new AllRequest(context, parameters, headers, "/plannedrequest/me", AllRequest.GET, new AllRequest.CallBackConnector() {
            @Override
            public void CallBackOnConnect(String response, Boolean success) {
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
    private ArrayList<PlannedRequest> fromJson(JSONArray jsonObjects) {
        ArrayList<PlannedRequest> plannedRequests = new ArrayList<PlannedRequest>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                plannedRequests.add(new PlannedRequest(jsonObjects.getJSONObject(i).getString("help_category"),
                        jsonObjects.getJSONObject(i).getString("description"),
                        jsonObjects.getJSONObject(i).getString("scheduled_at"),
                        jsonObjects.getJSONObject(i).getString("localisation"),
                        jsonObjects.getJSONObject(i).getString("id"),
                        jsonObjects.getJSONObject(i).getString("id_planned")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return plannedRequests;
    }

    public void getUser(Context context, String userId, final GetUserCB getUserCB) {
        User user = new User();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", user.getAPIKEY());
        Map<String, String> parameters = new HashMap<>();
        new AllRequest(context, parameters, headers, "/user/" + userId, AllRequest.GET, new AllRequest.CallBackConnector() {
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

    public void sendPlannedRequest(Context context, String help_category, String description,
                                   String scheduled_at, String localisation, String notifyFriends, final PostPlannedCB postPlannedCB) {
        User user = new User();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", user.getAPIKEY());
        Map<String, String> parameters = new HashMap<>();
        parameters.put("help_category", help_category);
        parameters.put("description", description);
        parameters.put("scheduled_at", scheduled_at);
        parameters.put("localisation", localisation);
        parameters.put("notify_friends", notifyFriends);

        new AllRequest(context, parameters, headers, "/plannedrequest", AllRequest.POST, new AllRequest.CallBackConnector() {
            @Override
            public void CallBackOnConnect(String response, Boolean success) {
                if (success) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.get("error").toString() == "false") {
                            postPlannedCB.onPlannedPosted(true);
                        } else
                            postPlannedCB.onPlannedPosted(false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    postPlannedCB.onPlannedPosted(false);

                }
            }
        });
    }


    public void answerPlanned(Context context, String description,
                              String idHelped, String idRequest, final PostPlannedCB postPlannedCB) {
        User user = new User();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", user.getAPIKEY());
        Map<String, String> parameters = new HashMap<>();
        parameters.put("description", description);
        parameters.put("id_helped", idHelped);

        new AllRequest(context, parameters, headers, "/respondplanned/" + idRequest, AllRequest.POST, new AllRequest.CallBackConnector() {
            @Override
            public void CallBackOnConnect(String response, Boolean success) {
                if (success) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.get("error").toString() == "false") {
                            postPlannedCB.onPlannedPosted(true);
                        } else
                            postPlannedCB.onPlannedPosted(false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    postPlannedCB.onPlannedPosted(false);

                }
            }
        });
    }


    public void getResponsesPlanned(Context context, final ResponsePlannedCB responsePlannedCB) {
        User user = new User();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", user.getAPIKEY());
        Map<String, String> parameters = new HashMap<>();
        new AllRequest(context, parameters, headers, "/responsesplanned", AllRequest.GET, new AllRequest.CallBackConnector() {
            @Override
            public void CallBackOnConnect(String response, Boolean success) {
                ArrayList<ResponsePlanned> arrayList = new ArrayList<>();

                if (success) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = new JSONArray(jsonObject.get("responses_planned").toString());

                        if (jsonObject.get("error").toString() == "false") {
                            responsePlannedCB.onResponsePlanned(responsesPlannedFromJson(jsonArray), true);

                        } else
                            responsePlannedCB.onResponsePlanned(arrayList, false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    responsePlannedCB.onResponsePlanned(arrayList, false);

                }
            }
        });
    }

    // Factory method to convert an array of JSON objects into a list of objects
    private ArrayList<ResponsePlanned> responsesPlannedFromJson(JSONArray jsonObjects) {
        ArrayList<ResponsePlanned> responsePlanneds = new ArrayList<>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                responsePlanneds.add(new ResponsePlanned(jsonObjects.getJSONObject(i).getString("id_request"),
                        jsonObjects.getJSONObject(i).getString("id_helper")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return responsePlanneds;
    }

    public void selectAnswerPlanned(Context context, String idHelper, String idRequest, final PostPlannedCB postPlannedCB) {
        User user = new User();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", user.getAPIKEY());
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id_request", idRequest);
        parameters.put("id_helper", idHelper);

        new AllRequest(context, parameters, headers, "/selectresponseplanned", AllRequest.POST, new AllRequest.CallBackConnector() {
            @Override
            public void CallBackOnConnect(String response, Boolean success) {
                if (success) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.get("error").toString() == "false") {
                            postPlannedCB.onPlannedPosted(true);
                        } else
                            postPlannedCB.onPlannedPosted(false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    postPlannedCB.onPlannedPosted(false);

                }
            }
        });
    }

    public void deleteResponsePlanned(Context context, String idHelper, String idRequest, final PostPlannedCB postPlannedCB) {
        User user = new User();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", user.getAPIKEY());
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id_request", idRequest);
        parameters.put("id_helper", idHelper);

        new AllRequest(context, parameters, headers, "/deleteresponseplanned", AllRequest.POST, new AllRequest.CallBackConnector() {
            @Override
            public void CallBackOnConnect(String response, Boolean success) {
                if (success) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.get("error").toString() == "false") {
                            postPlannedCB.onPlannedPosted(true);
                        } else
                            postPlannedCB.onPlannedPosted(false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    postPlannedCB.onPlannedPosted(false);

                }
            }
        });
    }



}
