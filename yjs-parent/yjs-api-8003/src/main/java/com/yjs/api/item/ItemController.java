package com.yjs.api.item;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.yjs.bean.dto.EncryptionPageResult;
import com.yjs.bean.dto.PageResult;
import com.yjs.service.item.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;


/**
 * 获取数据一件事接口
 */
@RestController
@Slf4j
public class ItemController {

    @Autowired
    private ItemService itemService;
    @Autowired
    private FastFileStorageClient storageClient;


    /**
     * 根据部门id查询该部门下未处理的事项列表
     * @param departmentId 部门id查询
     * @return EncryptionPageResult加密后返回的页面数据( code:状态码 msg:成功或失败的信息 data:加密返回的数据 )
     */
    @PostMapping("/getUntreatedItemsByDeptmentId")
    public ResponseEntity<EncryptionPageResult> getUntreatedItemsByDeptmentId(
            @RequestParam("departmentId") String departmentId){

        //未加密的页面返回数据
        PageResult pageResult = itemService.getUntreatedItemsByDeptmentId(departmentId);
        //对返回的数据进行加密并返回
        return ResponseEntity.ok(itemService.returnPageRsultEncryption(pageResult));
    }


    /**
     * 根据事项id数组去事项表更新对应事项的状态
     * @param itemId 事项id
     * @param itemStatus 事项的状态
     * @return EncryptionPageResult加密后返回的页面数据( code:状态码 msg:成功或失败的信息 data:加密返回的数据 )
     */
    @PostMapping("/UpdateItemStatusByItemId")
    public ResponseEntity<EncryptionPageResult> UpdateItemStatusByItemId(
            @RequestParam("itemId") String itemId,
            @RequestParam("itemStatus") String itemStatus,
            @RequestParam("departmentId") String departmentId,
            @RequestParam("approverName") String approverName,
            @RequestParam("approveOpinion") String approverOpinion,
            @RequestParam("businessCode") String businessCode,
            @RequestParam(value = "lackMaterials",required = false,defaultValue = "[]") String  lackMaterials,//需要重新上传的材料id数组
            @RequestParam(value = "approveEnclosure",required = false) MultipartFile approveEnclosure
    )  {

        String fileUrl = null;//上传的附件url
        String approveName = null;
        if(approveEnclosure != null){
            try {
                //将附件上传到FastDFS文件服务器
                String fileExtName = approveEnclosure.getOriginalFilename().substring(approveEnclosure.getOriginalFilename().lastIndexOf(".") + 1);
                approveName = approveEnclosure.getOriginalFilename();
                InputStream in  = approveEnclosure.getInputStream();
                fileUrl = storageClient.uploadFile(in,approveEnclosure.getSize(), fileExtName, null).getFullPath();
            } catch (IOException e) {
                //获取输入流失败
                log.error("获取后输入流失败！");
            }
        }


        //未加密的页面返回数据
        PageResult pageResult = itemService.UpdateItemStatusByItemId(itemId, itemStatus,businessCode,departmentId,
                approverName,approverOpinion,lackMaterials,approveName,fileUrl);
        //对返回的数据进行加密并返回
        return ResponseEntity.ok(itemService.returnPageRsultEncryption(pageResult));
    }


    /**
     * 根据业务编号查询主题的事项办理顺序
     * @param businessCode
     * @return EncryptionPageResult加密后返回的页面数据( code:状态码 msg:成功或失败的信息 data:加密返回的数据 )
     */
    @PostMapping("/getWorkFlowOrderByBusinessCode")
    public ResponseEntity<EncryptionPageResult> getWorkFlowOrderByBusinessCode(@RequestParam("businessCode") String businessCode){
        //未加密的页面返回数据
        PageResult pageResult = itemService.getWorkFlowOrderByBusinessCode(businessCode);
        //对返回的数据进行加密并返回
        return ResponseEntity.ok(itemService.returnPageRsultEncryption(pageResult));
    }



    /**
     * 根据业务编号和事项id查询部门下对应流水号的事项状态
     * @param businessCode 业务编码
     * @param itemId 事项id
     * @return EncryptionPageResult加密后返回的页面数据( code:状态码 msg:成功或失败的信息 data:加密返回的数据 )
     */
    @PostMapping("/getItemStatusByBusinessCodeAndItemId")
    public ResponseEntity<EncryptionPageResult> getItemStatusByBusinessCodeAndItemId(
            @RequestParam("businessCode") String businessCode,
            @RequestParam("itemId") Integer itemId
    ){
        //未加密的页面返回数据
        PageResult pageResult = itemService.getItemStatusByBusinessCodeAndItemId(itemId,businessCode);
        //对返回的数据进行加密并返回
        return ResponseEntity.ok(itemService.returnPageRsultEncryption(pageResult));
    }


	/**
     * 根据业务流水号查询该业务流水号所有审批历史
     * @param businessCode 业务流水号
     * @return EncryptionPageResult加密后返回的页面数据( code:状态码 msg:成功或失败的信息 data:加密返回的数据 )
     */
    @PostMapping("/getApproveHistoryByBusinessCode")
    public ResponseEntity<EncryptionPageResult> getApproveHistoryByBusinessCode(@RequestParam("businessCode") String businessCode){
        //未加密的页面返回数据
        PageResult pageResult = itemService.getApproveHistoryByBusinessCode(businessCode);
        //对返回的数据进行加密并返回
        return ResponseEntity.ok(itemService.returnPageRsultEncryption(pageResult));
    }


    /**
     * 根据业务流水号查询该业务流水号所有审批历史
     * @param businessCode 业务流水号
     * @return EncryptionPageResult加密后返回的页面数据( code:状态码 msg:成功或失败的信息 data:加密返回的数据 )
     */
    @PostMapping("/updateActivingItemStatusByBusinessCode")
    public ResponseEntity<EncryptionPageResult> updateActivingItemStatusByBusinessCode(@RequestParam("businessCode") String businessCode){
        //未加密的页面返回数据
        PageResult pageResult = itemService.updateActivingItemStatusByBusinessCode(businessCode);
        //对返回的数据进行加密并返回
        return ResponseEntity.ok(itemService.returnPageRsultEncryption(pageResult));

    }

    /**
     * 根据部门id和业务编号查询确定事项的相关材料
     * @param departmentId 部门id
     * @param businessCode 业务编号
     * @return EncryptionPageResult加密后返回的页面数据( code:状态码 msg:成功或失败的信息 data:加密返回的数据 )
     */
    @PostMapping("/getItemInfoByDeptmentIdAndbusinessCode")
    public ResponseEntity<EncryptionPageResult> getItemInfoByDeptmentIdAndbusinessCode(
            @RequestParam("departmentId") Integer departmentId,
            @RequestParam("businessCode") String businessCode
    ){
        //未加密的页面返回数据
       PageResult pageResult= itemService.getItemInfoByDeptmentIdAndbusinessCode(departmentId, businessCode);
        //对返回的数据进行加密并返回
        return ResponseEntity.ok(itemService.returnPageRsultEncryption(pageResult));
    }

    //根据材料id获取材料的内容
    @PostMapping("/getItemFileByFileId")
    public ResponseEntity<PageResult> getItemFileByFileId(@RequestParam("fileId") String fileId){
        PageResult pageResult = itemService.getItemFileByFileId(fileId);
        return ResponseEntity.ok(pageResult);
    }

}
