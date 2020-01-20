package com.yjs.api.business;

import com.yjs.bean.Receipt.Receipt;
import com.yjs.bean.Situation.Situation;
import com.yjs.bean.agent.Agent;
import com.yjs.bean.application.Applicat;
import com.yjs.bean.approver.Approver;
import com.yjs.bean.business.Business;
import com.yjs.bean.datum.Datum;
import com.yjs.bean.department.Department;
import com.yjs.bean.dto.PageResult;
import com.yjs.bean.enclosure.Enclosure;
import com.yjs.bean.file.Files;
import com.yjs.bean.item.Items;
import com.yjs.bean.matter.Matter;
import com.yjs.bean.tops.Tops;
import com.yjs.bean.utils.Result;
import com.yjs.dataSource.DataSourceSwitch;
import com.yjs.service.activiti.ActivitiService;
import com.yjs.service.agent.AgentService;
import com.yjs.service.applicat.ApplicatService;
import com.yjs.service.approver.service.ApproverService;
import com.yjs.service.business.BusinessService;
import com.yjs.service.datum.service.DatumService;
import com.yjs.service.department.Service.DepartmentService;
import com.yjs.service.enclosure.service.EnclosureService;
import com.yjs.service.file.FileService;
import com.yjs.service.item.ItemService;
import com.yjs.service.item.ItemsService;
import com.yjs.service.loginuser.UserService;
import com.yjs.service.matter.service.MatterService;
import com.yjs.service.receipt.ReceiptService;
import com.yjs.service.situation.SituationService;
import com.yjs.service.topsService.TopsService;
import com.yjs.utils.JsonKit;
import com.yjs.utils.JsonUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/business")
public class BusinessController {

	@Autowired
	BusinessService businessService;
	@Autowired
	DatumService datumService;
	@Autowired
	ApplicatService applicatService;
	@Autowired
	MatterService matterSerive;
	@Autowired
	TopsService topsService;
	@Autowired
	SituationService situationService;
	@Autowired
	AgentService agentService;
	@Autowired
	FileService fileService;
	@Autowired
	ItemsService itemsService;
	@Autowired
	ReceiptService receiptService;
	@Autowired
	DepartmentService departmentService;
	@Autowired
	ItemService itemService;
	@Autowired
    ApproverService approverService;
	@Autowired
	EnclosureService enclosureService;
	@Autowired
	MatterService matterService;
	@Autowired
	ActivitiService activitiService;
	/**
	 * 查询个人办事内容
	 * @param params
	 * @return
	 */
	@PostMapping("/findAllByUseridcode")
	@ResponseBody
	public JSONObject findAllByUserId(@RequestBody String params) {
		Map<String, Object> maps = JsonKit.toMap(params);
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> mapes = new HashMap<>();
		List<String> list = new ArrayList<>();

		if(!maps.containsKey("useridcode") || maps.get("useridcode") == null){
			mapes.put("code", 0);
			mapes.put("status", "false");
			mapes.put("message", "查询失败");
			return JSONObject.fromObject(mapes);
		}
		List<Business> business = businessService.findAllByUseridcode(maps.get("useridcode").toString());
		if(business.size() != 0) {
			for(Business b : business) {
				String businessCode = b.getBusinessCode();
				map.put("businessCode", businessCode);

				Date createAt = b.getCreateAt();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String dateAt = format.format(createAt);
				map.put("createAt", dateAt);

				Applicat applicants = applicatService.findAllByBusinessCode(businessCode);
				map.put("apName", applicants.getApName());

				int matterId = b.getMatterId();
				String mName = matterSerive.findNameByMatterId(matterId);
				map.put("mName", mName);
				map.put("state", b.getState());
				list.add(JsonKit.toJsonStr(map));
				mapes.put("data", JSONArray.fromObject(list));
				mapes.put("code", 1);
				mapes.put("status", "success");
				mapes.put("message", "查询成功");

			}
			return JSONObject.fromObject(mapes);

		}
        mapes.put("data",list);
		mapes.put("code", 1);
		mapes.put("status", "success");
		mapes.put("message", "该用户未办理任何事务");
		return JSONObject.fromObject(mapes);

	}


	/**
	 * 根据流水号查询所有信息
	 * @param params
	 * @return
	 */
	@PostMapping("/findDetailByBusinessCode")
	@ResponseBody
	public Object findDetailByBusinessCode( @RequestBody String params, HttpSession session){
		Map<String, Object> maps =  JsonKit.toMap(params);
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> mapes = new HashMap<>();
		List<Object> list = new ArrayList();
		String businessCode = maps.get("businessCode").toString();

		Business business = businessService.findAllByBusinessCode(businessCode);
		Date createAt = business.getCreateAt();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateAt = format.format(createAt);
		map.put("createAt", dateAt);

		Matter matter = matterService.findAllById(business.getMatterId());
		if(matter != null){
			map.put("matter", matter);
		}

		Tops tops = topsService.findAllByBusinessCode(businessCode);
		if(tops != null){
            map.put("tops", tops);
        }

		Situation situation = situationService.findAllByBusinessCode(businessCode);
		if(situation != null){
            map.put("situation", situation);
        }

		Agent agent = agentService.findAllByBusinessCode(businessCode);
		if(agent != null){
            map.put("agent", agent);
        }

		Applicat applicat = applicatService.findAllByBusinessCode(businessCode);
		if(applicat != null){
            map.put("applicat", applicat);
        }

		Receipt receipt = receiptService.findAllByBusinessCode(businessCode);
		if(receipt != null){
            map.put("receipt", receipt);
        }

		List<Datum> datumList = datumService.findAllByBusinessCode(businessCode);
		Map<String, Object> mapDatum = new HashMap<>();
		for (Datum datum : datumList){
			Map<String, Object> mapItems = new HashMap<>();
			int itemsId = datum.getItemsId();
			Items items = itemsService.findByItemsId(itemsId);
			mapItems.put("itemCode", items.getItemCode());
			mapItems.put("itemName", items.getItemName());
			//mapItems.put("state", items.getState());

			List<Files> files = fileService.findAllByBusinessCodeAnditemsId(businessCode,itemsId);
			if(files.size() >0){
				mapItems.put("files", JSONArray.fromObject(files));
			}else{
				mapItems.put("files", "");
			}


			Department department = departmentService.findByDepartmentId(items.getDepartmentId());

			mapDatum = JsonKit.toMap(datum.getContent());
			mapDatum.put("state",datum.getState());
			mapItems.put("datum", JSONObject.fromObject(mapDatum));
			mapItems.put("department", department);
			list.add(JsonKit.toJsonStr(mapItems));
		}

		List<Approver> approver = approverService.findAllByBusinessCode(businessCode);
		PageResult workFlow = itemService.getWorkFlowOrderByBusinessCode(businessCode);//获取审批历史
        JSONObject job = JSONObject.fromObject(workFlow.getData());
        JSONArray jArray = job.getJSONArray("workFlows");
        Iterator<JSONArray> itr = jArray.iterator();
		for (int i = 0; i < jArray.size(); i++){
			if(approver.size() == 0){ //判断是否有审批 没
				jArray.getJSONObject(i).accumulate("approver", "");
//				jArray.getJSONObject(i).accumulate("approverId", "");
				jArray.getJSONObject(i).accumulate("approveOpinion", "");
				jArray.getJSONObject(i).accumulate("createTime", "");
				jArray.getJSONObject(i).accumulate("approveState", "");
				jArray.getJSONObject(i).accumulate("itemId", "");
				Enclosure enclosure = new Enclosure();
				enclosure.setId(0);
				enclosure.setEnclosureName("");
				enclosure.setEnclosureUrl("");
				jArray.getJSONObject(i).accumulate("enclosure",enclosure);
				JSONArray fileArray = new JSONArray();
				jArray.getJSONObject(i).accumulate("fileFalse",fileArray);
				job.put("workFlows", jArray);
			}else if(approver.size() == jArray.size()){ //判断是否全部审批 是
				Iterator<Approver> approverIterator = approver.iterator();
				while (approverIterator.hasNext()) {
					Approver approverNext = approverIterator.next();
					if(approverNext.getDepartmentId() == jArray.getJSONObject(i).get("departmentId")){
						jArray.getJSONObject(i).accumulate("approver", approverNext.getApprover().toString());
//						jArray.getJSONObject(i).accumulate("approverId", approverNext.getId());
						jArray.getJSONObject(i).accumulate("approveOpinion", approverNext.getApproveOpinion());
						Date createTime = approverNext.getCreateTime();
						SimpleDateFormat formats = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
						String createTimes = formats.format(createTime);
						jArray.getJSONObject(i).accumulate("createTime", createTimes);
						jArray.getJSONObject(i).accumulate("approveState", approverNext.getApproveState());
						jArray.getJSONObject(i).accumulate("itemId", approverNext.getItemId().toString());
						String lackMaterials = approverNext.getLackMaterials();
						if(approverNext.getEnclosureId() != null && approverNext.getDepartmentId() == jArray.getJSONObject(i).get("departmentId") ){
							Enclosure enclosure = enclosureService.findById(approverNext.getEnclosureId());
							jArray.getJSONObject(i).accumulate("enclosure",enclosure);
						}else {
							Enclosure enclosure = new Enclosure();
							enclosure.setId(0);
							enclosure.setEnclosureName("");
							enclosure.setEnclosureUrl("");
							jArray.getJSONObject(i).accumulate("enclosure",enclosure);
						}
						if(lackMaterials == null){
							jArray.getJSONObject(i).accumulate("fileFalse", "");
						}else{
							JSONArray fileArray = new JSONArray();
							JSONArray jsonArray = JSONArray.fromObject(lackMaterials);
							for (int j = 0; j < jsonArray.size(); j++){
								int fileId = Integer.valueOf(String.valueOf(jsonArray.getJSONObject(j).get("id")));
								String correction = jsonArray.getJSONObject(j).get("correction").toString();
								Files files = fileService.findAllById(fileId);
								JSONObject jsonObject = JSONObject.fromObject(files);
								jsonObject.accumulate("correction", correction);
								fileArray.add(jsonObject);
//							fileArray.add(correction);
							}
							jArray.getJSONObject(i).accumulate("fileFalse", fileArray);
						}
						job.put("workFlows", jArray);
					}
				}
			}else{ //有审批未全部部门审批
				Iterator<Approver> approverIterator = approver.iterator();
				while (approverIterator.hasNext()) {
					Approver approverNext = approverIterator.next();
					if(approverNext.getDepartmentId() == jArray.getJSONObject(i).get("departmentId")){
						jArray.getJSONObject(i).accumulate("approver", approverNext.getApprover().toString());
//						jArray.getJSONObject(i).accumulate("approverId", approverNext.getId());
						jArray.getJSONObject(i).accumulate("approveOpinion", approverNext.getApproveOpinion());
						Date createTime = approverNext.getCreateTime();
						SimpleDateFormat formats = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
						String createTimes = formats.format(createTime);
						jArray.getJSONObject(i).accumulate("createTime", createTimes);
						jArray.getJSONObject(i).accumulate("approveState", approverNext.getApproveState());
						jArray.getJSONObject(i).accumulate("itemId", approverNext.getItemId().toString());
						String lackMaterials = approverNext.getLackMaterials();
						if(approverNext.getEnclosureId() != null && approverNext.getDepartmentId() == jArray.getJSONObject(i).get("departmentId") ){
							Enclosure enclosure = enclosureService.findById(approverNext.getEnclosureId());
							jArray.getJSONObject(i).accumulate("enclosure",enclosure);
						}else {
							Enclosure enclosure = new Enclosure();
							enclosure.setId(0);
							enclosure.setEnclosureName("");
							enclosure.setEnclosureUrl("");
							jArray.getJSONObject(i).accumulate("enclosure",enclosure);
						}
						if(lackMaterials == null){
							jArray.getJSONObject(i).accumulate("fileFalse", "");
						}else{
							JSONArray fileArray = new JSONArray();
							JSONArray jsonArray = JSONArray.fromObject(lackMaterials);
							for (int j = 0; j < jsonArray.size(); j++){
								int fileId = Integer.valueOf(String.valueOf(jsonArray.getJSONObject(j).get("id")));
								String correction = jsonArray.getJSONObject(j).get("correction").toString();
								Files files = fileService.findAllById(fileId);
								JSONObject jsonObject = JSONObject.fromObject(files);
								jsonObject.accumulate("correction", correction);
								fileArray.add(jsonObject);
							}
							jArray.getJSONObject(i).accumulate("fileFalse", fileArray);
						}
						job.put("workFlows", jArray);
					}else{
						jArray.getJSONObject(i).accumulate("approver", "");
						jArray.getJSONObject(i).accumulate("approveOpinion", "");
						jArray.getJSONObject(i).accumulate("createTime", "");
						jArray.getJSONObject(i).accumulate("approveState", "");
						Enclosure enclosure = new Enclosure();
						enclosure.setId(0);
						enclosure.setEnclosureName("");
						enclosure.setEnclosureUrl("");
						jArray.getJSONObject(i).accumulate("enclosure",enclosure);
						JSONArray fileArray = new JSONArray();
						jArray.getJSONObject(i).accumulate("fileFalse",fileArray);
						jArray.getJSONObject(i).accumulate("itemId", "");
						job.put("workFlows", jArray);
					}
				}

			}


		}
		workFlow.setData(job);
		map.put("workFlow", workFlow);
		map.put("items", list);
		mapes.put("data", map);
		mapes.put("code", 1);
		mapes.put("message", "查询成功");
		mapes.put("state", "success");
		return JSONObject.fromObject(mapes);
	}


	/**
	 * 给前端的接口，展示用户表单信息及审批历史记录
	 * @param business_code
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findUserinfo")
	public Result findUserinfo(@RequestParam(value = "businessCode") String business_code) {
		return new Result(1, "查询成功",datumService.findUserinfo(business_code));
	}


	@ResponseBody
	@PostMapping("/finishBusiness")
	public Object finishBusiness(HttpSession session, @RequestBody String params){
		Map<String, Object> maps = JsonKit.toMap(params);
		Map<String, Object> map = new HashMap<>();
		String businessCode =  maps.get("businessCode").toString();
        Business business = new Business();
        business.setBusinessCode(businessCode);
        business.setCreateAt(new Date());
//        String userIdCode = maps.get("useridcode").toString();
        String userIdCode = session.getAttribute("userIdCode").toString();
        business.setUserIdCode(userIdCode);
//        String matterCode = maps.get("matterCode").toString();
        String matterCode = session.getAttribute("matterCode").toString();
        Matter matter = matterService.findAllBymatterCode(matterCode);
        business.setMatterId(matter.getId());
        business.setState("1");
        if(maps.containsKey("businessTag")){
            business.setBusinessTag(maps.get("businessTag").toString());
        }
        Business businesses = businessService.findAllByBusinessCode(businessCode);

        if (businesses == null){
            businessService.save(business);	//保存业务信息
        }else{
            businessService.updata(business);
        }

		List<Department> departmentList = departmentService.findByBusinessCode(businessCode);
		for (Department department : departmentList){
			if(department.getId() == 7){
				DataSourceSwitch.set("yth_dba");
				datumService.updateState(businessCode, "1");
			}
			DataSourceSwitch.set(null);
			datumService.updateState(businessCode, "1");
		}

		activitiService.activitiProcedure(business.getMatterId(),businessCode); //启动工作流
		session.invalidate();
		map.put("code", 1);
		map.put("message", "业务办理成功");
		map.put("state", "success");
		return JSONObject.fromObject(map);
	}

	@PostMapping("/repeatFinish")
    @ResponseBody
    public PageResult repeatFinish(@RequestBody String params){
	    Map<String, Object> maps = JsonKit.toMap(params);
	    String businessCode = maps.get("businessCode").toString();
	    businessService.updateBusinessState(businessCode,"1");
	    String lackMaterials = "[]";
	    approverService.updateLackMaterials(businessCode,lackMaterials);
	    fileService.updateFileAgent(businessCode,1);
	    PageResult pageResult = activitiService.activitiActivate(businessCode);
	    pageResult.setData(JsonKit.toJsonStr(maps));
	    return pageResult;
    }
}
