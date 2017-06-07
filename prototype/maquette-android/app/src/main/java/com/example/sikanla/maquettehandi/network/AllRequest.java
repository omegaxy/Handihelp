package com.example.sikanla.maquettehandi.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sikanla on 02/05/2017.
 */
//Singleton class
public class AllRequest {

    public static String SERVERURL = "http://178.62.33.9/handi_help_server/v1";

    private static AllRequest mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;

    public static int GET = 0;
    public static int POST = 1;
    public static int PUT = 2;
    public static int DELETE = 3;


    private AllRequest(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized AllRequest getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new AllRequest(context);
        }
        return mInstance;
    }


    public interface CallBackConnector {
        void CallBackOnConnect(String response, Boolean success);
    }

    public void sendRequest(int method, final Map parameters, final HashMap headers, final String route, final CallBackConnector callBackConnector) {

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
        addToRequestQueue(jsonObjRequest);

    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    private <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }


}

