package com.example.sikanla.maquettehandi.DialogFragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sikanla.maquettehandi.R;
import com.example.sikanla.maquettehandi.network.UserRequester;

/**
 * Created by Sikanla on 09/06/2017.
 */

public class DeleteAccountDF extends DialogFragment implements TextWatcher{

    private View rootView;
    private TextView textView;
    private Button cancel, validate;
    private EditText email, password;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)  {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.AnswerTheme2);

        rootView = inflater.inflate(R.layout.delete_account_df, null);
        textView = (TextView) rootView.findViewById(R.id.delete_acct_tverror);
        email = (EditText) rootView.findViewById(R.id.delete_acct_email);
        password = (EditText) rootView.findViewById(R.id.delete_acct_pwd);
        cancel = (Button) rootView.findViewById(R.id.delete_acct_cancel);
        validate = (Button) rootView.findViewById(R.id.delete_acct_validate);
        validate.setEnabled(false);
        validate.setAlpha(0.5f);

        email.addTextChangedListener(this);
        password.addTextChangedListener(this);

        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserRequester userRequester;

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });



        final AlertDialog.Builder builder1 = builder.setView(rootView);

        return builder1.create();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (email.getText().toString().matches("") || password.getText().toString().matches("")){

            validate.setEnabled(false);
            validate.setAlpha(0.5f);
        }else {
            validate.setEnabled(true);
            validate.setAlpha(1f);
        }

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}

