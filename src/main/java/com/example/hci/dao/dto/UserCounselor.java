package com.example.hci.dao.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.hci.common.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("user_counselor")
public class UserCounselor extends Entity {

    @TableField("userId")
    private Integer userId;

    @TableField("counselorBookId")
    private Integer counselorBookId;

    @TableField("isOnline")
    private Integer isOnline;

    @TableField("basicInfo")
    private String basicInfo;

    @TableField("relation")
    private String relation;

    @TableField("problem")
    private String problem;

    @TableField("addition")
    private String addition;

    @TableField("road")
    private Integer road;

    @TableField("reason")
    private String reason;
    /**
     * 0 已预约
     * 1 已取消
     * 2 已完成
     */
    @TableField("type")
    private Integer type;

    @TableField("cancel")
    private String cancel;
    /**
     * 预约开始时间
     */
    @TableField(exist = false)
    private String date;
}
