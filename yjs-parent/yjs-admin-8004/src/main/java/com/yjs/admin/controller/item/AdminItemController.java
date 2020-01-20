package com.yjs.admin.controller.item;

import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.yjs.bean.approver.Approver;
import com.yjs.bean.enclosure.Enclosure;
import com.yjs.bean.oracle.OraApprover;
import com.yjs.dao.mapper.item.OraApproverMapper;
import com.yjs.dataSource.DataSourceSwitch;
import com.yjs.service.item.ItemService;
import com.yjs.utils.FileBase64ConvertUitl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@EnableScheduling
public class AdminItemController {

    @Autowired
    private ItemService itemService;
    @Autowired
    private FastFileStorageClient storageClient;
    @Autowired
    private OraApproverMapper approverMapper;

    /**
     * 定时去一体化平台获取更新一件事数据库
     * @return void
     */
    //项目启动5分钟开始执行定时任务,每隔10分钟执行一次
    @Scheduled(initialDelay = 1000 * 60 * 5, fixedDelay = 1000 * 60 * 10)//定时任务设置启动多久执行
    public ResponseEntity<Void> updateItemStatus() throws Exception {
        log.info("定时去oracle数据库获取数据更新数据任务启动了" + LocalDateTime.now());
        List<Approver> approvers = new ArrayList<>();
        DataSourceSwitch.set("ga_dba");
        List<OraApprover> list = approverMapper.findAllByStatus();
        DataSourceSwitch.set(null);
        if(CollectionUtils.isEmpty(list)){
            log.info("没有要更新的事项！");
            return null;
        }
        DataSourceSwitch.set("ga_dba");
        //更新外表的状态
        List<OraApprover> collect = list.stream().map(oraApprover -> {
            oraApprover.setStatus("0");
            return oraApprover;
        }).collect(Collectors.toList());
        approverMapper.updateOraApprover(collect);
        DataSourceSwitch.set(null);
        //如果为为空，则获取要更新数据,更新一件事的数据库的数据
        for (OraApprover approver : list) {
            Approver app = new Approver();
            app.setBusinessCode(approver.getBusinessCode());
            app.setDepartmentId(approver.getDepartmentId());
            app.setItemId(approver.getItemId());
            app.setLackMaterials(approver.getLackMaterials());
            app.setCreateTime(approver.getCreateTime());
            app.setApproveState(approver.getApproveState());
            app.setApprover(approver.getApprover());
            app.setApproveOpinion(approver.getApproveOpinion());
            if(!StringUtils.isEmpty(approver.getEnclosureName()) && !StringUtils.isEmpty(approver.getEnclosureContent())){
                //如果不为空，上传文件
                String fileExt = itemService.getFileExtNameNoPoint(approver.getEnclosureName());
                InputStream in = FileBase64ConvertUitl.base64ToInputStream(approver.getEnclosureContent());
                MultipartFile mfile = FileBase64ConvertUitl.base64ToMultipartFile(approver.getEnclosureName(), approver.getEnclosureContent());
                String fileUrl = storageClient.uploadFile(in,mfile.getSize(), fileExt, null).getFullPath();
                //设置
                Enclosure enclosure = new Enclosure();
                enclosure.setEnclosureUrl(fileUrl);
                enclosure.setEnclosureName(approver.getEnclosureName());
                app.setEnclosureId(itemService.updateEnclosure(enclosure));
            }
            //添加到审批人列表中
            approvers.add(app);
        }
        //更新或修改审批人表的数据
        itemService.updateApproverAndDatum(approvers);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
