package com.example.sikanla.maquettehandi.Model;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.sikanla.maquettehandi.network.AllRequest;
import com.example.sikanla.maquettehandi.network.FriendRequester;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sikanla on 02/05/2017.
 */

public class User extends Application {

    private static String APIKEY = "";
    private static String userId;
    private static String firstName;
    private static String surName;
    private static int birthYear;
    private static String email;
    private SharedPreferences sharedPreferences;
    public static final String MY_PREFS_NAME = "handiPref";
    private static ArrayList<String> listFriendsIds;


    public User() {
    }

    public void saveUserOnPhone(Context context, String APIKEY, String userId, String firstName,
                                String surName, int birthYear, String email) {
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("APIKEY", APIKEY);
        editor.putString("userid", userId);
        editor.putString("firstname", firstName);
        editor.putString("surname", surName);
        editor.putString("email", email);
        editor.putInt("birthyear", birthYear);
        fetchFriendsList(context);
        editor.commit();
    }

    public boolean isUserLoggedIn(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String str = prefs.getString("APIKEY", "");
        return !str.isEmpty();
    }

    public void deleteLocalUser(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("APIKEY", "");
        editor.putString("userid", "");
        editor.putString("firstname", "");
        editor.putString("surname", "");
        editor.putString("email", "");
        editor.putInt("birthyear", 0);
        editor.commit();


        listFriendsIds = null;
    }


    public void loadUser(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        APIKEY = prefs.getString("APIKEY", APIKEY);
        userId = prefs.getString("userid", userId);
        firstName = prefs.getString("firstname", firstName);
        surName = prefs.getString("surname", surName);
        email = prefs.getString("email", email);
        birthYear = prefs.getInt("birthyear", birthYear);
        fetchFriendsList(context);

    }

    public void fetchFriendsList(Context context) {
        FriendRequester friendRequester = new FriendRequester();
        friendRequester.getFriends(context, new FriendRequester.GetFriendCB() {
            @Override
            public void getArrayFriends(ArrayList<String> arrayList, Boolean success) {
                if (success)
                    listFriendsIds = arrayList;
            }
        });
    }

    public void addFriend(String id_friend) {
        this.listFriendsIds.add(id_friend);
    }

    public void deleteFriend(String id_friend) {
        listFriendsIds.remove(id_friend);
    }

    public ArrayList<String> getFriendList() {
        return listFriendsIds;
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

    public int getAge() {
        return 2017 - birthYear;
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



    public void saveAndroidIdtoServer(final Context context) {

        SharedPreferences prefs = context.getSharedPreferences(User.MY_PREFS_NAME, MODE_PRIVATE);
        String str = prefs.getString("androidid", "");

        Map<String, String> parameters = new HashMap<>();
        parameters.put("androidid", str);
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", getAPIKEY());
        AllRequest.getInstance(context)
                .sendRequest(AllRequest.POST, parameters, headers, "/user/androidid", new AllRequest.CallBackConnector() {
                    @Override
                    public void CallBackOnConnect(String response, Boolean success) {

                    }
                });
    }

    public void deleteAndroidIFromServer(final Context context) {

        Map<String, String> parameters = new HashMap<>();
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", getAPIKEY());
        AllRequest.getInstance(context)
                .sendRequest(AllRequest.PUT, parameters, headers, "/user/androidid", new AllRequest.CallBackConnector() {
                    @Override
                    public void CallBackOnConnect(String response, Boolean success) {

                    }
                });
    }
}
