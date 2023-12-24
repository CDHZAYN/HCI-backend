package com.example.hci.dao;

import com.example.hci.dao.dto.UserCounselor;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserCounselorMapper extends BaseMapper<UserCounselor> {


    List<UserCounselor> getBookRecord(@Param("userId") Integer userId, @Param("type") Integer type, @Param("date") String date);

    @Select("select fellowId from counselor_fellow where eventId = ${eventId} and ${userId} = userId")
    List<Integer> getUserCounselorFellow(@Param("eventId") Integer eventId, @Param("userId") Integer userId);
}
