package com.example.sikanla.maquettehandi.identification;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sikanla.maquettehandi.MainActivity;
import com.example.sikanla.maquettehandi.R;
import com.example.sikanla.maquettehandi.network.AllRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Nicolas on 06/05/2017.
 */


public class RegisterActivity extends AppCompatActivity {
    private EditText surnameEditText, firstnameEditText, birthYearEditText, mailEditText, passwordEditText1, passwordEditText2, phoneNumberEditText;
    private Button registerButton;
    private TextView text;
    private String surname, firstname, birthYear, mail, password1, password2, phoneNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_screen);

        surnameEditText = (EditText) findViewById(R.id.surname);
        firstnameEditText = (EditText) findViewById(R.id.firstname);
        birthYearEditText = (EditText) findViewById(R.id.birthYear);
        mailEditText = (EditText) findViewById(R.id.mail);
        passwordEditText1 = (EditText) findViewById(R.id.password1);
        passwordEditText2 = (EditText) findViewById(R.id.password2);
        phoneNumberEditText = (EditText) findViewById(R.id.phoneNumber);
        registerButton = (Button) findViewById(R.id.registrationButton);
        text = (TextView) findViewById(R.id.errorMsg);
       // text.setVisibility(View.GONE);

        registerButton.setEnabled(false);

        startTextWatchers();

        registerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //registerButton.setEnabled(false);

                surname = surnameEditText.getText().toString();
                firstname = firstnameEditText.getText().toString();
                birthYear = birthYearEditText.getText().toString();
                mail = mailEditText.getText().toString();
                password1 = passwordEditText1.getText().toString();
                password2 = passwordEditText2.getText().toString();
                phoneNumber = phoneNumberEditText.getText().toString();
                connectToServer(firstname, surname, mail, password1, phoneNumber, birthYear);


            }
        });

    }


    private boolean areInputsValids() {
        if (firstnameEditText.getText().toString().isEmpty() || surnameEditText.getText().toString().isEmpty()) {
            return false;
        } else if (!isEmailValid(mailEditText.getText().toString()) && !mailEditText.getText().toString().isEmpty()) {
            Log.e("mail", mailEditText.getText().toString());
            text.setText("Le champ email ne correspond pas à une adresse mail");
            text.setVisibility(View.VISIBLE);
            return false;
        } else if (!isPasswordValid(passwordEditText1.getText().toString()) && !passwordEditText1.getText().toString().isEmpty()) {
            text.setText("Le mot de passe doit contenir au minimum 6 caractères");
            text.setVisibility(View.VISIBLE);
            return false;
        } else if (!arePasswordSimilar(passwordEditText1.getText().toString(), passwordEditText2.getText().toString())
                && !passwordEditText1.getText().toString().isEmpty()
                && !passwordEditText2.getText().toString().isEmpty()) {
            text.setText("Les deux mot de passe ne sont pas identiques");
            text.setVisibility(View.VISIBLE);
            return false;
        } else if (!birthYearEditText.getText().toString().isEmpty()
                && !isbirthYearValid(Integer.parseInt(birthYearEditText.getText().toString()))
                ) {
            text.setText("Année de naissance comprise entre 1910 et 2017");
            text.setVisibility(View.VISIBLE);
            return false;
        } else if (!isPhoneNumberCorrect(phoneNumberEditText.getText().toString())
                && !phoneNumberEditText.getText().toString().isEmpty()) {
            text.setText("Le numéro doit être rentré sous la forme 0612345678");
            text.setVisibility(View.VISIBLE);
            return false;
        } else if (mailEditText.getText().toString().isEmpty() || passwordEditText1.getText().toString().isEmpty()
                || passwordEditText2.getText().toString().isEmpty() || birthYearEditText.getText().toString().isEmpty()
                || phoneNumberEditText.getText().toString().isEmpty()) {

            text.setText("");
           // text.setVisibility(View.GONE);
            return false;


        }
        text.setText("");
        return true;
    }


    private boolean arePasswordSimilar(String password1, String password2) {
        return password1.equals(password2);
    }

    //year of birth filter
    private boolean isbirthYearValid(int birthYear) {
        return birthYear > 1909 && birthYear < 2018;
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 5;
    }

    //verify phonenumber type
    private boolean isPhoneNumberCorrect(String phoneNumber) {
        Pattern pattern = Pattern.compile("((06)|(07))[0-9]{8}");
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }


    private void connectToServer(String firstname, String surname, String email,
                                 String password, String phoneNumber, String birth_year) {

        Map<String, String> headers = new HashMap<>();
        Map<String, String> parameters = new HashMap<>();
        parameters.put("firstname", firstname);
        parameters.put("surname", surname);
        parameters.put("email", email);
        parameters.put("password", password);
        parameters.put("phone_number", phoneNumber);
        parameters.put("birth_year", birth_year);

        new AllRequest(this, parameters, headers, "/register", AllRequest.POST, new AllRequest.CallBackConnector() {
            @Override
            public void CallBackOnConnect(String response) {
                Log.e("serv", response);
                User user = new User();
                text = (TextView) findViewById(R.id.errorMsg);
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if (response.contains("Sorry, this email already exists")) {

                        text.setText("Email déjà enregistré");

                    } else if (jsonObject.get("error").toString() == "false") {
                        text.setText("Inscription acceptée");
                        user.saveUserOnPhone(getBaseContext(),
                                jsonObject.getString("apiKey"), jsonObject.getString("id"),
                                jsonObject.getString("firstname"), jsonObject.getString("surname"),
                                jsonObject.getInt("birth_year"), jsonObject.getString("email"));
                        user.loadUser(getBaseContext());
                        user.saveAndroidIdtoServer(getBaseContext());
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));

                    } else {
                        text.setText("Inscription refusée");

                        // loginButton.setEnabled(true); //reactivate button
                        // progressBar.setVisibility(View.GONE); //hide loading wheel
                        // warnTv.setVisibility(View.VISIBLE); // show error message

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void startTextWatchers() {
        firstnameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!areInputsValids()) {
                    registerButton.setEnabled(false);
                } else {
                    registerButton.setEnabled(true);
                }
            }
        });
        surnameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!areInputsValids()) {
                    registerButton.setEnabled(false);
                } else {
                    registerButton.setEnabled(true);
                }
            }
        });
        birthYearEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!areInputsValids()) {
                    registerButton.setEnabled(false);
                } else {
                    registerButton.setEnabled(true);
                }
            }
        });
        mailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!areInputsValids()) {
                    registerButton.setEnabled(false);
                } else {
                    registerButton.setEnabled(true);
                }
            }
        });
        passwordEditText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!areInputsValids()) {
                    registerButton.setEnabled(false);
                } else {
                    registerButton.setEnabled(true);
                }
            }
        });
        passwordEditText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!areInputsValids()) {
                    registerButton.setEnabled(false);
                } else {
                    registerButton.setEnabled(true);
                }
            }
        });
        phoneNumberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!areInputsValids()) {
                    registerButton.setEnabled(false);
                } else {
                    registerButton.setEnabled(true);
                }
            }
        });
    }


}

