package com.yjs.api.test;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.yjs.bean.dto.*;
import com.yjs.bean.enclosure.Enclosure;
import com.yjs.bean.oracle.OraApprover;
import com.yjs.dao.mapper.item.IIBusinessMapper;
import com.yjs.dao.mapper.item.IIDatumMapper;
import com.yjs.dao.mapper.item.OraApproverMapper;
import com.yjs.dataSource.DataSource;
import com.yjs.dataSource.DataSourceSwitch;
import com.yjs.service.activiti.ActivitiService;
import com.yjs.service.item.ItemService;
import com.yjs.utils.DesUtil;
import com.yjs.utils.FileBase64ConvertUitl;
import com.yjs.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemTest {
    @Autowired
    private ItemService itemService;

    @Autowired
    private FastFileStorageClient storageClient;

    @Autowired
    private ActivitiService activitiService;

    @Test
    public void texs(){
        PageResult pageResult = activitiService.activitiTaskQuery("6");
        System.err.println(pageResult);
    }


    @Test
    public void test(){
        PageResult pageResult = itemService.UpdateItemStatusByItemId("11", "2", "201912171239066418", "6", "郑勇", "审批通过", null,"","");
        System.out.println(pageResult);
    }

    @Test
    public void test1(){
        PageResult pageResult = itemService.getUntreatedItemsByDeptmentId("6");
        System.out.println(pageResult);
    }

    @Test
    public void testBase64Update() throws IOException {
        //文件的base64编码
        String base64File = "MTIzNDU2NTU1NTU1NTU1NTU1NTU1NTU1NTU1NTU1NTU1NTU1NTU1NTU1NTU1NTU1NTU1NTU1NTU1NTU1NbLiytTOxLz+yc+0q2FkDQo=";
        //文件名
        String fileName = "测试文件上传2.doc";
        //获取文件的后缀名
        String fileExtName = fileName.substring(fileName.lastIndexOf(".") + 1);
        //将base64编码的字符串转成MultipartFile
        MultipartFile file = FileBase64ConvertUitl.base64ToMultipartFile(fileName,base64File);
        //利用fastDFS java客户端上传文件
        StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(), fileExtName, null);
        //获取上传之后的返回的全路径
        System.out.println(storePath.getFullPath());
    }

    @Test
    public void testGetFilePath(){
        //C:\Users\Administrator\Desktop\yjs_dg\yjs-parent\yjs-api-8003
        System.out.println(System.getProperty("user.dir"));
    }

    @Test
    public void testgetSuffixPath(){
        String fullPath = "group1/M00/00/01/CpMCql4NRHKAV2zaAAB-xsKFFes181.jpg";
        String s = StringUtils.substring(fullPath, fullPath.indexOf('/')+1, fullPath.length());
        String s1 = StringUtils.substring(s,  s.indexOf('/'), s.length());
        System.out.println("/root/yjs/fdfs/storage/data"+  s1);
    }
    @Autowired
    private IIDatumMapper datumMapper;
    @Test
    public void tsetGetByDepartmentIdAndBusinessCode(){
        OneDate data = datumMapper.findItemInfoByDepartmentIdAndBusinessCode("20200103157801228200021", 6);
        System.out.println(data);
    }
    @Test
    public void testGetItemId(){
        Integer itemId = datumMapper.findItemByDepartmentAndBusinessCnode("20200103157801228200021", 6);
        System.out.println(itemId);
    }

    @Test
    public void test6(){
        List<Correction> list = new ArrayList<>();
        Correction c = new Correction();
        c.setCorrectionInfo("拍的太丑了");
        c.setId("112");
        list.add(c);
        PageResult pageResult = itemService.UpdateItemStatusByItemId("10", "2", "20200103157801228200021", "10",
                "古正勇", "不通过", JsonUtils.serialize(list), null, null);
        System.out.println(pageResult);
    }
   @Test
    public void test7() throws Exception {
        String data ="";
       String s = DesUtil.decrypt(data, "rensheju20191225");
       System.out.println(s);
   }
   @Test
    public void test8(){
       String fileSuffixPath = itemService.getFileSuffixPath("group1/M00/00/00/E2gzUF4VP0yAdrtsAAO6Uydqm1M258.jpg");
       System.out.println(fileSuffixPath);
   }

   @Test
    public void test9(){
       PageResult pageResult = itemService.getItemFileByFileId("877");
       Material data = (Material)pageResult.getData();
       System.out.println(data);

   }
   @Test
   public void test10() throws Exception {
       String s = FileBase64ConvertUitl.encodeBase64File("D:/upload/21.jpg");
       System.out.println(s);
   }
   @Test
   public void test11() throws Exception {
        String data = "";
        FileBase64ConvertUitl.decoderBase64File(data,"D:/upload/13.jpg");

   }
   @Test
   public void test12(){
       PageResult pageResult = itemService.updateActivingItemStatusByBusinessCode("2020010715321078");
       System.out.println(pageResult);
   }
   @Test
    public void test13(){
       Enclosure e  = new Enclosure();
       e.setEnclosureName("别BB3.png");e.setEnclosureUrl("group1/M00/00/05/E2gzUF4eqJuAZkgeAABX37aBX_A095.png");
       Integer integer = itemService.updateEnclosure(e);
       System.out.println(e.getId());
   }
   private OraApproverMapper oraApproverMapper;
    @Test
    public void test14(){
        DataSourceSwitch.set("ga_dba");
        List<OraApprover> list = oraApproverMapper.findAllByStatus();
        System.out.println(list.size());
        DataSourceSwitch.set(null);
    }

    @Test
    public void test15(){
        List<OneDate> untreatedItemsByDeptmentId = datumMapper.findUntreatedItemsByDeptmentId(6);
        for (OneDate oneDate : untreatedItemsByDeptmentId) {
            System.out.println(oneDate);
        }
    }
    @Test
    public void test16(){
        OneDate info = datumMapper.findItemInfoByDepartmentIdAndBusinessCode("2020011011150482", 6);
        System.out.println(info);
    }
    @Autowired
    private IIBusinessMapper businessMapper;
    @Test
    public void test17(){
        List<DtoWorkFlow> list = businessMapper.getWorkFlowOrderByBusinessCode("2020010819534997");
        for (DtoWorkFlow dtoWorkFlow : list) {
            System.out.println(dtoWorkFlow);
        }
    }
    @Test
    public void test18(){
        PageResult pageResult = itemService.getWorkFlowOrderByBusinessCode("2020010819534997");
        WorkflowData workflowData = (WorkflowData) pageResult.getData();
        List<DtoWorkFlow> workFlows = workflowData.getWorkFlows();
        for (DtoWorkFlow workFlow : workFlows) {
            System.out.println(workFlow);
        }
    }


}
