package com.example.himanshu.springlrdemo1.util;

import android.util.Log;

import com.example.himanshu.springlrdemo1.users.FacebookUser;
import com.example.himanshu.springlrdemo1.users.GoogleUser;
import com.example.himanshu.springlrdemo1.users.SmartUser;
import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import org.json.JSONException;
import org.json.JSONObject;



public class UserUtil {

    public static GoogleUser populateGoogleUser(GoogleSignInAccount account){
        //Create a new google user
        GoogleUser googleUser = new GoogleUser();
        //populate the user
        googleUser.setDisplayName(account.getDisplayName());
        if (account.getPhotoUrl() != null) {
            googleUser.setPhotoUrl(account.getPhotoUrl().toString());
        }
        googleUser.setEmail(account.getEmail());
        googleUser.setIdToken(account.getIdToken());
        googleUser.setUserId(account.getId());
        if (account.getAccount() != null) {
            googleUser.setUsername(account.getAccount().name);
        }

        //return the populated google user
        return googleUser;
    }

    public static FacebookUser populateFacebookUser(JSONObject object, AccessToken accessToken){
        FacebookUser facebookUser = new FacebookUser();
        facebookUser.setGender(-1);
        facebookUser.setAccessToken(accessToken);
        try {
            if (object.has(Constants.FacebookFields.EMAIL))
                facebookUser.setEmail(object.getString(Constants.FacebookFields.EMAIL));
            if (object.has(Constants.FacebookFields.BIRTHDAY))
                facebookUser.setBirthday(object.getString(Constants.FacebookFields.BIRTHDAY));
            if (object.has(Constants.FacebookFields.GENDER)) {
                try {
                    Constants.Gender gender = Constants.Gender.valueOf(object.getString(Constants.FacebookFields.GENDER));
                    switch (gender) {
                        case male:
                            facebookUser.setGender(0);
                            break;
                        case female:
                            facebookUser.setGender(1);
                            break;
                    }
                } catch (Exception e) {
                    //if gender is not in the enum it is already set to unspecified value (-1)
                    Log.e("UserUtil", e.getMessage());
                }
            }
            if (object.has(Constants.FacebookFields.LINK))
                facebookUser.setBio(object.getString(Constants.FacebookFields.BIO));
            if (object.has(Constants.FacebookFields.ID))
                facebookUser.setUserId(object.getString(Constants.FacebookFields.ID));
            if (object.has(Constants.FacebookFields.NAME))
                facebookUser.setProfileName(object.getString(Constants.FacebookFields.NAME));
            if (object.has(Constants.FacebookFields.FIRST_NAME))
                facebookUser.setFirstName(object.getString(Constants.FacebookFields.FIRST_NAME));
            if (object.has(Constants.FacebookFields.MIDDLE_NAME))
                facebookUser.setMiddleName(object.getString(Constants.FacebookFields.MIDDLE_NAME));
            if (object.has(Constants.FacebookFields.LAST_NAME))
                facebookUser.setLastName(object.getString(Constants.FacebookFields.LAST_NAME));
        } catch (JSONException e){
            Log.e("UserUtil", e.getMessage());
            facebookUser = null;
        }
        return facebookUser;
    }

    public static SmartUser populateCustomUser(String username, String email, String userId){
        SmartUser user = new SmartUser();
        user.setEmail(email);
        user.setUsername(username);
        user.setUserId(userId);
        return user;
    }

}
