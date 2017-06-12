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

import com.example.sikanla.maquettehandi.DialogFragment.DisplayPlannedDF;
import com.example.sikanla.maquettehandi.Model.PlannedRequest;
import com.example.sikanla.maquettehandi.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Sikanla on 16/05/2017.
 */

public class PlannedRequestCardAdapter extends ArrayAdapter<PlannedRequest> {
    private static final String TAG = "PlannedRequestCardAdapter";
    private List<PlannedRequest> plannedRequests = new ArrayList<>();
    private Activity context;
    private boolean tomark = false;

    static class CardViewHolder {
        TextView localisation;
        TextView aideCategoryTv;
        TextView date;
        LinearLayout linearLayout;
        FrameLayout frameLayout;
    }


    public PlannedRequestCardAdapter(Activity context, int textViewResourceId) {
        super(context, textViewResourceId);
        this.context = context;
    }

    public PlannedRequestCardAdapter(Activity context, int textViewResourceId, boolean b) {
        super(context, textViewResourceId);
        this.context = context;
        this.tomark = b;
    }

    @Override
    public void add(PlannedRequest object) {
        plannedRequests.add(object);
        super.add(object);
    }

    public void addAll(ArrayList<PlannedRequest> plannedRequests) {
        this.plannedRequests = plannedRequests;
    }

    @Override
    public int getCount() {
        return this.plannedRequests.size();
    }

    @Override
    public PlannedRequest getItem(int index) {
        return this.plannedRequests.get(index);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        CardViewHolder viewHolder;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.item_planned_card, parent, false);
            viewHolder = new CardViewHolder();
            viewHolder.frameLayout = (FrameLayout) row.findViewById(R.id.frame_card);
            viewHolder.localisation = (TextView) row.findViewById(R.id.localisation_item);
            viewHolder.aideCategoryTv = (TextView) row.findViewById(R.id.aide_type_item);
            viewHolder.date = (TextView) row.findViewById(R.id.date_item);
            viewHolder.linearLayout = (LinearLayout) row.findViewById(R.id.item_color);
            row.setTag(viewHolder);
        } else {
            viewHolder = (CardViewHolder) row.getTag();
        }
        final PlannedRequest plannedRequest = getItem(position);
        viewHolder.localisation.setText(plannedRequest.localisation);

        getAideType(viewHolder, plannedRequest.helpCategory);

        String formattedDate = formatDate(plannedRequest.scheduledAt);
        viewHolder.date.setText(formattedDate);


        viewHolder.frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DisplayPlannedDF answerPlan = new DisplayPlannedDF();
                Bundle args = new Bundle();
                args.putString("type", plannedRequest.helpCategory);
                args.putString("localisation", plannedRequest.localisation);
                args.putString("scheduled", plannedRequest.scheduledAt);
                args.putString("id", plannedRequest.id);
                args.putString("idPlanned",plannedRequest.idPlanned);
                args.putString("description", plannedRequest.description);
                args.putString("tomark", String.valueOf(tomark));

                answerPlan.setArguments(args);

                answerPlan.show(context.getFragmentManager(), "answerPlanned");
            }
        });
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
