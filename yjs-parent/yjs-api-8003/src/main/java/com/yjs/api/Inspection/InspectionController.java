package com.yjs.api.Inspection;

import com.yjs.bean.inspection.Inspection;
import com.yjs.service.inspection.Service.InspectionService;
import com.yjs.utils.JsonKit;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/inspection")
public class InspectionController {

    @Autowired
    InspectionService inspectionService;

    @PostMapping("/getInspection")
    @ResponseBody
    public Object getInspection(@RequestBody String params){
        Map<String, Object> map = JsonKit.toMap(params);
        Map<String, Object> maps = new HashMap<>();
        String businessCode = map.get("businessCode").toString();
        Inspection inspection = inspectionService.findAllByBusinessCode(businessCode);
        maps.put("code", 1);
        maps.put("message", "回显成功");
        maps.put("status", "success");
        maps.put("inspection", inspection);
        return JSONObject.fromObject(maps);
    }
}
