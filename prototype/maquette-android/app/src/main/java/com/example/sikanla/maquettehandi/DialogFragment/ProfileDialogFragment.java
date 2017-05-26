package com.example.sikanla.maquettehandi.DialogFragment;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.sikanla.maquettehandi.R;
import com.example.sikanla.maquettehandi.Model.User;
import com.example.sikanla.maquettehandi.network.FriendRequester;
import com.example.sikanla.maquettehandi.network.ImageRequester;
import com.example.sikanla.maquettehandi.network.PlannedRequester;
import com.squareup.picasso.Picasso;

import android.widget.Toast;

import java.util.ArrayList;

public class ProfileDialogFragment extends DialogFragment {


    private View rootView;
    private TextView fistNameTv;
    private TextView ageTv;
    private ImageView imageViewPP, imageViewPP2;
    private String id;
    private Button message, addFriend;
    private RelativeLayout relativeLayout1, relativeLayout2;


    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        rootView = inflater.inflate(R.layout.fragment_profile, null);
        fistNameTv = (TextView) rootView.findViewById(R.id.firstnamedialog);
        ageTv = (TextView) rootView.findViewById(R.id.agedialog);


        id = getArguments().getString("id");

        final User user = new User();
//display another layout if you load your own profile
        if (id.equals(user.getUserId())) {
            relativeLayout1 = (RelativeLayout) rootView.findViewById(R.id.fp_relative1);
            relativeLayout2 = (RelativeLayout) rootView.findViewById(R.id.fp_relative2);
            imageViewPP2 = (ImageView) rootView.findViewById(R.id.profileImageV2);

            relativeLayout1.setVisibility(View.GONE);
            relativeLayout2.setVisibility(View.VISIBLE);

            ImageRequester imageRequest = new ImageRequester();
            imageRequest.getImage(id, getActivity(), new ImageRequester.ImageInterface() {
                @Override
                public void getUrl(String s) {
                    Picasso.with(getActivity()).load(s).into(imageViewPP2);

                }
            });

        } else {

            imageViewPP = (ImageView) rootView.findViewById(R.id.profileImageV);
            message = (Button) rootView.findViewById(R.id.fp_message);
            addFriend = (Button) rootView.findViewById(R.id.fp_add_friend);

            addFriend.setBackgroundColor(getResources().getColor(R.color.dd));

            if (isFriend(user.getFriendList(), id)) {
                addFriend.setText("Supprimer des amis");
                addFriend.setBackgroundColor(getResources().getColor(R.color.type8));
            }

            addFriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FriendRequester friendRequester = new FriendRequester();

                    if (isFriend(user.getFriendList(), id)) {
                        friendRequester.deleteFriend(getActivity(), id, new FriendRequester.DeleteFriendCB() {
                            @Override
                            public void onFriendDeleted(Boolean success) {
                                if (success) {
                                    addFriend.setText("Ajouter en ami");
                                    addFriend.setBackgroundColor(getResources().getColor(R.color.dd));
                                    user.deleteFriend(id);

                                } else {
                                    Toast.makeText(getActivity(), "ERREUR", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                    } else {
                        friendRequester.addFriend(getActivity(), id, new FriendRequester.AddFriendCB() {
                            @Override
                            public void onFriendAdded(Boolean success) {
                                if (success) {
                                    addFriend.setText("Supprimer des amis");
                                    addFriend.setBackgroundColor(getResources().getColor(R.color.type8));
                                    user.addFriend(id);
                                } else {
                                    Toast.makeText(getActivity(), "ERREUR", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                    }
                }
            });


            message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SendMessageDialog sendMessageDialog = new SendMessageDialog();
                    Bundle args = new Bundle();
                    args.putString("firstname", fistNameTv.getText().toString());
                    args.putString("id", id);
                    sendMessageDialog.setArguments(args);
                    sendMessageDialog.show(getActivity().getFragmentManager(), "sendMessage");
                }
            });

            ImageRequester imageRequest = new ImageRequester();
            imageRequest.getImage(id, getActivity(), new ImageRequester.ImageInterface() {
                @Override
                public void getUrl(String s) {
                    Picasso.with(getActivity()).load(s).into(imageViewPP);

                }
            });
        }


        PlannedRequester plannedRequest = new PlannedRequester();
        plannedRequest.getUser(getActivity(), id, new PlannedRequester.GetUserCB() {
            @Override
            public void getUser(String firstName, String surname, String age, Boolean success) {
                fistNameTv.setText(firstName);
                if (!age.equals(""))
                    ageTv.setText(String.valueOf(2017 - Integer.parseInt(age)));
            }
        });


        final AlertDialog.Builder builder1 = builder.setView(rootView)
                .setPositiveButton("Fermer", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        return builder1.create();


    }

    private boolean isFriend(ArrayList<String> arrayList, String id) {
        if (arrayList != null) {
            for (int i = 0; i < arrayList.size(); i++) {
                if (arrayList.get(i) == id)
                    return true;
            }
        }
        return false;
    }
}
