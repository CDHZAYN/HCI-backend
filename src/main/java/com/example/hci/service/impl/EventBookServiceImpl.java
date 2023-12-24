package com.example.hci.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.hci.controller.dto.EventBookCancelDTO;
import com.example.hci.controller.dto.EventBookDTO;
import com.example.hci.controller.dto.EventModifyDTO;
import com.example.hci.dao.EventBookMapper;
import com.example.hci.dao.EventFellowMapper;
import com.example.hci.dao.UserEventMapper;
import com.example.hci.dao.dto.EventBook;
import com.example.hci.dao.dto.EventFellow;
import com.example.hci.dao.dto.UserEvent;
import com.example.hci.exception.MyException;
import com.example.hci.service.IEventBookService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.*;

@Service
public class EventBookServiceImpl extends BaseServiceImpl<EventBookMapper, EventBook> implements IEventBookService {

    @Resource
    private EventBookMapper mapper;
    @Resource
    private UserEventMapper userEventMapper;

    @Resource
    private EventFellowMapper eventFellowMapper;

    @Override
    public List<EventBook> getEventBookList(String date) {
        return mapper.getEventBookList(date);
    }

    @Override
    public void book(EventBookDTO input) {
        EventBook eventBook = mapper.selectById(input.getEventId());
        List<Integer> fellowId = input.getFellowId();
        int remain = eventBook.getRemain();
        if(fellowId.size() > remain) {
            throw new MyException("BS0001", "超过活动人数");
        }else{
            for(Integer fellow : fellowId) {
                EventFellow eventFellow = new EventFellow();
                eventFellow.setUserId(input.getUserId());
                eventFellow.setEventId(input.getEventId());
                eventFellow.setFellowId(fellow);
                eventFellowMapper.insert(eventFellow);
            }
            eventBook.setRemain(remain - fellowId.size());
            mapper.updateById(eventBook);
            UserEvent userEvent = new UserEvent();
            BeanUtils.copyProperties(input, userEvent);
            userEvent.setType(0);
            userEventMapper.insert(userEvent);
        }
    }

    @Override
    public void modify(EventModifyDTO input) {
        UserEvent userEvent = new UserEvent();
        BeanUtils.copyProperties(input, userEvent);
        userEvent.setId(input.getUserEventId());
        userEventMapper.updateById(userEvent);
    }

    @Override
    public void deleteFellow(Integer userId, Integer eventId, Integer fellowId) {
        QueryWrapper<EventFellow> ew = new QueryWrapper<>();
        ew.eq("userId", userId);
        ew.eq("eventId", eventId);
        ew.eq("fellowId", fellowId);
        eventFellowMapper.delete(ew);
        EventBook eventBook = mapper.selectById(eventId);
        eventBook.setRemain(eventBook.getRemain() + 1);
        mapper.updateById(eventBook);
    }

    @Override
    public void cancel(EventBookCancelDTO input) {
        UserEvent userEvent = userEventMapper.selectById(input.getUserEventId());
        userEvent.setCancel(input.getCancel());
        userEvent.setType(1);
        userEventMapper.updateById(userEvent);
        List<Integer> temp = userEventMapper.getUserEventFellow(input.getEventId(), input.getUserId());
        EventBook eventBook = mapper.selectById(input.getEventId());
        eventBook.setRemain(eventBook.getRemain() + temp.size());
        mapper.updateById(eventBook);
        QueryWrapper<EventFellow> ew = new QueryWrapper<>();
        ew.eq("userId", input.getUserId());
        ew.eq("eventId", input.getEventId());
        eventFellowMapper.delete(ew);
    }

    @Override
    public void addFellow(Integer userId, Integer eventId, Integer fellowId) {
        EventFellow eventFellow = new EventFellow();
        eventFellow.setEventId(eventId);
        eventFellow.setUserId(userId);
        eventFellow.setFellowId(fellowId);
        eventFellowMapper.insert(eventFellow);
        EventBook eventBook = mapper.selectById(eventId);
        eventBook.setRemain(eventBook.getRemain() - 1);
        mapper.updateById(eventBook);
    }

    @Override
    public List<String> date() {
        List<EventBook> temp = mapper.getEventDate();
        Set<String> set = new HashSet<>();
        for(EventBook eventBook : temp){
            set.add(eventBook.getStartTime().substring(0,10));
        }
        return new ArrayList<>(set);
    }
}
