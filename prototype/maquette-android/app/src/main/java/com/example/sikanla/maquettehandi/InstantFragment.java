package com.example.sikanla.maquettehandi;

import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.sikanla.maquettehandi.identification.User;


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
                Bundle args =new Bundle();
                User user = new User();
                args.putString("firstname", user.getFirstName());
                args.putInt("birth_year", user.getAge());
                ProfileDialogFragment profileDialogFragment = new ProfileDialogFragment();
                profileDialogFragment.setArguments(args);
                profileDialogFragment.show(getFragmentManager(), "ProfileDialogFragment");

            }
        });
    }
}
