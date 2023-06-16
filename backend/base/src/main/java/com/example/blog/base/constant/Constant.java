package com.example.blog.base.constant;

import com.amazonaws.regions.Regions;

public class Constant {
    public static final String PHOTO_KEY = "d9859520938232779b5dbc4cd0d1283c_c_345_458.jpeg";
    public static final Regions AWS_CLIENT_REGION = Regions.AP_SOUTHEAST_2;
    public static final String AWS_BUCKET_NAME = "blogdly";
    public static final String AWS_SECRET_ID = "";
    public static final String AWS_SECRET_KEY = "";
    public static final String EMAIL_TITLE_RESET_PWD = "Reset password for your Phd Blog account";
    public static final String EMAIL_CONTENT_RESET_PWD = "Please click this link to reset your password. Note that the link will expire in 10 minutes.";
    public static final String RESET_PWD_URL = "192.168.1.104:3000/login/resetPwd";
    public static final String BASE_DIR = System.getProperty("user.dir") + "/tmp_file";
}
