package com.example.sikanla.maquettehandi.DialogFragment;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sikanla.maquettehandi.R;
import com.example.sikanla.maquettehandi.network.ImageRequester;
import com.example.sikanla.maquettehandi.network.PlannedRequester;
import com.squareup.picasso.Picasso;

public class ProfileDialogFragment extends DialogFragment {

    private View rootView;
    private TextView fistNameTv;
    private TextView ageTv;
    private ImageView imageViewPP;
    private String id;




    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        rootView = inflater.inflate(R.layout.fragment_profile, null);
        fistNameTv = (TextView) rootView.findViewById(R.id.firstnamedialog);
        ageTv = (TextView) rootView.findViewById(R.id.agedialog);
        imageViewPP = (ImageView) rootView.findViewById(R.id.profileImageV);

        id= getArguments().getString("id");
        PlannedRequester plannedRequest= new PlannedRequester();
        plannedRequest.getUser(getActivity(), id, new PlannedRequester.GetUserCB() {
            @Override
            public void getUser(String firstName, String surname, String age, Boolean success) {
                fistNameTv.setText(firstName);
                ageTv.setText(String.valueOf(2017-Integer.parseInt(age)));
            }
        });


        ImageRequester imageRequest = new ImageRequester();
        imageRequest.getImage(id, getActivity(), new ImageRequester.ImageInterface() {
            @Override
            public void getUrl(String s) {
                Picasso.with(getActivity()).load(s).into(imageViewPP);
            }
        });

        final AlertDialog.Builder builder1 = builder.setView(rootView)
                .setPositiveButton("Fermer", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        return builder1.create();


    }
}
