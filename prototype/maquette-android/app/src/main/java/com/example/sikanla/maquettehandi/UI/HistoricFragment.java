package com.example.sikanla.maquettehandi.UI;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.example.sikanla.maquettehandi.DialogFragment.HelpType_DF;
import com.example.sikanla.maquettehandi.R;
import com.example.sikanla.maquettehandi.identification.User;
import com.example.sikanla.maquettehandi.network.ImageRequester;
import com.squareup.picasso.Picasso;

/**
 * Created by Sikanla on 13/02/2017.
 */

public class HistoricFragment extends Fragment {

    public HistoricFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_history, container, false);

    }



    @Override
    public void onActivityCreated(Bundle savedInstance) {
        super.onActivityCreated(savedInstance);



    }


}



