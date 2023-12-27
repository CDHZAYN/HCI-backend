package com.example.hci.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class FellowModifyDTO {

    private Integer userId;

    private Integer bookId;

    private List<Integer> fellowId;
}
