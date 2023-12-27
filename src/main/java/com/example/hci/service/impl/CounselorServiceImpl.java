package com.example.hci.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.hci.VO.CounselorBriefVO;
import com.example.hci.controller.dto.CounselorListDTO;
import com.example.hci.dao.CounselorMapper;
import com.example.hci.dao.dto.Counselor;
import com.example.hci.service.ICounselorService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CounselorServiceImpl extends BaseServiceImpl<CounselorMapper, Counselor> implements ICounselorService {

    @Resource
    private CounselorMapper mapper;


    @Override
    public List<CounselorBriefVO> selectCounselorList(CounselorListDTO input) {
        QueryWrapper<Counselor> ew = new QueryWrapper<>();
        if (!StringUtils.isEmpty(input.getCounselorName())) {
            ew.like("name", input.getCounselorName());
        }
        if (input.getPriceLowerBound() != null && input.getPriceUpperBound() != null) {
            ew.between("basic", input.getPriceLowerBound(), input.getPriceUpperBound());
        }
        if (!StringUtils.isEmpty(input.getLocation())) {
            ew.eq("location", input.getLocation());
        }
        if (input.getSex() != null) {
            ew.eq("sex", input.getSex());
        }
        if (input.getPosition() != null) {
            ew.eq("position", input.getPosition());
        }
        List<Counselor> ret = mapper.selectCounselorList(input.getFieldLabel(), input.getForm(), input.getSkip()*8, ew);
        List<CounselorBriefVO> result = new ArrayList<>();
        for (Counselor counselor : ret) {
            CounselorBriefVO counselorBriefVO = new CounselorBriefVO();
            BeanUtils.copyProperties(counselor, counselorBriefVO);
            counselorBriefVO.setFieldLabel(mapper.getCounselorField(counselor.getId()));
            result.add(counselorBriefVO);
        }
        return result;
    }

    @Override
    public Counselor selectCounselor(Integer counselorId) {
        Counselor counselor = mapper.selectById(counselorId);
        counselor.setFieldLabel(mapper.getCounselorField(counselorId));
        counselor.setForm(mapper.getCounselorForm(counselorId));
        counselor.setAward(mapper.getCounselorAwards(counselorId));
        return counselor;
    }

    @Override
    public List<CounselorBriefVO> selectHomeCounselorList() {
        QueryWrapper<Counselor> ew = new QueryWrapper<>();
        ew.orderByDesc("position").last("limit 3");
        List<Counselor> ret = mapper.selectList(ew);
        List<CounselorBriefVO> result = new ArrayList<>();
        for (Counselor counselor : ret) {
            CounselorBriefVO counselorBriefVO = new CounselorBriefVO();
            BeanUtils.copyProperties(counselor, counselorBriefVO);
            result.add(counselorBriefVO);
        }
        return result;
    }
}
