package com.example.hci.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.hci.VO.FellowBriefVO;
import com.example.hci.dao.FellowMapper;
import com.example.hci.dao.dto.Fellow;
import com.example.hci.service.IFellowService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class FellowServiceImpl extends BaseServiceImpl<FellowMapper, Fellow> implements IFellowService {

    @Resource
    private FellowMapper mapper;

    @Override
    public List<FellowBriefVO> getFellowList(Integer userId) {
        QueryWrapper<Fellow> ew = new QueryWrapper<>();
        ew.eq("userId", userId);
        List<Fellow> ret = mapper.selectList(ew);
        List<FellowBriefVO> result = new ArrayList<>();
        for(Fellow fellow : ret) {
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
}
