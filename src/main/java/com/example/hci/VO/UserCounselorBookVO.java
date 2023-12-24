package com.example.hci.VO;


import com.example.hci.dao.dto.CounselorBook;
import com.example.hci.dao.dto.UserCounselor;
import lombok.Data;

import java.util.List;

@Data
public class UserCounselorBookVO {

    private CounselorBook counselorBook;

    private UserCounselor userCounselor;

    private List<Integer> userFellowId;

    private String deleteReason;
}
