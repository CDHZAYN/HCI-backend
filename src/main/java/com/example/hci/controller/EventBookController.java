package com.example.hci.controller;

import com.example.hci.common.Response;
import com.example.hci.controller.dto.EventBookCancelDTO;
import com.example.hci.controller.dto.EventBookDTO;
import com.example.hci.controller.dto.EventModifyDTO;
import com.example.hci.dao.dto.EventBook;
import com.example.hci.service.IEventBookService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/eventBook")
public class EventBookController {

    @Resource
    private IEventBookService service;

    @GetMapping("/list")
    public Response getEventBookList(@RequestParam String date) {
        List<EventBook> result = service.getEventBookList(date);
        return Response.buildSuccess(result);
    }

    @PostMapping("/book")
    public Response book(@RequestBody EventBookDTO input){
        service.book(input);
        return Response.buildSuccess();
    }

    @PostMapping("/modify")
    public Response modify(@RequestBody EventModifyDTO input){
        service.modify(input);
        return Response.buildSuccess();
    }

    @PostMapping("/addFellow")
    public Response addFellow(@RequestParam Integer userId, @RequestParam Integer eventId, @RequestParam Integer fellowId){
        service.addFellow(userId, eventId, fellowId);
        return Response.buildSuccess();
    }

    @PostMapping("/deleteFellow")
    public Response deleteFellow(@RequestParam Integer userId, @RequestParam Integer eventId, @RequestParam Integer fellowId){
        service.deleteFellow(userId, eventId, fellowId);
        return Response.buildSuccess();
    }

    @PostMapping("/cancel")
    public Response cancel(EventBookCancelDTO input){
        service.cancel(input);
        return Response.buildSuccess();
    }

    @GetMapping("/date")
    public Response date() {
        List<String> dates = service.date();
        return Response.buildSuccess(dates);
    }
}
