package com.example.hci.controller.dto;

import lombok.Data;

@Data
public class CounselorListDTO {

    private String counselorName;

    private Integer priceLowerBound;

    private Integer priceUpperBound;

    private String fieldLabel;

    private String location;

    private Integer sex;

    private Integer position;

    private String form;

    private Integer skip;
}
