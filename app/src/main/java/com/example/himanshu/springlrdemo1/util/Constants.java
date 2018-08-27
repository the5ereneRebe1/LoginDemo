package com.example.himanshu.springlrdemo1.util;


public class Constants {
    public static final String LAYOUT_ID = "login_layout_id";
    public static final int FATAL = 1001;
    public static final String LOGIN_THEME = "login_page_theme";
    private static final String LOGIN_LISTENER = "login_listener_instance";
    public static final String GOOGLE_PROGRESS_DIALOG_MESSAGE = "Getting User Info…";

    public static final String APPLOGO = "himanshu.springlrdemo1.applogo";
    public static final String USER = "himanshu.springlrdemo1.user";
    public static final String CUSTOMLOGINFLAG = "himanshu.springlrdemo1.custom_login_flag";
    public static final String FACEBOOKFLAG = "himanshu.springlrdemo1.facebook_flag";
    public static final String FACEBOOKPERMISSIONS = "himanshu.springlrdemo1.facebook_permissions";
    //public static final String TWITTERFLAG = "himanshu.springlrdemo1.twitter_flag";
    public static final String GOOGLEFLAG = "himanshu.springlrdemo1.google_flag";
    public static final String FACEBOOKID = "himanshu.springlrdemo1.facebook_id";
    public static final String CUSTOMUSERFLAG = "himanshu.springlrdemo1.custom_user";
    public static final String CUSTOMLOGINTYPE = "himanshu.springlrdemo1.custom_login_type";

    public static final String USER_TYPE = "user_type";

    public static final int FACEBOOK_LOGIN_REQUEST = 321;
    public static final int GOOGLE_LOGIN_REQUEST = 322;
    public static final int CUSTOM_LOGIN_REQUEST = 323;
    public static final int CUSTOM_SIGNUP_REQUEST = 324;
    public static final int LOGIN_REQUEST = 5;

    public static final String USER_SESSION = "user_session_key";
    public static final String USER_PREFS = "himanshu_springlrdemo1_user_prefs";
    public static final String DEFAULT_SESSION_VALUE = "No logged in user";

    public static class FacebookFields {
        public static final String EMAIL = "email";
        public static final String ID = "id";
        public static final String BIRTHDAY = "birthday";
        public static final String GENDER = "gender";
        public static final String FIRST_NAME = "first_name";
        public static final String MIDDLE_NAME = "middle_name";
        public static final String LAST_NAME = "last_name";
        public static final String NAME = "name";
        public static final String LINK = "link";
        public static final String BIO = "link";
    }

    public enum Gender {
        male, female
    }
}
