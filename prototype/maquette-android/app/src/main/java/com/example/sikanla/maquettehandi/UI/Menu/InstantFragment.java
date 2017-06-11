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
 * Created by Sikanla on 11/06/2017.
 */

public class InstantFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, container, false);



       // fetchMessages();
        return view;
    }

    private void fetchMessages() {
        FriendRequester friendRequester = new FriendRequester();
        friendRequester.getFriends(getActivity(), new FriendRequester.GetFriendCB() {
            @Override
            public void getArrayFriends(ArrayList<String> arrayList, Boolean success) {
                if (success) {


                } else {

                }
            }
        });

    }
}
