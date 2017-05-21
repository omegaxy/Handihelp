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

import com.example.sikanla.maquettehandi.MainActivity;
import com.example.sikanla.maquettehandi.R;
import com.example.sikanla.maquettehandi.UI.InstantFragment;

/**
 * Created by Nicolas on 15/05/2017.
 */

public class HelpType_DF extends DialogFragment {


    private View v;
    private Button button1, button2, button3, button4, button5, button6, button7, button8, button9, button10, button11, button12;
    private String numberButton = "";


    public interface DialogListener {
        void onDialogClick(String id);
    }

    DialogListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (DialogListener) context;
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
        button12 = (Button) v.findViewById(R.id.button12);


        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                numberButton = "1";
                mListener.onDialogClick(numberButton);
                dismiss();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                numberButton = "2";
                mListener.onDialogClick(numberButton);
                dismiss();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                numberButton = "3";
                mListener.onDialogClick(numberButton);
                dismiss();
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                numberButton = "4";
                mListener.onDialogClick(numberButton);
                dismiss();
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                numberButton = "5";
                mListener.onDialogClick(numberButton);
                dismiss();
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mListener.onDialogClick(numberButton);
                numberButton = "6";
                dismiss();
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                numberButton = "7";
                mListener.onDialogClick(numberButton);
                dismiss();
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                numberButton = "8";
                mListener.onDialogClick(numberButton);
                dismiss();
            }
        });

        button9.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                numberButton = "9";
                mListener.onDialogClick(numberButton);
                dismiss();
            }
        });

        button10.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                numberButton = "10";
                mListener.onDialogClick(numberButton);
                dismiss();
            }
        });
        button11.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                numberButton = "11";
                mListener.onDialogClick(numberButton);
                dismiss();
            }
        });

        button12.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                HelpTypeExplainDialogFragment help = new HelpTypeExplainDialogFragment();
                help.setArguments(args);
                help.show(getActivity().getFragmentManager(), "HelpTypeExplainDialogFragment");
            }
        });


        final AlertDialog.Builder builder1 = builder.setView(v);
        return builder1.create();

    }

}
