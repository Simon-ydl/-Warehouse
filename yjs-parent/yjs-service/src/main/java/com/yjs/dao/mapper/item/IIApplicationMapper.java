package com.yjs.dao.mapper.item;

import com.yjs.bean.application.Applicat;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface IIApplicationMapper extends Mapper<Applicat>, SelectByIdListMapper<Applicat,Integer> {
}
