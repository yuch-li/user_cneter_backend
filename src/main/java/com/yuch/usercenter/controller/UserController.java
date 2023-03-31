package com.yuch.usercenter.controller;

import cn.hutool.core.util.StrUtil;
import com.yuch.usercenter.common.BaseResponse;
import com.yuch.usercenter.common.ResultUtils;
import com.yuch.usercenter.enums.ErrorCode;
import com.yuch.usercenter.exception.BusinessException;
import com.yuch.usercenter.model.User;
import com.yuch.usercenter.model.request.UserLoginRequest;
import com.yuch.usercenter.model.request.UserRegisterRequest;
import com.yuch.usercenter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public BaseResponse<Long> userRegiter(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String username = userRegisterRequest.getUsername();
        String password = userRegisterRequest.getPassword();
        String confirmPassword = userRegisterRequest.getConfirmPassword();
        String verificationCode = userRegisterRequest.getVerificationCode();
        String invitationCode = userRegisterRequest.getInvitationCode();
        if (StrUtil.hasBlank(username, password, confirmPassword, verificationCode)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long result = userService.userRegister(username, verificationCode, password, confirmPassword, invitationCode);
        return ResultUtils.success(result);
    }

    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String username = userLoginRequest.getUsername();
        String password = userLoginRequest.getPassword();
        String verificationCode = userLoginRequest.getVerificationCode();
        if (StrUtil.hasBlank(username, password)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.userLogin(username, password, verificationCode, request);
        return ResultUtils.success(user);
    }

    @PostMapping("/logout")
    public BaseResponse<Long> logout(HttpServletRequest request) {
        Long id = userService.logout(request);
        return ResultUtils.success(id);
    }

    @GetMapping("/query")
    public BaseResponse<List<User>> queryUser(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        String username = user.getUsername();
        if (StrUtil.isBlank(username)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        List<User> users = userService.queryUser(username, request);
        return ResultUtils.success(users);
    }

    @GetMapping("/delete")
    public BaseResponse<Boolean> deleteUser(Long userid, HttpServletRequest request) {
        if (userid == null || userid <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = userService.deleteUser(userid, request);
        return ResultUtils.success(result);
    }

    @GetMapping("/current_user")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request) {
        User currentUser = userService.getCurrentUser(request);
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NO_LOGIN);
        }
        return ResultUtils.success(currentUser);
    }

    @GetMapping("/search")
    public BaseResponse<List<User>> searchUser(HttpServletRequest request) {
        List<User> users = userService.searchAll(request);
        return ResultUtils.success(users);
    }

}