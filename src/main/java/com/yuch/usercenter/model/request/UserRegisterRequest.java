package com.yuch.usercenter.model.request;

import lombok.Data;

@Data
public class UserRegisterRequest {
    private String username;
    private String verificationCode;
    private String password;
    private String confirmPassword;
    private String invitationCode;
}
