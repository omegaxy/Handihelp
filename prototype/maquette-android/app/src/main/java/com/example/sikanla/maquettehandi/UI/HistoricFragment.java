package com.example.sikanla.maquettehandi.UI;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.example.sikanla.maquettehandi.DialogFragment.HelpType_DF;
import com.example.sikanla.maquettehandi.R;
import com.example.sikanla.maquettehandi.identification.User;
import com.example.sikanla.maquettehandi.network.ImageRequester;
import com.squareup.picasso.Picasso;

/**
 * Created by Sikanla on 13/02/2017.
 */

public class HistoricFragment extends Fragment {

    ImageView imageView1;

    private Button testButton, helpButton;
    private Button testButtonImage, uploaB;
    private ImageView imageView;
    private int numberButton = 0;
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

        helpButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                HelpType_DF help = new HelpType_DF();
                help.setArguments(args);

                help.show(getActivity().getFragmentManager(), "HelpType_DF");

            }

        });

    }


}



