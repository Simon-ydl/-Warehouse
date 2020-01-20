package com.yjs.dao.mapper;

import com.yjs.bean.inspection.Inspection;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface InspectionMapper {
    void save(Inspection inspection);

    Inspection findAllByBusinessCode(String businessCode);

    void update(Inspection inspection);
}
