package com.example.sikanla.maquettehandi.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sikanla.maquettehandi.R;

/**
 * Created by Sikanla on 15/06/2017.
 */

public class CommentsAdapter extends ArrayAdapter<String> {

    private Activity context;
    View view1;

    private static class ViewHolder {
        TextView comment;

    }

    public CommentsAdapter(Activity context) {
        super(context, R.layout.item_comments_profile);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final String s = getItem(position);
        final ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_comments_profile, parent, false);
            viewHolder.comment = (TextView) convertView.findViewById(R.id.item_comments);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.comment.setText("\"" + s + "\"");

        return convertView;
    }

}
