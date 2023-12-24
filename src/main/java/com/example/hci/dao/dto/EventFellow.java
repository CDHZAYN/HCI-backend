package com.example.hci.dao.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("event_fellow")
public class EventFellow {

    @TableField("userId")
    private Integer userId;

    @TableField("eventId")
    private Integer eventId;

    @TableField("fellowId")
    private Integer fellowId;
}
