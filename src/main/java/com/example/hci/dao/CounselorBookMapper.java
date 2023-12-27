package com.example.hci.dao;

import com.example.hci.dao.dto.CounselorBook;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CounselorBookMapper extends BaseMapper<CounselorBook> {

    List<CounselorBook> getCounselorBookList(@Param("counselorId") List<Integer> counselorId, @Param("date") String date, @Param("form") String form);

    List<CounselorBook> getCounselorBookDate(@Param("counselorId") List<Integer> counselorId, @Param("type") String type);

    @Select("select id from counselor_book where endTime <= NOW() and endTime >= CURRENT_TIMESTAMP - INTERVAL 1 HOUR")
    List<Integer> getCounselorBookInOneHour();
}
