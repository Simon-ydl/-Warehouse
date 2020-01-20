package com.yjs.service.register;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;
import com.yjs.bean.file.Files;
import com.yjs.bean.item.Items;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface IRegisterService {

    /**
     * 现场登记保存情形启动保存业务接口 启动成功返回业务Id
     */
    String registerFirst(Map<String,String> param, HttpServletRequest request);

    PageInfo<Files> queryMaterialFile(int pageNum , int pageSize , String materialCode,String businessCode, String orderBy);

    void saveItem(JSONArray itemList);

    List<Items> queryItemsByCode(List<String> itemCode);

    int deleteMaterial(String materialCode,String businessCode);
}
