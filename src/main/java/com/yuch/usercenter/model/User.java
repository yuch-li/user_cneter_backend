package com.yuch.usercenter.model;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.yuch.usercenter.enums.UserRoleEnum;
import lombok.Data;

/**
 * 
 * @TableName t_user
 */
@TableName(value ="t_user")
@Data
public class User implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    @TableField(value = "password")
    private String password;

    /**
     * 
     */
    @TableField(value = "username")
    private String username;

    /**
     * 
     */
    @TableField(value = "email")
    private String email;

    /**
     * 
     */
    @TableField(value = "gender")
    private Integer gender;

    /**
     * 
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 头像url
     */
    @TableField(value = "avatar_url")
    private String avatarUrl;

    /**
     * 逻辑删除
     */
    @TableField(value = "id_delete")
    @TableLogic
    private Integer idDelete;

    /**
     * 
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     *用户角色。0为普通用户，1为管理员
     */
    @TableField(value = "user_role")
    private UserRoleEnum userRole;

    /**
     *邀请码
     */
    @TableField(value = "invitation_code")
    private String invitationCode;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}