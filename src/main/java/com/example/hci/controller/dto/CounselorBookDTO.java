package com.example.hci.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class CounselorBookDTO {

    private Integer userId;

    private Integer counselorBookId;

    private Integer isOnline;

    private List<Integer> userFellowId;

    private String basicInfo;

    private String relation;

    private String problem;

    private String addition;

    private Integer road;

    private String reason;
}
