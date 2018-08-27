package com.example.himanshu.springlrdemo1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.himanshu.springlrdemo1.users.GoogleUser;
import com.example.himanshu.springlrdemo1.util.Constants;
import com.example.himanshu.springlrdemo1.util.LoginException;
import com.example.himanshu.springlrdemo1.util.UserUtil;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;





public class GoogleLogin extends Login {
    @Override
    public void login(@NonNull LoginConfig config) {
        GoogleApiClient apiClient = config.getGoogleApiClient();
        Activity activity = config.getActivity();

        if (apiClient == null) {
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .requestProfile()
                    .build();

            apiClient = new GoogleApiClient.Builder(activity)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
        }

        ProgressDialog progress = ProgressDialog.show(activity, "", activity.getString(R.string.logging_holder), true);
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(apiClient);
        activity.startActivityForResult(signInIntent, Constants.GOOGLE_LOGIN_REQUEST);
        progress.dismiss();
    }

    @Override
    public void signup(LoginConfig config) {

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
            Log.e("GoogleLogin", e.getMessage());
            return false;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data, LoginConfig config) {
        ProgressDialog progress = ProgressDialog.show(config.getActivity(), "", config.getActivity().getString(R.string.getting_data), true);
        GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
        Log.d("GOOGLE SIGN IN", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            GoogleUser googleUser = UserUtil.populateGoogleUser(acct);
            // Save the user
            UserSessionManager.setUserSession(config.getActivity(), googleUser);
            config.getCallback().onLoginSuccess(googleUser);
            progress.dismiss();
        } else {
            Log.d("GOOGLE SIGN IN", "" + requestCode);
            // Signed out, show unauthenticated UI.
            progress.dismiss();
            config.getCallback().onLoginFailure(new LoginException("Google login failed", LoginType.Google));
        }
    }
}
