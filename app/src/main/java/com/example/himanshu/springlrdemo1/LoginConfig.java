package com.example.himanshu.springlrdemo1;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;


public class LoginConfig {

    private String facebookAppId;
    private ArrayList<String> facebookPermissions;
    private GoogleApiClient googleApiClient;
    private Activity activity;
    private LoginCallbacks callback;


    public LoginConfig(@NonNull Activity activity, LoginCallbacks callback) {
        this.activity = activity;
        this.callback = callback;
    }

    public Activity getActivity() {
        return activity;
    }

    public LoginCallbacks getCallback() {
        return callback;
    }

    public GoogleApiClient getGoogleApiClient() {
        return googleApiClient;
    }

    public void setGoogleApiClient(GoogleApiClient googleApiClient) {
        this.googleApiClient = googleApiClient;
    }

    public String getFacebookAppId() {
        return facebookAppId;
    }

    public void setFacebookAppId(String facebookAppId) {
        this.facebookAppId = facebookAppId;
    }

    public ArrayList<String> getFacebookPermissions() {
        return facebookPermissions;
    }

    public void setFacebookPermissions(ArrayList<String> facebookPermissions) {
        this.facebookPermissions = facebookPermissions;
    }

    public static ArrayList<String> getDefaultFacebookPermissions() {
        ArrayList<String> defaultPermissions = new ArrayList<>();
        defaultPermissions.add("public_profile");
        defaultPermissions.add("email");
        defaultPermissions.add("user_birthday");
        return defaultPermissions;
    }

}
