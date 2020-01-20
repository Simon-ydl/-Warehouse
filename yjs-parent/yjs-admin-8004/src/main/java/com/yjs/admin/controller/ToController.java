package com.yjs.admin.controller;


import com.yjs.bean.test.Model;
import com.yjs.service.test.service.ITestApp;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
public class ToController {

    @Resource
    private ITestApp testApp;

    @RequestMapping("/toContent")
    public String toContent(@RequestParam("businessCode") String businessCode,@RequestParam("itemsId") Integer itemsId, HttpSession session){
        session.setAttribute("detumBusinessCode",businessCode);
        session.setAttribute("detumItemsId",itemsId);
        if (itemsId.equals(10)){
            return "datum/basicform";
        }
        return "datum/talentsform";

    }


    @RequestMapping("/toPng")
    public String toPng(int id, HttpSession session){
        session.setAttribute("fileId",id);
        return "datum/png";
    }


    @RequestMapping("/toIndex")
    public String toIndex(){
        return "system/index";
    }


    /**
     * 人才入户表单
     * @return
     */
    @RequestMapping("toTalentsForm")
    public String toTalentsForm(@RequestParam("businessCode") String businessCode,@RequestParam("itemsId") String itemsId, HttpSession session) {
        session.setAttribute("talentsCode",businessCode);
        return "datum/talentsform";
    }

    /**
     * 公安表单
     * @return
     */
    @RequestMapping("toBasicsForm")
    public String toBasicsForm(@RequestParam("businessCode") String businessCode, HttpSession session) {
        session.setAttribute("basicsCode",businessCode);
        return "datum/basicform";

    }



    @RequestMapping("/toLogin")
    public String toLogin(){
        return "system/login";
    }


    @RequestMapping("/toWelcome")
    public String toWelcome(){
        return "demo/welcome";
    }













    @RequestMapping("/toDetails")
    public String toDetails(){
        return "system/toDetails";
    }


    @RequestMapping("/toTran")
    public String toTran(){
        return "datum/tran";
    }

    @RequestMapping("/toDealt")
    public String toDealt(){
        return "datum/dealt";
    }


    @GetMapping("/message")
    public String message(){
        return "system/message";
    }

}
