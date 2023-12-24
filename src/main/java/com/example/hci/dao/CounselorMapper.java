package com.example.hci.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.hci.dao.dto.Counselor;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CounselorMapper extends BaseMapper<Counselor> {

    List<Counselor> selectCounselorList(@Param("fieldLabel") String fieldLabel, @Param("form") String form, @Param("skip") Integer skip, @Param("ew")QueryWrapper<Counselor> ew);


    @Select("select award from counselor_award where counselorId = ${counselorId}")
    List<String> getCounselorAwards(@Param("counselorId") Integer counselorId);

    @Select("select field from counselor_field where counselorId = ${counselorId}")
    List<String> getCounselorField(@Param("counselorId") Integer counselorId);

    @Select("select form form counselor_form where counselorId = ${counselorId}")
    List<String> getCounselorForm(@Param("counselorId") Integer counselorId);
}
