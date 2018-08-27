package com.example.himanshu.springlrdemo1;



public class LoginFactory {
    public static Login build(LoginType loginType) {
        switch (loginType) {
            case Facebook:
                return new FacebookLogin();
            case Google:
                return new GoogleLogin();
            case CustomLogin:
                return new CustomLogin();
            default:
                // To avoid null pointers
                return new CustomLogin();
        }
    }
}
