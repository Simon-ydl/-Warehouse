package com.yjs.admin.controller;


import com.yjs.bean.test.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PublicController {

    @RequestMapping("/")
    public String toIndex(){

        return "system/index";
    }


    @RequestMapping("/to404")
    public String to404(){

        return "demo/error";
    }

    @RequestMapping("/to405")
    public String to405(){

        return "accept-registration";
    }
}
