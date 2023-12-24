package com.example.hci.dao;

import com.example.hci.dao.dto.EventBook;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface EventBookMapper extends BaseMapper<EventBook>{

    @Select("select *, counselor.location location from event_book, counselor where substring(#{date},1,10) = substring(startTime,1,10) and #{date} <= startTime and remain > 0 and event_book.counselorId = counselor.id")
    List<EventBook> getEventBookList(@Param("date") String date);

    @Select("select * from event_book where startTime >= CURDATE() and startTime <= ADDDATE(CURDATE(), 14)")
    List<EventBook> getEventDate();
}
