package com.example.sikanla.maquettehandi.Adapters;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sikanla.maquettehandi.DialogFragment.ProfileDialogFragment;
import com.example.sikanla.maquettehandi.Model.InstantRequest;
import com.example.sikanla.maquettehandi.Model.PlannedRequest;
import com.example.sikanla.maquettehandi.R;
import com.example.sikanla.maquettehandi.network.ImageRequester;
import com.example.sikanla.maquettehandi.network.UserRequester;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Sikanla on 13/06/2017.
 */

public class HelpSomeoneInstantAdapter extends ArrayAdapter<InstantRequest> {
    // View lookup cache
    private Activity context;
    private ArrayList<InstantRequest> instantRequests = new ArrayList<>();


    private static class ViewHolder {
        TextView firstname;
        TextView surname;
        ImageView pictureContact;
        Button halpButton;
    }

    public HelpSomeoneInstantAdapter(Activity context) {
        super(context, R.layout.item_answer_instant);
        this.context = context;
    }

    public void addAll(ArrayList<InstantRequest> instantRequests) {
        this.instantRequests = instantRequests;
        super.addAll(instantRequests);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        InstantRequest id = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        final ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_answer_instant, parent, false);
            viewHolder.firstname = (TextView) convertView.findViewById(R.id.answer_instant_firstname);
            viewHolder.surname = (TextView) convertView.findViewById(R.id.answer_instant_surname);
            viewHolder.pictureContact = (ImageView) convertView.findViewById(R.id.answer_instant_image);
            viewHolder.halpButton = (Button) convertView.findViewById(R.id.answer_instant_helpbutton);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);

            UserRequester  userRequester = new UserRequester();
            userRequester.getUser(context, id.getId(), new UserRequester.GetUserCB() {
                @Override
                public void getUser(String firstName, String surname, String age, Boolean success) {
                    if (success) {
                        viewHolder.firstname.setText(firstName);
                        viewHolder.surname.setText(surname);

                    }
                }
            });

            ImageRequester imageRequester = new ImageRequester();
            imageRequester.getImage(id.getId(), context, new ImageRequester.ImageInterface() {
                @Override
                public void getUrl(String url) {
                    if (!url.isEmpty())
                        Picasso.with(context).load(url).centerCrop().fit().into(viewHolder.pictureContact);

                }
            });
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }
}