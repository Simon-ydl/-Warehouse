package com.yjs.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: ApiAccess
 * @Auther：admin
 * @Description:
 * @Date: 2019/12/12 15:22
 * @Version: 1.0.0
 **/

public class ApiAccess {

    static String policyPwd = "hepbepzy";
    static String token;
    static Map<String, String> params;


    /**
     *
     * @param apiName
     * @param data
     * @return
     * @throws Exception
     */
    public static List Apipost(String apiName, String data) throws Exception {
        token = Api.token();
        String des = DesUtil.encrypt(data, policyPwd);          //加密
        params = new HashMap<>();                           //参数
        params.put("token", token);
        params.put("apiName", apiName);
        params.put("data", des);//
        String s = HttpClientUtil.sendPost("/api/access", params);      //post请求地址
        JSONObject json = JSONObject.fromObject(s);                     //JSon格式
        if (json.getInt("code") != 1) {
            throw new RuntimeException("接口调用失败：" + json.getString("msg"));
        }
        String d = json.getString("data");
        String decrypt = DesUtil.decrypt(d, policyPwd);
        JSONObject j = JSONObject.fromObject(decrypt);
        List objects = new ArrayList<>();
        objects = j.getJSONArray("data");
        return objects;
    }

    public static String StringApipost(String apiName, String data) throws Exception {
        System.out.println("----------------------------------------------");
        System.out.println(apiName);
        token = Api.token();
        String des = "";
        if(StringUtils.isNotBlank(data)){
            des = DesUtil.encrypt(data, policyPwd);
        }
        params = new HashMap<>();
        params.put("token", token);
        params.put("apiName", apiName);
        params.put("data", des);
        String s = HttpClientUtil.sendPost("/api/access", params);
        JSONObject json = JSONObject.fromObject(s);
        if (json.getInt("code") != 1) {
            throw new RuntimeException("接口调用失败：" + json.getString("msg"));
        }
        String d = json.getString("data");
        String decrypt = DesUtil.decrypt(d, policyPwd);
        return decrypt;
    }

}
