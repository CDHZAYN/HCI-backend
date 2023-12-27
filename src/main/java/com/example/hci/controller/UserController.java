package com.example.hci.controller;


import com.example.hci.VO.AccountVO;
import com.example.hci.VO.BookRecordVO;
import com.example.hci.VO.UserBookVO;
import com.example.hci.VO.UserVO;
import com.example.hci.common.Response;
import com.example.hci.controller.dto.BookRecordDTO;
import com.example.hci.controller.dto.LoginDTO;
import com.example.hci.controller.dto.RegisterDTO;
import com.example.hci.controller.dto.UserChangePwdDTO;
import com.example.hci.service.IUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService service;


    @PostMapping("/login")
    public Response login(@RequestBody LoginDTO loginDTO) {
        UserVO userVO = service.login(loginDTO.getUsername(), loginDTO.getEmail(), loginDTO.getPassword());
        return Response.buildSuccess(userVO);
    }

    @PostMapping("/register")
    public Response register(@RequestBody RegisterDTO registerDTO) {
        service.register(registerDTO);
        return Response.buildSuccess();
    }

    @PostMapping("/verify")
    public Response verify(@RequestParam String email) {
        String verifyCode = service.verify(email);
        return Response.buildSuccess(verifyCode);
    }

    @GetMapping("/getAccount")
    public Response getUserDetail(@RequestParam Integer userId) {
        AccountVO accountVO = service.getAccount(userId);
        return Response.buildSuccess(accountVO);
    }

    @PostMapping("/changePwd")
    public Response changeUserPasswd(@RequestBody UserChangePwdDTO input) {
        service.changeUserPwd(input);
        return Response.buildSuccess();
    }

    @PostMapping("/bookRecord")
    public Response getBookRecord(@RequestBody BookRecordDTO input) {
        BookRecordVO bookRecord = service.getBookRecord(input.getUserId(), input.getType(), input.getDate(), input.getBookType());
        return Response.buildSuccess(bookRecord);
    }
}
