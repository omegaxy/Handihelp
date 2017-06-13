package com.example.sikanla.maquettehandi.UI.Menu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sikanla.maquettehandi.Adapters.HelpSomeoneInstantAdapter;
import com.example.sikanla.maquettehandi.Adapters.NotificationAdapter;
import com.example.sikanla.maquettehandi.Model.InstantRequest;
import com.example.sikanla.maquettehandi.Model.User;
import com.example.sikanla.maquettehandi.R;
import com.example.sikanla.maquettehandi.UI.Activities.FormInstantRequestActi;
import com.example.sikanla.maquettehandi.network.FriendRequester;
import com.example.sikanla.maquettehandi.network.InstantRequester;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Sikanla on 11/06/2017.
 */

public class InstantFragment extends Fragment {

    private TextView countDownTv;
    private LinearLayout layoutRequest, layoutNoRequest;
    private Button createInstantButt;
    private ListView listView;
    private HelpSomeoneInstantAdapter adapter;


    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_instant_request, container, false);

        adapter = new HelpSomeoneInstantAdapter(getActivity());
        listView = (ListView) view.findViewById(R.id.fragment_instant_listview);
        listView.setAdapter(adapter);

        countDownTv = (TextView) view.findViewById(R.id.count_down);
        layoutRequest = (LinearLayout) view.findViewById(R.id.instant_request_enabled);
        layoutNoRequest = (LinearLayout) view.findViewById(R.id.instant_no_request);
        createInstantButt = (Button) view.findViewById(R.id.instant_create_instant);

        instantRequestBehaviour();


        fetchInstantRequests();
        return view;
    }

    private void instantRequestBehaviour() {
        Long tsLong = System.currentTimeMillis() / 1000;
        SharedPreferences prefs = getActivity().getSharedPreferences(User.MY_PREFS_NAME, MODE_PRIVATE);
        //if an instant request has been created
        if (-prefs.getLong("instantRequest", 0) + tsLong < 5 * 60) {
            layoutNoRequest.setVisibility(View.GONE);
            layoutRequest.setVisibility(View.VISIBLE);
            //request lasts 5 mintes
            new CountDownTimer((5 * 60 - (-prefs.getLong("instantRequest", 0) + tsLong)) * 1000, 1000) {

                public void onTick(long millisUntilFinished) {
                    if ((millisUntilFinished / 1000) % 60 < 10) {
                        countDownTv.setText("Temps restant: " + (millisUntilFinished / 1000) / 60 + ":" + "0" + (millisUntilFinished / 1000) % 60);

                    } else {
                        countDownTv.setText("Temps restant: " + (millisUntilFinished / 1000) / 60 + ":" + (millisUntilFinished / 1000) % 60);
                    }
                }

                public void onFinish() {
                    countDownTv.setText("done!");
                }

            }.start();


        } else {
            layoutNoRequest.setVisibility(View.VISIBLE);
            layoutRequest.setVisibility(View.GONE);

            createInstantButt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getActivity(), FormInstantRequestActi.class));
                }
            });


        }
    }

    public void onResume() {
        super.onResume();
        instantRequestBehaviour();


    }

    private void fetchInstantRequests() {
        InstantRequester instantRequester = new InstantRequester();
        instantRequester.getInstantRequests(getActivity(), new InstantRequester.InstantRequestCB() {
            @Override
            public void getArrayInstantRequest(ArrayList<InstantRequest> s, Boolean success) {
                adapter.clear();
                adapter.addAll(s);
                adapter.notifyDataSetChanged();


            }
        });

    }
}
