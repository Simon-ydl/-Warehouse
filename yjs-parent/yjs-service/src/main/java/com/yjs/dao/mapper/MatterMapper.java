package com.yjs.dao.mapper;

import com.yjs.bean.matter.Matter;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Repository
@Mapper
public interface MatterMapper {


	String findNameByMatterId(int matterId);


    Matter findAllBymatterCode(String matterCode);

    Matter findAllById(int matterId);
}
