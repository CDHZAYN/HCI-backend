package com.example.hci.service;

import com.example.hci.VO.AccountVO;
import com.example.hci.VO.BookRecordVO;
import com.example.hci.VO.UserBookVO;
import com.example.hci.VO.UserVO;
import com.example.hci.controller.dto.RegisterDTO;
import com.example.hci.controller.dto.UserChangePwdDTO;
import com.example.hci.dao.dto.User;

public interface IUserService extends BaseService<User>{

    UserVO login(String username, String email, String password);
    User auth(String token);
    void register(RegisterDTO registerDTO);
    String verify(String email);

    void changeUserPwd(UserChangePwdDTO input);

    BookRecordVO getBookRecord(Integer userId, Integer type, String date, String bookType);

    AccountVO getAccount(Integer userId);
}
