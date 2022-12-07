package com.czc.example.activiti.mapper;

import com.czc.example.activiti.bean.UserInfoBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * @author 金陵笑笑生
 * @description: TODO
 * @date 2022/10/4下午3:12
 */
@Mapper
@Component
public interface UserInfoMapper {

    /**
     * 从数据库中查询用户
     * @param username
     * @return
     */
    @Select("select * from user where username = #{username}")
    UserInfoBean selectByUsername(@Param("username") String username);
}
