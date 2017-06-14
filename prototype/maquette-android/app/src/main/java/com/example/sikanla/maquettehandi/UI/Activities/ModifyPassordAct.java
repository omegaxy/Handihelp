package com.example.sikanla.maquettehandi.UI.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.sikanla.maquettehandi.Model.User;
import com.example.sikanla.maquettehandi.R;
import com.example.sikanla.maquettehandi.network.UserRequester;

/**
 * Created by Sikanla on 08/06/2017.
 */

public class ModifyPassordAct extends AppCompatActivity {

    private EditText oldPass, newPass1, newPAss2;
    private TextView textViewError;

    private Button buttonValidate;
    private ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInst) {
        super.onCreate(savedInst);
        setContentView(R.layout.activ_modify_password_);

        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(getResources().getColor(R.color.back1));

        oldPass = (EditText) findViewById(R.id.password_oldpass);
        newPass1 = (EditText) findViewById(R.id.password_new1);
        newPAss2 = (EditText) findViewById(R.id.password_new2);

        buttonValidate = (Button) findViewById(R.id.passord_validate_bt);
        textViewError = (TextView) findViewById(R.id.password_error_tv);
        progressBar = (ProgressBar) findViewById(R.id.password_progressbar);

        buttonValidate.setEnabled(false);
        buttonValidate.setAlpha(.5f);
        buttonValidate.setBackgroundColor(getResources().getColor(R.color.greyy));

        buttonValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                sendToServer(newPAss2.getText().toString());

            }
        });

        setUpTextWatchers();


    }


    private void sendToServer(String s) {
        SharedPreferences prefs = getSharedPreferences(User.MY_PREFS_NAME, MODE_PRIVATE);

        String password = prefs.getString("password", "");
        if (oldPass.getText().toString().matches(password)) {
            UserRequester userRequester = new UserRequester();
            userRequester.modifyPassword(getApplicationContext(), s, new UserRequester.UserRequestCB() {
                @Override
                public void onRequest(Boolean success) {
                    if (success) {
                        finish();
                    } else {
                        progressBar.setVisibility(View.INVISIBLE);
                        textViewError.setVisibility(View.VISIBLE);
                        textViewError.setText("Erreur, veuillez réessayer");
                    }
                }
            });

        } else {
            progressBar.setVisibility(View.INVISIBLE);
            textViewError.setVisibility(View.VISIBLE);
            textViewError.setText("Erreur, veuillez réessayer");
        }


    }

    private void setUpTextWatchers() {
        newPass1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textViewError.setVisibility(View.INVISIBLE);
                if (areConditionsValid()) {
                    buttonValidate.setEnabled(true);
                    buttonValidate.setAlpha(1f);
                } else {
                    buttonValidate.setEnabled(false);
                    buttonValidate.setAlpha(0.5f);

                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        newPAss2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textViewError.setVisibility(View.INVISIBLE);

                if (areConditionsValid()) {
                    buttonValidate.setEnabled(true);
                    buttonValidate.setAlpha(1f);
                } else {
                    buttonValidate.setEnabled(false);
                    buttonValidate.setAlpha(0.5f);

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        oldPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textViewError.setVisibility(View.INVISIBLE);

                if (areConditionsValid()) {
                    buttonValidate.setEnabled(true);
                    buttonValidate.setAlpha(1f);
                } else {
                    buttonValidate.setEnabled(false);
                    buttonValidate.setAlpha(0.5f);

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private boolean areConditionsValid() {
        return !oldPass.getText().toString().matches("")
                && !newPass1.getText().toString().matches("")
                && newPAss2.getText().toString().matches(newPass1.getText().toString())
                && newPAss2.getText().toString().length() > 5;
    }


}
