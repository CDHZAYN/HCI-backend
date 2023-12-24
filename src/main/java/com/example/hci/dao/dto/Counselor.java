package com.example.hci.dao.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.hci.common.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@TableName("counselor")
public class Counselor extends Entity {

    @TableField("name")
    private String name;

    /**
     * 0 男
     * 1 女
     */
    @TableField("sex")
    private Integer sex;

    @TableField("position")
    private Integer position;

    @TableField("location")
    private String location;

    @TableField("introduction")
    private String introduction;

    @TableField("field")
    private String field;

    @TableField("method")
    private String method;

    @TableField("price")
    private String price;
    /**
     * 给来访者的话
     */
    @TableField("poison")
    private String poison;

    /**
     * 人像url
     */
    @TableField("profile")
    private String profile;

    @TableField("basic")
    private Integer basic;

    @TableField(exist = false)
    private List<String> award;

    @TableField(exist = false)
    private List<String> fieldLabel;

    @TableField(exist = false)
    private List<String> form;
}
