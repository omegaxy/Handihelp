package com.example.sikanla.maquettehandi.UI;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.sikanla.maquettehandi.ContactListAdapter;
import com.example.sikanla.maquettehandi.DialogFragment.ProfileDialogFragment;
import com.example.sikanla.maquettehandi.Model.Contact;
import com.example.sikanla.maquettehandi.Model.PlannedRequest;
import com.example.sikanla.maquettehandi.PlannedAdapter;
import com.example.sikanla.maquettehandi.R;
import com.example.sikanla.maquettehandi.identification.User;
import com.example.sikanla.maquettehandi.network.ImageRequester;
import com.example.sikanla.maquettehandi.network.MessageRequester;
import com.example.sikanla.maquettehandi.network.PlannedRequester;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by Sikanla on 13/02/2017.
 */

public class InstantFragment extends Fragment {
    private Button testButton;
    private Button testButtonImage, uploaB;
    private ImageView imageView;
    RequestQueue requestQueue;
    private SwipeRefreshLayout swipeContainer;
    private ListView listView;
    private ContactListAdapter adapter;


    public InstantFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_instant, container, false);
        ArrayList<Contact> arrayOfContacts = new ArrayList<Contact>();
        adapter = new ContactListAdapter(getActivity(), arrayOfContacts);
        listView = (ListView) view.findViewById(R.id.lvMessages);

        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainerMessages);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchMessages();
            }
        });

        fetchMessages();
        return view;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        //testMethods();
    }

    @Override
    public void onActivityCreated(Bundle savedInstance) {
        super.onActivityCreated(savedInstance);


    }

    private void fetchMessages() {
        final MessageRequester messageRequester = new MessageRequester();
        messageRequester.getContacts(getActivity(), new MessageRequester.MessagesCB() {

            @Override
            public void getArrayContacts(ArrayList<Contact> s, Boolean success) {
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


            @Override
            public void getArrayMessages(ArrayList<String> arrayList) {

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
        testButtonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageRequester imageRequest = new ImageRequester();
                imageRequest.getImage(user.getUserId(), getActivity(), new ImageRequester.BitmapInterface() {
                    @Override
                    public void getBitmap(Bitmap bitmap) {
                        imageView.setImageBitmap(bitmap);
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
