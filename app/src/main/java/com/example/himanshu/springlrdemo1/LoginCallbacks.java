package com.example.himanshu.springlrdemo1;

import com.example.himanshu.springlrdemo1.users.SmartUser;
import com.example.himanshu.springlrdemo1.util.LoginException;




public interface LoginCallbacks {

    void onLoginSuccess(SmartUser user);

    void onLoginFailure(LoginException e);

    SmartUser doCustomLogin();

    SmartUser doCustomSignup();
}
