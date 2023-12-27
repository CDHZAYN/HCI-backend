package com.example.hci.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.hci.controller.dto.*;
import com.example.hci.dao.*;
import com.example.hci.dao.dto.CounselorBook;
import com.example.hci.dao.dto.CounselorFellow;
import com.example.hci.dao.dto.Fellow;
import com.example.hci.dao.dto.UserCounselor;
import com.example.hci.exception.MyException;
import com.example.hci.service.EmailService;
import com.example.hci.service.ICounselorBookService;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.MessageFormat;
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

    @Resource
    private FellowMapper fellowMapper;

    @Resource
    private CounselorMapper counselorMapper;

    @Resource
    private EmailService emailService;

    @Override
    public List<CounselorBook> getCounselorBookList(CounselorBookListDTO input) {
        return mapper.getCounselorBookList(input.getCounselorId(), input.getDate(), input.getForm());
    }

    @Override
    public void book(CounselorBookDTO input) {
        List<Integer> fellowIds = input.getUserFellowId();
        for (Integer fellowId : fellowIds) {
            Fellow fellow = fellowMapper.selectById(fellowId);
            if (fellow.getDeleted() == 1) {
                String msg = MessageFormat.format("{0} 已被删除，无法预约", fellow.getNickname());
                throw new MyException("BS0001", msg);
            }
        }
        for (Integer fellowId : fellowIds) {
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
        String subject = "泽恩心理咨询预定确认";
        String content = "亲爱的{0}：\n" +
                "\n" +
                "感谢您选择泽恩心理咨询服务！我们已收到您的预定请求，并高兴地通知您，您的预定已确认。\n" +
                "\n" +
                "预定详情：\n" +
                "- 预定时间： {1}\n" +
                "- 预定地点： {2}\n" +
                "- 咨询师名称： {3}\n" +
                "{4}" +
                "\n" +
                "请您提前15分钟到达，我们期待为您提供优质的心理咨询服务。\n" +
                "\n" +
                "如有任何问题或需要进一步的帮助，请随时联系我们。\n" +
                "\n" +
                "谢谢！\n" +
                "泽恩心理咨询团队";
        String date = counselorBook.getStartTime();
        String location = counselorMapper.selectById(counselorBook.getCounselorId()).getLocation();
        String counselorBookName = counselorBook.getName();
        for (Integer fellowId : fellowIds) {
            Fellow fellow = fellowMapper.selectById(fellowId);
            String nickname = fellow.getNickname();
            String email = fellow.getEmail();
            if (userCounselor.getIsOnline() == 0) {
                emailService.sendBookMessage(email, subject, MessageFormat.format(content, nickname, date, location, counselorBookName, "\n"));
            } else {
                String meetingId = "- 腾讯会议号： " + emailService.generateRandomString() + "\n";
                emailService.sendBookMessage(email, subject, MessageFormat.format(content, nickname, date, location, counselorBookName, meetingId));
            }
        }
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
        List<CounselorFellow> counselorFellows = counselorFellowMapper.selectList(ew);
        String subject = "泽恩心理咨询预定取消确认";
        String content = "亲爱的{0}：\n" +
                "\n" +
                "我们收到了您的预定取消请求。我们很抱歉无法在这次的预定中见到您，但我们完全理解生活中的不可预测性。\n" +
                "\n" +
                "取消详情：\n" +
                "- 取消时间： {1}\n" +
                "- 取消地点： {2}\n" +
                "- 咨询师名称： {3}\n" +
                "\n" +
                "如果未来您有需要，我们将随时为您提供心理咨询服务。如果有任何其他问题或需要支持，请随时联系我们。\n" +
                "\n" +
                "谢谢您的理解和支持。\n" +
                "\n" +
                "祝您一天愉快！\n" +
                "泽恩心理咨询团队";
        String date = counselorBook.getStartTime();
        String location = counselorMapper.selectById(counselorBook.getCounselorId()).getLocation();
        String counselorBookName = counselorBook.getName();
        for (CounselorFellow counselorFellow : counselorFellows) {
            Fellow fellow = fellowMapper.selectById(counselorFellow.getFellowId());
            String nickname = fellow.getNickname();
            String email = fellow.getEmail();
            emailService.sendBookMessage(email, subject, MessageFormat.format(content, nickname, date, location, counselorBookName));
        }
    }

    @Override
    public List<String> date(DateDTO input) {
        List<CounselorBook> temp = mapper.getCounselorBookDate(input.getCounselorId(), input.getType());
        Set<String> set = new HashSet<>();
        for (CounselorBook counselorBook : temp) {
            set.add(counselorBook.getStartTime().substring(0, 10));
        }
        return new ArrayList<>(set);
    }

    @Override
    public void deleteFellow(FellowModifyDTO input) {
        QueryWrapper<CounselorFellow> ew = new QueryWrapper<>();
        ew.eq("userId", input.getUserId());
        ew.eq("counselorBookId", input.getBookId());
        ew.in("fellowId", input.getFellowId());
        counselorFellowMapper.delete(ew);
    }

    @Override
    public void addFellow(FellowModifyDTO input) {
        for (Integer fellowId : input.getFellowId()) {
            CounselorFellow counselorFellow = new CounselorFellow();
            counselorFellow.setFellowId(fellowId);
            counselorFellow.setCounselorBookId(input.getBookId());
            counselorFellow.setUserId(input.getUserId());
            counselorFellowMapper.insert(counselorFellow);
        }
    }

    @Override
    public void modify(CounselorModifyDTO input) {
        UserCounselor userCounselor = new UserCounselor();
        BeanUtils.copyProperties(input, userCounselor);
        userCounselor.setId(input.getUserCounselorId());
        userCounselorMapper.updateById(userCounselor);
    }

    @Override
    public void finishUserCounselor() {
        List<Integer> bookIds = mapper.getCounselorBookInOneHour();
        if (!bookIds.isEmpty()) {
            QueryWrapper<UserCounselor> ew = new QueryWrapper<>();
            ew.in("counselorBookId", bookIds);
            ew.eq("type", 0);
            List<UserCounselor> userCounselors = userCounselorMapper.selectList(ew);
            for (UserCounselor userCounselor : userCounselors) {
                userCounselor.setType(2);
                userCounselorMapper.updateById(userCounselor);
            }
        }
    }
}

