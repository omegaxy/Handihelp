package com.example.sikanla.maquettehandi.DialogFragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.sikanla.maquettehandi.Model.User;
import com.example.sikanla.maquettehandi.R;
import com.example.sikanla.maquettehandi.network.ImageRequester;
import com.example.sikanla.maquettehandi.network.UserRequester;
import com.squareup.picasso.Picasso;


/**
 * Created by Nicolas on 13/06/2017.
 */

//TODO get benevole id for display name and photo
//TODO send mark and comment to Server
//TODO note + comment


public class DisplayMarkDF extends DialogFragment {
    private View rootView;
    private TextView fistNameTv, surnameTv, fistNameTv2, surnameTv2;
    private ImageView imageView1, imageView2;
    private String id, id2;

    private RatingBar mark;
    private EditText commentaire;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AnswerTheme3);

        rootView = inflater.inflate(R.layout.df_display_mark_request, null);

        fistNameTv = (TextView) rootView.findViewById(R.id.firstname1);
        surnameTv = (TextView) rootView.findViewById(R.id.surname1);
        imageView1 = (ImageView) rootView.findViewById(R.id.imageView1);

        fistNameTv2 = (TextView) rootView.findViewById(R.id.firstname2);
        surnameTv2 = (TextView) rootView.findViewById(R.id.surname2);
        imageView2 = (ImageView) rootView.findViewById(R.id.imageView2);

        mark = (RatingBar) rootView.findViewById(R.id.note);
        commentaire = (EditText) rootView.findViewById(R.id.commentaire);

        id = getArguments().getString("id");

        //TODO pass benevole id in argument from RequestToMarkAdapter
        //id2 = getArguments().getString("id2");


        fistNameTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadProfile();
            }
        });

        surnameTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadProfile();
            }
        });

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadProfile();

            }
        });
/*
        fistNameTv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadProfile2();
            }
        });

        surnameTv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadProfile2();
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadProfile2();

            }
        });
*/

        loadUserProfile();
        //loadUser2Profile();

        User user = new User();
        final AlertDialog.Builder builder1 = builder.setView(rootView);
/*
        if(getArguments().getString("tomark").equals("true")){
            displayMarkButttons();
        }
        else {
            layout_mark.setVisibility(View.GONE);
            //display buttons only if user is not yourself
            if (!id.matches(user.getUserId())) {
                deletePlannedButton.setVisibility(View.GONE);
                sendMessage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SendMessageDialog sendMessageDialog = new SendMessageDialog();
                        Bundle args = new Bundle();
                        args.putString("firstname", fistNameTv.getText().toString());
                        args.putString("id", id);
                        sendMessageDialog.setArguments(args);
                        sendMessageDialog.show(getActivity().getFragmentManager(), "displayPlanned");

                    }
                });


                help.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AnswerPLannedDF answerPLannedDF = new AnswerPLannedDF();
                        Bundle args = new Bundle();
                        args.putString("idPlanned", getArguments().getString("idPlanned"));
                        args.putString("id", id);
                        answerPLannedDF.setArguments(args);
                        answerPLannedDF.show(getActivity().getFragmentManager(), "answerPlanned");

                    }
                });

            } else {
                deletePlannedButton.setVisibility(View.VISIBLE);
                deletePlannedButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(getActivity()).create();
                        alertDialog.setMessage("Supprimer la demande?");
                        alertDialog.setButton(android.app.AlertDialog.BUTTON_NEUTRAL, "Annuler",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.setButton(android.app.AlertDialog.BUTTON_POSITIVE, "Supprimer", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                PlannedRequester plannedRequester = new PlannedRequester();
                                plannedRequester.deletePlannedRequest(getActivity(), getArguments().getString("idPlanned"), new PlannedRequester.PostPlannedCB() {
                                    @Override
                                    public void onPlannedPosted(Boolean success) {
                                        if (!success) {
                                            Toast.makeText(getActivity(), "ERREUR", Toast.LENGTH_LONG).show();

                                        } else {
                                            dismiss();
                                        }
                                    }
                                });

                            }
                        });
                        alertDialog.show();
                    }
                });
                help.setVisibility(View.GONE);
                sendMessage.setVisibility(View.GONE);

            }

        } */

        return builder1.create();
    }



    private void loadProfile() {
        ProfileDialogFragment profileDialogFragment = new ProfileDialogFragment();
        Bundle args = new Bundle();
        args.putString("id", id);
        profileDialogFragment.setArguments(args);
        profileDialogFragment.show(getActivity().getFragmentManager(), "answerPlanned");
    }

    private void loadProfile2() {
        ProfileDialogFragment profileDialogFragment = new ProfileDialogFragment();
        Bundle args = new Bundle();
        args.putString("id2", id2);
        profileDialogFragment.setArguments(args);
        profileDialogFragment.show(getActivity().getFragmentManager(), "answerPlanned");
    }

    private void loadUserProfile() {
        ImageRequester imageRequest = new ImageRequester();

        imageRequest.getImage(id, getActivity(), new ImageRequester.ImageInterface() {
            @Override
            public void getUrl(String s) {
                Picasso.with(getActivity()).load(s).centerCrop().fit().into(imageView1);
            }
        });
        UserRequester userRequester = new UserRequester();
        userRequester.getUser(getActivity(), id, new UserRequester.GetUserCB() {
            @Override
            public void getUser(String firstName, String surname, String age, Boolean success) {
                if (success) {
                    fistNameTv.setText(firstName);
                    surnameTv.setText(surname);
                }
            }
        });
    }

    private void loadUser2Profile(){
        ImageRequester imageRequest = new ImageRequester();

        imageRequest.getImage(id2, getActivity(), new ImageRequester.ImageInterface() {
            @Override
            public void getUrl(String s) {
                Picasso.with(getActivity()).load(s).centerCrop().fit().into(imageView2);
            }
        });
        UserRequester userRequester = new UserRequester();
        userRequester.getUser(getActivity(), id, new UserRequester.GetUserCB() {
            @Override
            public void getUser(String firstName, String surname, String age, Boolean success) {
                if (success) {
                    fistNameTv2.setText(firstName);
                    surnameTv2.setText(surname);
                }
            }
        });
    }

}

