package com.example.sikanla.maquettehandi.UI.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.sikanla.maquettehandi.DialogFragment.HelpType_DF;
import com.example.sikanla.maquettehandi.GPSTracker;
import com.example.sikanla.maquettehandi.Model.PlannedRequest;
import com.example.sikanla.maquettehandi.R;
import com.example.sikanla.maquettehandi.network.InstantRequester;

/**
 * Created by Cecile on 22/05/2017.
 */

public class FormInstantRequestActi extends Activity implements HelpType_DF.DialogListener {
    private Button bSend, bClose, bHelpType;
    private EditText comment;
    private TextView adress, textViewError;
    private String helpType = "";
    private ImageView imageView;
    private ProgressBar progressBar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_instant_request);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(getResources().getColor(R.color.back1));

        bSend = (Button) findViewById(R.id.send_instant_request_btn);
        bClose = (Button) findViewById(R.id.close_btn);
        bHelpType = (Button) findViewById(R.id.aidetype_btn);
        comment = (EditText) findViewById(R.id.comment);
        textViewError = (TextView) findViewById(R.id.error_tv_fiA);
        imageView = (ImageView) findViewById(R.id.imageView_fiA2);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_fiA);
        

        bSend.setEnabled(false);
        bSend.setAlpha(.5f);

        bClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        bHelpType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelpType_DF help = new HelpType_DF();
                help.show(getFragmentManager(), "HelpType_DF");
            }
        });

        final GPSTracker gps = new GPSTracker(this);

        if (gps.canGetLocation()){
            progressBar.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);

        }


        bSend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (gps.canGetLocation()) {
                    InstantRequester instantRequester = new InstantRequester();
                    instantRequester.sendInstantRequest(getApplicationContext(), helpType, comment.getText().toString(), String.valueOf(gps.getLongitude()), String.valueOf(gps.getLatitude()), new InstantRequester.PostInstantCB() {
                        @Override
                        public void onInstantCB(boolean success) {
                            if (success) {
                                gps.stopUsingGPS();
                                finish();
                            } else {
                                textViewError.setVisibility(View.VISIBLE);
                                textViewError.setText("Erreur, Demande non Créée");

                            }
                        }
                    });

                } else {
                    textViewError.setVisibility(View.VISIBLE);
                    textViewError.setText("Position Non Trouvée");

                }
            }
        });
    }


    @Override
    public void onDialogClick(String id) {
        switch (id) {
            case "1":
                bHelpType.setText(PlannedRequest.n1);
                bHelpType.setBackgroundColor(getResources().getColor(R.color.type1));
                break;
            case "2":
                bHelpType.setText(PlannedRequest.n2);
                bHelpType.setBackgroundColor(getResources().getColor(R.color.type2));
                break;
            case "3":
                bHelpType.setText(PlannedRequest.n3);
                bHelpType.setBackgroundColor(getResources().getColor(R.color.type3));
                break;
            case "4":
                bHelpType.setText(PlannedRequest.n4);
                bHelpType.setBackgroundColor(getResources().getColor(R.color.type4));
                break;
            case "5":
                bHelpType.setText(PlannedRequest.n5);
                bHelpType.setBackgroundColor(getResources().getColor(R.color.type5));
                break;
            case "6":
                bHelpType.setText(PlannedRequest.n6);
                bHelpType.setBackgroundColor(getResources().getColor(R.color.type6));
                break;
            case "7":
                bHelpType.setText(PlannedRequest.n7);
                bHelpType.setBackgroundColor(getResources().getColor(R.color.type7));
                break;
            case "8":
                bHelpType.setText(PlannedRequest.n8);
                bHelpType.setBackgroundColor(getResources().getColor(R.color.type8));
                break;
            case "9":
                bHelpType.setText(PlannedRequest.n9);
                bHelpType.setBackgroundColor(getResources().getColor(R.color.type9));
                break;
            case "10":
                bHelpType.setText(PlannedRequest.n10);
                bHelpType.setBackgroundColor(getResources().getColor(R.color.type10));
                break;
            case "11":
                bHelpType.setText(PlannedRequest.n11);
                bHelpType.setBackgroundColor(getResources().getColor(R.color.type11));
                break;
        }
        this.helpType = id;

        bSend.setEnabled(true);
        bSend.setAlpha(1f);

    }

}






