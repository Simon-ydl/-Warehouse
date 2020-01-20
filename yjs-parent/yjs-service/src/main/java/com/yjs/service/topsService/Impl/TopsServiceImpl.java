package com.yjs.service.topsService.Impl;

import com.yjs.bean.tops.Tops;
import com.yjs.dao.mapper.TopsMapper;
import com.yjs.service.topsService.TopsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TopsServiceImpl implements TopsService {

    @Autowired
    TopsMapper topsMapper;

    @Override
    public Tops findAllByBusinessCode(String businessCode) {
        Tops tops = topsMapper.findAllByBusinessCode(businessCode);
        return tops;
    }

    @Override
    public void save(Tops tops) {
        topsMapper.save(tops);
    }

    @Override
    public void update(Tops tops) {
        topsMapper.update(tops);
    }
}
