package com.yjs.service.base.service;


import com.yjs.bean.Ids;
import com.yjs.bean.system.RoUser;

import java.util.List;

/**
 * 基础App接口
 *
 * @param <T>
 */
public interface IBaseApp<T>{

    public List<T> query(T t);

    public int insert(T t);

}
