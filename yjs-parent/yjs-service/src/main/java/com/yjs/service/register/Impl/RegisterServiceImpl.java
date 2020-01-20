package com.yjs.service.register.Impl;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.yjs.bean.Receipt.Receipt;
import com.yjs.bean.Situation.Situation;
import com.yjs.bean.agent.Agent;
import com.yjs.bean.application.Applicat;
import com.yjs.bean.business.Business;
import com.yjs.bean.file.Files;
import com.yjs.bean.inspection.Inspection;
import com.yjs.bean.item.Items;
import com.yjs.bean.materials.Materials;
import com.yjs.bean.matter.Matter;
import com.yjs.bean.tops.Tops;
import com.yjs.dao.mapper.FileMapper;
import com.yjs.dao.mapper.item.IIFileMapper;
import com.yjs.dao.mapper.item.IIItemMapper;
import com.yjs.dao.mapper.item.IIMaterialsMapper;
import com.yjs.service.agent.AgentService;
import com.yjs.service.applicat.ApplicatService;
import com.yjs.service.business.BusinessService;
import com.yjs.service.datum.service.DatumService;
import com.yjs.service.inspection.Service.InspectionService;
import com.yjs.service.item.ItemsService;
import com.yjs.service.matter.service.MatterService;
import com.yjs.service.receipt.ReceiptService;
import com.yjs.service.register.IRegisterService;
import com.yjs.service.situation.SituationService;
import com.yjs.service.topsService.TopsService;
import com.yjs.utils.IdWorker;
import com.yjs.utils.JsonKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class RegisterServiceImpl implements IRegisterService {

    @Autowired
    DatumService datumService;

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
    IIFileMapper fileMapper;

    @Autowired
    IIItemMapper itemMapper;

    @Autowired
    InspectionService inspectionService;

    @Autowired
    private FastFileStorageClient storageClient;

    @Autowired
    IIMaterialsMapper iMaterialsMapper;





    @Override
    @Transactional(rollbackFor = Exception.class)
    public String registerFirst(Map<String, String> param, HttpServletRequest request) {
        try {
            /*生成一个业务ID*/
            IdWorker id = new IdWorker();
            String businessCode = id.nextId()+"";

            /*第一步保存情形表*/
            Situation situation = new Situation();
            situation.setSiName(param.get("siName"));
            situation.setSiContent(param.get("siContent"));
            situation.setBusinessCode(businessCode);
            Situation situations = situationService.findAllByBusinessCode(businessCode);
            if (null == situations){
                situationService.save(situation);   //保存情形信息
            }else {
                situationService.update(situation);   //更新情形信息
            }

            /*保存前置条件数据——————*/
            Tops tops = new Tops();
            tops.setContent(param.get("content"));
            tops.setBusinessCode(businessCode);
            Tops topses = topsService.findAllByBusinessCode(businessCode);
            if(null == topses){
                topsService.save(tops);
            }else{
                topsService.update(tops);
            }

            /*保存经办人信息*/
            Agent agent = new Agent();
            agent.setANumberType(param.get("aNumberType"));
            agent.setAName(param.get("aName"));
            agent.setANumber(param.get("aNumber"));
            agent.setBusinessCode(businessCode);
            if(param.containsKey("aMobile")) {
                agent.setAMobile(param.get("aMobile"));
            }
            if(param.containsKey("aEmail")) {
                agent.setAEmail(param.get("aEmail"));
            }
            agentService.save(agent);	//保存经办人信息

            /*保存申请人信息*/
            Applicat applicat = new Applicat();
            applicat.setApName(param.get("apName"));
            applicat.setApNumberType(param.get("apNumberType"));
            applicat.setApNumber(param.get("apNumber"));
            applicat.setBusinessCode(businessCode);
            Applicat applicats = applicatService.findAllByBusinessCode(businessCode);
            if (null == applicats){
                applicatService.save(applicat);	//保存申请人信息
            }else {
                applicatService.update(applicat);   //更新申请人信息
            }


            /*保存接收方式---*/
            Receipt receipt = new Receipt();
            if(param.containsKey("rName")) {
                receipt.setRName(param.get("rName"));
            }
            if(param.containsKey("rWay")) {
                receipt.setRWay(param.get("rWay"));
            }
            if(param.containsKey("rMobile")) {
                receipt.setRMobile(param.get("rMobile"));
            }
            if(param.containsKey("rArea")) {
                receipt.setRArea(param.get("rArea"));
            }
            if(param.containsKey("rAddress")) {
                receipt.setRAddress(param.get("rAddress"));
            }
            if(param.containsKey("rHall")) {
                receipt.setRHall(param.get("rHall"));
            }
            receipt.setBusinessCode(businessCode);
            receiptService.save(receipt);	//保存结果接收方式


            if(param.containsKey("inContent")){
                Inspection inspection = new Inspection();
                inspection.setInContent(param.get("inContent").toString());
                inspection.setBusinessCode(businessCode);
                Inspection inspections = inspectionService.findAllByBusinessCode(businessCode);
                if(inspections == null){
                    inspectionService.save(inspection);  //保存所需上传材料的信息
                }else{
                    inspectionService.update(inspection);   //更新所需上传材料的信息
                }
            }


            //保存选择情形时，得到的材料信息（现场登记用到的逻辑处理）

            Materials m = new Materials();
            m.setBusinessCode(businessCode);
            m.setMaterial(param.get("materialId"));
            m.setMaterialInItem(param.get("materialItemId"));
            iMaterialsMapper.insert(m);


            Business business = new Business();
            business.setBusinessCode(businessCode);
            business.setCreateAt(new Date());
            String useridcode = param.get("useridcode");
            business.setUserIdCode(useridcode);
            String matterCode = param.get("matterCode");
            Matter matter = matterService.findAllBymatterCode(matterCode);
            business.setMatterId(matter.getId());
            business.setState("0");
            if(param.containsKey("businessTag")){
                business.setBusinessTag(param.get("businessTag"));
            }
            Business businesses = businessService.findAllByBusinessCode(businessCode);

            if (null == businesses){

                businessService.save(business);	//保存业务信息

            }


            return businessCode;
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取文件列表-----
     * @param pageNum
     * @param pageSize
     * @param materialCode
     * @param orderBy
     * @return
     */
    @Override
    public PageInfo<Files> queryMaterialFile(int pageNum, int pageSize, String materialCode,String businessCode, String orderBy) {

        PageHelper.startPage(pageNum, pageSize,null);

        Example example = new Example(Files.class);
        Example.Criteria criteria = example.createCriteria();

        criteria.andEqualTo("businessCode",businessCode);

        List<String> fileCode= new ArrayList<>();
        if(null != materialCode){

            String[] arr = materialCode.split(",");

            if(null != arr && arr.length>0){
                for(int i=0;i<arr.length;i++){
                    fileCode.add(arr[i]);
                }
            }
        }
        if(null != fileCode && fileCode.size()>0){

            criteria.andIn("fileCode",fileCode);
        }else{

            return new PageInfo<Files>(new ArrayList<Files>()) ;
        }

        return new PageInfo<Files>(fileMapper.selectByExample(example)) ;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveItem(JSONArray itemList){
        try{
            if(null != itemList && itemList.size()>0){
    //                List<String> itemCode = new ArrayList<>();
                for(int i=0;i<itemList.size();i++){

                    Items item =  itemsService.findByItemCode(itemList.getJSONObject(i).getString("itemId"));
                    System.out.print("11111111111111"+item);
                    if(null == item){
                        item = new Items();
                        item.setDepartmentId(0);
                        item.setItemCode(itemList.getJSONObject(i).getString("itemId"));
                        item.setItemName(itemList.getJSONObject(i).getString("taskName"));

                        itemsService.saveItem(item);

                    }
                    System.out.print("222222");

                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Items> queryItemsByCode(List<String> itemCode){

        Example example = new Example(Items.class);
        Example.Criteria criteria = example.createCriteria();

        criteria.andIn("itemCode",itemCode);

        return itemMapper.selectByExample(example);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteMaterial(String materialCode,String businessCode) {
        try{
            Example example = new Example(Files.class);
            Example.Criteria criteria = example.createCriteria();

            List<String> fileCode= new ArrayList<>();
            if(null != materialCode){

                String[] arr = materialCode.split(",");

                if(null != arr && arr.length>0){
                    for(int i=0;i<arr.length;i++){
                        fileCode.add(arr[i]);
                    }
                }
            }

            if(null != fileCode && fileCode.size()>0) {

                criteria.andIn("id", fileCode);
            }

            criteria.andEqualTo("businessCode",businessCode);

            List<Files> files = fileMapper.selectByExample(example);

            int size = 0;
            if(null != files && files.size()>0){

                for (Files f : files){

                    StorePath storePath = StorePath.parseFromUrl(f.getFileUrl());

                    storageClient.deleteFile(storePath.getGroup(), storePath.getPath());

                }

                size = fileMapper.deleteByExample(example);

            }else{
                return 0;
            }

            return size;
        }catch (Exception e){

            e.printStackTrace();
        }


        return 0;
    }


}
