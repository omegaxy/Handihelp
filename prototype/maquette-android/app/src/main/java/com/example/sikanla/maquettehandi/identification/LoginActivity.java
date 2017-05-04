package com.example.sikanla.maquettehandi.identification;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sikanla.maquettehandi.MainActivity;
import com.example.sikanla.maquettehandi.R;
import com.example.sikanla.maquettehandi.network.AllRequest;
import com.example.sikanla.maquettehandi.network.StaticInformations;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Cecile on 02/05/2017.
 */

public class LoginActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private String email, password;
    private  TextView warnTv;

    private AllRequest.CallBackConnector callBackConnector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        emailEditText = (EditText) findViewById(R.id.aa);
        passwordEditText = (EditText) findViewById(R.id.pass);
        warnTv = (TextView) findViewById(R.id.errortv);
        warnTv.setVisibility(View.INVISIBLE);
        loginButton = (Button) findViewById(R.id.loginbutton);
        loginButton.setEnabled(false);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailEditText.getText().toString();
                password = passwordEditText.getText().toString();
                loginToServer(email, password);

            }
        });
        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!isEmailValid(s) || passwordEditText.getText().toString().isEmpty()) {
                    loginButton.setEnabled(false);
                } else {
                    loginButton.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //On user changes the text
                if (!isEmailValid(emailEditText.getText()) || s == "") {
                    loginButton.setEnabled(false);
                } else {
                    loginButton.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });

    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void loginToServer(final String email, final String password) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("email", email);
        parameters.put("password", password);

        Thread thread = new AllRequest(this, parameters, "/login", new AllRequest.CallBackConnector() {
            @Override
            public void CallBackOnConnect(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.get("error").toString() == "false") {
                        warnTv.setVisibility(View.INVISIBLE);
                        StaticInformations.setApikey(jsonObject.getString("apiKey"));
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));

                    }
                    else {
                        warnTv.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
        thread.start();


    }
}



