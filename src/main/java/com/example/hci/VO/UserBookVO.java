package com.example.hci.VO;

import lombok.Data;

import java.util.List;

@Data
public class UserBookVO {

    private List<UserEventBookVO> userEventBookVOS;

    private List<UserCounselorBookVO> userCounselorBookVOS;
}
