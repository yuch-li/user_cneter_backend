package com.yuch.usercenter.mapper;

import com.yuch.usercenter.model.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author L6394
* @description 针对表【t_user】的数据库操作Mapper
* @createDate 2023-03-17 14:43:56
* @Entity com.yuch.usercenter.model.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




