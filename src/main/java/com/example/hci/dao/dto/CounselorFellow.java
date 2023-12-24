package com.example.hci.dao.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("counselor_fellow")
public class CounselorFellow {

    @TableField("userId")
    private Integer userId;

    @TableField("counselorBookId")
    private Integer counselorBookId;

    @TableField("fellowId")
    private Integer fellowId;
}
