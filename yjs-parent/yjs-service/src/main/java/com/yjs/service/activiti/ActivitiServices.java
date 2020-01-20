package com.yjs.service.activiti;

import com.yjs.bean.dto.*;
import com.yjs.service.business.BusinessService;
import com.yjs.service.item.ItemService;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.impl.util.CollectionUtil;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author summer
 */
@Service
public class ActivitiServices {

    private static ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    private static RepositoryService repositoryService = processEngine.getRepositoryService();

    private static RuntimeService runtimeService = processEngine.getRuntimeService();

    private static TaskService taskService = processEngine.getTaskService();

    private static HistoryService historyService = processEngine.getHistoryService();

    private static PageResult pageResult = new PageResult();

    @Resource
    private ItemService itemService;

    @Resource
    private BusinessService businessService;

    /**
     * 部署流程定义 根据流水号 启动流程实例
     *
     * @param uid 流水号
     */
    public PageResult activitiProcedure(String uid) {

        String key = "talentsFamily";
        ProcessDefinition processDefinition = repositoryService
                .createProcessDefinitionQuery().processDefinitionKey(key).singleResult();
        //查询工作流是否已经创建
        if (processDefinition == null) {
            Deployment deployment = repositoryService.createDeployment()
                    .addClasspathResource("diagram/talentsFamily/talentsFamily.bpmn")
                    .addClasspathResource("diagram/talentsFamily/talentsFamily.png")
                    .name("人才入户流程")
                    .deploy();
        }
        PageResult pageResult = itemService.getWorkFlowOrderByBusinessCode(uid);
        if (pageResult == null) {
            pageResult.setCode(0);
            pageResult.setMsg("失败");
            pageResult.setData(null);
            return pageResult;
        }
        WorkflowData w = (WorkflowData) pageResult.getData();
        List<DtoWorkFlow> workFlows = w.getWorkFlows();
        List<Integer> departmentIds = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        if (!CollectionUtil.isEmpty(workFlows)) {
            for (DtoWorkFlow workFlow : workFlows) {
                departmentIds.add(workFlow.getDepartmentId());
            }
        }
        if (!CollectionUtil.isEmpty(departmentIds)) {
            map.put("assigneeOne", departmentIds.get(0));
            map.put("assigneeTwo", departmentIds.get(1));
        }
        //设置流程实例参数（key：流程定义名称  uid：审批流水号  map：流程变量的值）
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(key, uid, map);
        if (processInstance != null) {
            pageResult.setCode(1);
            pageResult.setMsg("成功");
            pageResult.setData(null);
        }
        return pageResult;
    }

    /**
     * 根据部门查询所需审核的数据
     *
     * @param assignee 审批部门ID
     */
    public PageResult activitiTaskQuery(String assignee) {

        List<Task> taskList = taskService.createTaskQuery()
                .processDefinitionKey("talentsFamily")
                .taskAssignee(assignee)
                .list();
        if (CollectionUtils.isEmpty(taskList)) {
            pageResult.setCode(0);
            pageResult.setMsg("失败");
            pageResult.setData(null);
            return pageResult;
        }
        //任务列表的展示
        //System.err.println("需要受理的任务：");
        List<PageResult> pageResultList = new ArrayList<>();
        for (Task task : taskList) {
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
            //查询没有被挂起的任务
            boolean suspended = processInstance.isSuspended();
            if (!suspended) {
                PageResult pageResults = itemService.getItemInfoByDeptmentIdAndbusinessCode(Integer.valueOf(task.getAssignee()),processInstance.getBusinessKey());
                if(pageResults.getCode() == 0){
                    pageResult.setCode(0);
                    pageResult.setMsg("失败");
                    pageResult.setData(null);
                    return pageResult;
                }
                /*pageResultList.add(pageResults);
                List<PageOneData> pageOneDatas =  (List<PageOneData>)pageResult.getData();
                if(!StringUtils.isEmpty(pageOneDatas)){
                    for (PageOneData pageOneData : pageOneDatas) {
                        System.err.println("经办人：" + pageOneData.getAgent().toString());
                        System.err.println("申请人：" + pageOneData.getApplicant());
                        System.err.println("部门ID：" + pageOneData.getDepartmentId());
                        System.err.println("材料："  +pageOneData.getMaterials().toString());
                    }
                }
                System.err.println("流程实例ID：" + task.getProcessInstanceId());
                System.err.println("任务ID：" + task.getId());
                System.err.println("任务负责人：" + task.getAssignee());
                System.err.println("任务名称：" + task.getName());
                System.err.println("审批流水号：" + processInstance.getBusinessKey());
                System.err.println("------------------------------------------------");*/
            }
        }
        pageResult.setCode(1);
        pageResult.setMsg("成功");
        pageResult.setData(null);
        return pageResult;
    }

    /**
     * 操作流程任务
     *
     * @param assignee      审批部门ID
     * @param uid           流水号
     * @param status        状态 2：成功 3：失败
     * @param uname         受理人
     * @param opinion       备注
     * @param list          需要重新提交的材料ID
     * @param enclosureName 附件名
     * @param fileUrl       附件地址
     * @throws Exception
     */
    public PageResult activitiProceed(String assignee, String uid, int status, String uname, @Nullable String opinion, @Nullable String list, @Nullable String enclosureName, @Nullable String fileUrl) {

        //根据 任务受理人跟流水号ID 获取任务ID 完成对应的任务
        Task task = taskService.createTaskQuery()
                .taskAssignee(assignee)
                .processInstanceBusinessKey(uid).singleResult();
        if (task == null) {
            pageResult.setCode(0);
            pageResult.setMsg("失败");
            pageResult.setData(null);
            return pageResult;
        }
        PageResult pageResult = itemService.getItemInfoByDeptmentIdAndbusinessCode(Integer.valueOf(assignee), uid);
        List<PageOneData> pageOneDataList = (List<PageOneData>) pageResult.getData();
        PageOneData pageOneData = pageOneDataList.get(0);
        String itenId = pageOneData.getItemId();
        if (status == 2) {
                /*
                完成当前节点任务
                 */
            taskService.complete(task.getId());
               /* //发送短信
                Agent agent = pageOneData.getAgent();
                String name = agent.getAName(); //接收人名
                String content = "尊敬的"+name+"先生/女士: 您好! 你所申报的事项 【人才入户（本科生）】申报流水号为："+uid+"，【人社局人才入户部分】已经办理完成，请携带身份证原件、户口本（原件或复印件）到东莞市民服务中心公安办事大厅窗口现场办理【公安局人才入户部分】。";  //消息内容
                String tel = agent.getAMobile();;   //接收人手机号
                String type = "1";  //发送方式
                ApiAccess.StringApipost("sendMsg","content="+content+"&name="+name+"&tel="+tel+"&type="+type);*/
                /*
                 更改当前任务节点状态
                 */
            itemService.UpdateItemStatusByItemId(itenId, "2", uid, assignee, uname, opinion, list, enclosureName, fileUrl);
            //System.err.println("任务完成成功！" + assignee + "--------" + uid);
        } else if (status == 3) {
            //根据流程实例ID：task.getProcessInstanceId() 查询流程定义的对象
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
            //将任务进行挂起
            runtimeService.suspendProcessInstanceById(processInstance.getId());
                 /*
                更改当前任务节点状态
                */
            itemService.UpdateItemStatusByItemId(itenId, "3", uid, assignee, uname, opinion, list, enclosureName, fileUrl);
               /*
                更改主流程状态
                */
            //businessService.updateBusinessState(uid, "3");
            //System.err.println("流程定义：" + processInstance.getId() + "挂起");
        }
        /*
          更改主流程状态
        */
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(uid).singleResult();
        if(processInstance == null){
            businessService.updateBusinessState(uid, "2");
        }
        pageResult.setCode(1);
        pageResult.setMsg("成功");
        pageResult.setData(null);
        return pageResult;
    }

    /**
     * 根据流水号将挂起的任务重新激活
     *
     * @param uid 流水号
     */
    public PageResult activitiActivate(String uid) {

        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(uid).singleResult();
        if (processInstance == null) {
            pageResult.setCode(0);
            pageResult.setMsg("失败");
            pageResult.setData(null);
            return pageResult;
        }
        boolean suspended = processInstance.isSuspended();
        if (suspended) {
            //将任务进行激活
            runtimeService.activateProcessInstanceById(processInstance.getId());
            //System.err.println("流程定义：" + processInstance.getId() + "激活");
             /*
               更改当前任务节点状态
             */
            itemService.updateActivingItemStatusByBusinessCode(uid);
            /*
              更改主流程状态
            */
            //businessService.updateBusinessState(uid, "1");
            pageResult.setCode(1);
            pageResult.setMsg("成功");
            pageResult.setData(null);
        }
        return pageResult;
    }

    /**
     * 根据流水号查看历史审核记录
     *
     * @param uid 流水号
     */
    public PageResult activitiHistoryQuery(String uid) {

        //HistoricActivityInstanceQuery对象
        HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(uid).singleResult();
        if (processInstance == null) {
            pageResult.setCode(0);
            pageResult.setMsg("失败");
            pageResult.setData(null);
            return pageResult;
        }
        historicActivityInstanceQuery.processInstanceId(processInstance.getId());  //设置流程实例的ID
        //执行查询
        List<HistoricActivityInstance> historicActivityInstanceList = historicActivityInstanceQuery
                .orderByHistoricActivityInstanceStartTime()  //根据时间排序
                .asc()
                .list();
        historicActivityInstanceList.remove(0);
     /*   //遍历输出结果
        for (HistoricActivityInstance historicActivityInstance : historicActivityInstanceList){
            System.err.println("流程ID："+historicActivityInstance.getActivityId());
            System.err.println("流程名称："+historicActivityInstance.getActivityName());
            System.err.println("流程实例ID："+historicActivityInstance.getProcessInstanceId());
            System.err.println("流程审核时间："+historicActivityInstance.getEndTime());
            System.err.println("流程部署ID："+historicActivityInstance.getProcessDefinitionId());
            System.err.println("***********************************************************");
        }*/
        pageResult.setCode(1);
        pageResult.setMsg("成功");
        pageResult.setData(historicActivityInstanceList);
        return pageResult;
    }

    /**
     * 根据流水号查出当前流程 以及下一步流程
     *
     * @param uid 流水号ID
     */
    public PageResult activitiTaskNext(String uid) {

        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(uid).singleResult();
        if (processInstance == null) {
            pageResult.setCode(0);
            pageResult.setMsg("失败");
            pageResult.setData(null);
            return pageResult;
        }
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        PageResult pageResult = itemService.getWorkFlowOrderByBusinessCode(uid);
        WorkflowData w = (WorkflowData) pageResult.getData();
        List<DtoWorkFlow> workFlows = w.getWorkFlows();
        String actName = task.getName();
        Map<String, Object> map = new HashMap<>();
        map.put("actName", actName);
        if (!CollectionUtil.isEmpty(workFlows)) {
            int i = 0;
            for (DtoWorkFlow workFlow : workFlows) {
                if (workFlow.getActName().equals(actName)) {
                    i = workFlow.getOrder() + 1;
                }
                if (workFlow.getOrder() == i) {
                    map.put("nextName", workFlow.getActName());
                }
            }
        }
        pageResult.setCode(1);
        pageResult.setMsg("成功");
        pageResult.setData(map);
        return pageResult;
    }

}