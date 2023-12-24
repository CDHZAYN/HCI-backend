package com.example.hci.VO;

import lombok.Data;

import java.util.List;

@Data
public class CounselorBriefVO {

    private Integer id;

    private String name;

    private Integer position;

    private String profile;

    private String poison;

    private List<String> fieldLabel;
}
