package com.yjs.dao.mapper;

import com.yjs.bean.Situation.Situation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface SituationMapper {

	void save(Situation situation);


    Situation findAllByBusinessCode(String businessCode);

    void update(Situation situation);
}
