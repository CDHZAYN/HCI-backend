package com.example.hci.service;

import com.example.hci.controller.dto.EventBookCancelDTO;
import com.example.hci.controller.dto.EventBookDTO;
import com.example.hci.controller.dto.EventModifyDTO;
import com.example.hci.dao.dto.EventBook;

import java.util.List;

public interface IEventBookService extends BaseService<EventBook> {

    List<EventBook> getEventBookList(String date);

    void book(EventBookDTO input);

    void modify(EventModifyDTO input);

    void addFellow(Integer userId, Integer eventId, Integer fellowId);

    void deleteFellow(Integer userId, Integer eventId, Integer fellowId);

    void cancel(EventBookCancelDTO input);

    List<String> date();
}
