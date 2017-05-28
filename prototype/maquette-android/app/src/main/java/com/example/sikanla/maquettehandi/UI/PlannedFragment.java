package com.example.sikanla.maquettehandi.UI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.sikanla.maquettehandi.Adapters.PlannedRequestCardAdapter;
import com.example.sikanla.maquettehandi.Model.Message;
import com.example.sikanla.maquettehandi.Model.PlannedRequest;
import com.example.sikanla.maquettehandi.R;
import com.example.sikanla.maquettehandi.network.MessageRequester;
import com.example.sikanla.maquettehandi.network.PlannedRequester;

import java.util.ArrayList;

/**
 * Created by Sikanla on 13/02/2017.
 */

public class PlannedFragment extends Fragment {

    public SwipeRefreshLayout swipeContainer;
    public ListView listView;
    private PlannedRequestCardAdapter adapter;

    private ArrayList<PlannedRequest> plannedRequests;
    private PlannedRequester plannedRequester;


    public PlannedFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scheduled, container, false);

        adapter = new PlannedRequestCardAdapter(getActivity(), R.layout.planned_item_card);
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainerPlanned);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchPlannedRequests();
            }
        });
        listView = (ListView) view.findViewById(R.id.lvplanned);
        listView.addHeaderView(new View(getActivity()));
        listView.addFooterView(new View(getActivity()));

        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        plannedRequester = new PlannedRequester();
        fetchPlannedRequests();
        refreshForNewRequests();
        swipeContainer.setColorSchemeColors(getResources().getColor(android.R.color.holo_green_dark),
                getResources().getColor(android.R.color.holo_red_dark),
                getResources().getColor(android.R.color.holo_blue_dark),
                getResources().getColor(android.R.color.holo_orange_dark));

    }


    private void fetchPlannedRequests() {
        plannedRequester.getPlannedRequest(getActivity(), new PlannedRequester.PlannedRequestCB() {

            @Override
            public void getArrayPlannedRequest(ArrayList<PlannedRequest> s, Boolean success) {
                if (success) {
                    plannedRequests=s;
                    adapter.clear();
                    adapter.addAll(s);
                    listView.setAdapter(adapter);
                    swipeContainer.setRefreshing(false);
                } else {
                    swipeContainer.setRefreshing(false);
                    // todo display error message check your connection
                }
            }
        });
    }


    private void addNewPlanned(ArrayList<PlannedRequest> arrayList) {
        //check if there are responses
        if (arrayList.size() > plannedRequests.size()) {
            for (int i = plannedRequests.size(); i < arrayList.size(); i++) {
                //only display messages from other partie
                adapter.add(arrayList.get(i));
                // listView.setSelection(listView.getAdapter().getCount() - 1);


            }
            plannedRequests = arrayList;
        }


    }

    private void refreshForNewRequests() {
        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(5000);
                        if (getActivity() != null) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    plannedRequester.getPlannedRequest(getActivity(), new PlannedRequester.PlannedRequestCB() {

                                        @Override
                                        public void getArrayPlannedRequest(ArrayList<PlannedRequest> s, Boolean success) {
                                            addNewPlanned(s);
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

