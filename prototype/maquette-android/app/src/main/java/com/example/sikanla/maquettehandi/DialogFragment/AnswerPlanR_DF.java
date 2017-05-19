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

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        rootView = inflater.inflate(R.layout.answer_planned_request, null);

        fistNameTv = (TextView) rootView.findViewById(R.id.answ_firstname);
        surnameTv = (TextView) rootView.findViewById(R.id.answ_surname);
        typeAideTv = (TextView) rootView.findViewById(R.id.answ_type);
        localisationTv = (TextView) rootView.findViewById(R.id.answ_localisation);
        descriptionTv = (TextView) rootView.findViewById(R.id.answ_description);
        scheduledTv = (TextView) rootView.findViewById(R.id.answ_scheduled);
        imageViewPP = (ImageView) rootView.findViewById(R.id.answ_pic);

        typeAideTv.setText(getArguments().getString("type"));
        localisationTv.setText(getArguments().getString("localisation"));
        descriptionTv.setText(getArguments().getString("description"));
        String formattedDate = formatDate(getArguments().getString("scheduled"));

        scheduledTv.setText(formattedDate);

        String id = getArguments().getString("id");
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


        final AlertDialog.Builder builder1 = builder.setView(rootView)
                .setPositiveButton("Do something", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        return builder1.create();


    }

    private String formatDate(String epoch) {
        long unixSeconds = Long.parseLong(epoch);
        Log.e("11", String.valueOf(unixSeconds));
        Date date = new Date(unixSeconds * 1000L); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMMMM-yyyy à HH:mm"); // the format of your date
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+2")); // give a timezone reference for formating (see comment at the bottom
        return sdf.format(date);
    }
}
