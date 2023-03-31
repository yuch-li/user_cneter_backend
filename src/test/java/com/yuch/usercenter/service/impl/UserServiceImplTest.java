package com.yuch.usercenter.service.impl;
import java.util.Date;

import com.yuch.usercenter.model.User;
import com.yuch.usercenter.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    void testAddUser() {
        User user = new User();
        user.setId(0L);
        user.setPassword("");
        user.setUsername("");
        user.setEmail("");
        user.setGender(0);
        user.setStatus(0);
        user.setPhone("");
        user.setAvatarUrl("");
        user.setIdDelete(0);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setInvitationCode("asfa123");
        userService.save(user);
    }

//    @Test
//    @Transactional
//    void userRegister() {
//        String username = null;
//        String password = "1234";
//        String confirmPassword = "12345678asd";
//        String verificationCode = "1234567";
//        //用户名为空
//        Long result = userService.userRegister(username, verificationCode, password, confirmPassword, verificationCode);
//        Assertions.assertEquals(-1l, result);
//        //空串
//        username = "";
//        result = userService.userRegister(username, verificationCode, password, confirmPassword, verificationCode);
//        Assertions.assertEquals(-1l, result);
//        username = "111111";
//        //两个密码不一致
//        result = userService.userRegister(username, verificationCode, password, confirmPassword, verificationCode);
//        Assertions.assertEquals(-1l, result);
//        //用户名不足4位
//        username = "1";
//        result = userService.userRegister(username, verificationCode, password, confirmPassword, verificationCode);
//        Assertions.assertEquals(-1l, result);
//        //用户名已存在
//        password = "12345678asd";
//        confirmPassword = "12345678asd";
//        username = "zhangsan";
//        result = userService.userRegister(username, verificationCode, password, confirmPassword, verificationCode);
//        Assertions.assertEquals(-1l, result);
//        //成功插入
//        username = "zhangsan2";
//        result = userService.userRegister(username, verificationCode, password, confirmPassword, verificationCode);
//        Assertions.assertNotEquals(-1l, result);
//        //一个中文字符
//        username = "张";
//        result = userService.userRegister(username, verificationCode, password, confirmPassword, verificationCode);
//        Assertions.assertEquals(-1l, result);
//        //两个中文字符
//        username = "张四";
//        result = userService.userRegister(username, verificationCode, password, confirmPassword, verificationCode);
//        Assertions.assertNotEquals(-1l, result);
//    }
//
//    @Test
//    @Transactional
//    void userLogin() {
//        String username = "张三2";
//        String password = "123456asd";
//        String verificationCode = "1234";
//        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
//        Long result = userService.userRegister(username, verificationCode, password, password, "asf");
//        Assertions.assertNotEquals(-1, result);
//        User user = userService.userLogin(username, password, verificationCode, mockHttpServletRequest);
//        Assertions.assertEquals(username, user.getUsername());
//        user = userService.userLogin("lisi", password, verificationCode, mockHttpServletRequest);
//        Assertions.assertEquals(null, user);
//        user = userService.userLogin(username, "123asdf", verificationCode, mockHttpServletRequest);
//        Assertions.assertEquals(null, user);
//    }
}