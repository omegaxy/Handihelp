package com.example.sikanla.maquettehandi.DialogFragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

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


public class DisplayMarkDF extends DialogFragment {
    private View rootView;
    private TextView fistNameTv, surnameTv, fistNameTv2, surnameTv2;
    private ImageView imageView1, imageView2;
    private String id, id2;

    private RatingBar ratingBar;
    private Button btnSubmit;
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

        ratingBar = (RatingBar) rootView.findViewById(R.id.ratingBar);
        commentaire = (EditText) rootView.findViewById(R.id.commentaire);
        btnSubmit = (Button) rootView.findViewById(R.id.btnSubmit);

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


        //TODO
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
        //TODO
        //loadUser2Profile();

        User user = new User();
        final AlertDialog.Builder builder1 = builder.setView(rootView);

        btnSubmit.setEnabled(false);
        btnSubmit.setAlpha(0.5f);

        addListenerOnRatingBar();
        addListenerOnButton();


        return builder1.create();

    }


    public void addListenerOnRatingBar() {

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                btnSubmit.setEnabled(true);
                btnSubmit.setAlpha(1f);

            }
        });
    }

    public void addListenerOnButton() {

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //TODO send mark and comment to Serve
                //float : ratingBar.getRating()
                //String : commentaire.getText().toString()

            }

        });

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

