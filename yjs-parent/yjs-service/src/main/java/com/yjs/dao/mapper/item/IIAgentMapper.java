package com.yjs.dao.mapper.item;

import com.yjs.bean.agent.Agent;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface IIAgentMapper extends Mapper<Agent>, SelectByIdListMapper<Agent,Integer> {


}
