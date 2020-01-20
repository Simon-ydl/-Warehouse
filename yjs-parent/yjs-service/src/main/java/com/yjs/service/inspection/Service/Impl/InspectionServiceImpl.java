package com.yjs.service.inspection.Service.Impl;

import com.yjs.bean.inspection.Inspection;
import com.yjs.dao.mapper.InspectionMapper;
import com.yjs.service.inspection.Service.InspectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InspectionServiceImpl implements InspectionService {

    @Autowired
    InspectionMapper inspectionMapper;

    @Override
    public void save(Inspection inspection) {
        inspectionMapper.save(inspection);
    }

    @Override
    public Inspection findAllByBusinessCode(String businessCode) {
        Inspection inspection = inspectionMapper.findAllByBusinessCode(businessCode);
        return inspection;
    }

    @Override
    public void update(Inspection inspection) {
        inspectionMapper.update(inspection);
    }
}
