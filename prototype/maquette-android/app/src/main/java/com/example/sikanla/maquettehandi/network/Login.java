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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sikanla on 02/05/2017.
 */

public class Login extends Thread {

    private RequestQueue requestQueue;
    private String email;
    private String password;

    public Login(Context context, String email, String password) {
        requestQueue = Volley.newRequestQueue(context);
        this.email = email;
        this.password = password;
    }

    public void run() {
        sendRequest(email, password);
    }

    public void sendRequest(final String email, final String password) {
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, StaticInformations.SERVERURL + "/login",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            StaticInformations.setApikey(jsonObject.getString("apiKey"));
                            Log.e("Apikey", jsonObject.getString("apiKey"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //response.getChars(response.indexOf("apiKey") + 9, response.indexOf("apiKey") + 32 + 9, apikey, 0);
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
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }

        };

        requestQueue.add(jsonObjRequest);

    }


}

