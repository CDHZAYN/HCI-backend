package com.example.hci.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.hci.VO.CounselorBriefVO;
import com.example.hci.controller.dto.CounselorListDTO;
import com.example.hci.dao.dto.Counselor;

import java.util.List;

public interface ICounselorService extends BaseService<Counselor> {

    List<CounselorBriefVO> selectCounselorList(CounselorListDTO input);

    Counselor selectCounselor(Integer counselorId);

    List<CounselorBriefVO> selectHomeCounselorList();
}
