package com.example.sikanla.maquettehandi.network;

import android.content.Context;
import android.util.Log;

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

public class AllRequest {
    public static String SERVERURL = "http://178.62.33.9/handi_help_server/v1";

    private RequestQueue requestQueue;
    private String route;
    private Map parameters;
    private Map headers;
    private CallBackConnector callBackConnector;

    public interface CallBackConnector {
        void CallBackOnConnect(String response, Boolean success);
    }

    public AllRequest(Context context, Map<String, String> parameters, Map<String, String> headers,
                      String route, int method, CallBackConnector callBackConnector) {
        requestQueue = Volley.newRequestQueue(context);
        this.headers = headers;
        this.route = route;
        this.parameters = parameters;
        this.callBackConnector = callBackConnector;
        sendRequest(method, parameters, route);
    }

    public static int GET = 0;
    public static int POST = 1;
    public static int PUT = 2;
    public static int DELETE = 3;

    private void sendRequest(int method, final Map parameters, final String route) {

        StringRequest jsonObjRequest = new StringRequest(method, SERVERURL + route,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callBackConnector.CallBackOnConnect(response, true);
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callBackConnector.CallBackOnConnect(error.toString(), false);
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

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headers;
            }
        };
        requestQueue.add(jsonObjRequest);

    }




}

