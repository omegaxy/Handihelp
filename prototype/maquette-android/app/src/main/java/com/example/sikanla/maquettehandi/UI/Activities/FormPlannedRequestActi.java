package com.example.sikanla.maquettehandi.UI.Activities;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.sikanla.maquettehandi.DialogFragment.HelpType_DF;
import com.example.sikanla.maquettehandi.Model.PlannedRequest;
import com.example.sikanla.maquettehandi.R;

import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class FormPlannedRequestActi extends Activity implements HelpType_DF.DialogListener {


    private TextView dateDisplay;
    private Button mPickDate;
    private int mYear;
    private int mMonth;
    private int mDay;
    private Button mPickTime;
    private TextView mTimeDisplay;
    private int mHour;
    private int mMinute;
    private Button bClose;
    private Button bHelpType;
    //private TextView mEpochTimeDisplay;
    //private long epochTime;
    Date date;
    private String helpType;
    private Button bsend;

    static final int DATE_DIALOG_ID = 0;
    static final int TIME_DIALOG_ID = 1;
    // The callback received when the user "sets" the date in the Dialog


    TimePicker timePicker;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_planned_request);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(getResources().getColor(R.color.backg));

        //timePicker.setIs24HourView(true);
        // Capture our View elements
        /*dateDisplay = (TextView) findViewById(R.id.dateDisplay);
        mTimeDisplay = (TextView) findViewById(R.id.timeDisplay);*/
        //mEpochTimeDisplay = (TextView) findViewById(R.id.epochTimeDisplay);
        mPickDate = (Button) findViewById(R.id.pickDate);
        mPickTime = (Button) findViewById(R.id.pickTime);
        bClose = (Button) findViewById(R.id.close_btn);
        bHelpType = (Button) findViewById(R.id.aidetype_btn);
        bsend = (Button) findViewById(R.id.send_planned_request_btn);

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
            public void onClick(View v){
            
        }
        });



        // Get the current date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        //date = c.getTime();	//get the current date time
        //epochTime = date.getTime();	//get the epoch time
        // = setEpochTime(mYear, mMonth,  mDay,  mHour,mMinute);

        /*// Display the current date
        updateDisplay(0);
        //display the current time
        updateDisplay(1);*/
        //display Epoch time
        //updateDisplay(2);




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
    //replace this update display function with the access to DB
    private void updateDisplay(int type) {
        switch (type) {
            case 0:
                /*dateDisplay.setText(new StringBuilder()
                        // Month is 0 based so add 1
                        .append(mDay).append("-").append(mMonth + 1).append("-")
                        .append(mYear).append("   "));*/
                mPickDate.setText(new StringBuilder()
                        // Month is 0 based so add 1
                        .append(mDay).append("-").append(mMonth + 1).append("-")
                        .append(mYear).append("   "));
                //mEpochTimeDisplay.setText(new StringBuilder().append(epochTime));
                break;
            case 1:
                /*mTimeDisplay.setText(new StringBuilder().append(pad(mHour)).append(":").append((mMinute)));*/
                mPickTime.setText(new StringBuilder().append(pad(mHour)).append(":").append((mMinute)));
                //mEpochTimeDisplay.setText(new StringBuilder().append(epochTime));
                break;
            /*case 2:
                mEpochTimeDisplay.setText(Long.toString(setEpochTime(mYear, mMonth, mDay, mHour,
                        mMinute)));
                break;*/
        }
    }

    /*private long setEpochTime(int mYear2, int mMonth2, int mDay2, int mHour2,
                              int mMinute2) {


        StringBuilder str = new StringBuilder().append(mYear2).append('-')
                .append(mMonth2).append('-').append(mDay2).append("T")
                .append(mHour2).append(':')
                .append(mMinute2).append(':')
                .append("00.000-0700");    //truncating

        //String str = "Jun 13 2003 23:11:52.454 UTC";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Date date = null;
        try {
            date = df.parse(str.toString());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        long epoch = date.getTime();
        return epoch;
    }*/


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
    }

    private boolean areInputsValids() {
        if (mPickDate.getText().toString().matches("Date") || mPickTime.getText().toString().matches("Heure") || bHelpType.getText().toString().matches("Type d'aide")) {
            return false;
        }
        return true;
    }


    private void startTextWatchers() {

        mPickTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //On user changes the text
                if (!areInputsValids()) {
                    bsend.setEnabled(false);
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
                if (!areInputsValids()) {
                    bsend.setEnabled(false);
                } else {
                    bsend.setEnabled(true);
                    bsend.setAlpha(1f);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

        });

        bHelpType.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //On user changes the text
                if (!areInputsValids()) {
                    bsend.setEnabled(false);
                } else {
                    bsend.setEnabled(true);
                    bsend.setAlpha(1f);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

        });
    }
}