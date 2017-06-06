package com.example.sikanla.maquettehandi.DialogFragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sikanla.maquettehandi.MainActivity;
import com.example.sikanla.maquettehandi.Model.PlannedRequest;
import com.example.sikanla.maquettehandi.R;
import com.example.sikanla.maquettehandi.UI.Activities.FormInstantRequestActi;
import com.example.sikanla.maquettehandi.UI.Activities.FormPlannedRequestActi;
import com.example.sikanla.maquettehandi.network.MessageRequester;
import com.example.sikanla.maquettehandi.network.PlannedRequester;

/**
 * Created by Cecile on 05/06/2017.
 */

public class HelpChoice_DF extends DialogFragment {
    private View rootView;
    private TextView textView;
    private Button buttonInstant, buttonPlanned;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AnswerTheme2);
        rootView = inflater.inflate(R.layout.help_choice, null);
        buttonInstant = (Button) rootView.findViewById(R.id.help_choice_button_instant);
        buttonPlanned = (Button) rootView.findViewById(R.id.help_choice_button_planned);


        buttonInstant.setOnClickListener(new View.OnClickListener() {
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
        builder.setView(rootView);


        return builder.create();

    }


}
