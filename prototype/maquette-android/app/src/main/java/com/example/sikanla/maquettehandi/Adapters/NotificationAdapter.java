package com.example.sikanla.maquettehandi.Adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sikanla.maquettehandi.DialogFragment.DisplayPlannedDF;
import com.example.sikanla.maquettehandi.Model.PlannedRequest;
import com.example.sikanla.maquettehandi.Model.ResponsePlanned;
import com.example.sikanla.maquettehandi.R;
import com.example.sikanla.maquettehandi.network.ImageRequester;
import com.example.sikanla.maquettehandi.network.PlannedRequester;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Sikanla on 31/05/2017.
 */

public class NotificationAdapter extends ArrayAdapter<PlannedRequest> {
    private static final String TAG = "PlannedRequestCardAdapter";

    private List<PlannedRequest> plannedRequests = new ArrayList<>();
    private List<ResponsePlanned> responsePlanneds = new ArrayList<>();
    private Activity context;

    static class CardViewHolder {
        TextView localisation;
        TextView aideCategoryTv;
        TextView date;

        ImageView pictureContact;
        TextView firstname;
        TextView surname;

        LinearLayout linearLayout;
        LinearLayout linearLayoutHelpers;
        FrameLayout frameLayout;
        View.OnClickListener onclickListener;

    }


    public NotificationAdapter(Activity context, int textViewResourceId) {
        super(context, textViewResourceId);
        this.context = context;
    }

    @Override
    public void add(PlannedRequest object) {
        plannedRequests.add(object);
        super.add(object);
    }


    //add only cards with responses
    public void addAll(ArrayList<PlannedRequest> plannedRequests, ArrayList<ResponsePlanned> responsePlanneds) {
        for (int i = 0; i < plannedRequests.size(); i++) {
            for (int j = 0; j < responsePlanneds.size(); j++) {
                if (plannedRequests.get(i).idPlanned == responsePlanneds.get(j).id_request) {
                    add(plannedRequests.get(i));
                    break;
                }

            }

        }
        this.responsePlanneds = responsePlanneds;
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
        final CardViewHolder viewHolder;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.notification_item_card, parent, false);
            viewHolder = new CardViewHolder();
            viewHolder.frameLayout = (FrameLayout) row.findViewById(R.id.frame_card_notification);
            viewHolder.localisation = (TextView) row.findViewById(R.id.localisation_item_notification);
            viewHolder.aideCategoryTv = (TextView) row.findViewById(R.id.aide_type_item_notification);
            viewHolder.date = (TextView) row.findViewById(R.id.date_item_notification);
            viewHolder.linearLayout = (LinearLayout) row.findViewById(R.id.item_color_notification);
            viewHolder.linearLayoutHelpers = (LinearLayout) row.findViewById(R.id.list_helpers);

            viewHolder.onclickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("eee", String.valueOf(view.getId()));
                }
            };
            row.setTag(viewHolder);

            if (responsePlanneds != null) {
                for (int i = 0; i < responsePlanneds.size(); i++) {
                    if (responsePlanneds.get(i).id_request == getItem(position).idPlanned) {
                        View line = inflater.inflate(R.layout.notification_helper_row, null);
                        LinearLayout linear = (LinearLayout) line.findViewById(R.id.notification_linear_row);

                        viewHolder.pictureContact = (ImageView) line.findViewById(R.id.notification_row_pictureContact);
                        viewHolder.firstname = (TextView) line.findViewById(R.id.notification_row_tvItemContactFirstName);
                        viewHolder.surname = (TextView) line.findViewById(R.id.notification_row_tvItemContactSurname);
                        
                        Button refuse = new Button(context);
                        refuse.setText("Refuser");
                        Button accpt = new Button(context);
                        accpt.setText("Accepter");
                        accpt.setId(Integer.parseInt(responsePlanneds.get(i).id_helper));
                        accpt.setOnClickListener(viewHolder.onclickListener);

                        PlannedRequester plannedRequester= new PlannedRequester();
                        plannedRequester.getUser(context, responsePlanneds.get(i).id_helper, new PlannedRequester.GetUserCB() {
                            @Override
                            public void getUser(String firstName, String surname, String age, Boolean success) {
                                if (success) {
                                    viewHolder.firstname.setText(firstName);
                                    viewHolder.surname.setText(surname);

                                }
                            }
                        });

                        ImageRequester imageRequester = new ImageRequester();
                        imageRequester.getImage(responsePlanneds.get(i).id_helper, context, new ImageRequester.ImageInterface() {
                            @Override
                            public void getUrl(String url) {
                                Log.e("url", url);
                                if (!url.isEmpty())
                                    Picasso.with(context.getApplicationContext()).load(url).centerCrop().fit().into(viewHolder.pictureContact);

                            }
                        });


                        linear.addView(accpt);
                        linear.addView(refuse);
                        viewHolder.linearLayoutHelpers.addView(line);

                    }

                }
            }


        } else {
            viewHolder = (CardViewHolder) row.getTag();
        }
        final PlannedRequest plannedRequest = getItem(position);
        viewHolder.localisation.setText(plannedRequest.localisation);

        getAideType(viewHolder, plannedRequest.helpCategory);

        String formattedDate = formatDate(plannedRequest.scheduledAt);
        viewHolder.date.setText(formattedDate);

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

