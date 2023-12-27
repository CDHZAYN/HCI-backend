package com.example.hci.controller.dto;

import lombok.Data;

@Data
public class BookRecordDTO {

    private Integer userId;

    private Integer type;

    private String date;

    private String bookType;
}
