package com.example.blog.base.request.user;

import lombok.Data;

@Data
public class ResetPwdForm {
    private String token;
    private String password;
    private String email;
}
