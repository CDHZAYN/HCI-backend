package com.example.hci.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.hci.dao.BaseMapper;

public class BaseServiceImpl<M extends BaseMapper<T>,T> extends ServiceImpl<BaseMapper<T>,T> {
}
