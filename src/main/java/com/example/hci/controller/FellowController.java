package com.example.hci.controller;

import com.example.hci.VO.FellowBriefVO;
import com.example.hci.common.Response;
import com.example.hci.dao.dto.Fellow;
import com.example.hci.service.IFellowService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/fellow")
public class FellowController {

    @Resource
    private IFellowService service;

    @GetMapping("/list")
    public Response getFellowList(@RequestParam Integer userId) {
        List<FellowBriefVO> fellowBriefList = service.getFellowList(userId);
        return Response.buildSuccess(fellowBriefList);
    }

    @GetMapping("/detail")
    public Response getFellowDetail(@RequestParam Integer userId, @RequestParam Integer fellowId) {
        Fellow fellow = service.getFellowDetail(userId, fellowId);
        return Response.buildSuccess(fellow);
    }

    @PostMapping("/add")
    public Response addFellow(@RequestBody Fellow input) {
        if (input.getId() == null) {
            service.save(input);
        } else {
            service.updateById(input);
        }
        return Response.buildSuccess(input);
    }

    @PostMapping("/delete")
    public Response deleteFellow(@RequestParam Integer userId, @RequestParam Integer fellowId) {
        service.deleteFellow(userId, fellowId);
        return Response.buildSuccess();
    }
}
