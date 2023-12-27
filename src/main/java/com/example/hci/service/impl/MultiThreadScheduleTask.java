package com.example.hci.service.impl;

import com.example.hci.dao.UserCounselorMapper;
import com.example.hci.dao.UserEventMapper;
import com.example.hci.service.ICounselorBookService;
import com.example.hci.service.IEventBookService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@EnableScheduling
@EnableAsync
public class MultiThreadScheduleTask {

    @Resource
    private IEventBookService eventBookService;

    @Resource
    private ICounselorBookService counselorBookService;

    @Async
    @Scheduled(cron = "0 0 * * * *")
    public void finishUserEvent(){
        eventBookService.finishUserEvent();
    }

    @Async
    @Scheduled(cron = "0 0 * * * *")
    public void finishUserCounselor(){
        counselorBookService.finishUserCounselor();
    }
}
