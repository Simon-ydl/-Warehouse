package com.yjs.dao.mapper;

import com.yjs.bean.activiti.Task;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface ActivitisMapper extends Mapper<Task>, SelectByIdListMapper<Task, Integer> {

    @Select("SELECT\n" +
            "\tex.BUSINESS_KEY_\n" +
            "FROM\n" +
            "\tact_ru_task ta,\n" +
            "\tact_ru_execution ex\n" +
            "WHERE\n" +
            "\tta.PROC_INST_ID_ = ex.PROC_INST_ID_\n" +
            "AND ta.ASSIGNEE_ = #{ASSIGNEE_}\n" +
            "AND ta.SUSPENSION_STATE_ = 1\n" +
            "AND ex.BUSINESS_KEY_ != ''")
    @Results(id = "activitiMap", value = {
            @Result(column = "BUSINESS_KEY_", property = "")
    })
    List<String> findAssigneeBySuspension(@Param("ASSIGNEE_") String assignee);
}
