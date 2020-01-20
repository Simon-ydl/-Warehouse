package com.yjs.dao.mapper.item;

import com.yjs.bean.oracle.OraApprover;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface OraApproverMapper extends Mapper<OraApprover>, SelectByIdListMapper<OraApprover,Integer> {

    @Insert("insert into ora_approver (department_id,item_id,business_code,enclosure_content) VALUES(#{departmentId},#{itemId},#{businessCode},#{content})")
    int insertData(@Param("departmentId")Integer departmentId,@Param("itemId")Integer itemId,@Param("businessCode") String businessCode,@Param("content") String content);

    @Select("select * from ora_approver where status = '1'")
    List<OraApprover> findAllByStatus();

    @Update({"<script>" +
            "<foreach collection=\"list\" item=\"item\" index=\"index\" open=\"begin\" close=\";end;\" separator=\";\">" +
            " update ora_approver " +
            " set status = #{item.status,jdbcType=VARCHAR} " +
            " WHERE " +
            " id = #{item.id,jdbcType=NUMERIC} " +
            "</foreach>" +
            "</script>"})
    @Options(useGeneratedKeys = false)
    void updateOraApprover(@Param("list") List<OraApprover> oraApprovers);

    @Insert("INSERT INTO ora_approver (approver,approveOpinion,department_id,item_id,create_time,business_code,lack_materials,approve_state,enclosure_name,enclosure_content,status ) " +
            "VALUES( #{approver.approver,jdbcType=VARCHAR},#{approver.approveOpinion,jdbcType=VARCHAR},#{approver.departmentId,jdbcType=NUMERIC}," +
            "#{approver.itemId,jdbcType=NUMERIC},#{approver.createTime,jdbcType=DATE},#{approver.businessCode,jdbcType=VARCHAR}," +
            "#{approver.lackMaterials,jdbcType=VARCHAR},#{approver.approveState,jdbcType=VARCHAR}," +
            "#{approver.enclosureName,jdbcType=VARCHAR},#{approver.enclosureContent,jdbcType=VARCHAR},#{approver.status,jdbcType=VARCHAR})")
    int customInsert(@Param("approver") OraApprover approver);
}
