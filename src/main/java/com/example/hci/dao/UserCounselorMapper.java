package com.example.hci.dao;

import com.example.hci.dao.dto.UserCounselor;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserCounselorMapper extends BaseMapper<UserCounselor> {


    List<UserCounselor> getBookRecord(@Param("userId") Integer userId, @Param("type") Integer type, @Param("date") String date, @Param("bookType") String bookType);

    @Select("select fellowId from counselor_fellow where counselorBookId = ${counselorBookId} and ${userId} = userId")
    List<Integer> getUserCounselorFellow(@Param("counselorBookId") Integer eventId, @Param("userId") Integer userId);
}
