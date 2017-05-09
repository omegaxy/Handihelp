package com.example.sikanla.maquettehandi.UI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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

    public ScheduledFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scheduled, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstance) {
        super.onActivityCreated(savedInstance);
// Construct the data source
        ArrayList<PlannedRequest> arrayOfUsers = new ArrayList<PlannedRequest>();
// Create the adapter to convert the array to views
        final PlannedAdapter adapter = new PlannedAdapter(getActivity(), arrayOfUsers);
// Attach the adapter to a ListView
        ListView listView = (ListView) getActivity().findViewById(R.id.lvplanned);
        listView.setAdapter(adapter);

        final PlannedRequester plannedRequester = new PlannedRequester();
        plannedRequester.getPlannedRequest(getActivity(), new PlannedRequester.PlannedRequestCB() {
            @Override
            public void getArrayPlannedRequest(ArrayList<PlannedRequest> s, Boolean success) {
                if(success) {
                    adapter.addAll(s);
                }else {
                    // todo display error message
                }
            }
        });

    }

}

