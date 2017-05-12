package com.example.sikanla.maquettehandi.DialogFragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;

import com.example.sikanla.maquettehandi.R;

/**
 * Created by Sikanla on 06/05/2017.
 */

public class PickAideDialogFragment extends DialogFragment {
    private View rootView;
    private Window window;


    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
         window = getDialog().getWindow();
        assert window != null;
        window.setBackgroundDrawableResource(R.color.greyy);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity(), R.style.MyCustomThemeDialog);
        LayoutInflater inflater = getActivity().getLayoutInflater();

        rootView = inflater.inflate(R.layout.pick_aide_dialog, null);

        final AlertDialog.Builder builder1 = builder.setView(rootView);

        return builder1.create();


    }

}
