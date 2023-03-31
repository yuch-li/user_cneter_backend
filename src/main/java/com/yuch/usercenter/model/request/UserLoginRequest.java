package com.yuch.usercenter.model.request;

import lombok.Data;

import javax.servlet.http.HttpServletRequest;

@Data
public class UserLoginRequest {
    private String username;
    private String password;
    private String verificationCode;
}
