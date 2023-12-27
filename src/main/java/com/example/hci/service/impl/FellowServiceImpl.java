package com.example.hci.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.hci.VO.FellowBriefVO;
import com.example.hci.dao.*;
import com.example.hci.dao.dto.*;
import com.example.hci.exception.MyException;
import com.example.hci.service.IFellowService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class FellowServiceImpl extends BaseServiceImpl<FellowMapper, Fellow> implements IFellowService {

    @Resource
    private FellowMapper mapper;

    @Resource
    private UserEventMapper userEventMapper;

    @Resource
    private UserCounselorMapper userCounselorMapper;

    @Resource
    private EventFellowMapper eventFellowMapper;

    @Resource
    private CounselorFellowMapper counselorFellowMapper;

    @Override
    public List<FellowBriefVO> getFellowList(Integer userId) {
        QueryWrapper<Fellow> ew = new QueryWrapper<>();
        ew.eq("userId", userId);
        ew.eq("deleted", 0);
        List<Fellow> ret = mapper.selectList(ew);
        List<FellowBriefVO> result = new ArrayList<>();
        for (Fellow fellow : ret) {
            FellowBriefVO fellowBriefVO = new FellowBriefVO();
            fellowBriefVO.setFellowId(fellow.getId());
            fellowBriefVO.setNickname(fellow.getNickname());
            fellowBriefVO.setNote(fellow.getNote());
            result.add(fellowBriefVO);
        }
        return result;
    }

    @Override
    public Fellow getFellowDetail(Integer userId, Integer fellowId) {
        QueryWrapper<Fellow> ew = new QueryWrapper<>();
        ew.eq("userId", userId);
        ew.eq("id", fellowId);
        return mapper.selectOne(ew);
    }

    @Override
    public void deleteFellow(Integer userId, Integer fellowId) {
        QueryWrapper<UserEvent> ew = new QueryWrapper<>();
        ew.eq("userId", userId);
        ew.eq("type", 0);
        List<UserEvent> userEvents = userEventMapper.selectList(ew);
        for (UserEvent userEvent : userEvents) {
            QueryWrapper<EventFellow> ew2 = new QueryWrapper<>();
            ew2.eq("eventId", userEvent.getEventId());
            ew2.eq("fellowId", fellowId);
            ew2.eq("userId", userId);
            EventFellow eventFellow = eventFellowMapper.selectOne(ew2);
            if (eventFellow != null) {
                throw new MyException("DS0001", "该咨询者还有预约活动，不能删除");
            }
        }
        QueryWrapper<UserCounselor> ew3 = new QueryWrapper<>();
        ew3.eq("userId", userId);
        ew3.eq("type", 0);
        List<UserCounselor> userCounselors = userCounselorMapper.selectList(ew3);
        for (UserCounselor userCounselor : userCounselors) {
            QueryWrapper<CounselorFellow> ew4 = new QueryWrapper<>();
            ew4.eq("counselorBookId", userCounselor.getCounselorBookId());
            ew4.eq("fellowId", fellowId);
            ew4.eq("userId", userId);
            CounselorFellow counselorFellow = counselorFellowMapper.selectOne(ew4);
            if (counselorFellow != null) {
                throw new MyException("DS0002", "该咨询者还有预约咨询，不能删除");
            }
        }
        Fellow fellow = mapper.selectById(fellowId);
        fellow.setDeleted(1);
        mapper.updateById(fellow);
    }
}
