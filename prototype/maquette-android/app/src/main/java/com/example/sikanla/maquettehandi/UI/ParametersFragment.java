package com.example.sikanla.maquettehandi.UI;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.sikanla.maquettehandi.R;
import com.example.sikanla.maquettehandi.identification.User;
import com.example.sikanla.maquettehandi.network.ImageRequester;
import com.squareup.picasso.Picasso;


/**
 * Created by Sikanla on 20/05/2017.
 */

public class ParametersFragment extends Fragment {
    private Button uploadB;
    private ImageView imageView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.parameters_fragment, container, false);
        loadImage(view);
        setUpUploadButton(view);

        return view;
    }

    private void setUpUploadButton(View view) {
        uploadB = (Button) view.findViewById(R.id.parameters_upload_button);
        uploadB.setOnClickListener(new View.OnClickListener() {
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
    }

    private void loadImage(View view) {
        User user = new User();
        imageView = (ImageView) view.findViewById(R.id.parameters_image);
        final ImageRequester imageRequester = new ImageRequester();
        imageRequester.getImage(user.getUserId(), getContext(), new ImageRequester.ImageInterface() {
            @Override
            public void getUrl(String url) {
                if (url != null)
                    Picasso.with(getActivity()).load(url).into(imageView);
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(Bundle savedInstance) {
        super.onActivityCreated(savedInstance);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            ImageRequester imageRequester = new ImageRequester();
            if (requestCode == 10) {
                Uri imageUri = data.getData();

                imageRequester.sendImage(imageUri, getActivity(), new ImageRequester.ImageInterface2() {
                    @Override
                    public void onImageSent(Boolean success) {
                        if (success) {
                            NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
                            User user = new User();
                            View headerView = navigationView.getHeaderView(0);
                            final ImageView imageViewHeader = (ImageView) headerView.findViewById(R.id.user_pp);
                            ImageRequester imageRequester = new ImageRequester();
                            imageRequester.getImage(user.getUserId(), getContext(), new ImageRequester.ImageInterface() {
                                @Override
                                public void getUrl(String url) {
                                    if (url != null) {
                                        Picasso.with(getActivity()).load(url).into(imageViewHeader);
                                        Picasso.with(getContext()).load(url).into(imageView);
                                    }
                                }
                            });
                        }
                    }
                });


            }
        }

    }


}
