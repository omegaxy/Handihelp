package com.example.sikanla.maquettehandi.UI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.sikanla.maquettehandi.Model.PlannedRequest;
import com.example.sikanla.maquettehandi.PlannedAdapter;
import com.example.sikanla.maquettehandi.R;
import com.example.sikanla.maquettehandi.network.PlannedRequester;

import java.util.ArrayList;

/**
 * Created by Sikanla on 13/02/2017.
 */

public class ScheduledFragment extends Fragment {

    public SwipeRefreshLayout swipeContainer;
    public ListView listView;
    public PlannedAdapter adapter;

    public ScheduledFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scheduled, container, false);

        ArrayList<PlannedRequest> arrayOfUsers = new ArrayList<PlannedRequest>();

        adapter = new PlannedAdapter(getActivity(), arrayOfUsers);
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainerPlanned);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchPlannedRequests();
            }
        });
        listView = (ListView) view.findViewById(R.id.lvplanned);

        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fetchPlannedRequests();
        swipeContainer.setColorSchemeColors(getResources().getColor(android.R.color.holo_green_dark),
                getResources().getColor(android.R.color.holo_red_dark),
                getResources().getColor(android.R.color.holo_blue_dark),
                getResources().getColor(android.R.color.holo_orange_dark));
    }


    private void fetchPlannedRequests() {

        final PlannedRequester plannedRequester = new PlannedRequester();
        plannedRequester.getPlannedRequest(getActivity(), new PlannedRequester.PlannedRequestCB() {

            @Override
            public void getArrayPlannedRequest(ArrayList<PlannedRequest> s, Boolean success) {
                if (success) {
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

}

