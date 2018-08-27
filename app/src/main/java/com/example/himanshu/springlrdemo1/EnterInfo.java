package com.example.himanshu.springlrdemo1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.himanshu.springlrdemo1.users.SmartUser;

public class EnterInfo extends AppCompatActivity {

    private Button accept;
    private EditText etusername,etemail,etgender,etdob,etbio,etname;
    private SmartUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_info);
        user=UserSessionManager.getCurrentUser(this);
        bindviews();
        setListeners();
        setDefaults();
    }

    private void setDefaults() {
        if(user.getEmail()!=null)
            etemail.setText(user.getEmail());
        if(user.getGender()>=0){
            switch (user.getGender()){
                case 0:
                    etgender.setText("male");
                    break;
                case 1:
                    etgender.setText("female");
            }
        }
    }

    private void setListeners() {
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etemail.getText())) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                else if (TextUtils.isEmpty(etusername.getText())) {
                    Toast.makeText(getApplicationContext(), "Enter username!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (TextUtils.isEmpty(etname.getText())) {
                    Toast.makeText(getApplicationContext(), "Enter name!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (TextUtils.isEmpty(etdob.getText())) {
                    Toast.makeText(getApplicationContext(), "Enter date of birth!", Toast.LENGTH_SHORT).show();
                    return;
                }

                else if (TextUtils.isEmpty(etbio.getText())) {
                    Toast.makeText(getApplicationContext(), "Enter bio!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (TextUtils.isEmpty(etgender.getText())) {
                    Toast.makeText(getApplicationContext(), "Enter gender!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    user.setFirstName(etname.getText().toString());
                    user.setEmail(etemail.getText().toString());
                    user.setUsername(etusername.getText().toString());
                    user.setBio(etbio.getText().toString());
                    user.setBirthday(etdob.getText().toString());
                    if(etgender.getText().toString().equals("male"))
                        user.setGender(0);
                    else if(etgender.getText().toString().equals("female"))
                        user.setGender(1);
                    DatabaseHelper helper = new DatabaseHelper();
                    helper.writeEntry(user, getApplicationContext());
                    finish();
                }
            }
        });
    }

    private void bindviews() {
        accept = (Button)findViewById(R.id.ei_accept);
        etbio=(EditText)findViewById(R.id.ei_bio);
        etusername=(EditText)findViewById(R.id.ei_username);
        etemail=(EditText)findViewById(R.id.ei_email);
        etdob=(EditText)findViewById(R.id.ei_dob);
        etgender=(EditText)findViewById(R.id.ei_gender);
        etname=(EditText)findViewById(R.id.ei_name);
    }


}
