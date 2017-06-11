package com.example.sikanla.maquettehandi.UI.Activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.sikanla.maquettehandi.DialogFragment.HelpType_DF;
import com.example.sikanla.maquettehandi.GPSTracker;
import com.example.sikanla.maquettehandi.Model.PlannedRequest;
import com.example.sikanla.maquettehandi.R;
import com.example.sikanla.maquettehandi.network.PlannedRequester;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class FormPlannedRequestActi extends Activity implements HelpType_DF.DialogListener {


    private Button mPickDate;
    private int mYear;
    private int mMonth;
    private int mDay;
    private Button mPickTime;
    private int mHour;
    private int mMinute;
    private Button getMyPosition;
    private Button bClose;
    private Button bHelpType;
    private EditText localisationEt;
    private EditText commentaireEt;

    private String helpType = "";
    private Button bsend;
    private CheckBox checkBox;

    static final int DATE_DIALOG_ID = 0;
    static final int TIME_DIALOG_ID = 1;
    // The callback received when the user "sets" the date in the Dialog


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activ_form_planned_request);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(getResources().getColor(R.color.back1));


        mPickDate = (Button) findViewById(R.id.pickDate);
        mPickTime = (Button) findViewById(R.id.pickTime);
        bClose = (Button) findViewById(R.id.close_btn);
        bHelpType = (Button) findViewById(R.id.aidetype_btn);
        bsend = (Button) findViewById(R.id.send_planned_request_btn);
        localisationEt = (EditText) findViewById(R.id.localisation_fpA);
        commentaireEt = (EditText) findViewById(R.id.commentaire_fpA);
        checkBox = (CheckBox) findViewById(R.id.checkBox_fpA);
        getMyPosition = (Button) findViewById(R.id.localisation_get_my_position_fpA);
        final GPSTracker gps = new GPSTracker(this);
        final Geocoder geocoder = new Geocoder(this, Locale.getDefault());


        getMyPosition.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gps.canGetLocation()) {

                    try {
                        List<Address> adresses = geocoder.getFromLocation(gps.getLatitude(), gps.getLongitude(), 1);
                        StringBuilder stringBuilder = new StringBuilder();
                        if (adresses.size() > 0) {
                            Address adresse = adresses.get(0);

                            for (int i = 0; i < adresse.getMaxAddressLineIndex(); ++i)
                                stringBuilder.append(adresse.getAddressLine(i)).append("\n");

                            stringBuilder.append(adresse.getCountryName());

                        }
                        String adresseString = stringBuilder.toString();
                        localisationEt.setText(adresseString);


                    } catch (IOException e) {
                    }

                }
            }
        });

        bsend.setEnabled(false);
        bsend.setAlpha(.5f);

        startTextWatchers();

        // Set an OnClickListener on the Change The Date Button
        mPickDate.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }


        });


        // Set an OnClickListener on the Change The Time Button
        mPickTime.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);
            }
        });

        bClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        bHelpType.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                HelpType_DF help = new HelpType_DF();
                help.show(getFragmentManager(), "HelpType_DF");
            }
        });


        bsend.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                PlannedRequester plannedRequester = new PlannedRequester();
                plannedRequester.sendPlannedRequest(getApplicationContext(), helpType, commentaireEt.getText().toString(),
                        getEpoch(), localisationEt.getText().toString(), checkBox.isChecked() ? "yes" : "", new PlannedRequester.PostPlannedCB() {
                            @Override
                            public void onPlannedPosted(Boolean success) {
                                if (success) {
                                    gps.stopUsingGPS();
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "ERREUR", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });


        // Get the current date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            updateDisplay(0);
            updateDisplay(2);
        }
    };
    // The callback received when the user "sets" the time in the dialog
    private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            mHour = hourOfDay;
            mMinute = minute;
            updateDisplay(1);
            updateDisplay(2);
        }
    };


    // Update the date in the TextView
    private void updateDisplay(int type) {
        switch (type) {
            case 0:

                mPickDate.setText(new StringBuilder()
                        // Month is 0 based so add 1
                        .append(mDay).append("-").append(mMonth + 1).append("-")
                        .append(mYear).append("   "));
                break;
            case 1:
                mPickTime.setText(new StringBuilder().append(pad(mHour)).append(":").append((pad(mMinute))));
                break;

        }
    }

    // Prepends a "0" to 1-digit minutes
    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    // Create and return DatePickerDialog
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
                        mDay);
            case TIME_DIALOG_ID:
                return new TimePickerDialog(this, mTimeSetListener, mHour, mMinute,
                        true);
        }
        return null;
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

        if (areDateAndTimeValid() && isLocalisationSet()) {
            bsend.setEnabled(true);
            bsend.setAlpha(1f);
        }

    }

    private void startTextWatchers() {

        mPickTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //On user changes the text
                if (!areDateAndTimeValid() || helpType == "" || !isLocalisationSet()) {
                    bsend.setEnabled(false);
                    bsend.setAlpha(0.5f);
                } else {
                    bsend.setEnabled(true);
                    bsend.setAlpha(1f);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

        });

        mPickDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //On user changes the text
                if (!areDateAndTimeValid() || helpType.isEmpty() || !isLocalisationSet()) {
                    bsend.setEnabled(false);
                    bsend.setAlpha(0.5f);
                } else {
                    bsend.setEnabled(true);
                    bsend.setAlpha(1f);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

        });

        localisationEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!areDateAndTimeValid() || helpType.isEmpty() || !isLocalisationSet()) {
                    bsend.setEnabled(false);
                    bsend.setAlpha(0.5f);
                } else {
                    bsend.setEnabled(true);
                    bsend.setAlpha(1f);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    private boolean areDateAndTimeValid() {
        return (!mPickDate.getText().toString().matches("Date") && !mPickTime.getText().toString().matches("Heure"));
    }

    private boolean isLocalisationSet() {
        return !localisationEt.getText().toString().matches("");
    }

    private String getEpoch() {
        long epoch = 0;

        try {
            epoch = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm")
                    .parse(mPickDate.getText().toString() + " " + mPickTime.getText().toString())
                    .getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return String.valueOf(epoch);
    }
}