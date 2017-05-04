package com.example.sikanla.maquettehandi.identification;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.sikanla.maquettehandi.R;
import com.example.sikanla.maquettehandi.network.AllRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Cecile on 02/05/2017.
 */

public class LoginActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private String email, password;

    private RequestQueue requestQueue;

    private String serverUrl = "http://10.0.2.2/task_manager/v1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestQueue = Volley.newRequestQueue(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        emailEditText = (EditText) findViewById(R.id.aa);
        passwordEditText = (EditText) findViewById(R.id.pass);
        loginButton = (Button) findViewById(R.id.loginbutton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailEditText.toString();
                password = passwordEditText.toString();
                loginToServer(email, password);

            }
        });

    }

    private void loginToServer(final String email, final String password) {
        //example of AllRequest use
        Map<String,String> parameters = new HashMap<>();
        parameters.put("email", email);
        parameters.put("password", password);
        Thread thread = new AllRequest(this,parameters,"/login");
        thread.start();
    }
}



