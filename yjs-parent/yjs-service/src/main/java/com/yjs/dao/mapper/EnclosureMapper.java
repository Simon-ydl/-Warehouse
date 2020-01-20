package com.yjs.dao.mapper;

import com.yjs.bean.enclosure.Enclosure;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface EnclosureMapper {
    Enclosure findById(int enclosureId);
}
