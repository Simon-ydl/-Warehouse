package com.yjs.service.matter.service;

import com.github.pagehelper.PageInfo;
import com.yjs.bean.matter.Matter;
import com.yjs.bean.test.Model;
import com.yjs.service.base.service.IBaseApp;

import java.util.List;
import java.util.Map;

public interface IMatterApp extends IBaseApp<Matter> {


    PageInfo queryHelperPageInfo(int pageNum , int pageSize , Map<String,String> searchMap, String orderBy);

    Matter queryMatterByItemId(String itemId);

}
