package com.example.hci.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.hci.VO.ArticleBriefVO;
import com.example.hci.controller.dto.ArticleListDTO;
import com.example.hci.dao.ArticleMapper;
import com.example.hci.dao.EventBookMapper;
import com.example.hci.dao.dto.Article;
import com.example.hci.dao.dto.EventBook;
import com.example.hci.service.IArticleService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl extends BaseServiceImpl<ArticleMapper, Article> implements IArticleService {


    @Resource
    private ArticleMapper mapper;

    @Resource
    private EventBookMapper eventBookMapper;
    @Override
    public List<ArticleBriefVO> selectArticleList(ArticleListDTO input) {
        QueryWrapper<Article> ew = new QueryWrapper<>();
        if(!StringUtils.isEmpty(input.getTitle())){
            ew.like("title", input.getTitle());
        }
        if(input.getType() != null){
            ew.eq("type", input.getType());
        }
        List<Article> ret = mapper.selectArticleList(8*input.getSkip(), ew);
        List<ArticleBriefVO> result = new ArrayList<>();
        for(Article article : ret) {
            ArticleBriefVO articleBriefVO = new ArticleBriefVO();
            BeanUtils.copyProperties(article, articleBriefVO);
            result.add(articleBriefVO);
        }
        return result;
    }

    @Override
    public Article selectArticle(Integer articleId) {
        Article article = mapper.selectById(articleId);
        if(article.getEventId() != null) {
            EventBook eventBook = eventBookMapper.selectById(article.getEventId());
            article.setEventDate(eventBook.getStartTime());
        }
        return article;
    }

    @Override
    public List<ArticleBriefVO> selectHomeArticleList() {
        QueryWrapper<Article> ew = new QueryWrapper<>();
        ew.eq("type", 0);
        ew.orderByDesc("date").last("limit 4");
        List<Article> ret = mapper.selectList(ew);
        QueryWrapper<Article> ew2 = new QueryWrapper<>();
        ew2.ne("type", 0);
        ew2.orderByDesc("date").last("limit 4");
        ret.addAll(mapper.selectList(ew2));
        List<ArticleBriefVO> result = new ArrayList<>();
        for(Article article : ret) {
            ArticleBriefVO articleBriefVO = new ArticleBriefVO();
            BeanUtils.copyProperties(article,articleBriefVO);
            result.add(articleBriefVO);
        }
        return result;
    }
}
