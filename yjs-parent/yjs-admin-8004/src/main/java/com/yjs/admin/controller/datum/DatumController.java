package com.yjs.admin.controller.datum;

import com.github.pagehelper.PageInfo;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.yjs.bean.datum.Datum;
import com.yjs.bean.dto.PageResult;
import com.yjs.bean.utils.Result;
import com.yjs.bean.utils.StatusCode;
import com.yjs.bean.vo.TableData;
import com.yjs.dao.mapper.IDatumMapper;
import com.yjs.service.activiti.ActivitiService;
import com.yjs.service.agent.AgentService;
import com.yjs.service.applicat.ApplicatService;
import com.yjs.service.business.BusinessService;
import com.yjs.service.datum.service.DatumService;
import com.yjs.service.item.ItemsService;
import com.yjs.service.receipt.ReceiptService;
import com.yjs.service.situation.SituationService;
import com.yjs.utils.BulidUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/datum")
public class DatumController {

    @Autowired
    DatumService datumService;

    @Autowired
    ReceiptService receiptService;

    @Autowired
    BusinessService businessService;

    @Autowired
    ApplicatService applicatService;

    @Autowired
    AgentService agentService;

    @Autowired
    SituationService situationService;

    @Autowired
    ActivitiService activitiService;

    @Autowired
    ItemsService itemsService;

    @Autowired
    FastFileStorageClient storageClient;

    @Autowired
    IDatumMapper datumMapper;
    /**
     * 点击待办任务时跳转到待办任务页面
     * @param model
     * @return
     */
    @RequestMapping("/backlog")
    public String toIndex(Model model) {
        return "datum/lists";
    }

    /**
     * 点击已办任务时跳转到已办任务页面
     * @param model
     * @return
     */
    @RequestMapping("/haveDone")
    public String haveDone(Model model) {
        return "datum/haveDone";
    }

    /**
     * 加载待办任务数据表格及分页
     * @param request
     * @param pageNum
     * @param pageSize
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("/getBacklog")
    public TableData toDatumList(HttpServletRequest request,
                                 @RequestParam(name = "page", required = false, defaultValue = "1")
                                         int pageNum,
                                 @RequestParam(name = "limit", required = false, defaultValue = "10")
                                         int pageSize,
                                 HttpSession session) {
        String s = request.getParameter("searchField");
        Map<String, String> searchMap = new HashMap<>();
        if (s!=null) {
            if (StringUtils.isNotBlank(s)) {
                BulidUtils.buildSearchField(s, searchMap);
            }
        }
        Map<String,Object> user = (Map<String, Object>) session.getAttribute("loginTime");//从session中获取当前登录用户的部门及用户名称
        String userOrg = (String) user.get("user_org");//获取当前的部门
        String items_id = null;//事项id，用来查找所属部门
        String assignee = null;//部门id
        if (userOrg.equals("东莞市市监局")){
            items_id = "1";
            assignee = "1";
        }else if (userOrg.equals("东莞市公安局")) {
            items_id = "2,5,10";
            assignee = "7";
        }else if (userOrg.equals("东莞市消防局")){
            items_id = "3";
            assignee = "4";
        }else if (userOrg.equals("东莞市城管执法局")){
            items_id = "4";
            assignee = "3";
        }else if (userOrg.equals("东莞市人力资源和社会保障局")) {
            items_id = "11";
            assignee = "6";
        }else if (userOrg.equals("东莞市环保局")){
            items_id = "6";
            assignee = "2";
        }else{
            TableData td = new TableData("0", "", (long) 0, null);
            return td;
        }
        PageInfo<Datum> list = datumService.queryHelperPageInfo(pageNum, pageSize, searchMap, "create_at desc", items_id,assignee);//数据表格及分页的方法，用事项id用来获取属于当前部门的所有待办事项
        TableData td = new TableData("0", "", list.getTotal(), list.getList());
        return td;
    }

    /**
     * 加载已办任务数据表格及分页
     * @param request 搜索框
     * @param pageNum
     * @param pageSize
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("/getHaveDone")
    public TableData haveDone(HttpServletRequest request,
                              @RequestParam(name = "page", required = false, defaultValue = "1")
                                      int pageNum,
                              @RequestParam(name = "limit", required = false, defaultValue = "10")
                                      int pageSize,
                              HttpSession session) {
        String s = request.getParameter("searchField");
        Map<String, String> searchMap = new HashMap<>();
        if (s!=null) {
            if (StringUtils.isNotBlank(s)) {
                BulidUtils.buildSearchField(s, searchMap);
            }
        }
        Map<String,Object> user = (Map<String, Object>) session.getAttribute("loginTime");//从session中获取当前登录用户的部门及用户名称
        String userOrg = (String) user.get("user_org");//获取当前的部门
        String items_id = null;//事项id，用来查找所属部门
        String assignee = null;//部门id
        if (userOrg.equals("东莞市市监局")){
            items_id = "1";
            assignee = "1";
        }else if (userOrg.equals("东莞市公安局")) {
            items_id = "2,5,10";
            assignee = "7";
        }else if (userOrg.equals("东莞市消防局")){
            items_id = "3";
            assignee = "4";
        }else if (userOrg.equals("东莞市城管执法局")){
            items_id = "4";
            assignee = "3";
        }else if (userOrg.equals("东莞市人力资源和社会保障局")) {
            items_id = "11";
            assignee = "6";
        }else if (userOrg.equals("东莞市环保局")){
            items_id = "6";
            assignee = "2";
        }else{
            TableData td = new TableData("0", "", (long) 0, null);
            return td;
        }
        PageInfo<Datum> list = datumService.queryHelperPageInfo2(pageNum, pageSize, searchMap, "create_at desc", items_id,assignee);//数据表格及分页的方法，用事项id用来获取属于当前部门的所有已办事项
        TableData td = new TableData("0", "", list.getTotal(), list.getList());
        return td;
    }

    /**
     * 待办任务点击办理=====>
     * 待办任务页面点击办理时跳到这个方法先存储流水号和事项id
     * 前端传来的流水编号和事项id先存到session中，便于findBybusinessCode调用
     * findDatumInformation()与findBybusinessCode()同步运行，findDatumInformation()比findBybusinessCode()先调用
     * @param business_code 流水号
     * @param items_id  事项id
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("/findDatumInformation")
    public Result findDatumInformation(@RequestParam(value = "businessCode") String business_code,
                                       @RequestParam(value = "itemsId") Integer items_id,
                                       HttpSession session) {
        session.setAttribute("businessCode", business_code);
        session.setAttribute("itemsId", items_id);
        return new Result(true, StatusCode.OK, "查询成功");
    }

    /**
     *  待办任务页面点击办理时取出需要用的值
     *  从session中取出findDatumInformation()的流水编号和事项id,用流水号和事项id查找详细表单信息
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("/findBybusinessCode")
    public Result findBybusinessCode(HttpSession session) {
        String businessCode = (String) session.getAttribute("businessCode");
        Integer itemsId = (Integer) session.getAttribute("itemsId");
        Map<String,Object> user = (Map<String, Object>) session.getAttribute("loginTime");//从session中获取当前登录用户的部门及用户名称
        Map<String, Object> map = datumService.findDatumInformation(businessCode, itemsId);
        String userName = (String) user.get("user_name");//获得部门审批人
        map.put("userName",userName);
        if (map == null){
            return new Result(true,1, "查询失败");
        }
        return new Result(true,1, "查询成功",map);
    }

    /**
     *  待办任务页面点击办理时取出需要用的值
     *  从session中取出findDatumInformation()的流水编号和事项id,用流水号和事项id查找详细表单信息
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("/activitiTaskNext")
    public Result activitiTaskNext(HttpSession session) {
        String businessCode = (String) session.getAttribute("businessCode");
        PageResult pageResult = activitiService.activitiTaskNext(businessCode);
        return new Result(true,1, "查询成功",pageResult);
    }

    /**
     * 已办任务点击查看======>
     * 已办任务页面点击办理时跳到这个方法先存储流水号和事项id
     * 前端传来的流水编号和事项id先存到session中，便于findDealt调用
     * findCheck()findDealt()同步运行，findCheck()findDealt()先调用
     * @param business_code 流水号
     * @param items_id  事项id
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("/findCheck")
    public Result findCheck(@RequestParam(value = "businessCode") String business_code,
                            @RequestParam(value = "itemsId") Integer items_id,
                            HttpSession session) {
        session.setAttribute("businessCode", business_code);
        session.setAttribute("itemsId", items_id);
        return new Result(true, StatusCode.OK, "查询成功");
    }

    /**
     * 已办任务页面点击办理时取出需要用的值
     * 从session中取出findCheck()的流水编号和事项id,用流水号和事项id查找详细表单信息
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("/findDealt")
    public Result findDealt(HttpSession session) {
        String businessCode = (String) session.getAttribute("businessCode");
        Integer itemsId = (Integer) session.getAttribute("itemsId");
        Map<String, Object> map = datumService.findDatumInformation2(businessCode,itemsId);
        if (map == null){
            return new Result(true,1, "查询失败");
        }
        return new Result(true,1, "查询成功",map);
    }

    //根据流水号查询详细表单信息
    @ResponseBody
    @RequestMapping("/findFileByDatum")
    public String findFileByDatum(HttpSession session) {
        String businessCode = (String) session.getAttribute("businessCode");
        String business = datumService.findBusiness(businessCode);
        if (business.equals("ff8080816dd7ef4a016ded9734320011")) {
            return "/toContent";
        } else if (business.equals("ff8080816cb6dcad016cb72e02a104c6")) {
            return "/toContent2";
        }
        return "/toContent3";
    }


    @ResponseBody
    @RequestMapping("/findByBusinessResultFile")
    public Result findByBusinessResultFile(HttpSession session) {
        /**
         * 根据fileid查询file
         */
        Integer fileId = (Integer) session.getAttribute("fileId");
        return new Result(1, "查询成功", datumService.findFileById(fileId));
    }

    @ResponseBody
    @RequestMapping("/findFileByDatumDetails")
    public  Result findFileByDatumDetails(HttpSession session){
        /**
         * 根据流水号和事项id查询表单
         */
        String detumBusinessCode = (String) session.getAttribute("detumBusinessCode");
        Integer detumItemsId = (Integer) session.getAttribute("itemsId");
        Datum allByBusinessCodeAndItemsId = datumService.findAllByBusinessCodeAndItemsId(detumBusinessCode, detumItemsId);
        return  new Result(1,"查询成功",allByBusinessCodeAndItemsId);
    }

    /**
     * 审批接口
     * @param enclosureFile 上传文件
     * @param business_code 流水号
     * @param state         状态
     * @param userName      审核人
     * @param actName       当前部门
     * @param remark        意见
     * @param session
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/Pass")
    public Map<String,Object> Pass(@RequestParam (name = "file",required = false) MultipartFile enclosureFile,//上传文件
                        @RequestParam ("business_code") String business_code,//流水号
                        @RequestParam ("state") String state,//状态
                        @RequestParam ("userName") String userName,//审核人
                        @RequestParam ("actName") String actName,//当前部门
                        @RequestParam (name = "remark",required = false) String remark,//意见
                        @RequestParam (name = "correction",required = false) String correction,//材料补正原因及id
                        HttpSession session) throws Exception {
        //审批部门，流水号，状态，审批人，意见，需要重新上传的文件id,附件
        session.setAttribute("business_code",business_code);
        session.setAttribute("state",state);
        int state2 = Integer.parseInt(state);//将状态转换为int类型
        String assignee = null; //部门id
        Map<String,Object> loginTime = (Map<String, Object>) session.getAttribute("loginTime");//从session中取出当前登录用户的所有信息
        Integer deptId = (Integer) loginTime.get("deptId");//从session中取出部门id
        assignee = deptId.toString();//部门id
        String substring = correction.substring(14);//对correction进行切割,去掉前14位
        String incise = substring.substring(0, substring.length() - 1);//对correction进行切割,去掉最后一位值
        String enclosureName = null;//上传附件
        String fileUrl = null;//附件url地址
        if(!org.springframework.util.StringUtils.isEmpty(enclosureFile)){
            enclosureName = enclosureFile.getOriginalFilename();//文件名
            String suffix = enclosureName.substring(enclosureName.lastIndexOf(".") + 1);//截取文件后缀名
            fileUrl = storageClient.uploadFile(enclosureFile.getInputStream(), enclosureFile.getSize(), suffix, null).getFullPath();//输入流，文件大小，文件后缀名，返回的全文件名
        }
        PageResult pageResult = activitiService.activitiProceed(assignee, business_code, state2, userName, remark, incise,enclosureName,fileUrl);

        Map<String,Object> map2 = new HashMap<>();
        map2.put("pageResult",pageResult);
        return map2;
    }

    /**
     * 审批接口
     * @param session
     */
    @ResponseBody
    @RequestMapping("/activitiProceeds")
    public PageResult activitiProceeds(HttpSession session){
        String businessCode = (String) session.getAttribute("business_code");
        String sessionid = session.getAttribute("state").toString();
        int state = Integer.valueOf(sessionid);
        String assignee = null; //部门id
        Map<String,Object> loginTime = (Map<String, Object>) session.getAttribute("loginTime");//从session中取出当前登录用户的所有信息
        Integer deptId = (Integer) loginTime.get("deptId");//从session中取出部门id
        assignee = deptId.toString();//把部门id转为string类型
        PageResult pageResult = activitiService.activitiProceeds(assignee, businessCode, state);
        int code = pageResult.getCode();
        if (code == 1 && state == 2 && assignee.equals("6")){//当返回值为1,状态为2,部门id为6时修改下一部门的id
            int i = datumMapper.updateDeptState(businessCode);
        }
        return pageResult;
    }

    @RequestMapping("/findTalents")
    @ResponseBody
    public Result findTalents(HttpSession session){
        Integer itemsId = (Integer) session.getAttribute("itemsId");
        String talentsCode = (String) session.getAttribute("detumBusinessCode");
        return new Result(1,"查询成功",datumService.findByItemsIdAndBusinessCode(talentsCode, itemsId));
    }

    @RequestMapping("/findBasics")
    @ResponseBody
    public Result findBasics(HttpSession session){
        Integer itemsId = (Integer) session.getAttribute("itemsId");
        String talentsCode = (String) session.getAttribute("detumBusinessCode");
        return new Result(1,"查询成功",datumService.findByItemsIdAndBusinessCode(talentsCode, itemsId));
    }

    /**
     * 重新激活挂起的任务
     * @param business_code 流水号
     */
    @ResponseBody
    @RequestMapping("/activate")
    public PageResult activate(@RequestParam ("businessCode") String business_code){
        PageResult pageResult = activitiService.activitiActivate(business_code);
        return pageResult;
    }

    /**
     * pdf表单
     * @param business_code
     * @param itemsId
     * @param response
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("/application.pdf")
    public void createPdf(@RequestParam ("businessCode") String business_code,
                          @RequestParam ("itemsId") Integer itemsId,
                          HttpServletResponse response) throws Exception {
        //查找pdf表单
        datumService.findContent(business_code,itemsId,response);
    }

}
