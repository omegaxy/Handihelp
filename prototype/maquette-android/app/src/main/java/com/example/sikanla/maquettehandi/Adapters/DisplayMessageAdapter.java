package com.example.sikanla.maquettehandi.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sikanla.maquettehandi.Model.Message;
import com.example.sikanla.maquettehandi.R;
import com.example.sikanla.maquettehandi.network.ImageRequester;

import java.util.ArrayList;

/**
 * Created by Sikanla on 26/05/2017.
 */

public class DisplayMessageAdapter extends ArrayAdapter<Message> {

    private Activity context;

    public DisplayMessageAdapter(Activity context, ArrayList<Message> arrayList) {
        super(context, R.layout.item_message, arrayList);

    }

    private static class ViewHolder {
        TextView firstname;
        ImageView pictureContact;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Message mess = getItem(position);
        final ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_message, parent, false);
            viewHolder.firstname = (TextView) convertView.findViewById(R.id.item_message_tv2);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data from the data object via the viewHolder object
        if (mess != null)
            viewHolder.firstname.setText(mess.message);

        ImageRequester imageRequester = new ImageRequester();
        /*imageRequester.getImage(contact.id, context, new ImageRequester.ImageInterface() {
            @Override
            public void getUrl(String url) {
                if (!url.isEmpty())
                    Picasso.with(context).load(url).into(viewHolder.pictureContact);

            }
        });
        */

        // Return the completed view to render on screen
        return convertView;
    }
}
