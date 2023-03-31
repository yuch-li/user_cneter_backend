package com.yuch.usercenter.service;

import com.yuch.usercenter.model.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author L6394
* @description 针对表【t_user】的数据库操作Service
* @createDate 2023-03-17 14:43:56
*/
public interface UserService extends IService<User> {
    /**
     * 注册逻辑：
     * 1. 账号密码验证码非空
     * 2. 两次密码相同
     * 3. 用户名：不能重复，由中文、字母、数字、"_"、"#"的组合，4~16位，一个中文字符算两位
     * 4. 密码：由数字和字母组成，并且要同时含有数字和字母，且长度要在8-16位之间
     * 5. 用户名不能重复
     * 6. 对密码进行加密
     * 7. 向数据库插入用户数据
     *
     * @param username 用户名
     * @param verificationCode 验证码
     * @param password 密码
     * @param confirmPassword 确认密码
     * @param invitationCode 邀请码
     * @return 成功返回用户id，失败返回-1l
     */
    public Long userRegister(String username, String verificationCode, String password, String confirmPassword, String invitationCode);

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @param verificationCode 验证码
     * @param request 设置session
     * @return 成功返回脱敏后的User对象,失败返回null
     */
    public User userLogin(String username, String password, String verificationCode, HttpServletRequest request);

    /**
     * 根据用户名模糊查询用户
     * @param username 用户名
     * @param request
     * @return
     */
    public List<User> queryUser(String username, HttpServletRequest request);

    /**
     * 根据用户id逻辑删除用户
     * @param userid
     * @param request
     * @return
     */
    public boolean deleteUser(Long userid, HttpServletRequest request);

    /**
     * 更新session保存的用户的信息
     * @param request
     * @return 从数据库中获取的User对象
     */
    public User getCurrentUser(HttpServletRequest request);

    /**
     * 数据脱敏
     * @param user
     * @return 脱敏后的User对象
     */
    public User toSafeUser(User user);

    /**
     * 查询所有用户
     *
     * @return
     */
    public List<User> searchAll(HttpServletRequest request);

    /**
     * 用户注销登录
     * @param request
     */
    Long logout(HttpServletRequest request);

}
