package com.yjs.api.apiController;


import com.yjs.bean.UserBean;
import com.yjs.bean.utils.Result;
import com.yjs.service.loginuser.UserService;
import com.yjs.utils.Api;
import com.yjs.utils.ApiAccess;
import com.yjs.utils.Dase64Util;
import com.yjs.utils.UserApi;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
	@Autowired
    UserService userService;
    /*获取全部一件事*/
    @RequestMapping("/getAllOnethingList.action")
   public String getAllOnethingList() throws Exception {
       String apiName = "getAllOnethingList";
       String data="isActive=1";
       String rawData = ApiAccess.StringApipost(apiName,data);
       return rawData;
   }
   /*获取图片*/
    @RequestMapping("/downloadFile.action")
    public byte[] downloadFile(String id) throws Exception {
        String apiName= "downloadFile";
        String data="url="+id;
        String rawData=ApiAccess.StringApipost(apiName,data);
        System.out.println(rawData);
        JSONObject json = JSONObject.fromObject(rawData);
        String data1=json.getString("data");
        byte[] bytes = Dase64Util.base64ToImgByteArray(data1);
        return bytes;
    }
/*文件下載*/
@RequestMapping("/downloadFile/{id}")
public byte[] downLFile(@PathVariable("id") String id) throws Exception {
    String apiName= "downloadFile";
    String data="url="+id;
    String rawData=ApiAccess.StringApipost(apiName,data);
    JSONObject json = JSONObject.fromObject(rawData);
    int code=json.getInt("code");
    String msg=json.getString("msg");
    String data2=json.getString("data");
    byte[] bytes = Dase64Util.base64ToImgByteArray(data2);
    return bytes;
}
/*获取事项唯一标识Id*/
    @RequestMapping("/getQxByItemCode.action")
    public String  getQxByItemCode(String itemId) throws Exception {
        String apiName= "getQxByItemCode";
        String data="itemId="+itemId;
        String rawData= ApiAccess.StringApipost(apiName,data);
        System.out.println(rawData);
        return rawData;
    }

/*获取事项办事指南*/
    @RequestMapping("/getBsznByItemCode.action")
    public String  getBsznByItemCode(String tcode) throws Exception {
        String apiName= "getBsznByItemCode";
        String data="tcode="+tcode+"&apiVersion=2";
        String rawData= ApiAccess.StringApipost(apiName,data);
        System.out.println(rawData);
        return rawData;
    }
    
   //获取外网登录用户信息
    @RequestMapping("/findUserInfoByToken.action")
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
}
