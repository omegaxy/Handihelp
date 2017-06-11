package com.example.sikanla.maquettehandi.UI.Menu;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sikanla.maquettehandi.Model.User;
import com.example.sikanla.maquettehandi.R;
import com.example.sikanla.maquettehandi.network.FriendRequester;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Sikanla on 11/06/2017.
 */

public class InstantFragment extends Fragment {

    private TextView countDownTv;
    private LinearLayout layoutRequest, layoutNoRequest;


    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_instant_request, container, false);
        countDownTv = (TextView) view.findViewById(R.id.count_down);
        layoutRequest = (LinearLayout) view.findViewById(R.id.instant_request_enabled);
        layoutNoRequest = (LinearLayout) view.findViewById(R.id.instant_no_request);

        Long tsLong = System.currentTimeMillis() / 1000;
        SharedPreferences prefs = getActivity().getSharedPreferences(User.MY_PREFS_NAME, MODE_PRIVATE);
        //if an instant request has been created
        if (-prefs.getLong("instantRequest", 0) + tsLong < 5 * 60) {
            layoutNoRequest.setVisibility(View.GONE);
            layoutRequest.setVisibility(View.VISIBLE);
            //request lasts 5mintes
            new CountDownTimer((5 * 60 - (-prefs.getLong("instantRequest", 0) + tsLong)) * 1000, 1000) {

                public void onTick(long millisUntilFinished) {
                    countDownTv.setText("Temps restant: " + (millisUntilFinished / 1000) / 60 + ":" + (millisUntilFinished / 1000) % 60);
                    //here you can have your logic to set text to edittext
                }

                public void onFinish() {
                    countDownTv.setText("done!");
                }

            }.start();


        } else {
            layoutNoRequest.setVisibility(View.VISIBLE);
            layoutRequest.setVisibility(View.GONE);

        }


        // fetchMessages();
        return view;
    }

    private void fetchMessages() {
        FriendRequester friendRequester = new FriendRequester();
        friendRequester.getFriends(getActivity(), new FriendRequester.GetFriendCB() {
            @Override
            public void getArrayFriends(ArrayList<String> arrayList, Boolean success) {
                if (success) {


                } else {

                }
            }
        });

    }
}
