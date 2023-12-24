package com.example.hci.VO;

import com.example.hci.dao.dto.EventBook;
import com.example.hci.dao.dto.UserEvent;
import lombok.Data;

import java.util.List;

@Data
public class UserEventBookVO {

    private EventBook eventBook;

    private UserEvent userEvent;

    private List<Integer> userFellowId;

    private String deleteReason;

}
