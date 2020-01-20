package com.yjs.dao.mapper.item;

import com.yjs.bean.matter.Matter;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface IIMatterMapper extends Mapper<Matter>, SelectByIdListMapper<Matter,Integer> {
}
