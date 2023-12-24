package com.example.hci.dao.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.hci.common.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("article")
public class Article extends Entity {

    @TableField("title")
    private String title;

    @TableField("subtitle")
    private String subtitle;

    /**
     * 0 咨询师专栏
     * 1 活动预约
     * 2 活动回顾
     */
    @TableField("type")
    private Integer type;

    @TableField("date")
    private String date;

    @TableField("pic")
    private String pic;

    @TableField("isAvailable")
    private Integer isAvailable;

    @TableField("text")
    private String text;

    @TableField("eventId")
    private Integer eventId;
}
