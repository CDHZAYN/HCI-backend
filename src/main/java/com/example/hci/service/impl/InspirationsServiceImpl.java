package com.example.hci.service.impl;

import com.example.hci.dao.InspirationsMapper;
import com.example.hci.dao.dto.Inspirations;
import com.example.hci.service.IInspirationsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class InspirationsServiceImpl extends BaseServiceImpl<InspirationsMapper, Inspirations> implements IInspirationsService {

    @Resource
    private InspirationsMapper mapper;

    @Override
    public List<Inspirations> selectInspirationsRand() {
        return mapper.selectInspirationsRand();
    }
}
