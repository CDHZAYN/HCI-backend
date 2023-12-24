package com.example.hci.controller.dto;

import lombok.Data;

@Data
public class UserChangePwdDTO {

    private Integer userId;

    private String originalPwd;

    private String newPwd;
}
