package com.example.hci.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.hci.controller.dto.CounselorBookCancelDTO;
import com.example.hci.controller.dto.CounselorBookDTO;
import com.example.hci.controller.dto.CounselorBookListDTO;
import com.example.hci.controller.dto.CounselorModifyDTO;
import com.example.hci.dao.CounselorBookMapper;
import com.example.hci.dao.CounselorFellowMapper;
import com.example.hci.dao.UserCounselorMapper;
import com.example.hci.dao.dto.CounselorBook;
import com.example.hci.dao.dto.CounselorFellow;
import com.example.hci.dao.dto.UserCounselor;
import com.example.hci.service.ICounselorBookService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CounselorBookServiceImpl extends BaseServiceImpl<CounselorBookMapper, CounselorBook> implements ICounselorBookService {

    @Resource
    private CounselorBookMapper mapper;
    @Resource
    private CounselorFellowMapper counselorFellowMapper;

    @Resource
    private UserCounselorMapper userCounselorMapper;
    @Resource
    private CounselorBookMapper counselorBookMapper;

    @Override
    public List<CounselorBook> getCounselorBookList(CounselorBookListDTO input) {
        return mapper.getCounselorBookList(input.getCounselorId(), input.getDate(), input.getForm());
    }

    @Override
    public void book(CounselorBookDTO input) {
        List<Integer> fellowIds = input.getUserFellowId();
        for(Integer fellowId : fellowIds) {
            CounselorFellow counselorFellow = new CounselorFellow();
            counselorFellow.setUserId(input.getUserId());
            counselorFellow.setCounselorBookId(input.getCounselorBookId());
            counselorFellow.setFellowId(fellowId);
            counselorFellowMapper.insert(counselorFellow);
        }
        CounselorBook counselorBook = mapper.selectById(input.getCounselorBookId());
        counselorBook.setIsAvailable(1);
        mapper.updateById(counselorBook);
        UserCounselor userCounselor = new UserCounselor();
        BeanUtils.copyProperties(input, userCounselor);
        userCounselor.setType(0);
        userCounselorMapper.insert(userCounselor);
    }

    @Override
    public void cancel(CounselorBookCancelDTO input) {
        UserCounselor userCounselor = userCounselorMapper.selectById(input.getUserCounselorId());
        userCounselor.setCancel(input.getCancel());
        userCounselor.setType(1);
        userCounselorMapper.updateById(userCounselor);
        CounselorBook counselorBook = counselorBookMapper.selectById(input.getCounselorBookId());
        counselorBook.setIsAvailable(0);
        counselorBookMapper.updateById(counselorBook);
        QueryWrapper<CounselorFellow> ew = new QueryWrapper<>();
        ew.eq("userId", input.getUserId());
        ew.eq("counselorBookId", input.getCounselorBookId());
        counselorFellowMapper.delete(ew);

    }

    @Override
    public List<String> date(String type) {
        List<CounselorBook> temp = mapper.getCounselorBookDate(type);
        Set<String> set = new HashSet<>();
        for(CounselorBook counselorBook : temp){
            set.add(counselorBook.getStartTime().substring(0,10));
        }
        return new ArrayList<>(set);
    }

    @Override
    public void deleteFellow(Integer userId, Integer counselorBookId, Integer fellowId) {
        QueryWrapper<CounselorFellow> ew = new QueryWrapper<>();
        ew.eq("userId", userId);
        ew.eq("counselorBookId", counselorBookId);
        ew.eq("fellowId", fellowId);
        counselorFellowMapper.delete(ew);
    }

    @Override
    public void addFellow(Integer userId, Integer counselorBookId, Integer fellowId) {
        CounselorFellow counselorFellow = new CounselorFellow();
        counselorFellow.setFellowId(fellowId);
        counselorFellow.setCounselorBookId(counselorBookId);
        counselorFellow.setUserId(userId);
        counselorFellowMapper.insert(counselorFellow);
    }

    @Override
    public void modify(CounselorModifyDTO input) {
        UserCounselor userCounselor = new UserCounselor();
        BeanUtils.copyProperties(input, userCounselor);
        userCounselor.setId(input.getUserCounselorId());
        userCounselorMapper.updateById(userCounselor);
    }
}
