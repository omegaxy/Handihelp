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
import com.example.sikanla.maquettehandi.Model.InstantRequest;
import com.example.sikanla.maquettehandi.Model.User;
import com.example.sikanla.maquettehandi.R;
import com.example.sikanla.maquettehandi.UI.Activities.FormInstantRequestActi;
import com.example.sikanla.maquettehandi.network.InstantRequester;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
    private TextView textView;
    private Thread t;
    private InstantRequester instantRequester;

    private ArrayList<InstantRequest> instantRequests;


    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_instant_request, container, false);

        adapter = new HelpSomeoneInstantAdapter(getActivity());
        listView = (ListView) view.findViewById(R.id.fragment_instant_listview);
        listView.setAdapter(adapter);

        countDownTv = (TextView) view.findViewById(R.id.count_down);
        layoutRequest = (LinearLayout) view.findViewById(R.id.instant_request_enabled);
        layoutNoRequest = (LinearLayout) view.findViewById(R.id.instant_no_request);
        createInstantButt = (Button) view.findViewById(R.id.instant_create_instant);
        textView = (TextView) view.findViewById(R.id.fragment_instant_textv);
        instantRequests = new ArrayList<>();
        instantRequester = new InstantRequester();


        instantRequestBehaviour();


        fetchInstantRequests();

        refreshForInstantRequests();


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
        instantRequester.getInstantRequests(getActivity(), new InstantRequester.InstantRequestCB() {
            @Override
            public void getArrayInstantRequest(ArrayList<InstantRequest> s, Boolean success) {
                if (success) {
                    adapter.clear();
                    adapter.addAll(filterHelpRequests(s));
                    adapter.notifyDataSetChanged();
                    if (filterHelpRequests(s).size() > 0) {
                        textView.setVisibility(View.GONE);

                    } else {
                        textView.setVisibility(View.VISIBLE);
                    }
                }

            }
        });

    }

    private ArrayList<InstantRequest> filterHelpRequests(ArrayList<InstantRequest> arrayList) {

        //check if deamand is 5 minutes old
        ArrayList<InstantRequest> arrayList2 = new ArrayList<>();
        for (int i = 0; i < arrayList.size(); i++) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String startTime = arrayList.get(i).getCreatedAt();
            Date date = new Date();
            String nowTime = sdf.format(date);

            try {
                Date d1 = sdf.parse(startTime);
                Date d2 = sdf.parse(nowTime);
                //gmt+2
                long elapsed = (d2.getTime() - d1.getTime() - 7200000) / 1000;

                if (elapsed < 5 * 60) {
                    arrayList2.add(arrayList.get(i));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        //check if demand contains user id
        User user = new User();
        ArrayList<InstantRequest> arrayList1 = new ArrayList<>();
        for (int i = 0; i < arrayList2.size(); i++) {
            String[] parts = arrayList2.get(i).getCloseUsers().split(",");
            for (int j = 0; j < parts.length; j++) {
                if (user.getUserId().matches(parts[j])) {
                    arrayList1.add(arrayList2.get(i));
                }
            }
        }

        return arrayList1;


    }

    private synchronized void refreshForInstantRequests() {
        t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(5000);
                        if (getActivity() != null) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    instantRequester.getInstantRequests(getActivity(), new InstantRequester.InstantRequestCB() {
                                        @Override
                                        public void getArrayInstantRequest(ArrayList<InstantRequest> s, Boolean success) {
                                            if (success) {
                                                if (instantRequests.size() != filterHelpRequests(s).size()) {
                                                    instantRequests = filterHelpRequests(s);
                                                    adapter.clear();
                                                    adapter.addAll(filterHelpRequests(s));
                                                    adapter.notifyDataSetChanged();
                                                    if (filterHelpRequests(s).size() > 0) {
                                                        textView.setVisibility(View.GONE);

                                                    } else {
                                                        textView.setVisibility(View.VISIBLE);
                                                    }

                                                }

                                            }

                                        }
                                    });

                                }
                            });
                        }
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t.start();

    }

}
