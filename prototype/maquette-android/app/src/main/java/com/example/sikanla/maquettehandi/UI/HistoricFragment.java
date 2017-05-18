package com.example.sikanla.maquettehandi.UI;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.example.sikanla.maquettehandi.DialogFragment.HelpTypeDiaolgFragment;
import com.example.sikanla.maquettehandi.DialogFragment.ProfileDialogFragment;
import com.example.sikanla.maquettehandi.R;
import com.example.sikanla.maquettehandi.identification.User;
import com.example.sikanla.maquettehandi.network.ImageRequester;
import com.squareup.picasso.Picasso;

import java.io.IOException;

/**
 * Created by Sikanla on 13/02/2017.
 */

public class HistoricFragment extends Fragment {

    ImageView imageView1;

    private Button testButton, helpButton;
    private Button testButtonImage, uploaB;
    private ImageView imageView;
    private int numberButton=0;
    private TextView text;
    RequestQueue requestQueue;

    public HistoricFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_history, container, false);

    }

    public interface EditNameDialogListener {
        void onFinishEditDialog(String str);
    }

    public void onFinishEditDialog(String str) {
        numberButton = Integer.parseInt(str);
        text.setText(str);
    }

    @Override
    public void onActivityCreated(Bundle savedInstance) {
        super.onActivityCreated(savedInstance);
        imageView = (ImageView) getActivity().findViewById(R.id.imagehist);
        User user = new User();
        Picasso.with(getContext()).setLoggingEnabled(true);

        final ImageRequester imageRequester = new ImageRequester();
        imageRequester.getImage(user.getUserId(), getContext(), new ImageRequester.ImageInterface() {
            @Override
            public void getUrl(String url) {
                if (url != null && imageView != null)
                    Picasso.with(getContext()).load(url).fit().into(imageView);
            }
        });

        text = (TextView) getActivity().findViewById(R.id.textView1);
        helpButton = (Button) getActivity().findViewById(R.id.helpButton);

        helpButton.setOnClickListener(new View.OnClickListener()  {

            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                HelpTypeDiaolgFragment help = new HelpTypeDiaolgFragment();
                help.setArguments(args);

                help.show(getActivity().getFragmentManager(), "HelpTypeDiaolgFragment");

            }

        });

    }


    private void testMethods() {
        final User user = new User();
      /*  uploaB = (Button) getActivity().findViewById(R.id.uploadB);
        requestQueue = Volley.newRequestQueue(getActivity());
        imageView = (ImageView) getActivity().findViewById(R.id.testimageIV);
        testButton = (Button) getActivity().findViewById(R.id.button11);
        testButtonImage = (Button) getActivity().findViewById(R.id.testimage);
        */

/*
        testButtonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageRequester imageRequest = new ImageRequester();
                imageRequest.getImage(user.getUserId(), getActivity(), new ImageRequester.ImageInterface() {
                    @Override
                    public void getUrl(String s) {
                        // imageView.setImageBitmap(bitmap);
                    }
                });
            }
        });

        uploaB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT <= 19) {
                    Intent i = new Intent();
                    i.setType("image/jpg");
                    i.setAction(Intent.ACTION_GET_CONTENT);
                    i.addCategory(Intent.CATEGORY_OPENABLE);
                    startActivityForResult(i, 10);
                } else if (Build.VERSION.SDK_INT > 19) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 10);
                }
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
        */
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            ImageRequester imageRequester = new ImageRequester();
            if (requestCode == 10) {

                Uri imageUri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                    imageRequester.sendImage(bitmap, getActivity());
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }

    }
}



