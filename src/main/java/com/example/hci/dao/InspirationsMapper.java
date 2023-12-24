package com.example.hci.dao;

import com.example.hci.dao.dto.Inspirations;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface InspirationsMapper extends BaseMapper<Inspirations> {

    @Select("select * from inspirations order by rand() limit 3")
    List<Inspirations> selectInspirationsRand();

}
