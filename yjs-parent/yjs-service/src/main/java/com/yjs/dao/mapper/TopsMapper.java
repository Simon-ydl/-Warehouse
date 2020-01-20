package com.yjs.dao.mapper;

import com.yjs.bean.tops.Tops;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Mapper
public interface TopsMapper {


	Tops findAllByBusinessCode(String businessCode);

	void save(Tops tops);

	void update(Tops tops);
}
