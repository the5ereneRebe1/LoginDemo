package com.example.himanshu.springlrdemo1.util;


import com.example.himanshu.springlrdemo1.LoginType;


public class LoginException extends Exception {
    private LoginType loginType;

    public LoginException(String message, LoginType loginType) {
        super(message);
        this.loginType = loginType;
    }

    public LoginException(String message, Throwable cause, LoginType loginType) {
        super(message, cause);
        this.loginType = loginType;
    }

    public LoginType getLoginType() {
        return loginType;
    }
}
