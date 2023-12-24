package com.example.hci.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.hci.dao.dto.Article;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ArticleMapper extends BaseMapper<Article>{

    @Select("select * from article where 1=1 and ${ew.sqlSegment} and order by date desc limit 5 skip ${skip}")
    List<Article> selectArticleList(@Param("skip") Integer skip, @Param("ew")QueryWrapper<Article> ew);

}
