package com.example.sikanla.maquettehandi.network;

import android.content.SharedPreferences;

import com.example.sikanla.maquettehandi.identification.User;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Sikanla on 08/05/2017.
 */

public class FirebaseNotification extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        SharedPreferences prefs = this.getSharedPreferences(User.MY_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("androidid", refreshedToken);
        editor.commit();

    }
}
