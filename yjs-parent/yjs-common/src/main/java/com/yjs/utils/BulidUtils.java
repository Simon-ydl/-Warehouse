package com.yjs.utils;

import com.yjs.bean.vo.CodeAndVal;

import java.util.List;
import java.util.Map;

public class BulidUtils {

    public static void buildSearchField(String searchField, Map<String, String> map){
        if(null != searchField){
            List<CodeAndVal> list = FastJsonUtil.fromJsonForArray(searchField, CodeAndVal.class);
            if(null != list && list.size()>0){
                for(int i=0 ;i<list.size();i++){
                    map.put(list.get(i).getCode(), list.get(i).getVal().toString());
                }
            }
        }
    }
}
