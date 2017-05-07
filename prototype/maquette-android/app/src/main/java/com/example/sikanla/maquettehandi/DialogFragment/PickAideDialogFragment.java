package com.example.sikanla.maquettehandi.DialogFragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sikanla.maquettehandi.R;

/**
 * Created by Sikanla on 06/05/2017.
 */

public class PickAideDialogFragment extends DialogFragment {
    private View rootView;
    private TextView fistNameTv;
    private TextView ageTv;

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        rootView = inflater.inflate(R.layout.pick_aide_dialog, null);
       // fistNameTv = (TextView) rootView.findViewById(R.id.firstnamedialog);
        //ageTv = (TextView) rootView.findViewById(R.id.agedialog);

        //fistNameTv.setText(getArguments().getString("firstname"));
        //ageTv.setText((String.valueOf(getArguments().getInt("birth_year"))));

        final AlertDialog.Builder builder1 = builder.setView(rootView);
                //.setPositiveButton("Do something", new DialogInterface.OnClickListener() {
                  //  public void onClick(DialogInterface dialog, int id) {
                   // }
                //});

        return builder1.create();


    }
}
