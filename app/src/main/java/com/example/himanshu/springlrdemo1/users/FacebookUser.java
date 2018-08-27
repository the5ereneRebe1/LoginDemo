package com.example.himanshu.springlrdemo1.users;

import com.facebook.AccessToken;
import com.google.firebase.database.Exclude;

public class FacebookUser extends SmartUser {
    private String profileName;
    private AccessToken accessToken;

    public FacebookUser() {
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }
    @Exclude
    public AccessToken getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }
}
