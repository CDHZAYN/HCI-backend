package com.example.hci.service.impl;

import com.auth0.jwt.interfaces.Claim;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.hci.VO.*;
import com.example.hci.config.JwtConfig;
import com.example.hci.controller.dto.RegisterDTO;
import com.example.hci.controller.dto.UserChangePwdDTO;
import com.example.hci.dao.*;
import com.example.hci.dao.dto.*;
import com.example.hci.exception.MyException;
import com.example.hci.service.EmailService;
import com.example.hci.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private UserMapper mapper;

    @Resource
    private JwtConfig jwtConfig;
    @Resource
    private EmailService emailService;

    @Resource
    private UserEventMapper userEventMapper;

    @Resource
    private UserCounselorMapper userCounselorMapper;

    @Resource
    private EventBookMapper eventBookMapper;

    @Resource
    private CounselorBookMapper counselorBookMapper;

    @Override
    public UserVO login(String username, String email, String password) {
        QueryWrapper<User> ew = new QueryWrapper<>();
        if (!StringUtils.isEmpty(username)) {
            ew.eq("username", username);
        }
        if (!StringUtils.isEmpty(email)) {
            ew.eq("email", email);
        }
        if (!StringUtils.isEmpty(password)) {
            ew.eq("password", password);
        }
        User user = mapper.selectOne(ew);
        if (user == null) {
            throw new MyException("US0001", "用户名或密码错误");
        } else {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            userVO.setUserId(user.getId());
            userVO.setToken(jwtConfig.createJWT(user));
            return userVO;
        }
    }

    @Override
    public User auth(String token) {
        Map<String, Claim> claims = jwtConfig.parseJwt(token);
        User user = new User();
        user.setUsername(claims.get("username").as(String.class));
        user.setEmail(claims.get("email").as(String.class));
        user.setPassword(claims.get("password").as(String.class));
        return user;
    }

    @Override
    public void register(RegisterDTO registerDTO) {
        QueryWrapper<User> ew = new QueryWrapper<>();
        ew.eq("email", registerDTO.getEmail());
        User temp = mapper.selectOne(ew);
        if (temp != null) {
            throw new MyException("US0002", "邮箱已存在");
        } else {
            User user = new User();
            BeanUtils.copyProperties(registerDTO, user);
            mapper.insert(user);
        }
    }

    @Override
    public String verify(String email) {
        String verifyCode = UUID.randomUUID().toString()
                .replaceAll("-", "")
                .replaceAll("[a-z|A-Z]", "")
                .substring(0, 6);
        emailService.sendMessageToEmail(verifyCode, email);
        return verifyCode;
    }

    @Override
    public void changeUserPwd(UserChangePwdDTO input) {
        User user = mapper.selectById(input.getUserId());
        if (!user.getPassword().equals(input.getOriginalPwd())) {
            throw new MyException("US0003", "原密码错误");
        } else {
            mapper.updatePwd(input.getUserId(), input.getNewPwd());
        }
    }

    @Override
    public UserBookVO getBookRecord(Integer userId, Integer type, String date) {
        List<UserEvent> userEvents = userEventMapper.getBookRecord(userId, type, date);
        List<UserCounselor> userCounselors = userCounselorMapper.getBookRecord(userId, type, date);
        List<UserCounselorBookVO> resCounselor = new ArrayList<>();
        List<UserEventBookVO> resEvent = new ArrayList<>();
        int temp = 0;
        int tempE = 0;
        int tempC = 0;
        while (temp < 10) {
            if (tempC >= userCounselors.size() && tempE >= userEvents.size()) {
                break;
            } else if (tempC >= userCounselors.size()) {
                UserEventBookVO userEventBookVO = new UserEventBookVO();
                userEventBookVO.setUserEvent(userEvents.get(tempE));
                resEvent.add(userEventBookVO);
                tempE++;
            } else if (tempE >= userEvents.size()) {
                UserCounselorBookVO userCounselorBookVO = new UserCounselorBookVO();
                userCounselorBookVO.setUserCounselor(userCounselors.get(tempC));
                resCounselor.add(userCounselorBookVO);
                tempC++;
            } else {
                if (userEvents.get(tempE).getDate().compareTo(userCounselors.get(tempC).getDate()) > 0) {
                    UserEventBookVO userEventBookVO = new UserEventBookVO();
                    userEventBookVO.setUserEvent(userEvents.get(tempE));
                    resEvent.add(userEventBookVO);
                    tempE++;
                } else {
                    UserCounselorBookVO userCounselorBookVO = new UserCounselorBookVO();
                    userCounselorBookVO.setUserCounselor(userCounselors.get(tempC));
                    resCounselor.add(userCounselorBookVO);
                    tempC++;
                }
            }
            temp++;
        }
        for (UserEventBookVO userEventBookVO : resEvent) {
            userEventBookVO.setEventBook(eventBookMapper.selectById(userEventBookVO.getUserEvent().getEventId()));
            userEventBookVO.setUserFellowId(userEventMapper.getUserEventFellow(userEventBookVO.getUserEvent().getEventId(), userEventBookVO.getUserEvent().getUserId()));
        }
        for (UserCounselorBookVO userCounselorBookVO : resCounselor) {
            userCounselorBookVO.setCounselorBook(counselorBookMapper.selectById(userCounselorBookVO.getUserCounselor().getCounselorBookId()));
            userCounselorBookVO.setUserFellowId(userCounselorMapper.getUserCounselorFellow(userCounselorBookVO.getUserCounselor().getCounselorBookId(), userCounselorBookVO.getUserCounselor().getUserId()));
        }
        UserBookVO userBookVO = new UserBookVO();
        userBookVO.setUserCounselorBookVOS(resCounselor);
        userBookVO.setUserEventBookVOS(resEvent);
        return userBookVO;
    }

    @Override
    public AccountVO getAccount(Integer userId) {
        AccountVO accountVO = new AccountVO();
        User user = mapper.selectById(userId);
        accountVO.setUserId(user.getId());
        BeanUtils.copyProperties(user, accountVO);
        return accountVO;
    }
}
