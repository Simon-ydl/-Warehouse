package com.yjs.service.inspection.Service;

import com.yjs.bean.inspection.Inspection;

public interface InspectionService {
    void save(Inspection inspection);

    Inspection findAllByBusinessCode(String businessCode);

    void update(Inspection inspection);
}
