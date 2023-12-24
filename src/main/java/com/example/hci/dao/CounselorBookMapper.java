package com.example.hci.dao;

import com.example.hci.dao.dto.CounselorBook;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CounselorBookMapper extends BaseMapper<CounselorBook> {

    List<CounselorBook> getCounselorBookList(@Param("counselorId") List<Integer> counselorId, @Param("date") String date, @Param("form") String form);

    @Select("SELECT * FROM counselor_book WHERE startTime >= CURDATE() and startTime <= ADDDATE(CURDATE(), 14) AND name like '%${type}'")
    List<CounselorBook> getCounselorBookDate(@Param("type") String type);
}
