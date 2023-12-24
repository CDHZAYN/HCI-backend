package com.example.hci.dao.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.hci.common.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("inspirations")
public class Inspirations extends Entity {

    @TableField("poison")
    private String poison;

    @TableField("pic")
    private String pic;
}
