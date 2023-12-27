package com.example.hci.dao;

import com.example.hci.dao.dto.EventBook;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface EventBookMapper extends BaseMapper<EventBook>{

    @Select("select *, counselor.location location from event_book, counselor where substring(#{date},1,10) = substring(startTime,1,10) and #{date} <= startTime and remain > 0 and event_book.counselorId = counselor.id and event_book.id not in (select eventId from user_event where userId = #{userId} and type != 1)")
    List<EventBook> getEventBookList(@Param("userId") Integer userId, @Param("date") String date);

    @Select("select * from event_book where startTime >= NOW() and startTime <= ADDDATE(CURDATE(), 14) and remain > 0 and event_book.id not in (select eventId from user_event where userId = #{userId} and type != 1)")
    List<EventBook> getEventDate(@Param("userId") Integer userId);

    @Select("select id from event_book where endTime <= NOW() and endTime >= CURRENT_TIMESTAMP - INTERVAL 1 HOUR")
    List<Integer> getEventBookInOneHour();
}
