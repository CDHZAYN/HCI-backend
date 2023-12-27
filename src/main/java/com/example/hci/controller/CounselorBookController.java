package com.example.hci.controller;

import com.example.hci.common.Response;
import com.example.hci.controller.dto.*;
import com.example.hci.dao.dto.CounselorBook;
import com.example.hci.service.ICounselorBookService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/counselorBook")
public class CounselorBookController {

    @Resource
    private ICounselorBookService service;

    @PostMapping("/list")
    public Response getCounselorBookList(@RequestBody CounselorBookListDTO input) {
        List<CounselorBook> result = service.getCounselorBookList(input);
        return Response.buildSuccess(result);
    }

    @PostMapping("/book")
    public Response book(@RequestBody CounselorBookDTO input){
        service.book(input);
        return Response.buildSuccess();
    }

    @PostMapping("/modify")
    public Response modify(@RequestBody CounselorModifyDTO input){
        service.modify(input);
        return Response.buildSuccess();
    }
    @PostMapping("/addFellow")
    public Response addFellow(@RequestBody FellowModifyDTO input) {
        service.addFellow(input);
        return Response.buildSuccess();
    }

    @PostMapping("/deleteFellow")
    public Response deleteFellow(@RequestBody FellowModifyDTO input){
        service.deleteFellow(input);
        return Response.buildSuccess();
    }

    @PostMapping("/cancel")
    public Response cancel(@RequestBody CounselorBookCancelDTO input){
        service.cancel(input);
        return Response.buildSuccess();
    }

    @PostMapping("/date")
    public Response date(@RequestBody DateDTO input){
        List<String> dates = service.date(input);
        return Response.buildSuccess(dates);
    }

}
