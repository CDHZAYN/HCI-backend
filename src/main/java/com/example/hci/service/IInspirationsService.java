package com.example.hci.service;

import com.example.hci.dao.dto.Inspirations;

import java.util.List;

public interface IInspirationsService extends BaseService<Inspirations> {

    List<Inspirations> selectInspirationsRand();
}
