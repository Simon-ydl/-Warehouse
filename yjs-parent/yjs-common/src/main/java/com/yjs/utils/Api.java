package com.yjs.utils;

import net.sf.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: Api
 * @Auther：admin
 * @Description:
 * @Date: 2019/12/12 16:02
 * @Version: 1.0.0
 **/

public class Api {

    static String policyName = "广州南秀信息科技有限公司";
    static String policyPwd = "hepbepzy";


    /**
     * 获取api接口授权
     * @return
     * @throws Exception
     */
    public static  String token() throws Exception {
        String time = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss.SSS").format(new Date()); //时间戳
        String md5 = MD5.md5Encode(time + policyName + policyPwd);      //MD5加密
        Map<String, String> map = new HashMap<String, String>();    //参数
        map.put("time", time);
        map.put("policyName", policyName);
        map.put("loginSign", md5);
        String post = HttpClientUtil.sendPost("/api/token", map);          //post请求
        JSONObject json = JSONObject.fromObject(post);      //转换成JSON
        if (json.getInt("code") != 1) {
            throw new RuntimeException("令牌获取失败：" + json.getString("msg"));
        }
        String token = json.getString("data");      //get key 获取value
        System.err.println("一体化登陆token————"+token);
        return token;
    }
}
