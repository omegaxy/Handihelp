package com.example.sikanla.maquettehandi.UI.Menu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.sikanla.maquettehandi.Adapters.FriendsAdapter;
import com.example.sikanla.maquettehandi.R;
import com.example.sikanla.maquettehandi.network.FriendRequester;

import java.util.ArrayList;

/**
 * Created by Sikanla on 25/05/2017.
 */

public class FriendsFragment extends Fragment {

    public SwipeRefreshLayout swipeContainer;
    public ListView listView;
    private FriendsAdapter adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, container, false);
        ArrayList<String> friends = new ArrayList<>();
        adapter = new FriendsAdapter(getActivity(), friends);
        listView = (ListView) view.findViewById(R.id.lvFriends);

        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainerFriends);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchMessages();
            }
        });

        fetchMessages();
        return view;
    }

    private void fetchMessages() {
        FriendRequester friendRequester = new FriendRequester();
        friendRequester.getFriends(getActivity(), new FriendRequester.GetFriendCB() {
            @Override
            public void getArrayFriends(ArrayList<String> arrayList, Boolean success) {
                if (success) {
                    adapter.clear();
                    adapter.addAll(arrayList);
                    listView.setAdapter(adapter);
                    swipeContainer.setRefreshing(false);

                } else {
                    swipeContainer.setRefreshing(false);
                    // todo display error message check your connection
                }
            }
        });

    }
}
