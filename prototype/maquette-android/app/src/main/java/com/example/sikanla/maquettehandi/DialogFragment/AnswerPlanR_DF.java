package com.example.sikanla.maquettehandi.DialogFragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sikanla.maquettehandi.Model.PlannedRequest;
import com.example.sikanla.maquettehandi.R;
import com.example.sikanla.maquettehandi.network.ImageRequester;
import com.example.sikanla.maquettehandi.network.PlannedRequester;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Sikanla on 18/05/2017.
 */

public class AnswerPlanR_DF extends DialogFragment {
    private View rootView;
    private TextView fistNameTv, surnameTv, typeAideTv, localisationTv, descriptionTv, scheduledTv;
    private ImageView imageViewPP;
    private LinearLayout linearLayout;
    private String id;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        rootView = inflater.inflate(R.layout.answer_planned_request, null);
        linearLayout = (LinearLayout) rootView.findViewById(R.id.planned_color_answer);
        fistNameTv = (TextView) rootView.findViewById(R.id.answ_firstname);
        surnameTv = (TextView) rootView.findViewById(R.id.answ_surname);
        typeAideTv = (TextView) rootView.findViewById(R.id.answ_type);
        localisationTv = (TextView) rootView.findViewById(R.id.answ_localisation);
        descriptionTv = (TextView) rootView.findViewById(R.id.answ_description);
        scheduledTv = (TextView) rootView.findViewById(R.id.answ_scheduled);
        imageViewPP = (ImageView) rootView.findViewById(R.id.answ_pic);
        id = getArguments().getString("id");

        imageViewPP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileDialogFragment profileDialogFragment = new ProfileDialogFragment();
                Bundle args = new Bundle();
                args.putString("id", id);
                profileDialogFragment.setArguments(args);
                profileDialogFragment.show(getActivity().getFragmentManager(), "answerPlanned");

            }
        });

        getAideType(getArguments().getString("type"));

        localisationTv.setText(getArguments().getString("localisation"));
        descriptionTv.setText(getArguments().getString("description"));
        String formattedDate = formatDate(getArguments().getString("scheduled"));
        scheduledTv.setText(formattedDate);

        loadUserProfile();


        final AlertDialog.Builder builder1 = builder.setView(rootView)
                .setNeutralButton("Envoyer un message", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SendMessageDialog sendMessageDialog = new SendMessageDialog();
                        Bundle args = new Bundle();
                        args.putString("firstname", fistNameTv.getText().toString());
                        args.putString("id", id);
                        sendMessageDialog.setArguments(args);
                        sendMessageDialog.show(getActivity().getFragmentManager(), "answerPlanned");

                    }
                })
                .setPositiveButton("Aider!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        return builder1.create();


    }

    private void loadUserProfile() {
        ImageRequester imageRequest = new ImageRequester();

        imageRequest.getImage(id, getActivity(), new ImageRequester.ImageInterface() {
            @Override
            public void getUrl(String s) {
                Picasso.with(getActivity()).load(s).into(imageViewPP);
            }
        });
        PlannedRequester plannedRequester = new PlannedRequester();
        plannedRequester.getUser(getActivity(), id, new PlannedRequester.GetUserCB() {
            @Override
            public void getUser(String firstName, String surname, String age, Boolean success) {
                if (success) {

                    fistNameTv.setText(firstName);
                    surnameTv.setText(surname);
                }
            }
        });
    }

    private String formatDate(String epoch) {
        long unixSeconds = Long.parseLong(epoch);
        Date date = new Date(unixSeconds * 1000L); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-yyyy à HH:mm"); // the format of your date
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+2")); // give a timezone reference for formating (see comment at the bottom
        return sdf.format(date);
    }

    private void getAideType(String aideType) {
        switch (aideType) {
            case "1":
                typeAideTv.setText(PlannedRequest.n1);
                linearLayout.setBackgroundColor(getActivity().getResources().getColor(R.color.type1));
                break;
            case "2":
                typeAideTv.setText(PlannedRequest.n2);
                linearLayout.setBackgroundColor(getActivity().getResources().getColor(R.color.type2));
                break;
            case "3":
                typeAideTv.setText(PlannedRequest.n3);
                linearLayout.setBackgroundColor(getActivity().getResources().getColor(R.color.type3));
                break;
            case "4":
                typeAideTv.setText(PlannedRequest.n4);
                linearLayout.setBackgroundColor(getActivity().getResources().getColor(R.color.type4));
                break;
            case "5":
                typeAideTv.setText(PlannedRequest.n5);
                linearLayout.setBackgroundColor(getActivity().getResources().getColor(R.color.type5));
                break;
            case "6":
                typeAideTv.setText(PlannedRequest.n6);
                linearLayout.setBackgroundColor(getActivity().getResources().getColor(R.color.type6));
                break;
            case "7":
                typeAideTv.setText(PlannedRequest.n7);
                linearLayout.setBackgroundColor(getActivity().getResources().getColor(R.color.type7));
                break;
            case "8":
                typeAideTv.setText(PlannedRequest.n8);
                linearLayout.setBackgroundColor(getActivity().getResources().getColor(R.color.type8));
                break;
            case "9":
                typeAideTv.setText(PlannedRequest.n9);
                linearLayout.setBackgroundColor(getActivity().getResources().getColor(R.color.type9));
                break;
            case "10":
                typeAideTv.setText(PlannedRequest.n10);
                linearLayout.setBackgroundColor(getActivity().getResources().getColor(R.color.type10));
                break;
            case "11":
                typeAideTv.setText(PlannedRequest.n11);
                linearLayout.setBackgroundColor(getActivity().getResources().getColor(R.color.type11));
                break;
        }
    }
}
