package com.example.sikanla.maquettehandi.DialogFragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sikanla.maquettehandi.Adapters.DisplayResponsesAdapter;
import com.example.sikanla.maquettehandi.Model.PlannedRequest;
import com.example.sikanla.maquettehandi.Model.ResponsePlanned;
import com.example.sikanla.maquettehandi.R;
import com.example.sikanla.maquettehandi.network.PlannedRequester;

import java.util.ArrayList;

/**
 * Created by Sikanla on 07/06/2017.
 */


public class DisplayResponsesPlanned extends DialogFragment {
    private DisplayResponsesAdapter displayResponsesPlannedAdapter;
    private ListView listView;

    private PlannedRequester plannedRequester;


    private ArrayList<PlannedRequest> plannedRequests;
    private ArrayList<ResponsePlanned> responsePlanneds;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.df_display_responses_planned, container, false);
        ArrayList<ResponsePlanned> displayResponsePlanned = new ArrayList<>();
        for (int i = 0; i < responsePlanneds.size(); i++) {
            //only add helpers related to the planned request:
            if (responsePlanneds.get(i).id_request == getArguments().getString("id_planned")) {
                displayResponsePlanned.add(responsePlanneds.get(i));
            }
        }

        displayResponsesPlannedAdapter = new DisplayResponsesAdapter(getActivity(), displayResponsePlanned);
        listView = (ListView) view.findViewById(R.id.df_display_responses_lv);
        listView.setAdapter(displayResponsesPlannedAdapter);
        return view;
    }

    public void setArrays(ArrayList<PlannedRequest> plannedRequests, ArrayList<ResponsePlanned> responsePlanneds) {
        this.responsePlanneds = responsePlanneds;
        this.plannedRequests = plannedRequests;
    }

}
