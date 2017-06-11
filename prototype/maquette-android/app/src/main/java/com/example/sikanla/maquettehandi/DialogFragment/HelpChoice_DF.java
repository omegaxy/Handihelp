package com.example.sikanla.maquettehandi.DialogFragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.sikanla.maquettehandi.R;
import com.example.sikanla.maquettehandi.UI.Activities.FormInstantRequestActi;
import com.example.sikanla.maquettehandi.UI.Activities.FormPlannedRequestActi;

/**
 * Created by Cecile on 05/06/2017.
 */

public class HelpChoice_DF extends DialogFragment {
    private View rootView;
    private ImageView imageInstant, imageProg;
    private Button buttonInstant, buttonPlanned, bClose;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AnswerTheme2);
        rootView = inflater.inflate(R.layout.df_help_choice, null);
        buttonInstant = (Button) rootView.findViewById(R.id.help_choice_button_instant);
        buttonPlanned = (Button) rootView.findViewById(R.id.help_choice_button_planned);
        imageInstant = (ImageView) rootView.findViewById(R.id.imageInstant);
        imageProg = (ImageView) rootView.findViewById(R.id.imageProg);
        bClose = (Button) rootView.findViewById(R.id.close_btn);

        bClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        buttonInstant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                startActivity(new Intent(getActivity(), FormInstantRequestActi.class));

            }
        });

        imageInstant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                startActivity(new Intent(getActivity(), FormInstantRequestActi.class));

            }
        });

        buttonPlanned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                startActivity(new Intent(getActivity(), FormPlannedRequestActi.class));
            }
        });

        imageProg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                startActivity(new Intent(getActivity(), FormPlannedRequestActi.class));
            }
        });


        builder.setView(rootView);


        return builder.create();

    }


}
