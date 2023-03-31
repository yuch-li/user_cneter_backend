package com.yuch.usercenter.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuch.usercenter.enums.ErrorCode;
import com.yuch.usercenter.enums.UserRoleEnum;
import com.yuch.usercenter.exception.BusinessException;
import com.yuch.usercenter.model.User;
import com.yuch.usercenter.service.UserService;
import com.yuch.usercenter.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* @author L6394
* @description 针对表【t_user】的数据库操作Service实现
* @createDate 2023-03-17 14:43:56
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     *密码盐值
     */
    private static final String SALT = "password";

    @Autowired
    UserMapper userMapper;

    @Override
    public Long userRegister(String username, String verificationCode, String password, String confirmPassword, String invitationCode) {
        if (StrUtil.hasBlank(username, password, confirmPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (!password.equals(confirmPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //校验账号密码是否符合条件
        String validateUsername="^([\\u4e00-\\u9fa5]{2,4})|([A-Za-z0-9_]{4,16})|([a-zA-Z0-9_\\u4e00-\\u9fa5]{3,16})$";
        String validatePassword="^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";
        Pattern usernamePattern = Pattern.compile(validateUsername);
        Pattern passwordPattern = Pattern.compile(validatePassword);
        Matcher usernameMatcher = usernamePattern.matcher(username);
        Matcher passwordMatcher = passwordPattern.matcher(password);
        if (!usernameMatcher.matches() || !passwordMatcher.matches()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //判断用户名是否已存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        Long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.USER_EXIST, "");
        }
        //使用sha256对密码加密
        Digester sha256 = new Digester(DigestAlgorithm.SHA256);
        String sha256Password = sha256.digestHex(SALT + password);
        //插入到数据库
        User user = new User();
        user.setPassword(sha256Password);
        user.setUsername(username);
        user.setInvitationCode(invitationCode);
        userMapper.insert(user);
        return user.getId();
    }

    @Override
    public User userLogin(String username, String password, String verificationCode, HttpServletRequest request) {
        //是否为空
        if (StrUtil.hasBlank(username, password)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //校验账号密码是否符合条件
        String validateUsername="^([\\u4e00-\\u9fa5]{2,4})|([A-Za-z0-9_]{4,16})|([a-zA-Z0-9_\\u4e00-\\u9fa5]{3,16})$";
        String validatePassword="^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";
        Pattern usernamePattern = Pattern.compile(validateUsername);
        Pattern passwordPattern = Pattern.compile(validatePassword);
        Matcher usernameMatcher = usernamePattern.matcher(username);
        Matcher passwordMatcher = passwordPattern.matcher(password);
        if (!usernameMatcher.matches() || !passwordMatcher.matches()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //根据用户名查询用户
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new BusinessException(ErrorCode.USERNAME_NOT_EXIST);
        }
        //判断密码是否正确
        String dbPassword = user.getPassword();
        Digester sha256 = new Digester(DigestAlgorithm.SHA256);
        String sha256Password = sha256.digestHex(SALT + password);
        if (!sha256Password.equals(dbPassword)) {
            throw new BusinessException(ErrorCode.PASSWORD_ERROR);
        }
        //脱敏
        User safeUser = toSafeUser(user);
        //把用户对象存入session
        request.getSession().setAttribute("user", safeUser);
        return safeUser;
    }

    @Override
    public List<User> queryUser(String username, HttpServletRequest request) {
        //从session中取出User对象，判断是否为管理员
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new BusinessException(ErrorCode.NO_LOGIN);
        }
        if (user.getUserRole() == UserRoleEnum.NORMAL) {
            throw new BusinessException(ErrorCode.NOT_ADMIN);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("userName", username);
        List<User> users = userMapper.selectList(queryWrapper);
        if (users.isEmpty()) {
            return null;
        }
        //脱敏，把密码设为null
        for (User userEach : users) {
            userEach.setPassword(null);
        }
        return users;
    }

    @Override
    public boolean deleteUser(Long userid, HttpServletRequest request) {
        //从session中取出User对象，判断是否为管理员
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return false;
        }
        if (user.getUserRole() == UserRoleEnum.NORMAL) {
            return false;
        }

        int i = userMapper.deleteById(userid);
        if (i != 1) {
            return false;
        }
        return true;
    }

    @Override
    public User getCurrentUser(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new BusinessException(ErrorCode.NO_LOGIN);
        }
        Long id = user.getId();
        if (id < 0) {
            throw new BusinessException(ErrorCode.NO_LOGIN);
        }
        User currentUser = userMapper.selectById(id);
        request.getSession().setAttribute("user", currentUser);
        return currentUser;
    }

    @Override
    public User toSafeUser(User user) {
        if (user == null) {
            return null;
        }
        User safeUser = new User();
        safeUser.setId(user.getId());
        safeUser.setUsername(user.getUsername());
        safeUser.setEmail(user.getEmail());
        safeUser.setGender(user.getGender());
        safeUser.setStatus(user.getStatus());
        safeUser.setPhone(user.getPhone());
        safeUser.setAvatarUrl(user.getAvatarUrl());
        safeUser.setCreateTime(user.getCreateTime());
        safeUser.setUpdateTime(user.getUpdateTime());
        safeUser.setUserRole(user.getUserRole());
        safeUser.setInvitationCode(user.getInvitationCode());
        return safeUser;
    }

    @Override
    public List<User> searchAll(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new BusinessException(ErrorCode.NO_LOGIN);
        }
        if (user.getUserRole() != UserRoleEnum.ADMIN) {
            throw new BusinessException(ErrorCode.NOT_ADMIN);
        }
        return userMapper.selectList(null);
    }

    @Override
    public Long logout(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new BusinessException(ErrorCode.NO_LOGIN);
        }
        Long id = user.getId();
        request.getSession().removeAttribute("user");
        return id;
    }
}




