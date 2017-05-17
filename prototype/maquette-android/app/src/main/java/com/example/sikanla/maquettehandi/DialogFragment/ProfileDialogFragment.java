package com.example.sikanla.maquettehandi.DialogFragment;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sikanla.maquettehandi.R;
import com.example.sikanla.maquettehandi.network.ImageRequester;

public class ProfileDialogFragment extends DialogFragment {

    private View rootView;
    private TextView fistNameTv;
    private TextView ageTv;
    private ImageView imageViewPP;


    public interface DialogListener {
        void onDialogClick(DialogFragment dialog, int id);
    }

    DialogListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            //     mListener = (DialogListener) ((MainActivity) context).getSupportFragmentManager()
            //            .findFragmentById(R.id.container);
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement DialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        rootView = inflater.inflate(R.layout.fragment_profile, null);
        fistNameTv = (TextView) rootView.findViewById(R.id.firstnamedialog);
        ageTv = (TextView) rootView.findViewById(R.id.agedialog);
        imageViewPP = (ImageView) rootView.findViewById(R.id.profileImageV);

        fistNameTv.setText(getArguments().getString("firstname"));
        ageTv.setText((String.valueOf(getArguments().getInt("birth_year"))));

        ImageRequester imageRequest = new ImageRequester();
        imageRequest.getImage(getArguments().getString("userid"), getActivity(), new ImageRequester.ImageInterface() {
            @Override
            public void getUrl(String s) {
                //imageViewPP.setImageBitmap(bitmap);
            }
        });

        final AlertDialog.Builder builder1 = builder.setView(rootView)
                .setPositiveButton("Do something", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        return builder1.create();


    }
}
