package com.yjs.utils;


import net.sf.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UserApi {
    static String baseURL = "http://try.dg.gov.cn/api";
    static String policyName = "广州南秀信息科技有限公司";
    static String policyPwd = "hepbepzy";
    static String time = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss.SSS").format(new Date());
    public static String api(String strIn,String apiName) throws Exception {
    	 String url = baseURL + "/token";
    	// 1. 获得接口访问令牌(HTTP.POST)
        String loginSign = Tools.MD5(time + policyName + policyPwd);
        Map<String, String> params = new HashMap<String, String>();
        params.put("time", time);
        params.put("policyName", policyName);
        params.put("loginSign", loginSign);
        String res = UserHttpClientUtil.sendPost(url,params);
        System.out.println("获取令牌：："+res);
        JSONObject json = JSONObject.fromObject(res); // {code:1/0, data:token, msg:''}
        if (json.getInt("code") != 1) {
            throw new RuntimeException("令牌获取失败：" + json.getString("msg"));
        }
        String token = json.getString("data");
        // 2. 使用令牌访问业务接口(HTTP.POST)
        url = baseURL + "/access";
        String data = DesUtil.encrypt(strIn, policyPwd);// 接口参数（加密）
        System.out.println("----------------------------------------------");
        System.out.println(apiName);
        params = new HashMap<String, String>();
        params.put("token", token);
        params.put("apiName", apiName);
        params.put("data", data);
        res = UserHttpClientUtil.sendPost(url, params);
        System.out.println("通过令牌获取信息:::：："+res);
        json = JSONObject.fromObject(res); // {code:1/0, data:'', msg:''}
        if (json.getInt("code") != 1) {
            throw new RuntimeException("接口调用失败：" + json.getString("msg"));
        }
        String encryptData = json.getString("data");
        data = DesUtil.decrypt(encryptData, policyPwd); // 接口返回数据(解密)
        return data;
    }
    }
