package com.example.sikanla.maquettehandi.Adapters;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sikanla.maquettehandi.DialogFragment.ProfileDialogFragment;
import com.example.sikanla.maquettehandi.Model.ResponsePlanned;
import com.example.sikanla.maquettehandi.R;
import com.example.sikanla.maquettehandi.network.ImageRequester;
import com.example.sikanla.maquettehandi.network.PlannedRequester;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Sikanla on 07/06/2017.
 */

public class DisplayResponsesAdapter extends ArrayAdapter<ResponsePlanned> {
    // View lookup cache
    private Activity context;

    private static class ViewHolder {
        TextView firstname;
        TextView surname;
        ImageView pictureContact;
        LinearLayout linearLayout;
    }

    public DisplayResponsesAdapter (Activity context, ArrayList<ResponsePlanned> responsePlanneds) {
        super(context, R.layout.item_response_planned, responsePlanneds);
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ResponsePlanned responsePlanned = getItem(position);

        final ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_response_planned, parent, false);
           // viewHolder.linearLayout = (LinearLayout) convertView.findViewById(R.id.linear_contact_messages);
        //    viewHolder.firstname = (TextView) convertView.findViewById(R.id.tvItemContactFirstName);
          //  viewHolder.surname = (TextView) convertView.findViewById(R.id.tvItemContactSurname);
         //   viewHolder.pictureContact = (ImageView) convertView.findViewById(R.id.pictureContact);

            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }
      
        /*PlannedRequester plannedRequester = new PlannedRequester();
        plannedRequester.getUser(context, responsePlanned.id_helper, new PlannedRequester.GetUserCB() {
            @Override
            public void getUser(String firstName, String surname, String age, Boolean success) {
                if (success) {
                    viewHolder.firstname.setText(firstName);
                    viewHolder.surname.setText(surname);

                }
            }
        });


        ImageRequester imageRequester = new ImageRequester();
        imageRequester.getImage(responsePlanned.id_helper, context, new ImageRequester.ImageInterface() {
            @Override
            public void getUrl(String url) {
                if (!url.isEmpty())
                    Picasso.with(context).load(url).centerCrop().fit().into(viewHolder.pictureContact);

            }
        });
        */

        // Return the completed view to render on screen
        return convertView;
    }
}
