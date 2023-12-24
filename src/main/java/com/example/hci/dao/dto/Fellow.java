package com.example.hci.dao.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.hci.common.Entity;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@TableName("fellow")
public class Fellow extends Entity {

    @TableField("userId")
    private Integer userId;

    @TableField("nickname")
    private String nickname;

    @TableField("email")
    private String email;

    @TableField("sex")
    private String sex;

    @TableField("birthYear")
    private Integer birthYear;

    @TableField("birthMonth")
    private Integer birthMonth;

    @TableField("vocation")
    private String vocation;

    @TableField("note")
    private String note;
}
