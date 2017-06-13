package com.example.sikanla.maquettehandi.UI.Menu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sikanla.maquettehandi.Adapters.NotificationAdapter;
import com.example.sikanla.maquettehandi.Model.PlannedRequest;
import com.example.sikanla.maquettehandi.Model.ResponsePlanned;
import com.example.sikanla.maquettehandi.R;
import com.example.sikanla.maquettehandi.network.PlannedRequester;

import java.util.ArrayList;

/**
 * Created by Sikanla on 31/05/2017.
 */

public class NotificationFragment extends Fragment {

    public SwipeRefreshLayout swipeContainer;
    public ListView listView;
    private NotificationAdapter adapter;
    private LinearLayout linear1, linear2;
    private ArrayList<PlannedRequest> plannedRequests;
    private ArrayList<ResponsePlanned> localResponsePlanneds = new ArrayList<>();
    private PlannedRequester plannedRequester;
    private Thread t;


    public NotificationFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        adapter = new NotificationAdapter(getActivity(), R.layout.item_notification_card);
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainerNotification);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchPlannedRequests();
            }
        });
        listView = (ListView) view.findViewById(R.id.lvnotification);
        listView.addHeaderView(new View(getActivity()));
        listView.addFooterView(new View(getActivity()));

        linear1 = (LinearLayout) view.findViewById(R.id.notificationFG_linear1);
        linear2 = (LinearLayout) view.findViewById(R.id.notificationFG_linear2);

        linear1.setVisibility(View.VISIBLE);
        linear2.setVisibility(View.GONE);


        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        plannedRequester = new PlannedRequester();
        listView.setAdapter(adapter);
        fetchPlannedRequests();
        refreshForNewRequests();
        swipeContainer.setColorSchemeColors(getResources().getColor(android.R.color.holo_green_dark),
                getResources().getColor(android.R.color.holo_red_dark),
                getResources().getColor(android.R.color.holo_blue_dark),
                getResources().getColor(android.R.color.holo_orange_dark));

    }

    private void fetchPlannedRequests() {
        plannedRequester.getPlannedRequest(getActivity().getApplicationContext(), new PlannedRequester.PlannedRequestCB() {

            @Override
            public void getArrayPlannedRequest(ArrayList<PlannedRequest> s, Boolean success) {
                if (success) {
                    plannedRequests = s;
                    plannedRequester.getResponsesPlanned(getActivity().getApplicationContext(), new PlannedRequester.ResponsePlannedCB() {
                        @Override
                        public void onResponsePlanned(ArrayList<ResponsePlanned> t, Boolean success) {
                            if (success) {
                                Boolean b = false;
                                if (t != null && t.size() > 0) {
                                    adapter.clear();
                                    localResponsePlanneds = t;
                                    adapter.addAll(plannedRequests, t);
                                    adapter.notifyDataSetChanged();
                                    for (int i = 0; i < plannedRequests.size(); i++) {
                                        for (int j = 0; j < t.size(); j++) {
                                            if (plannedRequests.get(i).idPlanned.matches(t.get(j).id_request)) {
                                                b = true;
                                            }
                                        }
                                    }
                                    if (b) {
                                        linear2.setVisibility(View.VISIBLE);
                                        linear1.setVisibility(View.GONE);
                                    } else {
                                        linear1.setVisibility(View.VISIBLE);
                                        linear2.setVisibility(View.GONE);
                                    }
                                }
                                swipeContainer.setRefreshing(false);

                            } else {
                                swipeContainer.setRefreshing(false);
                            }
                        }
                    });


                } else {
                    swipeContainer.setRefreshing(false);
                }
            }
        });
    }

    private void refreshForNewRequests() {
        t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(4000);
                        if (getActivity() != null) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    plannedRequester.getPlannedRequest(getActivity(), new PlannedRequester.PlannedRequestCB() {

                                        @Override
                                        public void getArrayPlannedRequest(ArrayList<PlannedRequest> s, Boolean success) {
                                            if (success) {
                                                plannedRequests = s;
                                                if (getActivity() != null) {
                                                    plannedRequester.getResponsesPlanned(getActivity().getApplicationContext(), new PlannedRequester.ResponsePlannedCB() {
                                                        @Override
                                                        public void onResponsePlanned(ArrayList<ResponsePlanned> t, Boolean success) {
                                                            if (success) {
                                                                Boolean b = false;
                                                                if (localResponsePlanneds != null) {
                                                                    if (localResponsePlanneds.size() != t.size()) {
                                                                        localResponsePlanneds = t;
                                                                        adapter.clear();
                                                                        adapter.addAll(plannedRequests, t);
                                                                        adapter.notifyDataSetChanged();
                                                                        for (int i = 0; i < plannedRequests.size(); i++) {
                                                                            for (int j = 0; j < t.size(); j++) {
                                                                                if (plannedRequests.get(i).idPlanned.matches(t.get(j).id_request)) {
                                                                                    b = true;
                                                                                }
                                                                            }
                                                                        }
                                                                        if (b) {
                                                                            linear2.setVisibility(View.VISIBLE);
                                                                            linear1.setVisibility(View.GONE);
                                                                        } else {
                                                                            linear1.setVisibility(View.VISIBLE);
                                                                            linear2.setVisibility(View.GONE);
                                                                        }
                                                                    }
                                                                }

                                                            }
                                                        }
                                                    });


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



