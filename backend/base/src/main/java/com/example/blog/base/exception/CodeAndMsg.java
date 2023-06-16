package com.example.blog.base.exception;

public enum CodeAndMsg {
    NEED_LOGIN(401, "Login Required..."),
    METHOD_FAIL(501, "ENCOUNTER AN ERROR WHEN EXECUTE METHOD"),
    UNKNOWN_EXCEPTION(501, "THIS IS AN UNKNOWN EXCEPTION"),
    PARAM_VERIFICATION_FAIL(400, "Param verification failed..."),

    WRONG_USER_NAME(300, "Wrong username.."),
    WRONG_PASSWORD(301, "Wrong password.."),
    EXIST_USER(302, "This username or email has already registered.."),

    DUPLICATE_RESET(303, "Please don't sent duplicate request within 5 minutes.."),
    EXPIRED_RESET_TOKEN(304, "This link is not valid or is expired..."),
    FILE_UPLOAD_FAIL(601, "File upload failed...");

    private int code;
    private String msg;

    CodeAndMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
