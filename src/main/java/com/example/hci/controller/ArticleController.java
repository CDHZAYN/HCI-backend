package com.example.hci.controller;

import com.example.hci.VO.ArticleBriefVO;
import com.example.hci.common.Response;
import com.example.hci.controller.dto.ArticleListDTO;
import com.example.hci.dao.dto.Article;
import com.example.hci.service.IArticleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private IArticleService service;

    @GetMapping("/list")
    public Response getArticleList(@RequestBody ArticleListDTO input) {
        List<ArticleBriefVO> result = service.selectArticleList(input);
        return Response.buildSuccess(result);
    }

    @GetMapping("/detail")
    public Response getArticleDetail(Integer articleId) {
        Article article = service.selectArticle(articleId);
        return Response.buildSuccess(article);
    }
}
