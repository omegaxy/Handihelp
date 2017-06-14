package com.example.sikanla.maquettehandi.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sikanla.maquettehandi.DialogFragment.ProfileDialogFragment;
import com.example.sikanla.maquettehandi.Model.InstantRequest;
import com.example.sikanla.maquettehandi.Model.PlannedRequest;
import com.example.sikanla.maquettehandi.R;
import com.example.sikanla.maquettehandi.network.ImageRequester;
import com.example.sikanla.maquettehandi.network.InstantRequester;
import com.example.sikanla.maquettehandi.network.PlannedRequester;
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
        final InstantRequest id = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        final ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ViewHolder();
            final LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_answer_instant, parent, false);
            viewHolder.firstname = (TextView) convertView.findViewById(R.id.answer_instant_firstname);
            viewHolder.surname = (TextView) convertView.findViewById(R.id.answer_instant_surname);
            viewHolder.pictureContact = (ImageView) convertView.findViewById(R.id.answer_instant_image);
            viewHolder.halpButton = (Button) convertView.findViewById(R.id.answer_instant_helpbutton);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);

            UserRequester userRequester = new UserRequester();
            userRequester.getUser(context, id.getId(), new UserRequester.GetUserCB() {
                @Override
                public void getUser(String firstName, String surname, String age, Boolean success) {
                    if (success) {
                        viewHolder.firstname.setText(firstName);
                        viewHolder.surname.setText(surname);

                    }
                }
            });

            viewHolder.pictureContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loadProfile(id.id);
                }
            });

            viewHolder.halpButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);

                    builder.setMessage("Vous serez mis en contact, via la messagerie.\n" +
                            "L'aidant sera notifié de votre réponse.")
                            .setTitle("Aider cette personne?");


                    final AlertDialog dialog = builder.create();
                    dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Annuler", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialog.dismiss();
                        }
                    });
                    dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Aider!",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    final InstantRequester instantRequester = new InstantRequester();
                                    instantRequester.respondInstant(context.getApplicationContext(),
                                            id.getId(), id.idInstant, new InstantRequester.InstantCB() {
                                                @Override
                                                public void onInstantCB(boolean success) {
                                                    if (success) {
                                                        instantRequests.remove(id);
                                                        clear();
                                                        addAll(instantRequests);
                                                        notifyDataSetChanged();

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


            ImageRequester imageRequester = new ImageRequester();
            imageRequester.getImage(id.getId(), context, new ImageRequester.ImageInterface() {
                @Override
                public void getUrl(String url) {
                    if (!url.isEmpty())
                        Picasso.with(context).load(url).centerCrop().fit().into(viewHolder.pictureContact);

                }
            });
        } else

        {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    private void loadProfile(String id) {
        ProfileDialogFragment profileDialogFragment = new ProfileDialogFragment();
        Bundle args = new Bundle();
        args.putString("id", id);
        profileDialogFragment.setArguments(args);
        profileDialogFragment.show(context.getFragmentManager(), "answerPlanned");
    }
}