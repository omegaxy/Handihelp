package com.example.sikanla.maquettehandi.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    private ArrayList<ResponsePlanned> responsePlanneds;

    private static class ViewHolder {
        TextView firstname;
        TextView surname;
        ImageView pictureContact;
        LinearLayout linearLayout;
        Button accept, refuse;
    }

    @Override
    public ResponsePlanned getItem(int position) {
        return responsePlanneds.get(position);
    }

    public DisplayResponsesAdapter(Activity context, ArrayList<ResponsePlanned> responsePlanneds) {
        super(context, R.layout.item_response_planned, responsePlanneds);
        this.context = context;
        this.responsePlanneds = responsePlanneds;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ResponsePlanned responsePlanned = getItem(position);

        final ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_response_planned, parent, false);
            viewHolder.linearLayout = (LinearLayout) convertView.findViewById(R.id.linear_contact_messages);
            viewHolder.firstname = (TextView) convertView.findViewById(R.id.item_response_firsname_tv);
            viewHolder.surname = (TextView) convertView.findViewById(R.id.item_response_surname_tv);
            viewHolder.pictureContact = (ImageView) convertView.findViewById(R.id.item_response_imagev);
            viewHolder.accept = (Button) convertView.findViewById(R.id.item_response_accept_button);
            viewHolder.refuse = (Button) convertView.findViewById(R.id.item_response_refuse_button);

            convertView.setTag(viewHolder);

            PlannedRequester plannedRequester = new PlannedRequester();
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

            setListenerRefuse(responsePlanned, viewHolder, position);
            setListenersAccept(responsePlanned, viewHolder);
        } else

        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    private void setListenerRefuse(final ResponsePlanned responsePlanned, ViewHolder viewHolder, final int position) {
        viewHolder.refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Refuser cet aidant?");
                final AlertDialog dialog = builder.create();
                dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialog.dismiss();
                    }
                });
                dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Refuser",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                PlannedRequester plannedRequester = new PlannedRequester();
                                plannedRequester.deleteResponsePlanned(context,
                                        responsePlanned.id_helper, responsePlanned.id_request, new PlannedRequester.PostPlannedCB() {
                                            @Override
                                            public void onPlannedPosted(Boolean success) {
                                                if (success) {
                                                    responsePlanneds.remove(position);
                                                    notifyDataSetChanged();

                                                } else {
                                                }
                                            }
                                        });
                            }
                        });

                dialog.show();

            }

        });
    }

    private void setListenersAccept(final ResponsePlanned responsePlanned, ViewHolder viewHolder) {
        viewHolder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                                        responsePlanned.id_helper, responsePlanned.id_request, new PlannedRequester.PostPlannedCB() {
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

        });
    }
}
