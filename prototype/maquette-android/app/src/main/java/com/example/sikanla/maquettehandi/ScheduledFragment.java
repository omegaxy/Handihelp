package com.example.sikanla.maquettehandi;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sikanla on 13/02/2017.
 */

public class ScheduledFragment extends Fragment {
    private EditText editTextName, editTextMail, editTextPassword;
    private Button signInButton;
    private RequestQueue requestQueue;
    private TextView textViewServerResponse;
    private Button logInButton;
    private TextView textViewApiKey;
    private String serverUrl = "http://10.0.2.2/task_manager/v1";

    public ScheduledFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestQueue = Volley.newRequestQueue(getActivity());


        super.onCreate(savedInstanceState);


    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_scheduled, container, false);


    }

    @Override
    public void onActivityCreated(Bundle savedInstance) {
        super.onActivityCreated(savedInstance);

        editTextName = (EditText) getView().findViewById(R.id.editTextName);
        editTextMail = (EditText) getView().findViewById(R.id.editTextMail);
        editTextPassword = (EditText) getView().findViewById(R.id.editTextPassword);
        signInButton = (Button) getView().findViewById(R.id.signInButton);
        textViewServerResponse = (TextView) getView().findViewById(R.id.textViewServerResponse);
        textViewApiKey = (TextView) getView().findViewById(R.id.textViewApiKey);
        logInButton = (Button) getView().findViewById(R.id.logInButton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "sending request", Toast.LENGTH_LONG).show();

                StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, serverUrl + "/register",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                textViewServerResponse.setText(response);
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("volley", "Error: " + error.getMessage());
                        error.printStackTrace();

                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                // Now you can use any deserializer to make sense of data
                                JSONObject obj = new JSONObject(res);
                                textViewServerResponse.setText(obj.toString());
                            } catch (UnsupportedEncodingException e1) {
                                // Couldn't properly decode data to string
                                e1.printStackTrace();
                            } catch (JSONException e2) {
                                // returned data is not JSONObject?
                                e2.printStackTrace();
                            }
                        }

                    }
                }) {

                    @Override
                    public String getBodyContentType() {
                        return "application/x-www-form-urlencoded; charset=UTF-8";
                    }

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("name", editTextName.getText().toString());
                        params.put("email", editTextMail.getText().toString());
                        params.put("password", editTextPassword.getText().toString());
                        return params;
                    }

                };

                requestQueue.add(jsonObjRequest);

            }
        });

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "sending request", Toast.LENGTH_LONG).show();

                StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, serverUrl + "/login",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(getActivity(), "onResponse", Toast.LENGTH_LONG).show();
                                textViewServerResponse.setText(response);
                                char[] apikey = new char[32];
                                response.getChars(response.indexOf("apiKey")+9,response.indexOf("apiKey")+32+9,apikey,0);
                                textViewApiKey.setText("ApiKey:"+ String.copyValueOf(apikey));
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "onErrorResponse", Toast.LENGTH_LONG).show();
                        VolleyLog.d("volley", "Error: " + error.getMessage());
                        error.printStackTrace();

                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                // Now you can use any deserializer to make sense of data
                                JSONObject obj = new JSONObject(res);
                                textViewServerResponse.setText(obj.toString());
                            } catch (UnsupportedEncodingException e1) {
                                // Couldn't properly decode data to string
                                e1.printStackTrace();
                            } catch (JSONException e2) {
                                // returned data is not JSONObject?
                                e2.printStackTrace();
                            }
                        }

                    }
                }) {

                    @Override
                    public String getBodyContentType() {
                        return "application/x-www-form-urlencoded; charset=UTF-8";
                    }

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("email", editTextMail.getText().toString());
                        params.put("password", editTextPassword.getText().toString());
                        return params;
                    }

                };

                requestQueue.add(jsonObjRequest);

            }


        });


    }
}
