package com.example.hci.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class EventBookDTO {

    private Integer userId;

    private Integer eventId;

    private List<Integer> fellowId;

    private String expectation;

    private String question;

    private Integer road;

    private String reason;
}
