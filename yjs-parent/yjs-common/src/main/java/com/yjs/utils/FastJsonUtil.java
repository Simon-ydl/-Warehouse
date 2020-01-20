package com.yjs.utils;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * fastjson工具
 * @author tmh
 *
 */
public class FastJsonUtil {
	
	static final SerializerFeature[] emptyFilters = new SerializerFeature[0];
	
	public static String toJson(Object obj){
		return JSON.toJSONStringWithDateFormat(obj, "yyyy-MM-dd HH:mm:ss", emptyFilters);
	}
	
	public static <T> T fromJson(String json , Class<T> cls){
		return JSON.parseObject(json, cls);
	}
	
	public static <T> T  fromJson(String json , TypeReference<T> type){
		T t = JSON.parseObject(json, type);
		return t;
	}
	
	public static <T> List<T>  fromJsonForArray(String json , Class<T> cls){
		List<T> list = JSON.parseArray(json, cls);
		return list;
	}
}
