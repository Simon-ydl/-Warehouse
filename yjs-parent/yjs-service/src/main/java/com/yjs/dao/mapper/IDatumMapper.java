package com.yjs.dao.mapper;

import com.yjs.bean.datum.Datum;
import com.yjs.bean.datum.Datums;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

@Repository
public interface IDatumMapper extends Mapper<Datum>{
    Map<String,Object> findDatumInformation(@Param("business_code") String business_code,@Param("itemsId") Integer itemsId);//待办任务和已办任务页面点击办理时使用的方法

    List findFileBybusinessCode(String business_code);//根据流水号查找相关的文件和表单,未使用到这个方法

    List findFileNameAndUrl(@Param("businessCode") String business_code,@Param("itemsId") Integer itemsId);//根据流水号和事项id查找查找文件名和url地址

    Map<String,Object> findContent(@Param("businessCode") String business_code,@Param("itemsId") Integer itemsId); //根据流水号查找用户填写的申请表单

    Map<String,Object> findContentByItemsid(@Param("businessCode") String business_code,@Param("itemsId") Integer itemsId); //根据流水号和事项id查找用户填写的申请表单

    Map<String,Object> findApplicantDepartment(@Param("businessCode") String business_code,@Param("itemsId") Integer itemsId);//查询经办人申请人和部门

    List findApplicantDepartment2(@Param("businessCode") String business_code,@Param("itemsId") Integer itemsId);//查询经办人申请人和部门

    String findBusiness(String business_code);

    List findAllByFront(String business_code);//给前端的接口,获取申请人经办人和部门

    List findFile(String business_code);//给前端的接口，获取文件

    List<Map<String,Object>>  findByBusinessResultSatue(@Param("businessCode") String business_code);//已办任务页面审批流程节点图使用的方法

    int updateDeptState(String business_code);//修改下一部门的状态为1

    /**
     * 根据fileid查询file
     * @param id
     * @return
     */
    Map<String,Object> findFileById(Integer id);

    Map<String,Object> findDeptId(String account);//查询部门id

    List<Datums> getSearchResultByDepartmentIdAndState(@Param("state")String state, @Param("department_id") Integer department_id,  @Param("item_name") String item_name,@Param("start") int start,@Param("end") int end);

    Datum findByItemsIdAndBusinessCode(@Param("businessCode") String businessCode,@Param("itemsId") Integer itemsId);

}
