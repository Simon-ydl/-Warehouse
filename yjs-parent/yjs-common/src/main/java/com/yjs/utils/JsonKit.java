package com.yjs.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonKit {
	
	private static ObjectMapper mapper = new ObjectMapper();
	private static Gson gson;
	
    static{
        //将序列化的json转对象（json字符串转指定的对象）
        gson = new GsonBuilder()
                .setLenient()// json宽松
                .enableComplexMapKeySerialization()//支持Map的key为复杂对象的形式
                .setLongSerializationPolicy(LongSerializationPolicy.STRING)
                .serializeNulls() //智能null
                .setPrettyPrinting()// 调教格式
                .disableHtmlEscaping() //默认是GSON把HTML 转义的
                .create();
    }

    public static Gson getGson() {
        return gson;
    }
	
	public static Map<String, Object> toMap(String jsonStr){
		Map<String, Object> map = new HashMap<String, Object>();
	    try {
			map = mapper.readValue(jsonStr, new TypeReference<Map<String, Object>>() {});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
		
	}
	
	public static List<Map<String, Object>> toList(String jsonStr){
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			JsonNode node = mapper.readValue(jsonStr, JsonNode.class);
			if(node.isArray()){
				JSONArray ja = JSONArray.fromObject(jsonStr);
				for (int i = 0; i < ja.size(); i++) {
					list.add(toMap(ja.getString(i)));
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public static String toJsonStr(Object object){
		return JSONObject.fromObject(object).toString();
	}
	
	public static void main(String[] args) {
		System.out.println(toJsonStr("{id=9, title=fds, images=http://360yx.9351p.com/FhRrsmECb4wXoeaB86rknA-F0KI_}"));
	}
}