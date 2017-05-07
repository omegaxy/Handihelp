package com.example.sikanla.maquettehandi;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.sikanla.maquettehandi.identification.User;
import com.example.sikanla.maquettehandi.network.ImageRequester;


/**
 * Created by Sikanla on 13/02/2017.
 */

public class InstantFragment extends Fragment {
    private Button testButton;
    private Button testButtonImage;
    private ImageView imageView;
    RequestQueue requestQueue;
    private Bitmap bitmap1;

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
        requestQueue = Volley.newRequestQueue(getActivity());
        imageView = (ImageView) getActivity().findViewById(R.id.testimageIV);
        testButton = (Button) getActivity().findViewById(R.id.button11);
        testButtonImage = (Button) getActivity().findViewById(R.id.testimage);
        testButtonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageRequester imageRequest = new ImageRequester();
                imageRequest.getImage("2", getActivity(), new ImageRequester.BitmapInterface() {
                    @Override
                    public void getBitmap(Bitmap bitmap) {
                        bitmap1 = bitmap;
                        imageView.setImageBitmap(bitmap1);
                    }
                });
            }
        });


        testButton.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                User user = new User();
                args.putString("firstname", user.getFirstName());
                args.putInt("birth_year", user.getAge());
                args.putString("userid", user.getUserId());
                ProfileDialogFragment profileDialogFragment = new ProfileDialogFragment();
                profileDialogFragment.setArguments(args);
                profileDialogFragment.show(getFragmentManager(), "ProfileDialogFragment");

            }
        });
    }
}
