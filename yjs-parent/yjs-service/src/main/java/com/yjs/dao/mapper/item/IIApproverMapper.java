package com.yjs.dao.mapper.item;

import com.yjs.bean.approver.Approver;
import com.yjs.bean.dto.ApproveData;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import javax.persistence.Column;
import java.util.List;

@Repository
public interface IIApproverMapper extends Mapper<Approver>, SelectByIdListMapper<Approver,Integer> {
    @Select("SELECT rap.`approver`,rap.`approveOpinion`,rap.`create_time`,rde.`id`,rde.`department_name`,rd.`state`,rap.`enclosure_id` " +
            "  FROM ro_approver rap " +
            "  LEFT JOIN ro_department rde ON rap.`department_id`=rde.`id`" +
            "  LEFT JOIN ro_datum rd ON rap.`item_id`=rd.`items_id` AND rap.`business_code`=rd.`business_code`" +
            "  WHERE rap.`item_id`=#{item_id} AND rap.`business_code`=#{business_code}")
    @Results(id = "getItemStatusByBusinessCodeAndItemId",value = {
           @Result(column="approver",property="approver"),
           @Result(column="approveOpinion",property="approveOpinion"),
           @Result(column="create_time",property="createTime"),
           @Result(column="id",property="departmentId"),
           @Result(column="department_name",property="departmentName"),
           @Result(column="state",property="state"),
           @Result(column="enclosure_id",property="enclosureId")
    })
    ApproveData findByItemStatusByBusinessCodeAndItemId(@Param("item_id") Integer itemId,@Param("business_code") String businessCode);

    @Select("SELECT ra.`approver`,ra.`approveOpinion`,ra.`create_time`,ra.`enclosure_id`,ra.`department_id`," +
            "  rde.`department_name`,rd.`state`" +
            "  FROM ro_approver ra" +
            "  LEFT JOIN ro_datum rd ON ra.`business_code`=rd.`business_code` AND ra.`item_id`=rd.`items_id`" +
            "  LEFT JOIN ro_department rde ON ra.`department_id`=rde.`id`" +
            "  WHERE ra.`business_code`=#{business_code}")
    @Results(id = "getApproveHistoryByBusinessCode",value = {
            @Result(column="approver",property="approver"),
            @Result(column="approveOpinion",property="approveOpinion"),
            @Result(column="create_time",property="createTime"),
            @Result(column="enclosure_id",property="enclosureId"),
            @Result(column="department_id",property="departmentId"),
            @Result(column="department_name",property="departmentName"),
            @Result(column="state",property="state")
    })
    List<ApproveData> getApproveHistoryByBusinessCode(@Param("business_code") String businessCode);

}
