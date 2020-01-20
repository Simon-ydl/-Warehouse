package com.yjs.api.datum;


import com.alibaba.fastjson.JSON;
import com.yjs.bean.Receipt.Receipt;
import com.yjs.bean.Situation.Situation;
import com.yjs.bean.agent.Agent;
import com.yjs.bean.application.Applicat;
import com.yjs.bean.business.Business;
import com.yjs.bean.datum.Datum;
import com.yjs.bean.department.Department;
import com.yjs.bean.file.Files;
import com.yjs.bean.inspection.Inspection;
import com.yjs.bean.item.Items;
import com.yjs.bean.matter.Matter;
import com.yjs.bean.tops.Tops;
import com.yjs.dataSource.DataSourceSwitch;
import com.yjs.service.agent.AgentService;
import com.yjs.service.applicat.ApplicatService;
import com.yjs.service.business.BusinessService;
import com.yjs.service.datum.service.DatumService;
import com.yjs.service.department.Service.DepartmentService;
import com.yjs.service.inspection.Service.InspectionService;
import com.yjs.service.item.ItemService;
import com.yjs.service.item.ItemsService;
import com.yjs.service.matter.service.MatterService;
import com.yjs.service.receipt.ReceiptService;
import com.yjs.service.situation.SituationService;
import com.yjs.service.test.service.ITestApp;
import com.yjs.service.topsService.TopsService;
import com.yjs.utils.BusinessNumber;
import com.yjs.utils.JsonKit;
import com.yjs.utils.JsonUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.management.resources.agent;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.soap.Addressing;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.*;

@Controller
@RequestMapping(value = "datum")
public class DatumController {

    @Autowired
    DatumService datumService;

    @Resource
    private ITestApp testApp;

    @Autowired
    TopsService topsService;

    @Autowired
    ReceiptService receiptService;

    @Autowired
    MatterService matterService;

    @Autowired
    BusinessService businessService;

    @Autowired
    ApplicatService applicatService;

    @Autowired
    AgentService agentService;

    @Autowired
    SituationService situationService;

    @Autowired
    ItemsService itemsService;

    @Autowired
    InspectionService inspectionService;

    @Autowired
    DepartmentService departmentService;


    /**
     * 信息自检测保存
     * @param param
     * @param session
     * @return
     */
    @PostMapping("/addSelf")
    @ResponseBody
    public String addSelf(@RequestBody String param , HttpSession session) throws ParseException {
        Map<String, Object> maps = JsonKit.toMap(param);
        HashMap<String, Object> map = new HashMap<>();
        Situation situation = new Situation();
        Tops tops = new Tops();
        Agent agent = new Agent();
        Applicat applicat = new Applicat();
        Receipt receipt = new Receipt();
        Business business = new Business();
        Inspection inspection = new Inspection();

        if(!maps.containsKey("useridcode") || maps.get("useridcode").toString() == null){
            map.put("message", "保存失败，你未登录");
            map.put("code", 102);
            map.put("status", "false");
            return  JsonKit.toJsonStr(map);
        }


//        if(maps.containsKey("businessCode") && maps.get("businessCode").toString() != null){
//            businessCode = maps.get("businessCode").toString();
//
//        }else {
        String businessCode = BusinessNumber.getBusinessNumber();
//        }
//        session.setAttribute("businessCode", businessCode);

        String itemCode = maps.get("itemCode").toString();
        String[] itemCodes = itemCode.split(",");
        session.setAttribute("itemCode", itemCodes);

        if(!maps.containsKey("siName")&& maps.get("siName") == null && !maps.containsKey("siContent")
                && maps.get("siContent") == null){
            map.put("message", "保存失败，情形信息未完全");
            map.put("code", 0);
            map.put("status", "false");
            return  JsonKit.toJsonStr(map);
        }
        situation.setSiName(maps.get("siName").toString());
        situation.setSiContent(maps.get("siContent").toString());
        situation.setBusinessCode(businessCode);
        Situation situations =situationService.findAllByBusinessCode(businessCode);
        if (situations == null){
            situationService.save(situation);   //保存情形信息
        }else {
            situationService.update(situation);   //更新情形信息
        }

        if(!maps.containsKey("content") && maps.get("content") == null){
            map.put("message", "保存失败，未勾选自检情形头");
            map.put("code", 0);
            map.put("status", "false");
            return  JsonKit.toJsonStr(map);
        }
        tops.setContent(maps.get("content").toString());
        tops.setBusinessCode(businessCode);
        Tops topses = topsService.findAllByBusinessCode(businessCode);
        if(topses == null){
            topsService.save(tops); //保存自检是否勾选非东莞用户。。。
        }else{
            topsService.update(tops);
        }

        if(!maps.containsKey("aNumberType") && maps.get("aNumberType") == null
                && !maps.containsKey("aName") && maps.get("aName") == null
                && !maps.containsKey("aNumber") && maps.get("aNumber") == null
                && !maps.containsKey("aMobile") && maps.get("aMobile") == null
                && !maps.containsKey("aEmail") && maps.get("aEmail") == null){
            map.put("message", "保存失败，经办人信息未完全");
            map.put("code", 0);
            map.put("status", "false");
            return  JsonKit.toJsonStr(map);
        }
        agent.setANumberType(maps.get("aNumberType").toString());
        agent.setAName(maps.get("aName").toString());
        agent.setANumber(maps.get("aNumber").toString());
        agent.setBusinessCode(businessCode);
        agent.setAMobile(maps.get("aMobile").toString());
        agent.setAEmail(maps.get("aEmail").toString());
        Agent agents = agentService.findAllByBusinessCode(businessCode);
        if(agents == null){
            agentService.save(agent);	//保存经办人信息
        }else {
            agentService.update(agents); //更新经办人信息
        }

        if(!maps.containsKey("apName") && maps.get("apName") == null
                && !maps.containsKey("apNumberType") && maps.get("apNumberType") == null
                && !maps.containsKey("apNumber") && maps.get("apNumber") == null){
            map.put("message", "保存失败，申请人信息未完全");
            map.put("code", 0);
            map.put("status", "false");
            return  JsonKit.toJsonStr(map);
        }
        applicat.setApName(maps.get("apName").toString());
        applicat.setApNumberType(maps.get("apNumberType").toString());
        applicat.setApNumber(maps.get("apNumber").toString());
        applicat.setBusinessCode(businessCode);
        Applicat applicats = applicatService.findAllByBusinessCode(businessCode);
        if (applicats == null){
            DataSourceSwitch.set("yth_dba");
            applicatService.save(applicat);	//保存申请人信息
            DataSourceSwitch.set(null);
            applicatService.save(applicat);
        }else {
            DataSourceSwitch.set("yth_dba");
            applicatService.update(applicat);   //更新申请人信息
            DataSourceSwitch.set(null);
            applicatService.update(applicat);
        }

        if(!maps.containsKey("rWay") || maps.get("rWay").toString() == null){
            map.put("message", "保存失败，收件信息未完全");
            map.put("code", 0);
            map.put("status", "false");
            return  JsonKit.toJsonStr(map);
        }else if(maps.containsKey("rWay") && maps.get("rWay").toString() == "1"){
            receipt.setRWay(maps.get("rWay").toString());
            if(maps.containsKey("rHall")) {
                receipt.setRHall(maps.get("rHall").toString());
            }
        }else if(maps.containsKey("rWay") && maps.get("rWay").toString() == "2"){
            receipt.setRWay(maps.get("rWay").toString());
            if(maps.containsKey("rName")) {
                receipt.setRName(maps.get("rName").toString());
            }
            if(maps.containsKey("rMobile")) {
                receipt.setRMobile(maps.get("rMobile").toString());
            }
            if(maps.containsKey("rArea")) {
                receipt.setRArea(maps.get("rArea").toString());
            }
            if(maps.containsKey("rAddress")) {
                receipt.setRAddress(maps.get("rAddress").toString());
            }
        }
        receipt.setBusinessCode(businessCode);
        Receipt receipts = receiptService.findAllByBusinessCode(businessCode);
        if(receipts == null){
            receiptService.save(receipt);	//保存结果接收方式
        }else{
            receiptService.update(receipt);
        }


        if(!maps.containsKey("inContent") || maps.get("inContent").toString() == null){
            map.put("message", "保存失败，你未上传所需材料相关信息");
            map.put("code", 0);
            map.put("status", "false");
            return  JsonKit.toJsonStr(map);
        }
        inspection.setInContent(maps.get("inContent").toString());
        inspection.setBusinessCode(businessCode);
        Inspection inspections = inspectionService.findAllByBusinessCode(businessCode);
        if(inspections == null){
            inspectionService.save(inspection);  //保存所需上传材料的信息
        }else{
            inspectionService.update(inspection);   //更新所需上传材料的信息
        }

		String userIdCode = maps.get("useridcode").toString();
        session.setAttribute("userIdCode", userIdCode);
        String matterCode = maps.get("matterCode").toString();
        session.setAttribute("matterCode", matterCode);
        Matter matter = matterService.findAllBymatterCode(matterCode);
        session.setAttribute("matterId", matter.getId());

        map.put("businessCode", businessCode);
        map.put("message", "保存成功");
        map.put("code", 1);
        map.put("status", "success");
        return JsonKit.toJsonStr(map);
    }


    /**
     * 申请表单保存
     * @param params
     * @param session
     * @return
     */
    @PostMapping("/addDatum")
    @ResponseBody
    public String addDatum(@RequestBody String params, HttpSession session ) {

        HashMap<String, Object> map = new HashMap<>();
        Datum datum = new Datum();
        String[] itemCodes = (String[]) session.getAttribute("itemCode");
//        String[] itemCodes = {"rsjrcrh", "garcrh"};
//        String[] itemCodes = {"sjjcgkb", "cgzfjcgkb", "gajcgkb", "hbjcgkb","gacgkb","xfjcgkb"};
        session.setAttribute("itemCode", itemCodes);

        String  jsonStr = JsonKit.toJsonStr(params);
        Map<String, Object> mapes = JsonKit.toMap(jsonStr);
        Map<String, Object> maps = JsonKit.toMap(JsonKit.toJsonStr(mapes.get("content")));

        if(maps.isEmpty()) {
            map.put("status", "false");
            map.put("code", 0);
            map.put("message", "保存失败");
            return JsonKit.toJsonStr(map);
        }
        //一个坑，以后要修改为的，
        /*if(itemsIds == 11){
            datum.setState("1");
        }else {
            datum.setState("0");
        }*/
        String businessCode = mapes.get("businessCode").toString();

        datum.setCreateAt(new Date());
        datum.setState("0");
        datum.setBusinessCode(businessCode);

        String itemsId = String.valueOf(maps.get("itemsId"));
        if(itemsId.contains(",")){  //判断时否多个itemsId
            String[] itemsIdsu = itemsId.split(",");
            for (String itemsIds : itemsIdsu){
//                if(itemsIds.equals("11")){
//                    datum.setState("1");
//                }else {
//                    datum.setState("0");
//                }
                Department department = departmentService.findByItemsId(Integer.valueOf(itemsIds));
                Datum datums = datumService.findAllByBusinessCodeAndItemsId(businessCode,Integer.valueOf(itemsIds));
                if (datums == null){
                    datum.setItemsId(Integer.valueOf(itemsIds));
                    datum.setContent(JSON.toJSONString(maps));
                    if(department.getId() == 7){
                        DataSourceSwitch.set("yth_dba");
                        datumService.save(datum);
                    }
                    DataSourceSwitch.set(null);
                    datumService.save(datum);
                }else {
                    Map mapto = JsonKit.toMap(datums.getContent());
//                    maps.putAll(mapto);
                    mapto.putAll(maps);
                    datum.setContent(JSON.toJSONString(mapto));
                    datum.setItemsId(Integer.valueOf(itemsIds));
                    if(department.getId() == 7){
                        DataSourceSwitch.set("yth_dba");
                        datumService.update(datum);
                    }
                    DataSourceSwitch.set(null);
                    datumService.update(datum);
                }
            }
        }else{
//            if(itemsId.equals("11")){
//                datum.setState("1");
//            }else {
//                datum.setState("0");
//            }
            session.setAttribute("itemsId", itemsId);
            Department department = departmentService.findByItemsId(Integer.valueOf(itemsId));
            Datum datums = datumService.findAllByBusinessCodeAndItemsId(businessCode,Integer.valueOf(itemsId));
            if (datums == null){
                datum.setContent(JSON.toJSONString(maps));
                datum.setItemsId(Integer.valueOf(itemsId));
                if(department.getId() == 7){
                    DataSourceSwitch.set("yth_dba");
                    datumService.save(datum);
                }
                DataSourceSwitch.set(null);
                datumService.save(datum);
            }else{
                Map mapto = JsonKit.toMap(datums.getContent());
                if(mapto.containsKey("name")){
//                    maps.put("name", mapto.get("name").toString());
//                    maps.put("sex", mapto.get("sex").toString());
//                    maps.put("phone", mapto.get("phone").toString());
//                    maps.put("Idtype", mapto.get("Idtype").toString());
//                    maps.put("Idcard", mapto.get("Idcard").toString());
                    mapto.putAll(maps);
                    datum.setContent(JSON.toJSONString(mapto));
                    datum.setItemsId(Integer.valueOf(itemsId));
                    if(itemsId.equals("10")){
                        DataSourceSwitch.set("yth_dba");
                        datumService.update(datum);
                    }
                    DataSourceSwitch.set(null);
                    datumService.update(datum);
                }else{
                    datum.setContent(JSON.toJSONString(maps));
                    datum.setItemsId(Integer.valueOf(itemsId));
                    if(department.getId() == 7){
                        DataSourceSwitch.set("yth_dba");
                        datumService.update(datum);
                    }
                    DataSourceSwitch.set(null);
                    datumService.update(datum);
                }

            }
        }

        map.put("status", "success");
        map.put("code", 1);
        map.put("message", "保存成功");
        return JsonKit.toJsonStr(map);
    }

    /**
     * 信息自检回显
     * @return
     */
    @PostMapping("/backSelf")
    @ResponseBody
    public String backSelf( HttpSession session ){
        String businessCode = session.getAttribute("businessCode").toString();
//        String businessCode = "201912181239066479";
        session.setAttribute("businessCode", "businessCode");
//        String userIdCode = "e701c887a05a4a0f875b31afd3c30188";
        String userIdCode = session.getAttribute("userIdCode").toString();
        session.setAttribute("userIdCode", userIdCode);
        Map<String, Object> maps = new HashMap<>();

        Situation situation = situationService.findAllByBusinessCode(businessCode);
        Agent agent = agentService.findAllByBusinessCode(businessCode);
        Applicat applicant = applicatService.findAllByBusinessCode(businessCode);
        Receipt receipt = receiptService.findAllByBusinessCode(businessCode);
        maps.put("situation", situation);
        maps.put("agent", agent);
        maps.put("applicat",applicant);
        maps.put("receipt", receipt);
        maps.put("status" ,"success");
        maps.put("code", 1);
        maps.put("message", "回显成功");
        return JsonKit.toJsonStr(maps);
    }

    /**
     * 表单回调
     * @param session
     * @return
     */
    @PostMapping("/backDatum")
    @ResponseBody
    public Object backDatum(@RequestBody String params, HttpSession session ){
        Map<String, Object> map = JsonKit.toMap(params);
        Map<String, Object> maps = new HashMap<>();
        Map<String, Object> mapes = new HashMap<>();
        String businessCode = map.get("businessCode").toString();

        Datum datums = new Datum();
        String itemsId = String.valueOf(map.get("itemsId"));
        if(itemsId.contains(",")){
            String[] itemsIdsu = itemsId.split(",");
            for(String itemsIds : itemsIdsu ){
                datums = datumService.findAllByBusinessCodeAndItemsId(businessCode,Integer.valueOf(itemsIds));
//                List<Datum> datumList = datumService.findAllByBusinessCode(businessCode);
                if(datums == null){
                    maps.put("code", 1);
                    maps.put("message", "回显成功");
                    maps.put("status", "success");
                    mapes.put("content", "");
                    mapes.put("id", "");
                    mapes.put("state", "");
                    maps.put("datum", JSONObject.fromObject(mapes));
                    return JSONObject.fromObject(maps);
                }else{
                    Map mapto = JsonKit.toMap(datums.getContent());
                    if(mapto.containsKey("name")){
                        mapes.put("id", datums.getId());
                        mapes.put("content", datums.getContent());
                        mapes.put("state", datums.getState());
                        maps.put("code", 1);
                        maps.put("message", "回显成功");
                        maps.put("status", "success");
                        maps.put("datum", JSONObject.fromObject(mapes));
                        return JSONObject.fromObject(maps);
                    }
                }
            }
        }else{
            datums = datumService.findAllByBusinessCodeAndItemsId(businessCode,Integer.valueOf(itemsId));
        }
        if(datums == null ){
                maps.put("code", 1);
                maps.put("message", "回显成功");
                maps.put("status", "success");
                mapes.put("content", "");
                mapes.put("id", "");
                mapes.put("state", "");
                maps.put("datum", JSONObject.fromObject(mapes));
                return JSONObject.fromObject(maps);
        }
        mapes.put("id", datums.getId());
        Map mapto = JsonKit.toMap(datums.getContent());
        if(mapto.containsKey("name")){
            mapto.remove("name").toString();
            mapto.remove("phone").toString();
            mapto.remove("sex").toString();
            mapto.remove("itemsId").toString();
            mapto.remove("Idtype").toString();
            mapto.remove("Idcard").toString();
        }
        if (mapto.isEmpty()){
            maps.put("code", 1);
            maps.put("message", "回显成功");
            maps.put("status", "success");
            mapes.put("content", "");
            mapes.put("id", "");
            mapes.put("state", "");
            maps.put("datum", JSONObject.fromObject(mapes));
            return JSONObject.fromObject(maps);
        }
        mapes.put("content", JsonKit.toJsonStr(mapto));
        mapes.put("state", datums.getState());
        maps.put("code", 1);
        maps.put("message", "回显成功");
        maps.put("status", "success");
        maps.put("datum", JSONObject.fromObject(mapes));
        return JSONObject.fromObject(maps);
    }


    /**
     * 检查表单上传是否完全
     * @param session
     * @return
     */
    @PostMapping("/checkDatum")
    @ResponseBody
    public Object checkDatum(HttpSession session){
        String businessCode = session.getAttribute("businessCode").toString();
        session.setAttribute("businessCode", businessCode);
        String[] itemCodes = (String[]) session.getAttribute("itemCode");
        Map<String, Object> map = new HashMap<>();

        List<Items> itemsList = null;
        for (String itemCode : itemCodes){
            Items items = itemsService.findByItemCode(itemCode);
            if(items != null){
                itemsList.add(items);
            }
        }
        List<Datum> datumList = datumService.findAllByBusinessCode(businessCode);
        if(datumList.size() < itemsList.size()){ //判断表单的数量是否小于需要的事项数量
            map.put("code", 0);
            map.put("message", "表单未提交完全");
            map.put("status", "false");
            return JSONObject.fromObject(map);
        }
        map.put("code", 1);
        map.put("message", "表单和文件上传完全");
        map.put("status", "success");
        return JSONObject.fromObject(map);
    }

    /**
     * pdf表单
     */
    @ResponseBody
    @RequestMapping("/application.pdf")
    public void createPdf(@RequestParam ("businessCode") String business_code,
                          @RequestParam ("itemsId") Integer itemsId,
                          HttpServletResponse response) throws Exception {
        //查找pdf表单
        datumService.findWebContent(business_code,itemsId,response);
    }
}
