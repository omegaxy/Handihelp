package com.example.sikanla.maquettehandi.UI.Menu;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.sikanla.maquettehandi.Adapters.ContactListAdapter;
import com.example.sikanla.maquettehandi.Adapters.PlannedRequestCardAdapter;
import com.example.sikanla.maquettehandi.Model.Contact;
import com.example.sikanla.maquettehandi.R;

import java.util.ArrayList;

/**
 * Created by Sikanla on 25/05/2017.
 */

public class FriendsFragment extends Fragment {

    public SwipeRefreshLayout swipeContainer;
    public ListView listView;
    private ContactListAdapter adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, container, false);
        ArrayList<Contact> arrayOfContacts = new ArrayList<Contact>();
        adapter = new ContactListAdapter(getActivity(), arrayOfContacts);
        listView = (ListView) view.findViewById(R.id.lvFriends);

        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainerFriends);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //fetchMessages();
            }
        });

        //fetchMessages();
        return view;
    }
}
