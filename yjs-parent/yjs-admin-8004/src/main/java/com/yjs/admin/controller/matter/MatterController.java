package com.yjs.admin.controller.matter;


import com.github.pagehelper.PageInfo;
import com.yjs.bean.matter.Matter;
import com.yjs.bean.vo.TableData;
import com.yjs.service.base.service.IBaseApp;
import com.yjs.service.matter.service.IMatterApp;
import com.yjs.utils.BulidUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/matter")
public class MatterController {

    @Autowired
    private IMatterApp matterApp;

    @RequestMapping("/toList.do")
    public String toIndex(){
        return "matter/lists";
    }

    @ResponseBody
    @RequestMapping("/getMatterList")
    public TableData getMatterList(HttpServletRequest request,
                                   @RequestParam(name = "page", required = false, defaultValue = "1")
                                           int pageNum,
                                   @RequestParam(name = "limit", required = false, defaultValue = "10")
                                               int pageSize,
                                   Matter entity){

        String s = request.getParameter("searchField");

        Map<String,String> searchMap = new HashMap<>();

        if(StringUtils.isNotBlank(s)){
            BulidUtils.buildSearchField(s,searchMap);
        }

        PageInfo<Matter> list = matterApp.queryHelperPageInfo(pageNum,pageSize,searchMap,"create_date desc");

        TableData td = new TableData("0","",list.getTotal(),list.getList());

        return td;
    }


}
