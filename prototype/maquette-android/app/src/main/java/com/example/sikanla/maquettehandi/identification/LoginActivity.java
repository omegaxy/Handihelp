package com.example.sikanla.maquettehandi.identification;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.sikanla.maquettehandi.MainActivity;
import com.example.sikanla.maquettehandi.R;
import com.example.sikanla.maquettehandi.network.AllRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Cecile on 02/05/2017.
 */

public class LoginActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private Button loginButton, registerButton;
    private String email, password;
    private TextView warnTv;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        User user = new User();
        //test if user is already logged in
        if (user.isUserLoggedIn(this)) {
            user.loadUser(getApplicationContext());
            user.saveAndroidIdtoServer(getApplicationContext());
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        setContentView(R.layout.login_screen);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        emailEditText = (EditText) findViewById(R.id.aa);
        passwordEditText = (EditText) findViewById(R.id.pass);
        warnTv = (TextView) findViewById(R.id.errortv);
        //warning is invisible at first
        warnTv.setVisibility(View.INVISIBLE);
        loginButton = (Button) findViewById(R.id.loginbutton);
        registerButton =(Button) findViewById(R.id.goregister);
        loginButton.setEnabled(false);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailEditText.getText().toString();
                password = passwordEditText.getText().toString();
                progressBar.setVisibility(View.VISIBLE);
                loginButton.setEnabled(false);

                loginToServer(email, password);

            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));

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
                if (!isEmailValid(emailEditText.getText()) || s.toString().isEmpty()) {
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

    private void loginToServer(String email, String password) {
        Map<String, String> headers = new HashMap<>();
        Map<String, String> parameters = new HashMap<>();
        parameters.put("email", email);
        parameters.put("password", password);

        new AllRequest(this, parameters,headers, "/login", AllRequest.POST, new AllRequest.CallBackConnector() {
            @Override
            public void CallBackOnConnect(String response) {
                User user = new User();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.get("error").toString() == "false") {
                        user.saveUserOnPhone(getBaseContext(),jsonObject.getString("apiKey"), jsonObject.getString("id"),
                                jsonObject.getString("firstname"), jsonObject.getString("surname"),
                                jsonObject.getInt("birth_year"), jsonObject.getString("email"));
                        warnTv.setVisibility(View.INVISIBLE);
                        user.loadUser(getBaseContext());
                        user.saveAndroidIdtoServer(getBaseContext());
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    } else {
                        loginButton.setEnabled(true);
                        progressBar.setVisibility(View.GONE);
                        warnTv.setVisibility(View.VISIBLE);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}




