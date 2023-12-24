package com.example.hci.dao.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.hci.common.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("counselor_book")
public class CounselorBook extends Entity {

    @TableField("counselorId")
    private Integer counselorId;

    @TableField("name")
    private String name;

    @TableField("startTime")
    private String startTime;

    @TableField("endTime")
    private String endTime;

    @TableField("price")
    private Float price;

    @TableField("upLimit")
    private Integer UpLimit;

    /**
     * 0 有空
     * 1 没空
     */
    @TableField("isAvailable")
    private Integer isAvailable;

    @TableField(exist = false)
    private String location;

    @TableField(exist = false)
    private String profile;
}
