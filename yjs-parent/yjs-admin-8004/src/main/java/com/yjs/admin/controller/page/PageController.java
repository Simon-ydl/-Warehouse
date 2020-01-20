package com.yjs.admin.controller.page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yjs.bean.business.Business;
import com.yjs.service.business.BusinessService;
import com.yjs.service.page.PageService;
import com.yjs.utils.JsonKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/page")
public class PageController {
	
	@Autowired
	PageService pageService;
	@Autowired
	BusinessService businessService;
	
	
}
