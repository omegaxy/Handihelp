package com.example.sikanla.maquettehandi.DialogFragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sikanla.maquettehandi.R;
import com.example.sikanla.maquettehandi.UI.InstantFragment;

/**
 * Created by Nicolas on 15/05/2017.
 */

public class HelpTypeDiaolgFragment extends DialogFragment {


    private View v;
    private Button button1, button2, button3, button4, button5, button6, button7, button8, button9, button10, button11;
    private int numberButton = 0;


    public interface DialogListener {
        void onDialogClick(DialogFragment dialog, int id);
    }

    public interface EditNameDialogListener {
        void onFinishEditDialog(String inputText);
    }


    DialogListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

/*
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
    */


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        v = inflater.inflate(R.layout.fragment_help_type, null);

        button1 = (Button) v.findViewById(R.id.Button1);
        button2 = (Button) v.findViewById(R.id.Button2);
        button3 = (Button) v.findViewById(R.id.Button3);
        button4 = (Button) v.findViewById(R.id.Button4);
        button5 = (Button) v.findViewById(R.id.Button5);
        button6 = (Button) v.findViewById(R.id.Button6);
        button7 = (Button) v.findViewById(R.id.Button7);
        button8 = (Button) v.findViewById(R.id.Button8);
        button9 = (Button) v.findViewById(R.id.Button9);
        button10 = (Button) v.findViewById(R.id.Button10);
        button11 = (Button) v.findViewById(R.id.Button11);


        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditNameDialogListener listener = (EditNameDialogListener) getActivity();
                listener.onFinishEditDialog("123");
                dismiss();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                numberButton = 2;
                dismiss();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                numberButton = 3;
                dismiss();
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                numberButton = 4;
                dismiss();
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                numberButton = 5;
                dismiss();
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                numberButton = 6;
                dismiss();
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                numberButton = 7;
                dismiss();
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                numberButton = 8;
                dismiss();
            }
        });

        button9.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                numberButton = 9;
                dismiss();
            }
        });

        button10.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                numberButton = 10;
                dismiss();
            }
        });
        button11.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                numberButton = 11;
                dismiss();
            }
        });


        final AlertDialog.Builder builder1 = builder.setView(v);
               /* .setPositiveButton("Veuillez sélectionner un type d'aide", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
*/
        return builder1.create();

    }
/*
    private void sendResult(int REQUEST_CODE) {
        Intent intent = new Intent();
        intent.putExtra(EDIT_TEXT_BUNDLE_KEY, str);
        getTargetFragment().onActivityResult(
                getTargetRequestCode(), REQUEST_CODE, intent);
    }
*/


}
