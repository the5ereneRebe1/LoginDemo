package com.example.himanshu.springlrdemo1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.himanshu.springlrdemo1.users.FacebookUser;
import com.example.himanshu.springlrdemo1.util.Constants;
import com.example.himanshu.springlrdemo1.util.LoginException;
import com.example.himanshu.springlrdemo1.util.UserUtil;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;



public class FacebookLogin extends Login {

    private CallbackManager callbackManager;
    public FacebookLogin() {
        //Facebook login callback
        callbackManager = CallbackManager.Factory.create();
    }

    @Override
    public void login(@NonNull LoginConfig config) {
        final Activity activity = config.getActivity();
        final LoginCallbacks callback = config.getCallback();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(getApplicationContext());
        final ProgressDialog progress = ProgressDialog.show(activity, "", activity.getString(R.string.logging_holder), true);
        ArrayList<String> permissions = config.getFacebookPermissions();
        if (permissions == null) {
            permissions = LoginConfig.getDefaultFacebookPermissions();
        }
        LoginManager.getInstance().logInWithReadPermissions(activity, permissions);
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                progress.setMessage(activity.getString(R.string.getting_data));
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject jsonObject, GraphResponse response) {
                        progress.dismiss();
                        FacebookUser facebookUser = UserUtil.populateFacebookUser(jsonObject, loginResult.getAccessToken());
                        // Save the user
                        UserSessionManager.setUserSession(activity, facebookUser);
                        callback.onLoginSuccess(facebookUser);
                    }
                });
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                progress.dismiss();
                Log.d("Facebook Login", "User cancelled the login process");
                callback.onLoginFailure(new LoginException("User cancelled the login request", LoginType.Facebook));
            }

            @Override
            public void onError(FacebookException e) {
                progress.dismiss();
                callback.onLoginFailure(new LoginException(e.getMessage(), e, LoginType.Facebook));
            }
        });
    }

    @Override
    public void signup(LoginConfig config) {

    }

    @Override
    public boolean logout(Context context) {
        try {
            SharedPreferences preferences = context.getSharedPreferences(Constants.USER_PREFS, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            LoginManager.getInstance().logOut();
            editor.remove(Constants.USER_TYPE);
            editor.remove(Constants.USER_SESSION);
            editor.apply();
            return true;
        } catch (Exception e) {
            Log.e("FacebookLogin", e.getMessage());
            return false;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data, LoginConfig config) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
