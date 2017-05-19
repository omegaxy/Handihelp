package com.example.sikanla.maquettehandi.DialogFragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sikanla.maquettehandi.R;
import com.example.sikanla.maquettehandi.network.ImageRequester;
import com.example.sikanla.maquettehandi.network.MessageRequester;

/**
 * Created by Sikanla on 20/05/2017.
 */

public class SendMessageDialog extends DialogFragment {
    private TextView title;
    private EditText messageText;
    private TextView errorTv;
    private View rootView;
    String id_receiver;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        rootView = inflater.inflate(R.layout.send_message_dialog, null);
        title = (TextView) rootView.findViewById(R.id.df_message_title);
        errorTv = (TextView) rootView.findViewById(R.id.df_erreur_tv);
        messageText = (EditText) rootView.findViewById(R.id.df_message_edit_text);
        String s = "Envoyer à: " + getArguments().getString("firstname");
        id_receiver = getArguments().getString("id");


        title.setText(s);


        final AlertDialog.Builder builder1 = builder.setView(rootView)
                .setNeutralButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getDialog().dismiss();
                    }
                })
                .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        return builder1.create();


    }


    @Override
    public void onStart()
    {
        super.onStart();
        AlertDialog d = (AlertDialog)getDialog();
        if(d != null)
        {
            Button positiveButton = (Button) d.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    MessageRequester messageRequester = new MessageRequester();
                    messageRequester.sendMessage(getActivity(), messageText.getText().toString(),
                            id_receiver, new MessageRequester.SendMessagesCB() {
                                @Override
                                public void onMessageReceived(Boolean success) {
                                    if (success) {
                                        Toast.makeText(getActivity(), "Message Envoyé", Toast.LENGTH_LONG).show();
                                        getDialog().dismiss();
                                    } else {
                                        errorTv.setVisibility(View.VISIBLE);

                                    }


                                }
                            });

                }
            });
        }
    }
}
