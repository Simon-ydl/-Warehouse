package com.api;

import com.yjs.utils.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Test;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @ClassName: tesr
 * @Auther：admin
 * @Description:
 * @Date: 2019/12/12 10:45
 * @Version: 1.0.0
 **/

public class test {



    String policyName = "广州南秀信息科技有限公司"; // 接入商名称
    String policyPwd = "hepbepzy"; // 接入商密码
    String client_id = "gznxxxkjyxgs";
    String client_secret = "nicijugd";

    /**
     * 1.6.18.1获取OAUTH2授权码
     */
    @Test
    public void Test01() {
        Map<String, String> map = new HashMap<>();
        map.put("client_id", client_id);
        map.put("response_type", "code");
        map.put("redirect_uri", "http://106.12.124.36:3000/");
        String post = HttpClientUtil.sendGet("/cas/oauth2.0/authorize", map);
        System.out.println(post);
    }


    /**
     * 1.6.1获取授权接口
     */
    @Test
    public void Test02() throws Exception {
        String token = Api.token();
        System.out.println(token);

    }


    /**
     * 1.6.4.1获取全部一件事
     * @throws Exception
     */
    @Test
    public void Test04() throws Exception {
        /**
         * 传入接口名称和key value的数据结构
         */
        List getAllOnethingList = ApiAccess.Apipost("getAllOnethingList", "isActive=0");
        for (Object o : getAllOnethingList) {
            System.out.println(o);
        }
        System.out.println(getAllOnethingList);

    }


    /**
     * 1.6.4.1.1获取一件事情形
     * @throws Exception
     */
    @Test
    public void Test05() throws Exception {
        /**
         * 传入接口名称和key value的数据结构
         */
        String getQxByItemCode = ApiAccess.StringApipost("getQxByItemCode", "itemId=402885fb6c93cc0b016c948f46580004&versionId=");
        System.out.println(getQxByItemCode);

    }


    /**
     * 1.6.4.2获取一件事材料
     * @throws Exception
     */
    @Test
    public void Test06() throws Exception {
        /**
         * 传入接口名称和key value的数据结构
         */
        String getQxByItemCode = ApiAccess.StringApipost("getClqdByItemCode", "itemId=402885fb6c93cc0b016c948f46580004&versionId=");
        System.out.println(getQxByItemCode);

    }



    /**
     * 1.6.4.2获取一件事事项
     * @throws Exception
     */
    @Test
    public void Test07() throws Exception {
        /**
         * 传入接口名称和key value的数据结构
         */
        String getQxByItemCode = ApiAccess.StringApipost("getOnethingItem", "itemId=402885fb6c93cc0b016c948f46580004&versionId=");
        System.out.println(getQxByItemCode);

    }


    /**
     * 1.6.4.3获取事项办事指南
     * @throws Exception
     */
    @Test
    public void Test08() throws Exception {
        /**
         * 传入接口名称和key value的数据结构
         */
        String getQxByItemCode = ApiAccess.StringApipost("getBsznByItemCode", "itemId=402885fb6c93cc0b016c948f46580004");
        System.out.println(getQxByItemCode);

    }




}
