package com.example.hci.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class CounselorBookListDTO {

    private List<Integer> counselorId;

    private String date;

    private String form;
}
