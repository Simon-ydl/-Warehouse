package com.yjs.dao.mapper.item;

import com.yjs.bean.business.Business;
import com.yjs.bean.dto.DtoWorkFlow;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface IIBusinessMapper extends Mapper<Business>, SelectByIdListMapper<Business,Integer> {

    @Select("SELECT rw.`act_name`,rw.`workflow_order`,rw.`department_id`,rw.`approve_step`,rde.`department_name` " +
            "  FROM ro_business rb " +
            "  LEFT JOIN ro_workflow rw ON rb.matter_id = rw.`matter_id`" +
            "  LEFT JOIN ro_department rde ON rw.department_id = rde.id" +
            "  WHERE rb.`business_code`= #{business_code}")
    @Results(id="getWorkFlowOrderByBusinessCode",value = {
            @Result(column = "department_name",property = "departmentName"),
            @Result(column = "act_name",property = "actName"),
            @Result(column = "workflow_order",property = "order"),
            @Result(column = "department_id",property = "departmentId"),
            @Result(column = "approve_step",property = "approveStep")
    })
    List<DtoWorkFlow> getWorkFlowOrderByBusinessCode(@Param("business_code") String businessCode);
}
