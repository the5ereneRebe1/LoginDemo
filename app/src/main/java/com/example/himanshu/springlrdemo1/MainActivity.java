package com.example.himanshu.springlrdemo1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.himanshu.springlrdemo1.users.FacebookUser;
import com.example.himanshu.springlrdemo1.users.GoogleUser;
import com.example.himanshu.springlrdemo1.users.SmartUser;
import com.example.himanshu.springlrdemo1.util.LoginException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.common.StringUtils;

public class MainActivity extends AppCompatActivity implements LoginCallbacks {

    private Button facebookLoginButton, googleLoginButton, customSigninButton, customSignupButton, logoutButton, resetButton;
    private EditText emailEditText, passwordEditText;
    private int custonSignInFlag=0;
    private ProgressBar progressBar;
    SmartUser currentUser;
    //GoogleApiClient mGoogleApiClient;
    LoginConfig config;
    Login login;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
        setListeners();
        auth = FirebaseAuth.getInstance();
        DatabaseHelper.init();
       /**if (auth.getCurrentUser() != null) {
            startActivity(new Intent(MainActivity.this, ProfileView.class));
            finish();
        }**/
        config = new LoginConfig(this, this);
        config.setFacebookAppId(getString(R.string.facebook_app_id));
        config.setFacebookPermissions(null);
        config.setGoogleApiClient(null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        currentUser = UserSessionManager.getCurrentUser(this);
        refreshLayout();
    }

    private void refreshLayout() {
        currentUser = UserSessionManager.getCurrentUser(this);
        if (currentUser != null) {
           /** Log.d("Smart Login", "Logged in user: " + currentUser.toString());
            facebookLoginButton.setVisibility(View.GONE);
            googleLoginButton.setVisibility(View.GONE);
            customSigninButton.setVisibility(View.GONE);
            customSignupButton.setVisibility(View.GONE);
            emailEditText.setVisibility(View.GONE);
            passwordEditText.setVisibility(View.GONE);
            logoutButton.setVisibility(View.VISIBLE);**/
           startActivity(new Intent(this,ProfileView.class));
        } else {
            facebookLoginButton.setVisibility(View.VISIBLE);
            googleLoginButton.setVisibility(View.VISIBLE);
            customSigninButton.setVisibility(View.VISIBLE);
            customSignupButton.setVisibility(View.VISIBLE);
            emailEditText.setVisibility(View.VISIBLE);
            passwordEditText.setVisibility(View.VISIBLE);
           // logoutButton.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (login != null) {
            login.onActivityResult(requestCode, resultCode, data, config);
        }
    }

    private void setListeners() {
        facebookLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform Facebook login
                login = LoginFactory.build(LoginType.Facebook);
                login.login(config);
            }
        });

        googleLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform Google login
                login = LoginFactory.build(LoginType.Google);
                login.login(config);
            }
        });

        customSigninButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform custom sign in
                login = LoginFactory.build(LoginType.CustomLogin);
                login.login(config);
            }
        });
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //login = LoginFactory.build(LoginType.CustomLogin);
                //login.reset(config);
                startActivity(new Intent(MainActivity.this, ResetPasswordActivity.class));
            }
        });
        customSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform custom sign up
               // login = LoginFactory.build(LoginType.CustomLogin);
                //login.signup(config);
                startActivity(new Intent(MainActivity.this, SignupActivity.class));
            }
        });

       /** logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser != null) {
                    if (currentUser instanceof FacebookUser) {
                        login = LoginFactory.build(LoginType.Facebook);
                    } else if(currentUser instanceof GoogleUser) {
                        login = LoginFactory.build(LoginType.Google);
                    } else {
                        login = LoginFactory.build(LoginType.CustomLogin);
                    }
                    boolean result = login.logout(MainActivity.this);
                    if (result) {
                        refreshLayout();
                        Toast.makeText(MainActivity.this, "User logged out successfully", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });**/
    }

    private void bindViews() {
        facebookLoginButton = (Button) findViewById(R.id.facebook_login_button);
        googleLoginButton = (Button) findViewById(R.id.google_login_button);
        customSigninButton = (Button) findViewById(R.id.custom_signin_button);
        customSignupButton = (Button) findViewById(R.id.custom_signup_button);
        emailEditText = (EditText) findViewById(R.id.email_edittext);
        passwordEditText = (EditText) findViewById(R.id.password_edittext);
        //logoutButton = (Button) findViewById(R.id.logout_button);
        resetButton =(Button)findViewById(R.id.btn_reset_password);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
    }

    @Override
    public void onLoginSuccess(SmartUser user) {
        Toast.makeText(this, user.toString(), Toast.LENGTH_SHORT).show();
        //TODO: Add the profile entry page by passing the user data.
        refreshLayout();
    }

    @Override
    public void onLoginFailure(LoginException e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public SmartUser doCustomLogin() {

        String email = emailEditText.getText().toString();
        final String password = passwordEditText.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();

        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
        }

        progressBar.setVisibility(View.VISIBLE);

        //authenticate user
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        passwordEditText.setError(getString(R.string.minimum_password));
                                    } else {
                                        Toast.makeText(MainActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();

                                    }
                                } else {
                                    custonSignInFlag=1;
                                    //Intent intent = new Intent(MainActivity.this, MainActivity.class);
                                    //startActivity(intent);
                                    //finish();
                                }
                            }
                        });
        if(custonSignInFlag==1) {
            SmartUser user = new SmartUser();
            user.setEmail(emailEditText.getText().toString());
            user.setUserId(String.valueOf(emailEditText.getText().toString().hashCode()));
            user.setGender(-1);
            return user;
        }
        else return null;
    }

    @Override
    public SmartUser doCustomSignup() {
        SmartUser user = new SmartUser();
        user.setEmail(emailEditText.getText().toString());
        return user;
    }
}
