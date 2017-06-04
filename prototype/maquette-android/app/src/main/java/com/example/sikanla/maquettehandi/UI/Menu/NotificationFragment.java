package com.example.sikanla.maquettehandi.UI.Menu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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

    private ArrayList<PlannedRequest> plannedRequests;
    private ArrayList<ResponsePlanned> localResponsePlanneds;
    private PlannedRequester plannedRequester;
    private Thread t;


    public NotificationFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        adapter = new NotificationAdapter(getActivity(), R.layout.notification_item_card);
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
                                adapter.clear();
                                localResponsePlanneds = t;
                                adapter.addAll(plannedRequests, t);
                                adapter.notifyDataSetChanged();
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
                                                                if (localResponsePlanneds != null) {
                                                                    if (localResponsePlanneds.size() != t.size()) {
                                                                        localResponsePlanneds = t;
                                                                        adapter.clear();
                                                                        adapter.addAll(plannedRequests, t);
                                                                        adapter.notifyDataSetChanged();
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



