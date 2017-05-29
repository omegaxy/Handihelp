package com.example.sikanla.maquettehandi.Adapters;

import android.app.Activity;
import android.view.Gravity;
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
        this.context = context;

    }

    private static class ViewHolder {
        TextView messageTextView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Message mess = getItem(position);
        final ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_message, parent, false);
            viewHolder.messageTextView = (TextView) convertView.findViewById(R.id.item_message_tv2);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data from the data object via the viewHolder object
        viewHolder.messageTextView.setText(mess.message);

        if (mess.isMine) {
            viewHolder.messageTextView.setGravity(Gravity.RIGHT);
            viewHolder.messageTextView.setBackgroundColor(context.getResources().getColor(R.color.dd));
        }
        if (!mess.isMine) {
            viewHolder.messageTextView.setGravity(Gravity.LEFT);
            viewHolder.messageTextView.setBackgroundColor(context.getResources().getColor(R.color.oraaange));
        }


        // Return the completed view to render on screen
        return convertView;
    }
}
