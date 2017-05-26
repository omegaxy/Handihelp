package com.example.sikanla.maquettehandi.DialogFragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sikanla.maquettehandi.Adapters.DisplayMessageAdapter;
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
    private EditText textTosend;
    private Button sendText;
    private MessageRequester messageRequester;
    String idContact;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.df_display_message, container, false);
        arrayOfMessages = new ArrayList<Message>();
        displayMessageAdapter = new DisplayMessageAdapter(getActivity(), arrayOfMessages);
        listView = (ListView) view.findViewById(R.id.lv_display_message);
        textTosend = (EditText) view.findViewById(R.id.df_display_message_edittext_send);
        sendText = (Button) view.findViewById(R.id.df_display_message_button_send);
        messageRequester = new MessageRequester();

        idContact = getArguments().getString("id");
        
        sendText.setEnabled(false);
        sendText.setAlpha(0.5f);
        sendText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendText();
            }
        });


        textViewName = (TextView) view.findViewById(R.id.df_display_message_textview_name);
        textViewName.setText(getArguments().getString("firstname"));
        fetchMessages(idContact);
        sendButtonBehaviour();
        return view;
    }


    private void sendText() {

        messageRequester.sendMessage(getActivity(), textTosend.getText().toString(), idContact, new MessageRequester.SendMessagesCB() {
            @Override
            public void onMessageReceived(Boolean success) {
                if (success) {
                    displayMessageAdapter.add(new Message(textTosend.getText().toString(), true));
                    textTosend.setText("");

                }
            }
        });
    }


    private void fetchMessages(String id) {
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

    private void sendButtonBehaviour() {
        textTosend.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (textTosend.getText().toString().matches("")) {
                    sendText.setEnabled(false);
                    sendText.setAlpha(0.5f);
                } else {
                    sendText.setAlpha(1f);
                    sendText.setEnabled(true);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }
}

