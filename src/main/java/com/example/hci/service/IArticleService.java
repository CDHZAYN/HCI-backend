package com.example.hci.service;

import com.example.hci.VO.ArticleBriefVO;
import com.example.hci.controller.dto.ArticleListDTO;
import com.example.hci.dao.dto.Article;

import java.util.List;

public interface IArticleService extends BaseService<Article> {
    List<ArticleBriefVO> selectArticleList(ArticleListDTO input);

    Article selectArticle(Integer articleId);

    List<ArticleBriefVO> selectHomeArticleList();
}
