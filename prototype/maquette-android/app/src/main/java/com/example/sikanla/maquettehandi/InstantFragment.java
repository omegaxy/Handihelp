package com.example.sikanla.maquettehandi;

import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * Created by Sikanla on 13/02/2017.
 */

public class InstantFragment extends Fragment {
    private Button testButton;

    public InstantFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_instant, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
       testButton = (Button) getActivity().findViewById(R.id.button11);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
       ProfileDialogFragment profileDialogFragment =new ProfileDialogFragment();
               profileDialogFragment.show(getFragmentManager(),"ProfileDialogFragment");

            }
        });
    }
}
