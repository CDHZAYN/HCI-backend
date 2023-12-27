package com.example.hci.dao.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.example.hci.common.Entity;
import lombok.Data;

@Data
public class UserBook extends Entity {

    @TableField(exist = false)
    private String date;
}
