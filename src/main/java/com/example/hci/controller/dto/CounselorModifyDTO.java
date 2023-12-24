package com.example.hci.controller.dto;

import lombok.Data;


@Data
public class CounselorModifyDTO {

    private Integer userCounselorId;

    private Integer userId;

    private Integer counselorBookId;

    private Integer isOnline;

    private String basicInfo;

    private String relation;

    private String problem;

    private String addition;

    private Integer road;

    private String reason;
}
