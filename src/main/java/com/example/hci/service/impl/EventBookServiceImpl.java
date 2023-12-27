package com.example.hci.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.hci.controller.dto.EventBookCancelDTO;
import com.example.hci.controller.dto.EventBookDTO;
import com.example.hci.controller.dto.EventModifyDTO;
import com.example.hci.controller.dto.FellowModifyDTO;
import com.example.hci.dao.*;
import com.example.hci.dao.dto.*;
import com.example.hci.exception.MyException;
import com.example.hci.service.EmailService;
import com.example.hci.service.IEventBookService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.*;

@Service
public class EventBookServiceImpl extends BaseServiceImpl<EventBookMapper, EventBook> implements IEventBookService {

    @Resource
    private EventBookMapper mapper;
    @Resource
    private UserEventMapper userEventMapper;

    @Resource
    private EventFellowMapper eventFellowMapper;

    @Resource
    private FellowMapper fellowMapper;

    @Resource
    private CounselorMapper counselorMapper;

    @Resource
    private EmailService emailService;

    @Resource
    private ArticleMapper articleMapper;

    @Override
    public List<EventBook> getEventBookList(Integer userId, String date) {
        return mapper.getEventBookList(userId, date);
    }

    @Override
    public void book(EventBookDTO input) {
        EventBook eventBook = mapper.selectById(input.getEventId());
        List<Integer> fellowId = input.getFellowId();
        int remain = eventBook.getRemain();
        if (fellowId.size() > remain) {
            throw new MyException("BS0001", "超过活动人数");
        } else {
            for (Integer fellow : fellowId) {
                Fellow f = fellowMapper.selectById(fellow);
                if (f.getDeleted() == 1) {
                    String msg = MessageFormat.format("{0} 已被删除，无法预约", f.getNickname());
                    throw new MyException("BS0001", msg);
                }
            }
            for (Integer fellow : fellowId) {
                EventFellow eventFellow = new EventFellow();
                eventFellow.setUserId(input.getUserId());
                eventFellow.setEventId(input.getEventId());
                eventFellow.setFellowId(fellow);
                eventFellowMapper.insert(eventFellow);
            }
            eventBook.setRemain(remain - fellowId.size());
            mapper.updateById(eventBook);
            if(eventBook.getRemain()== 0){
                QueryWrapper<Article> ew = new QueryWrapper<>();
                ew.eq("eventId", eventBook.getId());
                ew.eq("type", 1);
                Article article = articleMapper.selectOne(ew);
                article.setIsAvailable(1);
                articleMapper.updateById(article);
            }
            UserEvent userEvent = new UserEvent();
            BeanUtils.copyProperties(input, userEvent);
            userEvent.setType(0);
            userEventMapper.insert(userEvent);
            List<Fellow> fellows = fellowMapper.selectBatchIds(fellowId);
            String subject = "泽恩心理咨询预定确认";
            String content = "亲爱的{0}：\n" +
                    "\n" +
                    "感谢您选择泽恩心理咨询服务！我们已收到您的预定请求，并高兴地通知您，您的预定已确认。\n" +
                    "\n" +
                    "预定详情：\n" +
                    "- 预定时间： {1}\n" +
                    "- 预定地点： {2}\n" +
                    "- 活动名称： {3}\n" +
                    "\n" +
                    "请您提前15分钟到达，我们期待为您提供优质的心理咨询服务。\n" +
                    "\n" +
                    "如有任何问题或需要进一步的帮助，请随时联系我们。\n" +
                    "\n" +
                    "谢谢！\n" +
                    "泽恩心理咨询团队";
            String date = eventBook.getStartTime();
            String location = counselorMapper.selectById(eventBook.getCounselorId()).getLocation();
            String name = eventBook.getName();
            for (Fellow fellow : fellows) {
                String nickname = fellow.getNickname();
                String email = fellow.getEmail();
                emailService.sendBookMessage(email, subject, MessageFormat.format(content, nickname, date, location, name));
            }
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
    public void deleteFellow(FellowModifyDTO input) {
        QueryWrapper<EventFellow> ew = new QueryWrapper<>();
        ew.eq("userId", input.getUserId());
        ew.eq("eventId", input.getBookId());
        ew.in("fellowId", input.getFellowId());
        eventFellowMapper.delete(ew);
        EventBook eventBook = mapper.selectById(input.getBookId());
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
        if(eventBook.getRemain() == 0){
            QueryWrapper<Article> ew2 = new QueryWrapper<>();
            ew2.eq("eventId", eventBook.getId());
            ew2.eq("type", 1);
            Article article = articleMapper.selectOne(ew2);
            article.setIsAvailable(0);
            articleMapper.updateById(article);
        }
        eventBook.setRemain(eventBook.getRemain() + temp.size());
        mapper.updateById(eventBook);
        QueryWrapper<EventFellow> ew = new QueryWrapper<>();
        ew.eq("userId", input.getUserId());
        ew.eq("eventId", input.getEventId());
        List<EventFellow> fellows = eventFellowMapper.selectList(ew);
        String subject = "泽恩心理咨询预定取消确认";
        String content = "亲爱的{0}：\n" +
                "\n" +
                "我们收到了您的预定取消请求。我们很抱歉无法在这次的预定中见到您，但我们完全理解生活中的不可预测性。\n" +
                "\n" +
                "取消详情：\n" +
                "- 取消时间： {1}\n" +
                "- 取消地点： {2}\n" +
                "- 活动名称： {3}\n" +
                "\n" +
                "如果未来您有需要，我们将随时为您提供心理咨询服务。如果有任何其他问题或需要支持，请随时联系我们。\n" +
                "\n" +
                "谢谢您的理解和支持。\n" +
                "\n" +
                "祝您一天愉快！\n" +
                "泽恩心理咨询团队";
        String date = eventBook.getStartTime();
        String location = counselorMapper.selectById(eventBook.getCounselorId()).getLocation();
        String name = eventBook.getName();
        for (EventFellow eventFellow : fellows) {
            Fellow fellow = fellowMapper.selectById(eventFellow.getFellowId());
            String email = fellow.getEmail();
            String nickname = fellow.getNickname();
            emailService.sendBookMessage(email, subject, MessageFormat.format(content, nickname, date, location, name));
        }
    }

    @Override
    public void addFellow(FellowModifyDTO input) {
        for (Integer fellowId : input.getFellowId()) {
            EventFellow eventFellow = new EventFellow();
            eventFellow.setEventId(input.getBookId());
            eventFellow.setUserId(input.getUserId());
            eventFellow.setFellowId(fellowId);
            eventFellowMapper.insert(eventFellow);
        }
        EventBook eventBook = mapper.selectById(input.getBookId());
        eventBook.setRemain(eventBook.getRemain() - 1);
        mapper.updateById(eventBook);
    }

    @Override
    public List<String> date() {
        List<EventBook> temp = mapper.getEventDate();
        Set<String> set = new HashSet<>();
        for (EventBook eventBook : temp) {
            set.add(eventBook.getStartTime().substring(0, 10));
        }
        return new ArrayList<>(set);
    }

    @Override
    public void finishUserEvent() {
        List<Integer> eventIds = mapper.getEventBookInOneHour();
        if (!eventIds.isEmpty()) {
            QueryWrapper<UserEvent> ew = new QueryWrapper<>();
            ew.in("eventId", eventIds);
            ew.eq("type", 0);
            List<UserEvent> userEvents = userEventMapper.selectList(ew);
            for (UserEvent userEvent : userEvents) {
                userEvent.setType(2);
                userEventMapper.updateById(userEvent);
            }
        }
    }
}
