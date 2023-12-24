package com.example.hci.controller.dto;

import lombok.Data;

@Data
public class EventModifyDTO {

    private Integer userEventId;

    private Integer userId;

    private Integer eventId;

    private String expectation;

    private String question;

    private Integer road;

    private String reason;
}
