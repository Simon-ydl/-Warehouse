package com.yjs.admin.controller.business;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.yjs.bean.application.Applicat;
import com.yjs.bean.business.Business;
import com.yjs.service.applicat.ApplicatService;
import com.yjs.service.business.BusinessService;
import com.yjs.service.datum.service.DatumService;
import com.yjs.service.loginuser.UserService;
import com.yjs.service.matter.service.MatterService;
import com.yjs.utils.JsonKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import net.sf.json.JSONArray;

@Controller
@RequestMapping("/business")
public class BusinessController {
	
	@Autowired
	BusinessService businessService;
	@Autowired
	UserService userService;
	@Autowired
	DatumService datumService;
	@Autowired
	ApplicatService applicatService;
	@Autowired
	MatterService matterSerive;
	

}
