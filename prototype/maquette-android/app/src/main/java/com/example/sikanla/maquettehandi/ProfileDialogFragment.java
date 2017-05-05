package com.example.sikanla.maquettehandi;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class ProfileDialogFragment extends DialogFragment {

    private View rootView;
    private TextView fistNameTv;
    private TextView ageTv;


    public interface DialogListener {
        void onDialogClick(DialogFragment dialog, int id);
    }

    DialogListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            //     mListener = (DialogListener) ((MainActivity) context).getSupportFragmentManager()
            //            .findFragmentById(R.id.container);
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement DialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        rootView = inflater.inflate(R.layout.fragment_profile, null);
        fistNameTv = (TextView) rootView.findViewById(R.id.firstnamedialog);
        ageTv = (TextView) rootView.findViewById(R.id.agedialog);

        fistNameTv.setText(getArguments().getString("firstname"));
        ageTv.setText((String.valueOf(getArguments().getInt("birth_year"))));

        final AlertDialog.Builder builder1 = builder.setView(rootView)
                .setPositiveButton("Do something", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        return builder1.create();


    }
}
