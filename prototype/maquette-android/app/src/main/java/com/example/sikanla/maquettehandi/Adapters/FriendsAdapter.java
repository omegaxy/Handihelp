package com.example.sikanla.maquettehandi.Adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sikanla.maquettehandi.DialogFragment.ProfileDialogFragment;
import com.example.sikanla.maquettehandi.Model.Contact;
import com.example.sikanla.maquettehandi.R;
import com.example.sikanla.maquettehandi.network.ImageRequester;
import com.example.sikanla.maquettehandi.network.PlannedRequester;
import com.example.sikanla.maquettehandi.network.UserRequester;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Sikanla on 25/05/2017.
 */

public class FriendsAdapter extends ArrayAdapter<String> {
    // View lookup cache
    private Activity context;

    private static class ViewHolder {
        TextView firstname;
        TextView surname;
        ImageView pictureContact;
        LinearLayout linearLayout;
    }

    public FriendsAdapter(Activity context, ArrayList<String> strings) {
        super(context, R.layout.item_contact_messages, strings);
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        String id = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        final ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_contact_messages, parent, false);
            viewHolder.linearLayout = (LinearLayout) convertView.findViewById(R.id.linear_contact_messages);
            viewHolder.firstname = (TextView) convertView.findViewById(R.id.tvItemContactFirstName);
            viewHolder.surname = (TextView) convertView.findViewById(R.id.tvItemContactSurname);
            viewHolder.pictureContact = (ImageView) convertView.findViewById(R.id.pictureContact);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data from the data object via the viewHolder object
        // into the template view.
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileDialogFragment profileDialogFragment = new ProfileDialogFragment();
                Bundle args = new Bundle();
                args.putString("id", getItem(position));
                profileDialogFragment.setArguments(args);
                profileDialogFragment.show(context.getFragmentManager(), "answerPlanned");
            }
        });
        UserRequester  userRequester = new UserRequester();
        userRequester.getUser(context, id, new UserRequester.GetUserCB() {
            @Override
            public void getUser(String firstName, String surname, String age, Boolean success) {
                if (success) {
                    viewHolder.firstname.setText(firstName);
                    viewHolder.surname.setText(surname);

                }
            }
        });

        ImageRequester imageRequester = new ImageRequester();
        imageRequester.getImage(id, context, new ImageRequester.ImageInterface() {
            @Override
            public void getUrl(String url) {
                if (!url.isEmpty())
                    Picasso.with(context).load(url).centerCrop().fit().into(viewHolder.pictureContact);

            }
        });

        // Return the completed view to render on screen
        return convertView;
    }
}
