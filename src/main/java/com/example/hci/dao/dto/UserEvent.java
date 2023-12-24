package com.example.hci.dao.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.hci.common.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("user_event")
public class UserEvent extends Entity {

    @TableField("eventId")
    private Integer eventId;

    @TableField("userId")
    private Integer userId;

    @TableField("expectation")
    private String expectation;

    @TableField("question")
    private String question;

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
