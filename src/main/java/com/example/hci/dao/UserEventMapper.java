package com.example.hci.dao;

import com.example.hci.dao.dto.UserEvent;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserEventMapper extends BaseMapper<UserEvent> {

    List<UserEvent> getBookRecord(@Param("userId") Integer userId, @Param("type") Integer type, @Param("date") String date);

    @Select("select fellowId from event_fellow where eventId = ${eventId} and ${userId} = userId")
    List<Integer> getUserEventFellow(@Param("eventId") Integer eventId, @Param("userId") Integer userId);
}
