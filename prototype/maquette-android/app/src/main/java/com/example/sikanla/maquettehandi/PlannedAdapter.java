package com.example.sikanla.maquettehandi;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sikanla.maquettehandi.Model.PlannedRequest;
import com.example.sikanla.maquettehandi.network.ImageRequester;

import java.util.ArrayList;

/**
 * Created by Sikanla on 09/05/2017.
 */

public class PlannedAdapter extends ArrayAdapter<PlannedRequest> {
    // View lookup cache
    private static class ViewHolder {
        TextView description;
        TextView aideCategoryTv;
        ImageView imageView;
    }

public PlannedAdapter(Context context, ArrayList<PlannedRequest> plannedRequests) {
        super(context, R.layout.item_list_view_planned, plannedRequests);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        PlannedRequest plannedRequest = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_list_view_planned, parent, false);
            viewHolder.description = (TextView) convertView.findViewById(R.id.name_item);
            viewHolder.aideCategoryTv = (TextView) convertView.findViewById(R.id.aide_category_item);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.image_view_item);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data from the data object via the viewHolder object
        // into the template view.
        viewHolder.description.setText(plannedRequest.description);
        //viewHolder.aideCategoryTv.setText(plannedRequest.helpCategory);

        // Return the completed view to render on screen
        return convertView;
    }
}