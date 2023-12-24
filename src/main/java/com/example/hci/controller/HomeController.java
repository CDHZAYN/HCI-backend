package com.example.hci.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.hci.VO.ArticleBriefVO;
import com.example.hci.VO.CounselorBriefVO;
import com.example.hci.common.Response;
import com.example.hci.dao.dto.Article;
import com.example.hci.dao.dto.Counselor;
import com.example.hci.dao.dto.Inspirations;
import com.example.hci.service.IArticleService;
import com.example.hci.service.ICounselorService;
import com.example.hci.service.IInspirationsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Resource
    private ICounselorService iCounselorService;
    @Resource
    private IInspirationsService iInspirationsService;
    @Resource
    private IArticleService iArticleService;

    @GetMapping("/banner")
    public Response getCounselorList(){
        List<CounselorBriefVO> result = iCounselorService.selectHomeCounselorList();
        return Response.buildSuccess(result);
    }

    @GetMapping("/carousel")
    public Response getInspirationsList(){
        List<Inspirations> result = iInspirationsService.selectInspirationsRand();
        return Response.buildSuccess(result);
    }

    @GetMapping("/article")
    public Response getArticleList(){
        List<ArticleBriefVO> result = iArticleService.selectHomeArticleList();
        return Response.buildSuccess(result);
    }
}
