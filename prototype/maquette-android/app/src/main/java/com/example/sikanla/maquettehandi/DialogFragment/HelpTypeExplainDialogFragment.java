package com.example.sikanla.maquettehandi.DialogFragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.example.sikanla.maquettehandi.R;

/**
 * Created by Nicolas on 20/05/2017.
 */

public class HelpTypeExplainDialogFragment extends DialogFragment {

    private View v;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        v = inflater.inflate(R.layout.df_help_type_explain, null);

        final AlertDialog.Builder builder1 = builder.setView(v);

        return builder1.create();
    }

}
