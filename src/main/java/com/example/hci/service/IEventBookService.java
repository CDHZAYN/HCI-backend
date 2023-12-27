package com.example.hci.service;

import com.example.hci.controller.dto.EventBookCancelDTO;
import com.example.hci.controller.dto.EventBookDTO;
import com.example.hci.controller.dto.EventModifyDTO;
import com.example.hci.controller.dto.FellowModifyDTO;
import com.example.hci.dao.dto.EventBook;


import java.util.List;

public interface IEventBookService extends BaseService<EventBook> {

    List<EventBook> getEventBookList(Integer userId, String date);

    void book(EventBookDTO input);

    void modify(EventModifyDTO input);

    void addFellow(FellowModifyDTO input);

    void deleteFellow(FellowModifyDTO input);

    void cancel(EventBookCancelDTO input);

    List<String> date();

    void finishUserEvent();
}
