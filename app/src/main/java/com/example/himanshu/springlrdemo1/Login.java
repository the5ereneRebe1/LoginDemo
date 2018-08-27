package com.example.himanshu.springlrdemo1;

import android.content.Context;
import android.content.Intent;



public abstract class Login {

    public abstract void login(LoginConfig config);

    public abstract void signup(LoginConfig config);

    public abstract boolean logout(Context context);

    public abstract void onActivityResult(int requestCode, int resultCode, Intent data, LoginConfig config);


}
