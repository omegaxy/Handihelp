package com.example.sikanla.maquettehandi.DialogFragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sikanla.maquettehandi.R;
import com.example.sikanla.maquettehandi.network.PlannedRequester;

/**
 * Created by Sikanla on 28/05/2017.
 */

public class AnswerPLannedDF extends DialogFragment {
    private View rootView;
    private TextView textView;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.AnswerTheme2);
        rootView = inflater.inflate(R.layout.df_answer_planned, null);
        textView = (TextView) rootView.findViewById(R.id.df_answerTVerror);

        final AlertDialog.Builder builder1 = builder.setView(rootView)
                .setMessage("Proposer votre aide?")
                .setNeutralButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getDialog().dismiss();
                    }
                })
                .setPositiveButton("Proposer", new DialogInterface.OnClickListener() {
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
                    PlannedRequester plannedRequester = new PlannedRequester();
                    plannedRequester.answerPlanned(getActivity(), " ",
                            getArguments().getString("id"), getArguments().getString("idPlanned"), new PlannedRequester.PostPlannedCB() {
                                @Override
                                public void onPlannedPosted(Boolean success) {
                                    if (success) {
                                        dismiss();
                                    } else {
                                        textView.setText("Erreur, veuillez réessayer");
                                        textView.setVisibility(View.VISIBLE);

                                    }
                                }
                            });

                }
            });
        }
    }
}
