package com.example.sikanla.maquettehandi.identification;

import android.app.Application;
import android.content.SharedPreferences;

/**
 * Created by Sikanla on 02/05/2017.
 */

public class User extends Application {

    private String APIKEY = "";
    private String userId;
    private String firstName;
    private String surName;
    private int birthYear;
    private String email;
    private SharedPreferences sharedPreferences;
    public static final String MY_PREFS_NAME = "handiPref";


    public User() {
    }

    public void saveUserOnPhone(String APIKEY, String userId, String firstName,
                                String surName, int birthYear, String email) {
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("APIKEY", APIKEY);
        editor.putString("userid", userId);
        editor.putString("firstname", firstName);
        editor.putString("surname", surName);
        editor.putString("email", email);
        editor.putInt("birthyear", birthYear);

        editor.commit();
    }

    public boolean isUserLogedIn() {
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String str = prefs.getString("APIKEY", "");
        return !str.isEmpty();
    }

    public void loadUser() {
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        APIKEY = prefs.getString("APIKEY", APIKEY);
        userId = prefs.getString("userid", userId);
        firstName = prefs.getString("firstname", firstName);
        surName = prefs.getString("surname", surName);
        email = prefs.getString("email", email);
        birthYear = prefs.getInt("birthyear", birthYear);

    }

    public void setApikey(String apikey) {
        this.APIKEY = apikey;
    }

    public String getAPIKEY() {
        return APIKEY;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
