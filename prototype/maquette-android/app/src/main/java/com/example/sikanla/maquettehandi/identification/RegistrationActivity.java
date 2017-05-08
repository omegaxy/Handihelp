package com.example.sikanla.maquettehandi.identification;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class RegistrationActivity extends AppCompatActivity {
    private EditText surnameEditText, firstnameEditText, ageEditText, mailEditText, passwordEditText1, passwordEditText2, phoneNumberEditText;
    private Button registrationButton;
    private String surname, firstname, age, mail, password1, password2, phoneNumber;
    private TextView errorMsg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        User user = new User();

        setContentView(R.layout.registration_screen);

        surnameEditText = (EditText) findViewById(R.id.surname);

        firstnameEditText = (EditText) findViewById(R.id.firstname);

        ageEditText = (EditText) findViewById(R.id.age);

        mailEditText = (EditText) findViewById(R.id.mail);

        passwordEditText1 = (EditText) findViewById(R.id.password1);

        passwordEditText2 = (EditText) findViewById(R.id.password2);

        phoneNumberEditText = (EditText) findViewById(R.id.phoneNumber);


        registrationButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                surname = surnameEditText.getText().toString();
                firstname = firstnameEditText.getText().toString();
                age = ageEditText.getText().toString();
                mail = mailEditText.getText().toString();
                password1 = passwordEditText1.getText().toString();
                password2 = passwordEditText2.getText().toString();
                phoneNumber = phoneNumberEditText.getText().toString();
                errorMsg = (TextView) findViewById(R.id.errorMsg);

                errorMsg.setVisibility(View.INVISIBLE);
                registrationButton = (Button) findViewById(R.id.registrationButton);
                registrationButton.setEnabled(false);


                if (surname.equals("") || firstname.equals("") || age.equals("") || mail.equals("") || password1.equals("") || password2.equals("") || phoneNumber.equals("")) {
                    Toast.makeText(RegistrationActivity.this, "Un des champs à saisir est vide, veuillez complétez les informations manquantes", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!(isInteger(age))){
                    Toast.makeText(RegistrationActivity.this, "Veuillez rentrer dans le champ âge un nombre entier", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!(isAgeValid(Integer.parseInt(age)))){
                    Toast.makeText(RegistrationActivity.this, "Votre âge doit être compris entre 0 et 150", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!(isEmailFormatValid(mail))){
                    Toast.makeText(RegistrationActivity.this, "Le champ email ne correspond pas à une adresse mail", Toast.LENGTH_SHORT).show();
                    return;
                }


                if(!(isPasswordValid(password1))){
                    Toast.makeText(RegistrationActivity.this, "Le 1er champ mot de passe doit contenir au minimum 6 caractères", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!(isPasswordValid(password2))){
                    Toast.makeText(RegistrationActivity.this, "Le 2eme champ mot de passe doit contenir au minimum 6 caractères", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!(password1.equals(password2))){
                    Toast.makeText(RegistrationActivity.this, "Les deux mot de passe ne sont pas identiques", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!(isPhoneNumberCorrect(phoneNumber))){
                    Toast.makeText(RegistrationActivity.this, "Le numéro doit être rentrer sous la forme 0612345678", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);

                startActivity(intent);
            }
        });

    }


    //age filter
    public boolean isAgeValid(int age){
        if(age<0 || age > 150){
            return false;
        }
        return true;
    }

    //verif age type
    private static boolean isInteger(String s) {
        try{ Integer.parseInt(s); }
        catch(NumberFormatException nfe){ return false; }
        return true;
    }

    //verify identic mails
    boolean isEmailFormatValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    boolean isPasswordValid(String password)
    {
        if(password1.length() < 6)
            return false;

        return true;
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
    private void connectToServer(String email) {

        Map<String, String> headers = new HashMap<>();
        Map<String, String> parameters = new HashMap<>();
        parameters.put("email", email);




    }
}
