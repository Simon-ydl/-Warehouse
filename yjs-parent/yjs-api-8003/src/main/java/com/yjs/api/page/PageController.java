package com.yjs.api.page;

import com.yjs.bean.business.Business;
import com.yjs.bean.item.Items;
import com.yjs.bean.page.Page;
import com.yjs.service.business.BusinessService;
import com.yjs.service.datum.service.DatumService;
import com.yjs.service.item.ItemsService;
import com.yjs.service.page.PageService;
import com.yjs.utils.JsonKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/page")
public class PageController {

	@Autowired
	PageService pageService;
	@Autowired
	BusinessService businessService;
	@Autowired
	DatumService datumService;
	@Autowired
	ItemsService itemsService;

	@PostMapping("/getPage")
	@ResponseBody
	public String getPage(@RequestBody String params, HttpSession session) {

		Map<String, Object> maps = JsonKit.toMap(params);
		Map<String, Object> map = new HashMap<String, Object>();
		String businessCode = maps.get("businessCode").toString();
		Business business = businessService.findAllByBusinessCode(businessCode);

		int matterId = Integer.valueOf(String.valueOf(session.getAttribute("matterId")));
//		int matterId = 3;

		String[] itemCodes = (String[]) session.getAttribute("itemCode");
//		String[] itemCodes = {"rsjrcrh", "garcrh"};
//		String[] itemCodes = {"sjjcgkb", "cgzfjcgkb", "gajcgkb", "hbjcgkb","gacgkb","xfjcgkb"};
		List<Object> list = new ArrayList<>();
		String itemId = null;
		for (String itemCode : itemCodes){
			Items items = itemsService.findByItemCode(itemCode);
			String itemsId = items.getId().toString();
			Page page = pageService.findAllByMatterIdAndItemId(matterId,itemsId);
			if(page != null){
				list.add(JsonKit.toJsonStr(page));
			}
			if(itemId == null){
				itemId = itemsId;
			}else {
				itemId = itemId + "," +itemsId;
			}
		}
		Page page = pageService.findAllByMatterIdAndItemId(matterId,itemId);
		if(page != null){
//			list.add(JsonKit.toJsonStr(page));
			list.add(0,JsonKit.toJsonStr(page));
		}
//		List<Object> list = pageService.findAllByMatterIdAndItemId(business.getMatterId(),items.getId());
		map.put("data", list);
		map.put("code", 1);
		map.put("message", "查询成功");
		return JsonKit.toJsonStr(map);

	}


}
