package com.example.sikanla.maquettehandi.Adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sikanla.maquettehandi.DialogFragment.DisplayResponsesPlanned;
import com.example.sikanla.maquettehandi.Model.PlannedRequest;
import com.example.sikanla.maquettehandi.Model.ResponsePlanned;
import com.example.sikanla.maquettehandi.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Sikanla on 31/05/2017.
 */

public class NotificationAdapter extends ArrayAdapter<PlannedRequest> {
    private static final String TAG = "PlannedRequestCardAdapter";

    private ArrayList<PlannedRequest> localPlannedRequests = new ArrayList<>();
    private ArrayList<ResponsePlanned> responsePlanneds = new ArrayList<>();
    private Activity context;

    static class CardViewHolder {
        TextView localisation;
        TextView aideCategoryTv;
        TextView date;
        LinearLayout linearLayout;
        FrameLayout frameLayout;
    }


    public NotificationAdapter(Activity context, int textViewResourceId) {
        super(context, textViewResourceId);
        this.context = context;
    }


    @Override
    public void add(PlannedRequest object) {
        localPlannedRequests.add(object);
    }

    @Override
    public void clear() {
        if (localPlannedRequests != null) {
            for (int i = 0; i < localPlannedRequests.size(); i++) {
                localPlannedRequests.remove(i);
            }
        }
        if (responsePlanneds != null) {
            for (int i = 0; i < responsePlanneds.size(); i++) {
                responsePlanneds.remove(i);
            }
        }
        super.clear();
    }


    //add only cards with responses
    public void addAll(ArrayList<PlannedRequest> plannedRequests, ArrayList<ResponsePlanned> responsePlanneds) {
        for (int i = 0; i < plannedRequests.size(); i++) {
            for (int j = 0; j < responsePlanneds.size(); j++) {
                if (plannedRequests.get(i).idPlanned.matches(responsePlanneds.get(j).id_request)) {
                    if (!localContainsObject(responsePlanneds.get(j).id_request)) {
                        add(plannedRequests.get(i));
                        break;
                    }
                }

            }

        }
        this.responsePlanneds = responsePlanneds;
    }

    private boolean localContainsObject(String s) {
        for (int i = 0; i < localPlannedRequests.size(); i++) {
            if (localPlannedRequests.get(i).idPlanned.matches(s))
                return true;
        }
        return false;
    }

    @Override
    public int getCount() {
        return this.localPlannedRequests.size();
    }

    @Override
    public PlannedRequest getItem(int index) {
        return this.localPlannedRequests.get(index);
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        final CardViewHolder viewHolder;

        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.notification_item_card, parent, false);
        viewHolder = new CardViewHolder();
        viewHolder.frameLayout = (FrameLayout) row.findViewById(R.id.frame_card_notification);
        viewHolder.localisation = (TextView) row.findViewById(R.id.localisation_item_notification);
        viewHolder.aideCategoryTv = (TextView) row.findViewById(R.id.aide_type_item_notification);
        viewHolder.date = (TextView) row.findViewById(R.id.date_item_notification);
        viewHolder.linearLayout = (LinearLayout) row.findViewById(R.id.item_color_notification);

        viewHolder.frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DisplayResponsesPlanned displayResponsesPlanned = new DisplayResponsesPlanned();
                Bundle args = new Bundle();
                args.putString("id_planned", getItem(position).idPlanned);
                displayResponsesPlanned.setArrays(localPlannedRequests, responsePlanneds);
                displayResponsesPlanned.setArguments(args);
                displayResponsesPlanned.show(context.getFragmentManager(), "answerPlanned");
            }
        });

        final PlannedRequest plannedRequest = getItem(position);
        viewHolder.localisation.setText(plannedRequest.localisation);

        getAideType(viewHolder, plannedRequest.helpCategory);

        String formattedDate = formatDate(plannedRequest.scheduledAt);
        viewHolder.date.setText(formattedDate);

        row.setTag(viewHolder);

        return row;
    }


    private String formatDate(String epoch) {
        long unixSeconds = Long.parseLong(epoch);
        Date date = new Date(unixSeconds * 1000L); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-yyyy à HH:mm"); // the format of your date
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+2")); // give a timezone reference for formating (see comment at the bottom
        return sdf.format(date);
    }

    private void getAideType(CardViewHolder viewHolder, String aideType) {
        switch (aideType) {
            case "1":
                viewHolder.aideCategoryTv.setText(PlannedRequest.n1);
                viewHolder.linearLayout.setBackgroundColor(context.getResources().getColor(R.color.type1));
                break;
            case "2":
                viewHolder.aideCategoryTv.setText(PlannedRequest.n2);
                viewHolder.linearLayout.setBackgroundColor(context.getResources().getColor(R.color.type2));
                break;
            case "3":
                viewHolder.aideCategoryTv.setText(PlannedRequest.n3);
                viewHolder.linearLayout.setBackgroundColor(context.getResources().getColor(R.color.type3));
                break;
            case "4":
                viewHolder.aideCategoryTv.setText(PlannedRequest.n4);
                viewHolder.linearLayout.setBackgroundColor(context.getResources().getColor(R.color.type4));
                break;
            case "5":
                viewHolder.aideCategoryTv.setText(PlannedRequest.n5);
                viewHolder.linearLayout.setBackgroundColor(context.getResources().getColor(R.color.type5));
                break;
            case "6":
                viewHolder.aideCategoryTv.setText(PlannedRequest.n6);
                viewHolder.linearLayout.setBackgroundColor(context.getResources().getColor(R.color.type6));
                break;
            case "7":
                viewHolder.aideCategoryTv.setText(PlannedRequest.n7);
                viewHolder.linearLayout.setBackgroundColor(context.getResources().getColor(R.color.type7));
                break;
            case "8":
                viewHolder.aideCategoryTv.setText(PlannedRequest.n8);
                viewHolder.linearLayout.setBackgroundColor(context.getResources().getColor(R.color.type8));
                break;
            case "9":
                viewHolder.aideCategoryTv.setText(PlannedRequest.n9);
                viewHolder.linearLayout.setBackgroundColor(context.getResources().getColor(R.color.type9));
                break;
            case "10":
                viewHolder.aideCategoryTv.setText(PlannedRequest.n10);
                viewHolder.linearLayout.setBackgroundColor(context.getResources().getColor(R.color.type10));
                break;
            case "11":
                viewHolder.aideCategoryTv.setText(PlannedRequest.n11);
                viewHolder.linearLayout.setBackgroundColor(context.getResources().getColor(R.color.type11));
                break;
        }
    }


}

