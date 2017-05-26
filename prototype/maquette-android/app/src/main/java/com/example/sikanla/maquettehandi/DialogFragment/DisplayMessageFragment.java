package com.example.sikanla.maquettehandi.DialogFragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sikanla.maquettehandi.Adapters.ContactListAdapter;
import com.example.sikanla.maquettehandi.Adapters.DisplayMessageAdapter;
import com.example.sikanla.maquettehandi.Model.Contact;
import com.example.sikanla.maquettehandi.Model.Message;
import com.example.sikanla.maquettehandi.R;
import com.example.sikanla.maquettehandi.network.MessageRequester;

import java.util.ArrayList;

/**
 * Created by Sikanla on 26/05/2017.
 */

public class DisplayMessageFragment extends DialogFragment {
    private DisplayMessageAdapter displayMessageAdapter;
    private ListView listView;
    private TextView textViewName;
    private ArrayList<Message> arrayOfMessages;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.df_display_message, container, false);
        arrayOfMessages= null;
        arrayOfMessages = new ArrayList<Message>();
        displayMessageAdapter = new DisplayMessageAdapter(getActivity(), arrayOfMessages);
        listView = (ListView) view.findViewById(R.id.lv_display_message);
        textViewName = (TextView) view.findViewById(R.id.df_display_message_textview_name);
        textViewName.setText(getArguments().getString("firstname"));
        String id = getArguments().getString("id");
        fetchMessages(id);
        return view;
    }

    private void fetchMessages(String id) {
        MessageRequester messageRequester = new MessageRequester();
        messageRequester.getMessage(getActivity(), id, new MessageRequester.GetMessagesCB() {
            @Override
            public void onMessagesReceived(ArrayList<Message> arrayList, Boolean success) {
                if (success) {
                    displayMessageAdapter.clear();
                    displayMessageAdapter.addAll(arrayList);
                    listView.setAdapter(displayMessageAdapter);
                }
            }
        });
    }


}
