package com.example.himanshu.springlrdemo1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.himanshu.springlrdemo1.users.SmartUser;
import com.example.himanshu.springlrdemo1.util.Constants;
import com.example.himanshu.springlrdemo1.util.LoginException;




public class CustomLogin extends Login {
    @Override
    public void login(LoginConfig config) {
        SmartUser user = config.getCallback().doCustomLogin();
        if (user != null) {
            // Save the user
            UserSessionManager.setUserSession(config.getActivity(), user);
            config.getCallback().onLoginSuccess(user);
        } else {
            config.getCallback().onLoginFailure(new LoginException("Custom login failed", LoginType.CustomLogin));
        }
    }

    @Override
    public void signup(LoginConfig config) {

        SmartUser user = config.getCallback().doCustomSignup();
        if (user != null) {
            // Save the user
            UserSessionManager.setUserSession(config.getActivity(), user);
            config.getCallback().onLoginSuccess(user);
        } else {
            config.getCallback().onLoginFailure(new LoginException("Custom signup failed", LoginType.CustomLogin));
        }
    }
    public void reset(LoginConfig config) {

    }
    @Override
    public boolean logout(Context context) {
        try {
            SharedPreferences preferences = context.getSharedPreferences(Constants.USER_PREFS, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove(Constants.USER_TYPE);
            editor.remove(Constants.USER_SESSION);
            editor.apply();
            return true;
        } catch (Exception e) {
            Log.e("CustomLogin", e.getMessage());
            return false;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data, LoginConfig config) {

    }
}
