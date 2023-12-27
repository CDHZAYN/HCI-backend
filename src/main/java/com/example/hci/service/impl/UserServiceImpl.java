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

    @Resource
    private CounselorMapper counselorMapper;

    @Resource
    private FellowMapper fellowMapper;

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
    public BookRecordVO getBookRecord(Integer userId, Integer type, String date, String bookType) {
        List<UserBookVO> res = new ArrayList<>();
        if(StringUtils.isEmpty(bookType)){
            List<UserEvent> userEvents = userEventMapper.getBookRecord(userId, type, date);
            List<UserCounselor> userCounselors = userCounselorMapper.getBookRecord(userId, type, date, bookType);
            List<UserBook> books  = new ArrayList<>(userEvents);
            books.addAll(userCounselors);
            Collections.sort(books, (o1, o2) -> o2.getDate().compareTo(o1.getDate()));
            int target = Math.min(10, books.size());
            for(int i= 0; i< target; i++){
                UserBookVO userBookVO = new UserBookVO();
                userBookVO.setUserBook(books.get(i));
                if(books.get(i) instanceof UserEvent){
                    EventBook eventBook = eventBookMapper.selectById(((UserEvent) books.get(i)).getEventId());
                    Counselor c = counselorMapper.selectById(eventBook.getCounselorId());
                    eventBook.setLocation(c.getLocation());
                    eventBook.setProfile(c.getProfile());
                    userBookVO.setBook(eventBook);
                    List<Integer> fellowId = userEventMapper.getUserEventFellow(((UserEvent) books.get(i)).getEventId(),((UserEvent) books.get(i)).getUserId());
                    List<FellowBriefVO> fellowBriefVOS = new ArrayList<>();
                    for(int j= 0; j<fellowId.size(); j++) {
                        FellowBriefVO fellowBriefVO = new FellowBriefVO();
                        fellowBriefVO.setFellowId(fellowId.get(j));
                        Fellow f = fellowMapper.selectById(fellowId.get(j));
                        fellowBriefVO.setNickname(f.getNickname());
                        fellowBriefVO.setNote(f.getNote());
                        fellowBriefVOS.add(fellowBriefVO);
                    }
                    userBookVO.setUserFellow(fellowBriefVOS);
                }else{
                    CounselorBook counselorBook = counselorBookMapper.selectById(((UserCounselor) books.get(i)).getCounselorBookId());
                    Counselor c = counselorMapper.selectById(counselorBook.getCounselorId());
                    counselorBook.setLocation(c.getLocation());
                    counselorBook.setProfile(c.getProfile());
                    userBookVO.setBook(counselorBook);
                    List<Integer> fellowId = userCounselorMapper.getUserCounselorFellow(((UserCounselor) books.get(i)).getCounselorBookId(),((UserCounselor) books.get(i)).getUserId());
                    List<FellowBriefVO> fellowBriefVOS = new ArrayList<>();
                    for(int j= 0; j<fellowId.size(); j++) {
                        FellowBriefVO fellowBriefVO = new FellowBriefVO();
                        fellowBriefVO.setFellowId(fellowId.get(j));
                        Fellow f = fellowMapper.selectById(fellowId.get(j));
                        fellowBriefVO.setNickname(f.getNickname());
                        fellowBriefVO.setNote(f.getNote());
                        fellowBriefVOS.add(fellowBriefVO);
                    }
                    userBookVO.setUserFellow(fellowBriefVOS);
                }
                res.add(userBookVO);
            }
        }else if(bookType.equals("活动预约")){
            List<UserEvent> userEvents = userEventMapper.getBookRecord(userId, type, date);
            for(UserEvent userEvent: userEvents){
                UserBookVO userBookVO = new UserBookVO();
                userBookVO.setUserBook(userEvent);
                EventBook eventBook = eventBookMapper.selectById(userEvent.getEventId());
                Counselor c = counselorMapper.selectById(eventBook.getCounselorId());
                eventBook.setLocation(c.getLocation());
                eventBook.setProfile(c.getProfile());
                userBookVO.setBook(eventBook);
                List<Integer> fellowId = userEventMapper.getUserEventFellow(userEvent.getEventId(),userEvent.getUserId());
                List<FellowBriefVO> fellowBriefVOS = new ArrayList<>();
                for(int j= 0; j<fellowId.size(); j++) {
                    FellowBriefVO fellowBriefVO = new FellowBriefVO();
                    fellowBriefVO.setFellowId(fellowId.get(j));
                    Fellow f = fellowMapper.selectById(fellowId.get(j));
                    fellowBriefVO.setNickname(f.getNickname());
                    fellowBriefVO.setNote(f.getNote());
                    fellowBriefVOS.add(fellowBriefVO);
                }
                userBookVO.setUserFellow(fellowBriefVOS);
                res.add(userBookVO);
            }
        }else{
            List<UserCounselor> userCounselors = userCounselorMapper.getBookRecord(userId, type, date, bookType);
            for(UserCounselor userCounselor: userCounselors) {
                UserBookVO userBookVO = new UserBookVO();
                userBookVO.setUserBook(userCounselor);
                CounselorBook counselorBook = counselorBookMapper.selectById(userCounselor.getCounselorBookId());
                Counselor c = counselorMapper.selectById(counselorBook.getCounselorId());
                counselorBook.setLocation(c.getLocation());
                counselorBook.setProfile(c.getProfile());
                userBookVO.setBook(counselorBook);
                List<Integer> fellowId = userCounselorMapper.getUserCounselorFellow(userCounselor.getCounselorBookId(), userCounselor.getUserId());
                List<FellowBriefVO> fellowBriefVOS = new ArrayList<>();
                for(int j= 0; j<fellowId.size(); j++) {
                    FellowBriefVO fellowBriefVO = new FellowBriefVO();
                    fellowBriefVO.setFellowId(fellowId.get(j));
                    Fellow f = fellowMapper.selectById(fellowId.get(j));
                    fellowBriefVO.setNickname(f.getNickname());
                    fellowBriefVO.setNote(f.getNote());
                    fellowBriefVOS.add(fellowBriefVO);
                }
                userBookVO.setUserFellow(fellowBriefVOS);
                res.add(userBookVO);
            }
        }
        BookRecordVO bookRecordVO = new BookRecordVO();
        bookRecordVO.setBookRecord(res);
        return bookRecordVO;
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
