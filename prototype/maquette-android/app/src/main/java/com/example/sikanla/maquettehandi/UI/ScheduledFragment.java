package com.example.sikanla.maquettehandi.UI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.sikanla.maquettehandi.Model.PlannedRequest;
import com.example.sikanla.maquettehandi.PlannedAdapter;
import com.example.sikanla.maquettehandi.R;
import com.example.sikanla.maquettehandi.network.PlannedRequester;

import java.util.ArrayList;

/**
 * Created by Sikanla on 13/02/2017.
 */

public class ScheduledFragment extends Fragment {
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeContainer;
    private ListView listView;


    public ScheduledFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scheduled, container, false);
    }

    private PlannedAdapter adapter;

    @Override
    public void onActivityCreated(Bundle savedInstance) {
        super.onActivityCreated(savedInstance);
        ArrayList<PlannedRequest> arrayOfUsers = new ArrayList<PlannedRequest>();
        adapter = new PlannedAdapter(getActivity(), arrayOfUsers);
        // progressBar = (ProgressBar) getActivity().findViewById(R.id.progressBar_sch);
        swipeContainer = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchPlannedRequests();
            }
        });
        fetchPlannedRequests();

    }

    private void fetchPlannedRequests() {
        listView = (ListView) getActivity().findViewById(R.id.lvplanned);
        final PlannedRequester plannedRequester = new PlannedRequester();
        plannedRequester.getPlannedRequest(getActivity(), new PlannedRequester.PlannedRequestCB() {
            @Override
            public void getArrayPlannedRequest(ArrayList<PlannedRequest> s, Boolean success) {
                if (success) {
                    adapter.clear();
                    adapter.addAll(s);
                    listView.setAdapter(adapter);
                    swipeContainer.setRefreshing(false);
                } else{
                    swipeContainer.setRefreshing(false);
                    // todo display error message check your connection
                }
            }
        });
    }

}

