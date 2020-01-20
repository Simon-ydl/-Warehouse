package com.yjs.admin.controller.admin;

import com.yjs.bean.system.RoUser;
import com.yjs.bean.system.User;
import com.yjs.bean.utils.Result;
import com.yjs.bean.utils.StatusCode;
import com.yjs.service.datum.service.DatumService;
import com.yjs.service.user.service.IRoUserService;
import com.yjs.utils.FastJsonUtil;
import com.yjs.utils.HttpRequestUtil;
import com.yjs.utils.JsonKit;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: RoUserController
 * @Auther：admin
 * @Description:
 * @Date: 2019/12/13 10:12
 * @Version: 1.0.0
 **/

@Controller
@RequestMapping("/user")
public class RoUserController {

    @Autowired
    IRoUserService roUserService;


    @Autowired
    DatumService service;

    @RequestMapping("/code")
    public String code(String code, HttpSession session) throws Exception {

        String token = HttpRequestUtil.sendGet("http://19.104.11.180:80/cas/oauth2.0/accessToken",
                "client_id=gznxxxkjyxgs&client_secret=nicijugd&code=" + code + "&redirect_uri=http://127.0.0.1:7004/user/code");
//        String token = HttpRequestUtil.sendGet("http://19.104.11.180:80/cas/oauth2.0/accessToken",
//                "client_id=gznxxxkjyxgs&client_secret=nicijugd&code=" + code + "&redirect_uri=http://19.104.51.80:7004/user/code");
        String[] split = token.split("&");
        String access_token = split[0].substring("access_token=".length());
        String expires_in = split[1].substring("expires=".length());

        String t = HttpRequestUtil.sendGet("http://19.104.11.180:80/cas/oauth2.0/profile", "access_token=" + access_token);
        JSONObject json = JSONObject.fromObject(t);

        JSONArray attributes = json.getJSONArray("attributes");
        Map<String, String> map = new HashMap   <>();
        RoUser roUser = new RoUser();
        for (int i = 0; i < attributes.size(); i++) {
            JSONObject a = attributes.getJSONObject(i);
            Map<String, String> m = FastJsonUtil.fromJson(a.toString(), Map.class);
            if (m.containsKey("userName")) {
                roUser.setUserName(m.get("userName"));
            }
            if (m.containsKey("ckId")) {
                roUser.setCkId(m.get("ckId"));
            }
            if (m.containsKey("ckName")) {
                roUser.setCkName(m.get("ckName"));
            }
            if (m.containsKey("dtId")) {
                roUser.setDtId(m.get("dtId"));
            }
            if (m.containsKey("dtName")) {
                roUser.setDtName(m.get("dtName"));
            }
            if (m.containsKey("userOrg")) {
                String org = m.get("userOrg").split("/ ")[1];
                roUser.setUserOrg(org);
            }
            if (m.containsKey("userRole")) {
                roUser.setUserRole(m.get("userRole"));
            }

            if (m.containsKey("userTel")) {
                roUser.setUserTel(m.get("userTel"));
            }

        }
        String id = json.getString("id");
        roUser.setAccount(id);

        RoUser roUser1 = new RoUser();
        roUser1.setAccount(id);

        List<RoUser> query = roUserService.query(roUser1);
        if (query.size()==0){
            roUser.setCreateDate(new Date());
            roUserService.insert(roUser);
        }else{
            roUser.setId(query.get(0).getId());
            roUserService.update(roUser);
        }

        Map<String, Object> deptId = service.findDeptId(roUser1.getAccount());
        System.out.println(deptId);
        session.setAttribute("loginTime",deptId);
        session.setMaxInactiveInterval(Integer.parseInt(expires_in));

        return "redirect:/toIndex";
    }

    @GetMapping("/exit")
    public String Exit() throws Exception{
        return  "redirect:/toLogin";
    }

    @RequestMapping("/details")
    @ResponseBody
    public Result UserList(String token, HttpSession session) throws Exception{
        Map<String,Object> user = (Map<String, Object>) session.getAttribute("loginTime");
        return new Result(true, StatusCode.OK,"查询成功",user);
    }


}
