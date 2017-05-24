package com.example.sikanla.maquettehandi.Adapters;

/**
 * Created by Sikanla on 15/05/2017.
 */


import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sikanla.maquettehandi.Model.Contact;
import com.example.sikanla.maquettehandi.Model.PlannedRequest;
import com.example.sikanla.maquettehandi.R;
import com.example.sikanla.maquettehandi.network.ImageRequester;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Sikanla on 09/05/2017.
 */

public class ContactListAdapter extends ArrayAdapter<Contact> {
    // View lookup cache
    private Context context;

    private static class ViewHolder {
        TextView firstname;
        TextView surname;
        ImageView pictureContact;
    }

    public ContactListAdapter(Context context, ArrayList<Contact> contacts) {
        super(context, R.layout.item_contact_messages, contacts);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Contact contact = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        final ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_contact_messages, parent, false);
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
        viewHolder.firstname.setText(contact.firstname);
        viewHolder.surname.setText(contact.surname);
        ImageRequester imageRequester = new ImageRequester();
        imageRequester.getImage(contact.id, context, new ImageRequester.ImageInterface() {
            @Override
            public void getUrl(String url) {
                if (!url.isEmpty())
                    Picasso.with(context).load(url).into(viewHolder.pictureContact);

            }
        });

        // Return the completed view to render on screen
        return convertView;
    }
}