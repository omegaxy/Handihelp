package com.example.sikanla.maquettehandi.identification;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    private Button registrationButton;
    private TextView text;
    private String surname, firstname, birthYear, mail, password1, password2, phoneNumber;
    private boolean registerOk = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        User user = new User();

        setContentView(R.layout.registration_screen);

        surnameEditText = (EditText) findViewById(R.id.surname);

        firstnameEditText = (EditText) findViewById(R.id.firstname);

        birthYearEditText = (EditText) findViewById(R.id.birthYear);

        mailEditText = (EditText) findViewById(R.id.mail);

        passwordEditText1 = (EditText) findViewById(R.id.password1);

        passwordEditText2 = (EditText) findViewById(R.id.password2);

        phoneNumberEditText = (EditText) findViewById(R.id.phoneNumber);

        registrationButton = (Button) findViewById(R.id.registrationButton);

        text = (TextView) findViewById(R.id.errorMsg);


        registrationButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //registrationButton.setEnabled(false);

                surname = surnameEditText.getText().toString();
                firstname = firstnameEditText.getText().toString();
                birthYear = birthYearEditText.getText().toString();
                mail = mailEditText.getText().toString();
                password1 = passwordEditText1.getText().toString();
                password2 = passwordEditText2.getText().toString();
                phoneNumber = phoneNumberEditText.getText().toString();


                if (surname.equals("") || firstname.equals("") || birthYear.equals("") || mail.equals("") || password1.equals("") || password2.equals("") || phoneNumber.equals("")) {
                    text.setText("Un des champs à saisir est vide");
                    return;
                }

                if(!(isInteger(birthYear))){
                    text.setText("Votre année de naissance doit être un nombre entier");
                    return;
                }

                if(!(isbirthYearValid(Integer.parseInt(birthYear)))){
                    text.setText("Votre année de naissance doit être compris entre 1850 et 2100");
                    return;
                }

                if(!(isEmailFormatValid(mail))){
                    text.setText("Le champ email ne correspond pas à une adresse mail");
                    return;
                }

                if(!(isPasswordValid(password1))){
                    text.setText("Le 1er champ mot de passe doit contenir au minimum 6 caractères");
                    return;
                }

                if(!(password1.equals(password2))){
                    text.setText("Les deux mot de passe ne sont pas identiques");
                    return;
                }

                if(!(isPhoneNumberCorrect(phoneNumber))){
                    text.setText("Le numéro doit être rentrer sous la forme 0612345678");
                    return;
                }

                connectToServer(firstname, surname, mail, password1, phoneNumber, birthYear);
                if(registerOk){
                    //text.setText("true");
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                }

            }
        });

    }

    //year of birth filter
    private boolean isbirthYearValid(int birthYear){
        if(birthYear<1850 || birthYear > 2100){
            return false;
        }
        return true;
    }

    //verify year of birth type
    static boolean isInteger(String s) {
        try{ Integer.parseInt(s); }
        catch(NumberFormatException nfe){ return false; }
        return true;
    }


    //verify identic mails
    boolean isEmailFormatValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordValid(String password)
    {
        return password.length() > 5;
    }

    //verify phonenumber type
    private boolean isPhoneNumberCorrect(String phoneNumber) {

        Pattern pattern = Pattern.compile("((06)|(07))[0-9]{8}");
        Matcher matcher = pattern.matcher(phoneNumber);
        //"(0|(\\+33)|(0033))[1-9][0-9]{8}"

        if (matcher.matches())
            return true;

        return false;
    }

    //envoyer les données au serveur
    //vérifier que le mail donné n'est pas déjà pris
    //bouton s'inscrire -> redirection vers questionnaire ?


    //verify mail not already taken
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
                        //todo manage error message because email already exists
                        text.setText("mail déjà enregistré");
                        registerOk = false;

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
                        // todo display error because register failed
                        registerOk = false;
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


}

