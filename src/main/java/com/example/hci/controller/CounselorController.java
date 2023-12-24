package com.example.hci.controller;

import com.example.hci.VO.CounselorBriefVO;
import com.example.hci.common.Response;
import com.example.hci.controller.dto.CounselorListDTO;
import com.example.hci.dao.dto.Counselor;
import com.example.hci.service.ICounselorService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/counselor")
public class CounselorController {

    @Resource
    private ICounselorService iCounselorService;

    @PostMapping("/list")
    public Response getCounselorList(@RequestBody CounselorListDTO input){
        List<CounselorBriefVO> result = iCounselorService.selectCounselorList(input);
        return Response.buildSuccess(result);
    }

    @GetMapping("/detail")
    public Response getCounselorDetail(@RequestParam Integer counselorId) {
        Counselor counselor = iCounselorService.selectCounselor(counselorId);
        return Response.buildSuccess(counselor);
    }

}
