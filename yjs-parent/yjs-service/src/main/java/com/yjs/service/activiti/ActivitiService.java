package com.yjs.service.activiti;

import com.yjs.bean.dto.DtoWorkFlow;
import com.yjs.bean.dto.PageResult;
import com.yjs.bean.dto.WorkflowData;
import com.yjs.dao.mapper.ActivitisMapper;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author summer
 */
@Service
public class ActivitiService {

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

    @Autowired
    private ActivitisMapper activitiMapper;

    /**
     * 部署流程定义 根据主题编号和业务流水号 启动流程实例
     *
     * @param matterId     主题编号
     * @param businessCode 业务流水号
     */
    public PageResult activitiProcedure(Integer matterId, String businessCode) {
        Map<String, Object> map = new HashMap<>();
        String key = null;
        //人才入库的主题事项
        if (matterId == 1) {
            key = "talentsFlowDiagram";
            ProcessDefinition processDefinition = repositoryService
                    .createProcessDefinitionQuery().processDefinitionKey(key).singleResult();
            //查询工作流是否已经创建
            if (processDefinition == null) {
                Deployment deployment = repositoryService.createDeployment()
                        .addClasspathResource("diagram/talents/talentsFlowDiagram.bpmn")
                        .addClasspathResource("diagram/talents/talentsFlowDiagram.png")
                        .name("人才入户流程")
                        .deploy();
            }
            PageResult pageResult = itemService.getWorkFlowOrderByBusinessCode(businessCode);
            if (pageResult == null) {
                pageResult.setCode(0);
                pageResult.setMsg("失败");
                pageResult.setData(null);
                return pageResult;
            }
            WorkflowData w = (WorkflowData) pageResult.getData();
            List<DtoWorkFlow> workFlows = w.getWorkFlows();
            List<Integer> departmentIds = new ArrayList<>();
            if (!CollectionUtil.isEmpty(workFlows)) {
                for (DtoWorkFlow workFlow : workFlows) {
                    departmentIds.add(workFlow.getDepartmentId());
                }
            }
            if (!CollectionUtil.isEmpty(departmentIds)) {
                map.put("assigneeOne", departmentIds.get(0));
                map.put("assigneeTwo", departmentIds.get(1));
            }
            //餐馆开办的主题事项
        } else if (matterId == 3) {
            key = "restaurantFlowDiagram";
            ProcessDefinition processDefinition = repositoryService
                    .createProcessDefinitionQuery().processDefinitionKey(key).singleResult();
            //查询工作流是否已经创建
            if (processDefinition == null) {
                Deployment deployment = repositoryService.createDeployment()
                        .addClasspathResource("diagram/restaurant/restaurantFlowDiagram.bpmn")
                        .addClasspathResource("diagram/restaurant/restaurantFlowDiagram.png")
                        .name("小餐馆开办流程")
                        .deploy();
            }
            PageResult pageResult = itemService.getWorkFlowOrderByBusinessCode(businessCode);
            if (pageResult == null) {
                pageResult.setCode(0);
                pageResult.setMsg("失败");
                pageResult.setData(null);
                return pageResult;
            }
            WorkflowData w = (WorkflowData) pageResult.getData();
            List<DtoWorkFlow> workFlows = w.getWorkFlows();
            List<Integer> departmentIds = new ArrayList<>();
            if (!CollectionUtil.isEmpty(workFlows)) {
                for (DtoWorkFlow workFlow : workFlows) {
                    departmentIds.add(workFlow.getDepartmentId());
                }
            }
            if (!CollectionUtil.isEmpty(departmentIds)) {
                map.put("assigneeOne", departmentIds.get(0));
                map.put("assigneeTwo", departmentIds.get(1));
                map.put("assigneeThree", departmentIds.get(2));
                map.put("assigneeFour", departmentIds.get(3));
                map.put("assigneeFive", departmentIds.get(4));
            }
        }
        //设置流程实例参数（key：流程定义名称  businessCode：审批流水号  map：流程变量的值）
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(key, businessCode, map);
        if (processInstance != null) {
            pageResult.setCode(1);
            pageResult.setMsg("成功");
            pageResult.setData(null);
        }
        return pageResult;
    }

    /**
     * 根据部门编号查询所需审核的数据
     *
     * @param assignee 审批的部门编号
     */
    public PageResult activitiTaskQuery(String assignee) {

        List<String> list = activitiMapper.findAssigneeBySuspension(assignee);
        if (CollectionUtil.isEmpty(list)) {
            pageResult.setCode(0);
            pageResult.setMsg("失败");
            pageResult.setData(null);
            return pageResult;
        }
       /* List<Task> taskList = taskService.createTaskQuery()
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
        List<String> list = new ArrayList<>();
        for (Task task : taskList) {
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
            boolean suspended = processInstance.isSuspended();
            if (!suspended) {
                list.add(processInstance.getBusinessKey());
            }
        }*/
        pageResult.setCode(1);
        pageResult.setMsg("成功");
        pageResult.setData(list);
        return pageResult;
    }

    /**
     * 操作流程任务
     * 对内部审核表进行操作
     *
     * @param assignee      审批的部门编号
     * @param businessCode  业务流水号
     * @param status        状态 2：成功 3：失败
     * @param uname         受理人
     * @param opinion       备注
     * @param list          需要重新提交的材料ID
     * @param enclosureName 附件名
     * @param fileUrl       附件地址
     * @throws Exception
     */
    public PageResult activitiProceed(String assignee, String businessCode, int status, String uname, @Nullable String opinion, @Nullable String list, @Nullable String enclosureName, @Nullable String fileUrl) {

        //根据 任务受理人跟流水号ID 获取任务ID 完成对应的任务
    /*    Task task = taskService.createTaskQuery()
                .taskAssignee(assignee)
                .processInstanceBusinessKey(businessCode).singleResult();
        if (task == null) {
            pageResult.setCode(0);
            pageResult.setMsg("失败");
            pageResult.setData(null);
            return pageResult;
        }*/
        PageResult pageResult = itemService.getItemIdByDeptmentIdAndbusinessCode(businessCode, Integer.valueOf(assignee));
        Integer itemId = (Integer) pageResult.getData();
        if (status == 2) {
            /*
            完成当前节点任务
             */
            //taskService.complete(task.getId());
            //发送短信
            /*
            Agent agent = pageOneData.getAgent();
            String name = agent.getAName(); //接收人名
            String content = "尊敬的"+name+"先生/女士: 您好! 你所申报的事项 【人才入户（本科生）】申报流水号为："+businessCode+"，【人社局人才入户部分】已经办理完成，请携带身份证原件、户口本（原件或复印件）到东莞市民服务中心公安办事大厅窗口现场办理【公安局人才入户部分】。";  //消息内容
            String tel = agent.getAMobile();;   //接收人手机号
            String type = "1";  //发送方式
            ApiAccess.StringApipost("sendMsg","content="+content+"&name="+name+"&tel="+tel+"&type="+type);*/
            /*
             更改当前任务节点状态
             */
            itemService.UpdateItemStatusByItemId(String.valueOf(itemId), "2", businessCode, assignee, uname, opinion, list, enclosureName, fileUrl);
        } else if (status == 3) {
            //根据流程实例ID：task.getProcessInstanceId() 查询流程定义的对象
         /*   ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
            //将任务进行挂起
            runtimeService.suspendProcessInstanceById(processInstance.getId());*/
             /*
              更改当前任务节点状态
             */
            itemService.UpdateItemStatusByItemId(String.valueOf(itemId), "3", businessCode, assignee, uname, opinion, list, enclosureName, fileUrl);
            /**
             *  更改主流程状态
             */
            businessService.updateBusinessState(businessCode, "3");
        }
        /*      *//*
          更改主流程状态
        *//*
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(businessCode).singleResult();
        if (processInstance == null) {
            businessService.updateBusinessState(businessCode, "2");
        }*/
        pageResult.setCode(1);
        pageResult.setMsg("成功");
        pageResult.setData(null);
        return pageResult;
    }

    /**
     * 操作流程任务
     * 对工作流进行操作
     *
     * @param assignee     审批的部门编号
     * @param businessCode 业务流水号
     * @param status       状态 2：成功 3：失败
     * @throws Exception
     */
    public PageResult activitiProceeds(String assignee, String businessCode, int status) {

        //根据 任务受理人跟流水号ID 获取任务ID 完成对应的任务
        Task task = taskService.createTaskQuery()
                .taskAssignee(assignee)
                .processInstanceBusinessKey(businessCode).singleResult();
        if (task == null) {
            pageResult.setCode(0);
            pageResult.setMsg("失败");
            pageResult.setData(null);
            return pageResult;
        }
        if (status == 2) {
                /*
                完成当前节点任务
                 */
            taskService.complete(task.getId());
        } else if (status == 3) {
            //根据流程实例ID：task.getProcessInstanceId() 查询流程定义的对象
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
            //将任务进行挂起
            runtimeService.suspendProcessInstanceById(processInstance.getId());
        }
            /*
              更改主流程状态
            */
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(businessCode).singleResult();
        if (processInstance == null) {
            businessService.updateBusinessState(businessCode, "2");
        }
        pageResult.setCode(1);
        pageResult.setMsg("成功");
        pageResult.setData(null);
        return pageResult;
    }

    /**
     * 根据业务流水号将挂起的任务重新激活
     *
     * @param businessCode 业务流水号
     */
    public PageResult activitiActivate(String businessCode) {

        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(businessCode).singleResult();
        if (processInstance == null) {
            pageResult.setCode(0);
            pageResult.setMsg("失败");
            pageResult.setData(null);
            return pageResult;
        }
        //将任务进行激活
        runtimeService.activateProcessInstanceById(processInstance.getId());
        //System.err.println("流程定义：" + processInstance.getId() + "激活");
             /*
               更改当前任务节点状态
             */
        itemService.updateActivingItemStatusByBusinessCode(businessCode);
            /*
              更改主流程状态
            */
        businessService.updateBusinessState(businessCode, "1");
        pageResult.setCode(1);
        pageResult.setMsg("成功");
        pageResult.setData(null);
        return pageResult;
    }

    /**
     * 根据业务流水号查看历史审核记录
     *
     * @param businessCode 业务流水号
     */
    public PageResult activitiHistoryQuery(String businessCode) {

        //HistoricActivityInstanceQuery对象
        HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(businessCode).singleResult();
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
     * 根据业务流水号查出当前流程 以及下一步流程
     *
     * @param businessCode 业务流水号
     */
    public PageResult activitiTaskNext(String businessCode) {

        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(businessCode).singleResult();
        if (processInstance == null) {
            pageResult.setCode(0);
            pageResult.setMsg("失败");
            pageResult.setData(null);
            return pageResult;
        }
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        PageResult pageResult = itemService.getWorkFlowOrderByBusinessCode(businessCode);
        WorkflowData w = (WorkflowData) pageResult.getData();
        List<DtoWorkFlow> workFlows = w.getWorkFlows();
        String actName = task.getName();
        Map<String, Object> map = new HashMap<>();
        List<String> list = new ArrayList<>();
        map.put("actName", actName);

        if (!CollectionUtil.isEmpty(workFlows)) {
            int i = 0;
            for (DtoWorkFlow workFlow : workFlows) {
                if (workFlow.getActName().equals(actName)) {
                    map.put("approveStep", workFlow.getApproveStep());
                    i = workFlow.getOrder() + 1;
                }
                if (workFlow.getOrder() == i) {
                    list.add(workFlow.getActName());
                }
            }
        }
        map.put("nextName", list);
        pageResult.setCode(1);
        pageResult.setMsg("成功");
        pageResult.setData(map);
        return pageResult;
    }

}