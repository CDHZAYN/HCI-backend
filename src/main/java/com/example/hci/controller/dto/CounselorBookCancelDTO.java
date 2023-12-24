package com.example.hci.controller.dto;

import lombok.Data;

@Data
public class CounselorBookCancelDTO {

    private Integer userCounselorId;

    private Integer userId;

    private Integer counselorBookId;

    private String cancel;
}
