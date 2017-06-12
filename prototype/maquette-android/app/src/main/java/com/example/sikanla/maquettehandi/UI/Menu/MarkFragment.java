package com.example.sikanla.maquettehandi.UI.Menu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.sikanla.maquettehandi.Adapters.PlannedRequestCardAdapter;
import com.example.sikanla.maquettehandi.Model.PlannedRequest;
import com.example.sikanla.maquettehandi.R;
import com.example.sikanla.maquettehandi.network.PlannedRequester;

import java.util.ArrayList;

/**
 * Created by Nicolas on 11/06/2017.
 */

//TODO verify if not commented yet

public class MarkFragment extends Fragment {
    public SwipeRefreshLayout swipeContainer;
    public ListView listView;
    private PlannedRequestCardAdapter adapter;

    private PlannedRequester plannedRequester;


    public MarkFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scheduled, container, false);

        adapter = new PlannedRequestCardAdapter(getActivity(), R.layout.item_planned_card, true);
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


    }


    private void fetchPlannedRequests() {
        plannedRequester.getMyPlannedRequest(getActivity(), new PlannedRequester.PlannedRequestCB() {

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
