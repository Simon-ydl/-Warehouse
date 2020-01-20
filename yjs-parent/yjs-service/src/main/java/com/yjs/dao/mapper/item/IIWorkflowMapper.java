package com.yjs.dao.mapper.item;


import com.yjs.bean.workflow.Workflow;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface IIWorkflowMapper extends Mapper<Workflow>, SelectByIdListMapper<Workflow,Integer> {
}
