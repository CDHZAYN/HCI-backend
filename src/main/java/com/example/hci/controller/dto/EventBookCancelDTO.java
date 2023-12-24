package com.example.hci.controller.dto;

import lombok.Data;

@Data
public class EventBookCancelDTO {

    private Integer userEventId;

    private Integer userId;

    private Integer eventId;

    private String cancel;
}
