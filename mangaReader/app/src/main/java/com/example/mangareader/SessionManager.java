package com.example.mangareader;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "LOGIN";
    public static final String LOGIN = "IS_LOGIN";
    public static final String EMAIL = "EMAIL";
    public static final String PROFILE_PHOTO = "PROFILE_PHOTO";

    public SessionManager(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createSession(String email, String profile_image){
        editor.putBoolean(LOGIN, true);
        editor.putString(EMAIL, email);
        editor.putString(PROFILE_PHOTO, profile_image);
        editor.apply();
    }

    //    this method will save the nightmode State : True or False
    public void setNightModeState(Boolean state){
        editor = sharedPreferences.edit();
        editor.putBoolean("NightMode", state);
        editor.commit();
    }

    //    this method will load the night mode state
    public Boolean loadNightModeState(){
        Boolean state = sharedPreferences.getBoolean("NightMode", false);
        return state;
    }


    public boolean isLoggin(){
        return sharedPreferences.getBoolean(LOGIN, false);
    }

    public void checkLogin(){
        if (!this.isLoggin()){
            Intent i = new Intent(context, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
            ((MainActivity) context).finish();
        }
    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<>();
        user.put(EMAIL, sharedPreferences.getString(EMAIL, null));
        user.put(PROFILE_PHOTO, sharedPreferences.getString(PROFILE_PHOTO, null));

        return user;
    }

    public void logout(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, MainActivity.class);
        context.startActivity(i);
        ((MainActivity) context).finish();
    }

}
