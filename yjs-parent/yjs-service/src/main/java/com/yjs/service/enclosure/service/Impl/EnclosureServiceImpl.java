package com.yjs.service.enclosure.service.Impl;

import com.yjs.bean.enclosure.Enclosure;
import com.yjs.dao.mapper.EnclosureMapper;
import com.yjs.service.enclosure.service.EnclosureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnclosureServiceImpl implements EnclosureService {

    @Autowired
    EnclosureMapper enclosureMapper;

    @Override
    public Enclosure findById(int enclosureId) {
        Enclosure enclosure = enclosureMapper.findById(enclosureId);
        return enclosure;
    }
}
