package com.example.hci.service;

import com.example.hci.VO.FellowBriefVO;
import com.example.hci.dao.dto.Fellow;

import java.util.List;

public interface IFellowService extends BaseService<Fellow> {

    List<FellowBriefVO> getFellowList(Integer userId);

    Fellow getFellowDetail(Integer userId, Integer fellowId);

    void deleteFellow(Integer userId, Integer fellowId);

}
