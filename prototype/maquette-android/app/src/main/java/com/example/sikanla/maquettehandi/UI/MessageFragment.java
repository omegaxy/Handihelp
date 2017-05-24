package com.example.sikanla.maquettehandi.UI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.sikanla.maquettehandi.Adapters.ContactListAdapter;
import com.example.sikanla.maquettehandi.Model.Contact;
import com.example.sikanla.maquettehandi.R;
import com.example.sikanla.maquettehandi.network.MessageRequester;

import java.util.ArrayList;


/**
 * Created by Sikanla on 13/02/2017.
 */

public class MessageFragment extends Fragment {
    private SwipeRefreshLayout swipeContainer;
    private ListView listView;
    private ContactListAdapter adapter;


    public MessageFragment() {
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
}
