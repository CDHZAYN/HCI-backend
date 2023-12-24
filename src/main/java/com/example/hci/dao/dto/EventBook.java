package com.example.hci.dao.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.hci.common.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("event_book")
public class EventBook extends Book {

    @TableField("name")
    private String name;

    @TableField("counselorId")
    private Integer counselorId;

    @TableField("startTime")
    private String startTime;

    @TableField("endTime")
    private String endTime;

    @TableField("price")
    private Float price;

    @TableField("remain")
    private Integer remain;

    /**
     * 0 线上
     * 1 线下
     */
    @TableField("isOnline")
    private Integer isOnline;

    @TableField(exist = false)
    private String location;

}
