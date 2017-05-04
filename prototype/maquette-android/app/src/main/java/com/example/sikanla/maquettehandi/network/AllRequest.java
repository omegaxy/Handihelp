package com.example.sikanla.maquettehandi.network;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import java.util.Map;

/**
 * Created by Sikanla on 02/05/2017.
 */

public class AllRequest extends Thread {
    public static String SERVERURL = "http://178.62.33.9/task_manager/v1";


    private RequestQueue requestQueue;
    private String route;
    private Map parameters;
    private CallBackConnector callBackConnector;

    public interface CallBackConnector {

        void CallBackOnConnect(String response);
    }


    public AllRequest(Context context, Map<String, String> parameters, String route, CallBackConnector callBackConnector) {
        requestQueue = Volley.newRequestQueue(context);
        this.route = route;
        this.parameters = parameters;
        this.callBackConnector = callBackConnector;
    }

    public void run() {
        sendRequest(parameters, route);
    }

    public void sendRequest(final Map parameters, final String route) {

        StringRequest jsonObjRequest = new StringRequest(com.android.volley.Request.Method.POST, SERVERURL + route,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //depending on the route chose a different method to use the response with
                        switch (route) {
                            case "/login":
                                callBackConnector.CallBackOnConnect(response);
                                break;
                            //case "/register": etc...
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("volley", "Error: " + error.getMessage());
                        error.printStackTrace();
                    }
                }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return parameters;
            }
        };
        requestQueue.add(jsonObjRequest);

    }


}

