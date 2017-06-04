package com.example.sikanla.maquettehandi.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;

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

    private List<PlannedRequest> localPlannedRequests = new ArrayList<>();
    private List<ResponsePlanned> responsePlanneds = new ArrayList<>();
    private Activity context;

    static class CardViewHolder {
        TextView localisation;
        TextView aideCategoryTv;
        TextView date;

        ImageView[] pictureContact;
        TextView[] firstname;
        TextView[] surname;

        LinearLayout linearLayout;
        LinearLayout linearLayoutHelpers;
        FrameLayout frameLayout;
        View.OnClickListener acceptOnclickListener;
        View.OnClickListener refuseOnclickListener;
    }


    public NotificationAdapter(Activity context, int textViewResourceId) {
        super(context, textViewResourceId);
        this.context = context;
    }


    @Override
    public void add(PlannedRequest object) {
        localPlannedRequests.add(object);
    }

    @Override
    public void clear() {
        if (localPlannedRequests != null) {
            for (int i = 0; i < localPlannedRequests.size(); i++) {
                localPlannedRequests.remove(i);
            }
        }
        if (responsePlanneds != null) {
            for (int i = 0; i < responsePlanneds.size(); i++) {
                responsePlanneds.remove(i);
            }
        }
        super.clear();
    }


    //add only cards with responses
    public void addAll(ArrayList<PlannedRequest> plannedRequests, ArrayList<ResponsePlanned> responsePlanneds) {
        for (int i = 0; i < plannedRequests.size(); i++) {
            for (int j = 0; j < responsePlanneds.size(); j++) {
                if (plannedRequests.get(i).idPlanned.matches(responsePlanneds.get(j).id_request)) {
                    if (!localPlannedRequests.contains(plannedRequests.get(i)))
                        add(plannedRequests.get(i));
                    break;
                }

            }

        }
        this.responsePlanneds = responsePlanneds;
    }

    @Override
    public int getCount() {
        return this.localPlannedRequests.size();
    }

    @Override
    public PlannedRequest getItem(int index) {
        return this.localPlannedRequests.get(index);
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        final CardViewHolder viewHolder;

        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.notification_item_card, parent, false);
        viewHolder = new CardViewHolder();
        viewHolder.frameLayout = (FrameLayout) row.findViewById(R.id.frame_card_notification);
        viewHolder.localisation = (TextView) row.findViewById(R.id.localisation_item_notification);
        viewHolder.aideCategoryTv = (TextView) row.findViewById(R.id.aide_type_item_notification);
        viewHolder.date = (TextView) row.findViewById(R.id.date_item_notification);
        viewHolder.linearLayout = (LinearLayout) row.findViewById(R.id.item_color_notification);
        viewHolder.linearLayoutHelpers = (LinearLayout) row.findViewById(R.id.list_helpers);

        viewHolder.acceptOnclickListener = new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                //send request and hide other views
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setMessage("Vous serez mis en contact, via la messagerie.\n" +
                        "L'aidant sera notifié de votre réponse.")
                        .setTitle("Selectionner cet aidant?");


                final AlertDialog dialog = builder.create();
                dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialog.dismiss();
                    }
                });
                dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Sélectionner",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                PlannedRequester plannedRequester = new PlannedRequester();
                                plannedRequester.selectAnswerPlanned(context,
                                        String.valueOf(view.getId()), getItem(position).idPlanned, new PlannedRequester.PostPlannedCB() {
                                            @Override
                                            public void onPlannedPosted(Boolean success) {
                                                if (success) {
                                                    Toast.makeText(context, "Succès", Toast.LENGTH_LONG).show();
                                                } else {
                                                    Toast.makeText(context, "ERREUR, REESSAYER", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                                dialog.dismiss();
                            }
                        });

                dialog.show();
            }
        };
        viewHolder.refuseOnclickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlannedRequester plannedRequester = new PlannedRequester();
                plannedRequester.deleteResponsePlanned(context,
                        String.valueOf(view.getId()), getItem(position).idPlanned, new PlannedRequester.PostPlannedCB() {
                            @Override
                            public void onPlannedPosted(Boolean success) {
                                if (success) {
                                } else {
                                }
                            }
                        });
            }
        };
        row.setTag(viewHolder);

        if (responsePlanneds != null) {
            //display rows of helpers below the planned request, this dynamically adds helpers to the view
            //plus listeners, loading profile etc
            viewHolder.firstname = new TextView[responsePlanneds.size()];
            viewHolder.surname = new TextView[responsePlanneds.size()];
            viewHolder.pictureContact = new ImageView[responsePlanneds.size()];
            for (int i = 0; i < responsePlanneds.size(); i++) {
                //only add helpers related to the planned request:
                if (responsePlanneds.get(i).id_request == getItem(position).idPlanned) {
                    View line = inflater.inflate(R.layout.notification_helper_row, null);
                    LinearLayout linear = (LinearLayout) line.findViewById(R.id.notification_linear_row);
                    
                    //listeners on buttons and ids:
                    viewHolder.pictureContact[i] = (ImageView) line.findViewById(R.id.notification_row_pictureContact);
                    viewHolder.firstname[i] = ((TextView) line.findViewById(R.id.notification_row_tvItemContactFirstName));
                    viewHolder.surname[i] = (TextView) line.findViewById(R.id.notification_row_tvItemContactSurname);
                    Button refuse = new Button(context);
                    refuse.setText("Refuser");
                    Button accpt = new Button(context);
                    accpt.setText("Accepter");
                    accpt.setId(Integer.parseInt(responsePlanneds.get(i).id_helper));
                    accpt.setOnClickListener(viewHolder.acceptOnclickListener);
                    refuse.setId(Integer.parseInt(responsePlanneds.get(i).id_helper));
                    refuse.setOnClickListener(viewHolder.refuseOnclickListener);

                    //load profiles:
                    loadHelperProfileAsync(viewHolder, i);

                    linear.addView(refuse);
                    linear.addView(accpt);
                    viewHolder.linearLayoutHelpers.addView(line);

                }

            }
        }


        final PlannedRequest plannedRequest = getItem(position);
        viewHolder.localisation.setText(plannedRequest.localisation);

        getAideType(viewHolder, plannedRequest.helpCategory);

        String formattedDate = formatDate(plannedRequest.scheduledAt);
        viewHolder.date.setText(formattedDate);

        return row;
    }

    private void loadHelperProfileAsync(final CardViewHolder viewHolder, int i) {
        PlannedRequester plannedRequester = new PlannedRequester();
        final int finalI = i;
        plannedRequester.getUser(context, responsePlanneds.get(i).id_helper, new PlannedRequester.GetUserCB() {
            @Override
            public void getUser(String firstName, String surname, String age, Boolean success) {
                if (success) {
                    viewHolder.firstname[finalI].setText(firstName);
                    viewHolder.surname[finalI].setText(surname);

                }
            }
        });

        ImageRequester imageRequester = new ImageRequester();
        imageRequester.getImage(responsePlanneds.get(i).id_helper, context, new ImageRequester.ImageInterface() {
            @Override
            public void getUrl(String url) {
                if (!url.isEmpty())
                    Picasso.with(context.getApplicationContext()).load(url).centerCrop().fit().into(viewHolder.pictureContact[finalI]);

            }
        });
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

