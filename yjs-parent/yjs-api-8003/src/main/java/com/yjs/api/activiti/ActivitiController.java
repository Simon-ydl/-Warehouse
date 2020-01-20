package com.yjs.api.activiti;

import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.yjs.bean.dto.Correction;
import com.yjs.bean.dto.PageResult;
import com.yjs.service.activiti.ActivitiService;
import com.yjs.utils.JsonKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author summer
 */

@RestController
@RequestMapping("/activiti")
public class ActivitiController {

    @Autowired
    ActivitiService activitiService;

    @Autowired
    private FastFileStorageClient storageClient;

    PageResult pageResult = new PageResult();

    /**
     * 根据主题编号和业务流水号 启动流程实例
     *
     * @param matterId：主题编号
     * @param businessCode：业务流水号
     * @return
     */
    @PostMapping("/activitiProcedure")
    public Object activitiProcedure(@RequestParam("matterId") Integer matterId, @RequestParam("businessCode") String businessCode) {
        pageResult = activitiService.activitiProcedure(matterId, businessCode);
        return pageResult;
    }

    /**
     * 根据部门编号查询所需审核的数据
     *
     * @param assignee：审批的部门编号
     * @return
     */
    @PostMapping("/activitiTaskQuery")
    public Object activitiTaskQuery(@RequestParam("assignee") String assignee) {
        pageResult = activitiService.activitiTaskQuery(assignee);
        return pageResult;
    }

    /**
     * 完成审核 或者 将任务挂起
     *
     * @param assignee：审批的部门编号
     * @param businessCode：业务流水号
     * @param status：2是完成审核；3是将任务挂起
     * @param opinion:              受理人
     * @param list:                 审批附件
     * @return
     */
    @PostMapping("/activitiProceed")
    public Object activitiProceed(
            @RequestParam("assignee") String assignee,
            @RequestParam("businessCode") String businessCode,
            @RequestParam("status") int status,
            @RequestParam("uname") String uname,
            @RequestParam("opinion") String opinion,
            @RequestParam(name = "list", required = false, defaultValue = "[]") String list,
            @RequestParam(name = "enclosureFile", required = false) MultipartFile enclosureFile
    ) throws Exception {
        String enclosureName = null;
        String fileUrl = null;
        if (enclosureFile != null) {
            enclosureName = enclosureFile.getOriginalFilename();
            String suffix = enclosureName.substring(enclosureName.lastIndexOf(".") + 1);
            fileUrl = storageClient.uploadFile(enclosureFile.getInputStream(), enclosureFile.getSize(), suffix, null).getFullPath();
        }
        pageResult = activitiService.activitiProceed(assignee, businessCode, status, uname, opinion, list, enclosureName, fileUrl);
        pageResult = activitiService.activitiProceeds(assignee, businessCode, status);
        return pageResult;
    }

    /**
     * 根据业务流水号将挂起的任务重新激活
     *
     * @param businessCode：业务流水号
     * @return
     */
    @PostMapping("/activitiActivate")
    public Object activitiActivate(@RequestParam("businessCode") String businessCode) {
        pageResult = activitiService.activitiActivate(businessCode);
        return pageResult;
    }

    /**
     * 根据业务流水号查看历史审核记录
     *
     * @param businessCode：业务流水号
     * @return
     */
    @PostMapping("/activitiHistoryQuery")
    public Object activitiHistoryQuery(@RequestParam("businessCode") String businessCode) {
        pageResult = activitiService.activitiHistoryQuery(businessCode);
        return pageResult;
    }

    /**
     * 根据业务流水号获取当前任务节点及下一步节点
     *
     * @param businessCode: 业务流水号
     * @return
     */
    @PostMapping("/activitiTaskNext")
    public Object activitiTaskNext(@RequestParam("businessCode") String businessCode) {
        pageResult = activitiService.activitiTaskNext(businessCode);
        return pageResult;
    }

}