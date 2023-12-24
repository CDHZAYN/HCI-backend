package com.example.hci.VO;

import lombok.Data;

@Data
public class ArticleBriefVO {

    private String title;

    private String subtitle;

    /**
     * 0 咨询师专栏
     * 1 活动预约
     * 2 活动回顾
     */
    private Integer type;

    private String date;

    private String pic;

    private Integer isAvailable;

    private Integer eventId;
}
