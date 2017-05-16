package com.example.sikanla.maquettehandi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sikanla.maquettehandi.Model.PlannedRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sikanla on 16/05/2017.
 */

public class PlannedRequestCardAdapter extends ArrayAdapter<PlannedRequest> {
    private static final String TAG = "PlannedRequestCardAdapter";
    private List<PlannedRequest> plannedRequests = new ArrayList<>();

    static class CardViewHolder {
        TextView localisation;
        TextView aideCategoryTv;
        TextView date;
    }


    public PlannedRequestCardAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    @Override
    public void add(PlannedRequest object) {
        plannedRequests.add(object);
        super.add(object);
    }

    public void addAll(ArrayList<PlannedRequest> plannedRequests){
        this.plannedRequests=plannedRequests;
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
            row = inflater.inflate(R.layout.planned_item_card, parent, false);
            viewHolder = new CardViewHolder();
            viewHolder.localisation = (TextView) row.findViewById(R.id.localisation_item);
            viewHolder.aideCategoryTv = (TextView) row.findViewById(R.id.aide_type_item);
            viewHolder.date = ( TextView) row.findViewById(R.id.date_item);
            row.setTag(viewHolder);
        } else {
            viewHolder = (CardViewHolder)row.getTag();
        }
        PlannedRequest plannedRequest = getItem(position);
        viewHolder.localisation.setText(plannedRequest.localisation);
        viewHolder.aideCategoryTv.setText(plannedRequest.helpCategory);
        viewHolder.date.setText(plannedRequest.scheduledAt);
        return row;
    }


}
