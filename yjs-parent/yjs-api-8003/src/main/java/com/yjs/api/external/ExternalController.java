package com.yjs.api.external;

import com.yjs.bean.UserBean;
import com.yjs.bean.utils.Result;
import com.yjs.service.loginuser.UserService;
import com.yjs.utils.ApiAccess;
import com.yjs.utils.Dase64Util;
import com.yjs.utils.UserApi;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * @author summer
 * 获取外部一件事接口
 */
@RestController
public class ExternalController {

    @Autowired
    UserService userService;

    /**
     * 1.6.4.1 获取全部一件事主题 接口名称：getAllOnethingList
     * http://192.168.1.105:7005/getAllOnethingList.action
     * @return
     * @throws Exception
     */
    @RequestMapping("/getAllOnethingList")
    public Object getAllOnethingList() throws Exception {
        return ApiAccess.StringApipost("getAllOnethingList", "isActive=1");
    }

    /**
     * 1.6.4.2 获取一件事情形 接口名称：getQxByItemCode
     * @param itemId
     * @return
     * @throws Exception
     */
    @RequestMapping("/getQxByItemCode/{itemId}")
    public Object getQxByItemCode(@PathVariable("itemId") String itemId) throws Exception {
        return ApiAccess.StringApipost("getQxByItemCode","itemId="+itemId);
    }

    /**
     * 1.6.4.3获取一件事材料 接口名称：getClqdByItemCode
     * @param itemId
     * @return
     * @throws Exception
     */
    @RequestMapping("/getClqdByItemCode")
    public Object getClqdByItemCode(@RequestParam(name = "itemId",required = false) String itemId, @RequestParam(name = "ywqxId",required = false,defaultValue = "ff8080816f180b4a016f1cf7c4a60240") String ywqxId) throws Exception {
        return ApiAccess.StringApipost("getClqdByItemCode","itemId="+itemId+"&ywqxId="+ywqxId);
    }

    /**
     * 1.6.4.4获取一件事事项 接口名称：getOnethingItem
     * @param oneThingId
     * @return
     * @throws Exception
     */
    @RequestMapping("/getOnethingItem/{oneThingId}")
    public Object getOnethingItem(@PathVariable(name = "oneThingId") String oneThingId) throws Exception {
        return ApiAccess.StringApipost("getOnethingItem","oneThingId="+oneThingId);
    }

    /**
     * 1.6.5.8获取事项/业务办理项对应的情形项信息 接口名称：listLastSituationItemInfo
     * @param itemId
     * @return
     * @throws Exception
     */
    @RequestMapping("/listLastSituationItemInfo/{itemId}")
    public Object listLastSituationItemInfo(@PathVariable(name = "itemId") String itemId) throws Exception {
        return ApiAccess.StringApipost("listLastSituationItemInfo","itemId="+itemId);
    }
    //*文件消息查询
    @RequestMapping("/getFileInfo.action")
    public String  getFileInfo(String id)throws Exception {
        //文件查询
        String apiName= "getFileInfo";
        String data="id="+id;
        String rawData=ApiAccess.StringApipost(apiName,data);
        JSONObject json = JSONObject.fromObject(rawData);
        return rawData;
    }

   //*获取图片*/
    @RequestMapping("/tpdownloadFile")
    public byte[] downloadFile(String id) throws Exception {
        String apiName= "downloadFile";
        String data="url="+id;
        String rawData=ApiAccess.StringApipost(apiName,data);
        JSONObject json = JSONObject.fromObject(rawData);
        String data1=json.getString("data");
        byte[] bytes = Dase64Util.base64ToImgByteArray(data1);
        return bytes;
    }
    //下载
    @RequestMapping("/downloadFile")
    public byte[] downloadxzFile(String id, String name, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if(name==null){
            //文件查询
            String cxapiName= "getFileInfo";
            String cxdata="id="+id;
            String cxrawData= ApiAccess.StringApipost(cxapiName,cxdata);
            JSONObject json2 = JSONObject.fromObject(cxrawData);
            if(json2.containsKey("data")){
                String data2=json2.getString("data");
                json2 = JSONObject.fromObject(data2);
                name=json2.getString("name");
            }else{
                name="universal.txt";
            }
        }
        //文件下载
        String apiName= "downloadFile";
        String data="url="+id;
        String rawData= ApiAccess.StringApipost(apiName,data);
        JSONObject json = JSONObject.fromObject(rawData);
        String xzdata=json.getString("data");
        byte[] bytes = Dase64Util.base64ToImgByteArray(xzdata);
        response.setContentType("multipart/form-data;charset=UTF-8");
        response.setHeader("Content-Disposition", "`attachment`;fileName="+java.net.URLEncoder.encode(name, "UTF-8"));
        OutputStream stream = response.getOutputStream();
        return bytes;
    }
   /* *//*获取事项唯一标识Id*//*
    @RequestMapping("/getQxByItemCode.action")
    public String  getQxByItemCode(String itemId) throws Exception {
        String apiName= "getQxByItemCode";
        String data="itemId="+itemId;
        String rawData= ApiAccess.StringApipost(apiName,data);
        System.out.println(rawData);
        return rawData;
    }*/

    /*获取事项办事指南*/
    @RequestMapping("/getBsznByItemCode")
    public String  getBsznByItemCode(String tcode) throws Exception {
        String apiName= "getBsznByItemCode";
        String data="tcode="+tcode+"&apiVersion=2";
        String rawData= ApiAccess.StringApipost(apiName,data);
        System.out.println(rawData);
        return rawData;
    }

    //获取外网登录用户信息
    @RequestMapping("/findUserInfoByToken")
    @ResponseBody
    public Object findUserInfoByToken(String tokenCode) throws Exception{
        String apiName= "findUserInfoByToken";
        String strIn="tokenCode="+tokenCode;
        String rawData= UserApi.api(strIn,apiName);
        JSONObject json = JSONObject.fromObject(rawData);
        System.out.println(json);
        int code=json.getInt("code");
        String msg=json.getString("msg");
        if(code==1) {
            String data=json.getString("data");
            JSONObject json1 = JSONObject.fromObject(data);
            String useridcode = json1.getString("useridcode");
            String cn=json1.getString("cn");
            String idcardtype=json1.getString("idcardtype") ;
            String idcardnumber=json1.getString("idcardnumber") ;
            String telephonenumber=json1.getString("telephonenumber");
            UserBean userBean =new UserBean();
            userBean.setUseridcode(useridcode);
            userBean.setCn(cn);
            userBean.setIdcardtype(idcardtype);
            userBean.setIdcardnumber(idcardnumber);
            userBean.setTelephonenumber(telephonenumber);
            userService.selectUser(userBean);
            return new Result(code, msg, userBean);
        }else {
            return new Result(0,msg);
        }

    }
    /**
     * 1.6.4.3 获取事项的重复情形和材料 接口名称：listMappingInfo
     * @param oneThingId
     * @return
     * @throws Exception
     */
    @RequestMapping("/listMappingInfo")
    public Object getClqdByItemCode(@RequestParam(name = "oneThingId",required = false) String oneThingId) throws Exception {
        return ApiAccess.StringApipost("listMappingInfo","oneThingId="+oneThingId);
    }

}
