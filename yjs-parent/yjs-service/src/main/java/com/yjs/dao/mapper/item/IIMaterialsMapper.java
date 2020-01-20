package com.yjs.dao.mapper.item;

import com.yjs.bean.materials.Materials;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface IIMaterialsMapper extends Mapper<Materials>, SelectByIdListMapper<Materials,Integer> {
}
