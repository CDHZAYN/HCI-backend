package com.example.hci.dao;
import com.example.hci.dao.dto.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface UserMapper extends BaseMapper<User> {

    @Update("update user set password = #{newPwd} where id = ${userId}")
    void updatePwd(@Param("userId") Integer userId, @Param("newPwd") String newPwd);
}
