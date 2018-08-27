package com.example.himanshu.springlrdemo1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.himanshu.springlrdemo1.users.FacebookUser;
import com.example.himanshu.springlrdemo1.users.GoogleUser;
import com.example.himanshu.springlrdemo1.users.SmartUser;
//TODO: Set middle name visiblity to GONE if null
public class ProfileView extends AppCompatActivity {

    private SmartUser user;
    private Login login;
    TextView username,name,email,dob,bio,gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);
        DatabaseHelper helper = new DatabaseHelper();
        user = helper.readEntry(UserSessionManager.getCurrentUser(getBaseContext()),getApplicationContext());
        user=UserSessionManager.getCurrentUser(getApplicationContext());
        if(user.getUsername()==null  || user.getEmail()==null || user.getBio()==null || user.getGender()<0){
            startActivity(new Intent(this,EnterInfo.class));
        }
        bindViews();
        addValues(user);
    }



    private void addValues(SmartUser user) {
        username.setText(user.getUsername());
        name.setText("NAME:"+user.getFirstName());
        email.setText(user.getEmail());
        dob.setText("DATE OF BIRTH:"+user.getBirthday());
        switch(user.getGender()){
            case 0:
                gender.setText("GENDER: Male");
                break;
            case 1:
                gender.setText("GENDER: Female");
                break;
        }

        bio.setText("BIO:"+user.getBio());
    }

    private void bindViews() {
        username = (TextView)findViewById(R.id.profile_username);
        name=(TextView)findViewById(R.id.profile_name);
        email=(TextView)findViewById(R.id.profile_email);
        dob=(TextView)findViewById(R.id.profile_date_of_birth);
        bio=(TextView)findViewById(R.id.profile_bio);
        gender=(TextView)findViewById(R.id.profile_gender);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.action_logout:
                logout(user);
        }


        return super.onOptionsItemSelected(item);
    }

    private void logout(SmartUser currentUser) {
        if (currentUser != null) {
            if (currentUser instanceof FacebookUser) {
                login = LoginFactory.build(LoginType.Facebook);
            } else if(currentUser instanceof GoogleUser) {
                login = LoginFactory.build(LoginType.Google);
            } else {
                login = LoginFactory.build(LoginType.CustomLogin);
            }
            boolean result = login.logout(this);
            if (result) {
                finish();
                Toast.makeText(this, "User logged out successfully", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
