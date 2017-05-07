package com.example.sikanla.maquettehandi.identification;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sikanla.maquettehandi.R;


/**
 * Created by Nicolas on 06/05/2017.
 */

public class RegistrationActivity extends AppCompatActivity {
    private EditText surnameEditText, firstnameEditText, ageEditText, mailEditText1, mailEditText2, passwordEditText1, passwordEditText2, phoneNumberEditText;
    private Button inscriptionButton;
    private String surname, firstname, age, mail1, mail2, password1, password2, phoneNumber;
    private TextView errorMsg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        User user = new User();

        setContentView(R.layout.registration_screen);

        surnameEditText = (EditText) findViewById(R.id.surname);
        surname = surnameEditText.getText().toString();

        firstnameEditText = (EditText) findViewById(R.id.firstname);
        firstname = firstnameEditText.getText().toString();

        ageEditText = (EditText) findViewById(R.id.age);
        age = ageEditText.getText().toString();

        mailEditText1 = (EditText) findViewById(R.id.mail1);
        mail1 = mailEditText1.getText().toString();

        mailEditText2 = (EditText) findViewById(R.id.mail2);
        mail2 = mailEditText2.getText().toString();

        passwordEditText1 = (EditText) findViewById(R.id.password1);
        password1 = passwordEditText1.getText().toString();

        passwordEditText2 = (EditText) findViewById(R.id.password2);
        password2 = passwordEditText2.getText().toString();

        phoneNumberEditText = (EditText) findViewById(R.id.phoneNumber);
        phoneNumber = phoneNumberEditText.getText().toString();

        errorMsg = (TextView) findViewById(R.id.errorMsg);
        errorMsg.setVisibility(View.INVISIBLE);
        inscriptionButton = (Button) findViewById(R.id.inscriptionButton);
        inscriptionButton.setEnabled(false);
    }

    //vérification mail type + mail identique
    //vérification password + password identique + refuser pass facile + nombre char minimum
    //verfi age type 
    //verif phonenumber type

    //envoyer les données au serveur

    //s'inscrire -> redirection vers questionnaire ?

}
