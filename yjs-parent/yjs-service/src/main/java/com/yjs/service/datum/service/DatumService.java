package com.yjs.service.datum.service;

import com.github.pagehelper.PageInfo;
import com.yjs.bean.datum.*;
import com.yjs.service.base.service.IBaseApp;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface DatumService  extends IBaseApp<Datum> {

    Map<String,Object> findDatumInformation(String business_code,Integer itemsId);
    Map<String,Object> findDatumInformation2(String business_code,Integer itemsId);
    PageInfo queryHelperPageInfo(int pageNum, int pageSize, Map<String, String> searchMap, String orderBy,String items_id,String assignee);
    PageInfo queryHelperPageInfo2(int pageNum, int pageSize, Map<String, String> searchMap, String orderBy,String items_id,String assignee);
    List findFileBybusinessCode(String business_code);

    String findBusiness(String business_code);

    void save(Datum datum);

    void findContent(String business_code,Integer itemsId,HttpServletResponse response) throws Exception;

    void findWebContent(String business_code,Integer itemsId,HttpServletResponse response) throws Exception;

    Datum findAllByBusinessCodeAndItemsId(String businessCode, int itemsId);

    Map<String,Object> findDeptId(String account);//查询部门id

    void update(Datum datum);

    List<Datum> findAllByBusinessCode(String businessCode);

    List findFileNameAndUrl(String business_code,Integer item_id);

    /**
     * 获取用户申请信息和审批历史记录
     * @param business_code
     * @return
     */
    List findUserinfo(String business_code);

    /**
     * 根据fileid查询file
     * @param id
     * @return
     */
    Map<String,Object> findFileById(Integer id);


    Datum findByItemsIdAndBusinessCode(String businessCode, Integer itemsId);

    /**
     * 修改表单状态
     * @param businessCode
     */
    void updateState(String businessCode, String state);

    /**
     * 查询表单
     * @return
     */
    List<Datum> findAllByItemId();
}
