package com.yjs.dao.mapper.item;

import com.yjs.bean.datum.Datum;
import com.yjs.bean.dto.OneDate;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface IIDatumMapper extends Mapper<Datum>, SelectByIdListMapper<Datum, Integer> {

    @Select("    SELECT rd.`business_code`,rd.`content`,rd.`items_id`,rt.`department_id`," +
            "    ra.`ap_name`,ra.`ap_number`,rag.`id`,rag.`aName`,rag.`aNumber`,rag.`aNumberType`," +
            "    rag.`aMobile`,rag.`aEmail`,rm.`matter_code`" +
            "    FROM ro_datum rd" +
            "    LEFT JOIN ro_items rt ON rd.`items_id`=rt.`id`" +
            "    LEFT JOIN  ro_applicant ra ON rd.`business_code`=ra.`business_code`" +
            "    LEFT JOIN ro_agent rag ON rd.`business_code`=rag.`business_code`" +
            "    LEFT JOIN ro_business rb ON rd.`business_code`=rb.`business_code`" +
            "    LEFT JOIN ro_matter rm ON rb.`matter_id`=rm.`id`"+
            "    WHERE rd.`business_code`=#{business_code} AND rt.`department_id`= #{department_id} AND rd.`state` = '1'")
    @Results(id = "oneDateResult",value = {
            @Result(column="business_code",property="businessCode"),
            @Result(column="content",property="content"),
            @Result(column="items_id" ,property="itemsId"),
            @Result(column="department_id" ,property="departmentId"),
            @Result(column="ap_name" ,property="apName"),
            @Result(column="ap_number" ,property="apNumber"),
            @Result(column="id" ,property="id"),
            @Result(column="aName" ,property="aName"),
            @Result(column="aNumber", property="aNumber"),
            @Result(column="aNumberType" ,property="aNumberType"),
            @Result(column="aMobile" ,property="aMobile"),
            @Result(column="aEmail" ,property="aEmail"),
            @Result(column="matter_code" ,property="matterId")
    })
    OneDate findItemInfoByDepartmentIdAndBusinessCode(@Param("business_code") String businessCode,@Param("department_id") Integer departmentId);


     @Select("  SELECT rd.`items_id` FROM ro_datum rd" +
            "  LEFT JOIN ro_items rt ON rd.`items_id`=rt.`id`" +
            "  WHERE rd.`business_code`=#{business_code} AND rt.`department_id`= #{department_id}")
     @Results(id = "getItemId",value = {
           @Result(column="items_id",property="itemsId")
     })
    Integer findItemByDepartmentAndBusinessCnode(@Param("business_code") String businessCode,@Param("department_id") Integer department_id );

     @Select("SELECT rt.`department_id`,rt.`id` AS item_id,rd.`content`,rd.`business_code`,rap.`ap_name`,rap.`ap_number`," +
             "             rag.`id`,rag.`aEmail`,rag.`aMobile`,rag.`aName`,rag.`aNumber`,rag.`aNumberType`,rm.`matter_code`" +
             "             FROM ro_items rt " +
             "             LEFT JOIN ro_datum rd ON rt.`id`=rd.`items_id` " +
             "             LEFT JOIN ro_applicant rap ON rd.`business_code`=rap.`business_code` " +
             "             LEFT JOIN ro_agent rag ON rd.`business_code`=rag.`business_code`" +
             "             LEFT JOIN ro_business rb ON rd.`business_code`=rb.`business_code`" +
             "             LEFT JOIN ro_matter rm ON rb.`matter_id`=rm.`id`" +
             "             WHERE rt.`department_id`=#{department_id} AND rd.`state`='1'")
    @Results(id = "getUntreatedItemsByDeptmentId",value = {
            @Result(column="department_id" ,property="departmentId"),
            @Result(column="item_id" ,property="itemsId"),
            @Result(column="content",property="content"),
            @Result(column="business_code",property="businessCode"),
            @Result(column="ap_name" ,property="apName"),
            @Result(column="ap_number" ,property="apNumber"),
            @Result(column="id" ,property="id"),
            @Result(column="aName" ,property="aName"),
            @Result(column="aNumber", property="aNumber"),
            @Result(column="aNumberType" ,property="aNumberType"),
            @Result(column="aMobile" ,property="aMobile"),
            @Result(column="aEmail" ,property="aEmail"),
            @Result(column="matter_code" ,property="matterId")
    })
     List<OneDate> findUntreatedItemsByDeptmentId(@Param("department_id") Integer departmentId);

}
