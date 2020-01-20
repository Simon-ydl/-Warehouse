package com.yjs.service.item;


import com.yjs.bean.agent.Agent;
import com.yjs.bean.application.Applicat;
import com.yjs.bean.approver.Approver;
import com.yjs.bean.business.Business;
import com.yjs.bean.datum.Datum;
import com.yjs.bean.department.Department;
import com.yjs.bean.dto.*;
import com.yjs.bean.enclosure.Enclosure;
import com.yjs.bean.file.Files;
import com.yjs.bean.item.Items;
import com.yjs.bean.matter.Matter;
import com.yjs.bean.oracle.OraApprover;
import com.yjs.bean.workflow.Workflow;
import com.yjs.dao.mapper.item.*;
import com.yjs.dataSource.DataSourceSwitch;
import com.yjs.service.activiti.ActivitiService;
import com.yjs.utils.DesUtil;
import com.yjs.utils.FileBase64ConvertUitl;
import com.yjs.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 事项处理和材料获取的服务类
 * @author huangsifen
 *
 */

@Service
@Slf4j
public class ItemService {

    private static String policyPwd = "rensheju20191225";//数据加密的密码
    private static final String urlPrefix = "http://19.104.51.80/";//返回前端的url前缀
    private static final String pathPrefix = "/home/yjs/yjs/fdfs/storage/data";//返回前端的url前缀
    private static final  String fileNameParam = "?attname=";
    private static final List<String> pictureTypes = Arrays.asList("bmp", "jpg","jpeg","png","gif");
    @Autowired
    private IIApplicationMapper applicationMapper;
    @Autowired
    private IIDatumMapper datumMapper;
    @Autowired
    private IIItemMapper itemmapper;
    @Autowired
    private IIFileMapper fileMapper;
    @Autowired
    private IIAgentMapper agentMapper;
    @Autowired
    private IIBusinessMapper businessMapper;
    @Autowired
    private IIWorkflowMapper workflowMapper;
    @Autowired
    private IIDepartmentMapper departmentMapper;
    @Autowired
    private IIMatterMapper matterMapper;
    @Autowired
    private IIApproverMapper approverMapper;
    @Autowired
    private IIEnclosureMapper enclosureMapper;
    @Autowired
    private OraApproverMapper oraApproverMapper;
    @Autowired
    private ActivitiService activitiService;




    /**
     * 根据部门id查询该部门下未处理的事项列表
     * @param _departmentId 部门id
     * @return
     */
    public PageResult getUntreatedItemsByDeptmentId(String _departmentId) {

        //返回的页面对象
        PageResult<List> pageResult = new PageResult<List>();
        //返回事项数据的材料数组,如果该部门下没有要处理的事项，则返回一个空的数组
        //页面返回的数据列表 1)有则填充数据，没有则返回一个空数组
        List<PageOneData> pageDatas = new ArrayList<>();
        //判断输入参数
        if(StringUtils.isEmpty(_departmentId)){
            pageResult.setCode(0);
            pageResult.setData(pageDatas);
            pageResult.setMsg("输入参数有误!");
            return pageResult;
        }

        //转换传入的参数
        Integer departmentId = Integer.valueOf(_departmentId.trim());
        List<OneDate> datas = datumMapper.findUntreatedItemsByDeptmentId(departmentId);

        if(CollectionUtils.isEmpty(datas)){
            //如果items为空,则返回一个空的数据数组
            pageResult.setCode(0);
            pageResult.setData(pageDatas);
            pageResult.setMsg("您的部门没有要处理的待办件!");
            return pageResult;
        }

        //如果不为空，则遍历数据得到返回的数据
        for (OneDate data : datas) {
            //创建单条页面返回数据
            PageOneData pageOneData = new PageOneData();
            List<DtoFile> dtoMaterials = new ArrayList<>();
            //返回页面填的申请人对象
            DtoApplication dtoApplication = new DtoApplication();
            //返回页面填充的申请人表单数据对象
            FormData formData =  new FormData();
            //返回页面填充的经办人表单数据对象
            Agent a  = new Agent();


            //设置返回的值
            pageOneData.setMatterId(String.valueOf(data.getMatterId()));
            pageOneData.setDepartmentId(String.valueOf(data.getDepartmentId()));
            pageOneData.setItemId(String.valueOf(data.getItemsId()));
            pageOneData.setBusinessCode(data.getBusinessCode());

            a.setId(data.getId());a.setAName(data.getAName());a.setAEmail(data.getAEmail());
            a.setANumberType(data.getANumberType());a.setANumber(data.getApNumber());
            a.setAMobile(data.getAMobile());a.setBusinessCode(data.getBusinessCode());

            dtoApplication.setApplicationName(data.getAName());
            dtoApplication.setApplicationNummber(data.getApNumber());

            formData.setContent(data.getContent());

            Files file = new Files();//数据库材料实体
            file.setItemId(data.getItemsId());//设置itemId
            file.setBusinessCode(data.getBusinessCode());//设置业务流水号
            List<Files> files = fileMapper.select(file);
            if(!CollectionUtils.isEmpty(files)){
                //填充返回页面填的事项材料列表数据
                for (Files _file : files) {
                    //设置返回的材料id和名称
                    DtoFile dtoFile = new DtoFile();
                    dtoFile.setFileId(String.valueOf(_file.getId()));
                    String fileExt = getFileExtName(_file.getOriginalFileName());
                    dtoFile.setFileName(_file.getFileName() + fileExt);
                    String fileExtNoPoint = getFileExtNameNoPoint(_file.getOriginalFileName());
                    //如果为图片
                    if(pictureTypes.contains(fileExtNoPoint.toLowerCase())){
                        dtoFile.setFileUrl(urlPrefix + _file.getFileUrl());
                    }else {
                        dtoFile.setFileUrl(urlPrefix + _file.getFileUrl() + fileNameParam + _file.getFileName() + fileExt);
                    }
                    dtoMaterials.add(dtoFile);//向页面材料列表添加数据
                }
            }

            pageOneData.setApplicant(dtoApplication);
            pageOneData.setAgent(a);
            pageOneData.setFormData(formData);
            pageOneData.setMaterials(dtoMaterials);
            pageDatas.add(pageOneData);
        }

        //填充页面返回的数据
        pageResult.setMsg("获取数据成功");
        pageResult.setData(pageDatas);

        pageResult.setCode(1);
        return pageResult;
    }


    /**
     * 在部门处理完事项之后，调用此接口修改事项的状态(修改单个事项)
     * 根据事项id和事项状态修改(单个更新) 状态: 1审核中，2已审核，3拒绝
     * @param _itemId 事项id
     * @param itemStatus 事项状态
     * @param businessCode 业务编号
     * @param _departmentId 部门id
     * @param approverName 审批人名称
     * @Param approveOpinion 审批意见
     * @Param lackMaterials 需要重新上传上传材料的名称
     * @Param enclosureName 附件名称
     * @Param fileUrl 附件的url
     */
    @Transactional
    public PageResult UpdateItemStatusByItemId(String _itemId,String itemStatus,String businessCode,
                                               String _departmentId, String approverName,
                                               String approveOpinion,@Nullable String  lackMaterials,
                                               @Nullable String enclosureName,@Nullable String fileUrl){


        //请求页面返回实体
        PageResult<UpdateStatusData> pageResult = new PageResult<UpdateStatusData>();
        //更新数据成功/失败的信息
        UpdateStatusData statusData = new UpdateStatusData();
        if(lackMaterials == null){
            //如果为空，则空数组
            lackMaterials = "[]";
        }
        if(_itemId == null  || businessCode == null ){
            //如果失败
            statusData.setMsg("部门处理后申请人表单信息表更新状态失败,传入的事项id不能为空或业务编号不能为空");
            statusData.setCode(0);
            //填充返回页面
            pageResult.setCode(0);
            pageResult.setData(statusData);
            pageResult.setMsg("更新状态失败,传入的事项id不能为空");
            return pageResult;
        }

        //转换转入的参数
        Integer itemId = Integer.valueOf(_itemId.trim());

        //判断部门id是否为空，如果是则返回失败信息，部门id不能为空
        if(_departmentId == null){
            //如果失败
            statusData.setItemId(String.valueOf(itemId));
            statusData.setMsg("办件id为："+itemId+",部门处理后申请人表单信息表更新状态失败,传入的部门id不能为空");
            statusData.setCode(0);
            //填充返回页面
            pageResult.setCode(0);
            pageResult.setData(statusData);
            pageResult.setMsg("更新状态失败,传入的部门id不能为空");
            return pageResult;
        }

        //转换转入的参数
        Integer departmentId = Integer.valueOf(_departmentId.trim());
        Enclosure enclosure = null;

        //判断附件是否为空,如果为空则证明没有附件,不为空证明有数据
        if(!StringUtils.isEmpty(enclosureName) && !StringUtils.isEmpty(fileUrl)){
            //向附件表插入
            Enclosure e = new Enclosure();
            e.setEnclosureName(enclosureName);e.setEnclosureUrl(fileUrl);
            //将附件上传到FastDFS文件服务器
            int insert = enclosureMapper.insert(e);
            if(insert == 1){
                Enclosure _enclosure = enclosureMapper.selectOne(e);
                enclosure = _enclosure;
            }
        }

        //根据事项id和业务流水号去审批人表查询数据，如果有更新，如果没有则插入新数据
        Approver a = new Approver();a.setItemId(itemId);a.setBusinessCode(businessCode);a.setDepartmentId(departmentId);
        List<Approver> approverList = approverMapper.select(a);
        if(CollectionUtils.isEmpty(approverList)){
            //如果为空，则证明数据库中不存在该条数据，插入一条新的数据
            //如果不为空则存储审批人信息
            Approver _approver = new Approver();
            //设置需要需要上传的材料的id
            _approver.setLackMaterials(lackMaterials);
            //设置审批意见
            _approver.setApproveOpinion(approveOpinion);
            //设置审批人
            _approver.setApprover(approverName);
            //如果不为空,则设置审批人部门所在的部门id
            _approver.setDepartmentId(departmentId);
            //设置审批的事项id
            _approver.setItemId(itemId);
            //设置审批时间
            _approver.setCreateTime(new Date());
            //设置审批表的业务流水号
            _approver.setBusinessCode(businessCode);
            //设置审批状态
            _approver.setApproveState(itemStatus);
            //设置审批附件
            if(enclosure != null && enclosure.getId()!= null){
                _approver.setEnclosureId(enclosure.getId());
            }



            //向数据库存中存储审批人信息
            int insert = approverMapper.insert(_approver);
            if(insert != 1){
                //向数据库中存储数据失败
                statusData.setItemId(String.valueOf(itemId));
                statusData.setMsg("办件id为："+itemId+",部门处理后事项表更新状态失败,向审批人表插入数据失败!");
                statusData.setCode(0);

                //填充返回页面
                pageResult.setCode(0);
                pageResult.setData(statusData);
                pageResult.setMsg("更新状态失败,向审批人表插入数据失败");
                return pageResult;
            }
        }

        if(!CollectionUtils.isEmpty(approverList)){
            //如果不为空，则更新审批人表中的数据
            Approver approver = approverList.stream().findFirst().get();
            //设置更正材料
            approver.setLackMaterials(lackMaterials);
            //设置审批意见
            approver.setApproveOpinion(approveOpinion);
            //设置审批人
            approver.setApprover(approverName);
            //如果不为空,则设置审批人部门所在的部门id
            approver.setDepartmentId(departmentId);
            //设置审批的事项id
            approver.setItemId(itemId);
            //设置审批时间
            approver.setCreateTime(new Date());
            //设置审批表的业务流水号
            approver.setBusinessCode(businessCode);
            //设置审批状态
            approver.setApproveState(itemStatus);
            //设置审批附件
            if(enclosure != null && enclosure.getId()!= null){
                approver.setEnclosureId(enclosure.getId());
            }

            int update = approverMapper.updateByPrimaryKeySelective(approver);
            if(update != 1){
                //向数据库中存储数据失败
                statusData.setItemId(String.valueOf(itemId));
                statusData.setMsg("办件id为："+itemId+",部门处理后办件表更新状态失败,审批人表更新数据失败!");
                statusData.setCode(0);

                //填充返回页面
                pageResult.setCode(0);
                pageResult.setData(statusData);
                pageResult.setMsg("更新状态失败,更新审批人表数据更新数据失败");
                return pageResult;
            }
        }

        //更新ro_datum表的
        Datum dat = new Datum();
        dat.setItemsId(itemId);dat.setBusinessCode(businessCode);
        //先进行查询,由于目前数据库的数据不是真实数据(与事实中的逻辑数据不符合)所以采用了select，真实中应该实际中采用selectOne方法
        List<Datum> datums = datumMapper.select(dat);
        if(!CollectionUtils.isEmpty(datums)){
            Datum datum = datums.get(0);//有且只有一条数据
            if(datum != null){
                datum.setState(itemStatus);//设置ro_datum表的状态
                int count = datumMapper.updateByPrimaryKeySelective(datum);
                if(count != 1){
                    //如果失败
                    statusData.setItemId(String.valueOf(itemId));
                    statusData.setMsg("办件d为："+ itemId +",部门处理后事项更新状态失败,更新申请人信息表的状态!");
                    statusData.setCode(0);

                    //填充返回页面
                    pageResult.setCode(0);
                    pageResult.setData(statusData);

                    pageResult.setMsg("更新状态失败");
                    return pageResult;
                }

                //如果都没有则为更新状态成功,data此时为空
                pageResult.setCode(1);
                pageResult.setData(new UpdateStatusData());
                pageResult.setMsg("更新状态成功");
                return pageResult;
            }
        }

        //如果集合为空,则要更新的事项对应的申请人信息表的数据不存在,更新失败
        statusData.setItemId(String.valueOf(itemId));
        statusData.setMsg("办件id为："+itemId+",部门处理后事项更新状态失败,该事项对应的申请人信息表的数据不存在!!");
        statusData.setCode(0);

        //填充返回页面
        pageResult.setCode(0);
        pageResult.setData(statusData);
        pageResult.setMsg("更新状态失败");
        return pageResult;

    }


    /**
     * 根据部门id和业务流水号查询单个事项的材料（注解版）
     * @param departmeId 部门id
     * @param businessCode 业务流水号
     * @return
     */
    public PageResult getItemInfoByDeptmentIdAndbusinessCode(Integer departmeId,String businessCode){

        PageResult<PageOneData> pageResult =  new  PageResult();
        PageOneData pageOneData = new PageOneData();
        List<DtoFile> dtoMaterials = new ArrayList<>();
        if(departmeId ==null || StringUtils.isEmpty(businessCode)){
            pageResult.setData(pageOneData);
            pageResult.setMsg("传败参数有误！");
            pageResult.setCode(0);
            return pageResult;
        }

        //如果不为空
        OneDate data = datumMapper.findItemInfoByDepartmentIdAndBusinessCode(businessCode, departmeId);
        if(data != null){
            //设置返回的值
            pageOneData.setMatterId(String.valueOf(data.getMatterId()));
            pageOneData.setDepartmentId(String.valueOf(data.getDepartmentId()));
            pageOneData.setItemId(String.valueOf(data.getItemsId()));
            pageOneData.setBusinessCode(businessCode);

            Agent a  = new Agent();
            a.setId(data.getId());a.setAName(data.getAName());a.setAEmail(data.getAEmail());
            a.setANumberType(data.getANumberType());a.setANumber(data.getApNumber());
            a.setAMobile(data.getAMobile());a.setBusinessCode(data.getBusinessCode());

            DtoApplication dtoApplication = new DtoApplication();
            dtoApplication.setApplicationName(data.getAName());
            dtoApplication.setApplicationNummber(data.getApNumber());

            FormData formData = new FormData();
            formData.setContent(data.getContent());

            Files file = new Files();//数据库材料实体
            file.setItemId(data.getItemsId());//设置itemId
            file.setBusinessCode(businessCode);//设置业务流水号
            List<Files> files = fileMapper.select(file);
            if(!CollectionUtils.isEmpty(files)){
                //填充返回页面填的事项材料列表数据
                for (Files _file : files) {
                    //填充材料的名称和材料的下载url
                    DtoFile dtoFile = new DtoFile();
                    dtoFile.setFileId(String.valueOf(_file.getId()));
                    String fileExt = getFileExtName(_file.getOriginalFileName());
                    dtoFile.setFileName(_file.getFileName() + fileExt);
                    String fileExtNoPoint = getFileExtNameNoPoint(_file.getOriginalFileName());
                    //如果为图片
                    if(pictureTypes.contains(fileExtNoPoint.toLowerCase())){
                        dtoFile.setFileUrl(urlPrefix + _file.getFileUrl());
                    }else {
                        dtoFile.setFileUrl(urlPrefix + _file.getFileUrl() + fileNameParam + _file.getFileName() + fileExt);
                    }
                    dtoMaterials.add(dtoFile);//向页面材料列表添加数据
                }
            }

            pageOneData.setApplicant(dtoApplication);
            pageOneData.setAgent(a);
            pageOneData.setFormData(formData);
            pageOneData.setMaterials(dtoMaterials);
        }
        pageResult.setData(pageOneData);
        pageResult.setMsg("获取数据成功！");
        pageResult.setCode(1);
        return pageResult;
    }


    /**
     * 根据部门id和业务流水号查询单个事项的id（注解版）
     * @param businessCode 业务流水
     * @param departmentId 部门id
     * @return
     */
    public PageResult getItemIdByDeptmentIdAndbusinessCode(String businessCode,Integer departmentId){
        PageResult<Integer> pageResult = new PageResult<>();
        if(departmentId ==null || StringUtils.isEmpty(businessCode)){
            pageResult.setData(null);
            pageResult.setMsg("传败参数有误！");
            pageResult.setCode(0);
            return pageResult;
        }
        Integer data = datumMapper.findItemByDepartmentAndBusinessCnode(businessCode, departmentId);
        pageResult.setData(data);
        pageResult.setMsg("获取数据成功！");
        pageResult.setCode(1);
        return pageResult;
    }


    /**
     * 根据业务编号查询主题的事项办理顺序
     * @param businessCode 业务编号
     * @return PageResult返回的页面数据( code:状态码 msg:成功或失败的信息 data:返回的数据 )
     */
    public PageResult getWorkFlowOrderByBusinessCode(String businessCode) {

        //返回需要流水号部门名称和部门顺序和部门id
        PageResult<WorkflowData> pageResult = new PageResult<WorkflowData>();
        //返回的工作流数据,没有数据的话则返回一个空的数组
        WorkflowData workflowData = new WorkflowData();
        //填充返回的业务编号
        workflowData.setBusinessCode(businessCode);
        //判断输入参数是否为空
        if(businessCode == null){
            workflowData.setWorkFlows(new ArrayList<>());//此时的工作流数据为空
            pageResult.setCode(0);
            pageResult.setMsg("输入参数错误,传入的业务流水号不能为空!");
            pageResult.setData(workflowData);
            return pageResult;
        }

        List<DtoWorkFlow> data = businessMapper.getWorkFlowOrderByBusinessCode(businessCode);
        if(CollectionUtils.isEmpty(data)){
            workflowData.setWorkFlows(new ArrayList<>());//此时的工作流数据为空
            pageResult.setCode(0);
            pageResult.setMsg("根据业务编号在业务表中未找到数据,业务编号错误!");
            pageResult.setData(workflowData);
            return pageResult;
        }

        //如果不为空，添加返回的结果,此时的workFlows工作流数据不为空
        workflowData.setWorkFlows(data);

        //成功信息
        pageResult.setCode(1);
        pageResult.setMsg("根据业务编号获取工作流信息成功！");
        pageResult.setData(workflowData);
        return pageResult;

    }


    /**
     * 返回的数据加密方法,此方法用于第三方调用(非常内部人员调用)
     * @param pageResult 未加密的返回页面数据
     * @return EncryptionPageResult 加密的页面返回数据
     */
    public EncryptionPageResult returnPageRsultEncryption(PageResult pageResult){
        EncryptionPageResult encryptionPageResult = new EncryptionPageResult();
        //设置数据加密后返回的状态码
        encryptionPageResult.setCode(pageResult.getCode());
        //设置数据加密后返回的失败或成功的信息
        encryptionPageResult.setMsg(pageResult.getMsg());

        //对返回的数据进行加密
        try{
            //对返回的数据进行加密
            String encryptData = DesUtil.encrypt(JsonUtils.serialize(pageResult.getData()), policyPwd);
            ////设置数据加密后返回的数据
            encryptionPageResult.setData(encryptData);
        } catch (Exception e){
            //如果失败则记录日志
            log.error("返回数据加密失败");

        }
        return encryptionPageResult;
    }


    /**
     * 获取材料的文件路径
     * @param fullPath 包含group1的路径
     * @return String 去除group1的路径的路径
     */
    public String getFileSuffixPath(String fullPath){
        String suffixUrl = "";
        if(fullPath != null && StringUtils.isNotEmpty(fullPath)){
            //第一次切
            String _suffixUrl = StringUtils.substring(fullPath, fullPath.indexOf('/') + 1, fullPath.length());
            //第二次切
            suffixUrl = StringUtils.substring(_suffixUrl,  _suffixUrl.indexOf('/'), _suffixUrl.length());
        }
        return suffixUrl;
    }


    /**
     *  根据业务编号和事项id查询部门下对应流水号的事项状态
     * @param itemId 事项id
     * @param businessCode 业务编号
     * @return PageResult返回的页面数据( code:状态码 msg:成功或失败的信息 data:返回的数据 )
     */
    public PageResult getItemStatusByBusinessCodeAndItemId(Integer itemId, String businessCode) {

        //页面数据返回的对象
        PageResult<ItemStatusData> pageResult = new PageResult<ItemStatusData>();
        //返回的事项的数据
        ItemStatusData itemStatusData = new ItemStatusData();
        //事项提交的相关材料数据
        List<DtoApproveFile> dtoMaterials = new ArrayList<>();

        //判断事项的部门id与传入的部门id是否存在的标识,当业务编号下的对应事项存在但查出来的事项部门编号与传入的部门编号不匹配
        if(itemId == null || businessCode == null){
            //设置返回的状态码和失败信息
            pageResult.setCode(0);
            pageResult.setMsg("输入参数有误！");
            //返回一个空的数据对象
            pageResult.setData(itemStatusData);
            return pageResult;
        }


        ApproveData data = approverMapper.findByItemStatusByBusinessCodeAndItemId(itemId, businessCode);

        //如果为空，则返回失败和信息
        if(data == null){
            //设置返回的状态码和失败信息
            pageResult.setCode(0);
            pageResult.setMsg("该业务流水号对应的事项不存在！");
            //返回一个空的数据对象
            pageResult.setData(itemStatusData);
            return pageResult;
        }

        //如果为空，则返回目标的信息
        if(data != null){
            //设置返回的审批人意见
            itemStatusData.setApproveOpinion(data.getApproveOpinion());
            //设置返回的审批人
            itemStatusData.setDepartApprover(data.getApprover());
            //设置返回的部门id

            itemStatusData.setDepartmentId(String.valueOf(data.getDepartmentId()));
            //设置返回的部门名称
            itemStatusData.setDepartmentName(data.getDepartmentName());
            //设置返回的时间
            String time = new SimpleDateFormat("yyyy年MM月dd日 HH:mm").format(data.getCreateTime());
            itemStatusData.setItemDealTime(time);

            //状态: 1审核中，2已审核，3审核失败
            if(Integer.valueOf(data.getState().trim()) == 1){
                //1审核中
                itemStatusData.setItemResult("办理中");
            }else if(Integer.valueOf(data.getState().trim()) == 2){
                //2已审核
                itemStatusData.setItemResult("办理成功");
            }else if(Integer.valueOf(data.getState().trim()) == 3){
                //3审核失败
                itemStatusData.setItemResult("办理失败");
            }

            //填充返回的附件信息
            if(data.getEnclosureId() != null){
                Enclosure e  = new Enclosure();e.setId(data.getEnclosureId());
                Enclosure enclosure = enclosureMapper.selectByPrimaryKey(e);
                //设置返回的审批附件
                DtoApproveFile approveFile = new DtoApproveFile();
                approveFile.setFileName(enclosure.getEnclosureName());
                //如果为图片
                String fileExtName = getFileExtName(enclosure.getEnclosureName());
                if(pictureTypes.contains(getFileExtNameNoPoint(enclosure.getEnclosureName()).toLowerCase())){
                    approveFile.setFileUrl(urlPrefix + enclosure.getEnclosureUrl());
                }else {
                    approveFile.setFileUrl(urlPrefix + enclosure.getEnclosureUrl() + fileNameParam + enclosure.getEnclosureName() + fileExtName);
                }

                dtoMaterials.add(approveFile);
            }
        }


        //设置返回审批附件信息
        itemStatusData.setMaterials(dtoMaterials);

        //成功返回成功信息
        pageResult.setCode(1);
        pageResult.setMsg("查询事项办理成功");
        //返回一个空的数据对象
        pageResult.setData(itemStatusData);
        return pageResult;
    }


	/**
     * 根据业务流水号查询该业务流水号所有审批历史
     * @param businessCode 业务编号
     * @return PageResult返回的页面数据( code:状态码 msg:成功或失败的信息 data:返回的数据 )
     */
    public PageResult getApproveHistoryByBusinessCode(String businessCode) {
        //返回的页面对象
        PageResult<List> pageResult = new PageResult<List>();
        //返回的审批对象数组
        List<ItemStatusData> itemStatusDatas = new ArrayList<>();

        //判断输入参数的是否有误
        if(businessCode == null){
            //设置返回的状态码和失败信息
            pageResult.setCode(0);
            pageResult.setMsg("输入参数有误！");
            //返回一个空的数据对象
            pageResult.setData(itemStatusDatas);
            return pageResult;
        }

        List<ApproveData> data = approverMapper.getApproveHistoryByBusinessCode(businessCode);

        //如果为空，则返回失败和信息
        if(CollectionUtils.isEmpty(data)){
            //设置返回的状态码和失败信息
            pageResult.setCode(0);
            pageResult.setMsg("该业务流水号不存在审批历史!");
            //返回一个空的数据对象
            pageResult.setData(itemStatusDatas);
            return pageResult;
        }

        //如果不为空，遍历获取数据
        for (ApproveData datum : data) {
            //返回的事项的数据
            ItemStatusData itemStatusData = new ItemStatusData();
            //事项提交的相关材料数据
            List<DtoApproveFile> dtoMaterials = new ArrayList<>();

            //设置返回的审批人意见
            itemStatusData.setApproveOpinion(datum.getApproveOpinion());
            //设置返回的审批人
            itemStatusData.setDepartApprover(datum.getApprover());
            //设置返回的部门id
            itemStatusData.setDepartmentId(String.valueOf(datum.getDepartmentId()));
            //设置返回的部门名称
            itemStatusData.setDepartmentName(datum.getDepartmentName());
            //设置返回的时间
            String time = new SimpleDateFormat("yyyy年MM月dd日 HH:mm").format(datum.getCreateTime());
            itemStatusData.setItemDealTime(time);

            //状态: 1审核中，2已审核，3审核失败
            if(Integer.valueOf(datum.getState().trim()) == 1){
                //1审核中
                itemStatusData.setItemResult("办理中");
            }else if(Integer.valueOf(datum.getState().trim()) == 2){
                //2已审核
                itemStatusData.setItemResult("办理成功");
            }else if(Integer.valueOf(datum.getState().trim()) == 3){
                //3审核失败
                itemStatusData.setItemResult("办理失败");
            }

            //填充返回的附件信息
            if(datum.getEnclosureId() != null){
                Enclosure e  = new Enclosure();e.setId(datum.getEnclosureId());
                Enclosure enclosure = enclosureMapper.selectByPrimaryKey(e);
                //设置返回的审批附件
                DtoApproveFile approveFile = new DtoApproveFile();
                approveFile.setFileName(enclosure.getEnclosureName());
                String fileExtName = getFileExtName(enclosure.getEnclosureName());
                if(pictureTypes.contains(getFileExtNameNoPoint(enclosure.getEnclosureName()).toLowerCase())){
                    approveFile.setFileUrl(urlPrefix + enclosure.getEnclosureUrl());
                }else {
                    approveFile.setFileUrl(urlPrefix + enclosure.getEnclosureUrl() + fileNameParam + enclosure.getEnclosureName());
                }
                dtoMaterials.add(approveFile);
            }
            //设置返回审批附件信息
            itemStatusData.setMaterials(dtoMaterials);
            //向查询数组中添加一条审批状态历史
            itemStatusDatas.add(itemStatusData);
        }

        //获取成功，返回成功的信息
        pageResult.setCode(1);
        pageResult.setMsg("获取该业务流水号下的审批历史成功!");
        pageResult.setData(itemStatusDatas);
        return pageResult;
    }


    /**
     * 根据业务流水号修改重新激活且审批不通过的单个事项的状态
     * @param businessCode 业务流水号
     * @return PageResult返回的页面数据( code:状态码 msg:成功或失败的信息 data:返回的数据 )
     */
    @Transactional
    public PageResult updateActivingItemStatusByBusinessCode(String businessCode){

        //返回的页面对象
        PageResult<String> pageResult = new PageResult<String>();
        //传入参数判断，如果为空，则返回失败的信息
        if(businessCode == null){
            pageResult.setCode(0);
            pageResult.setData("");
            pageResult.setMsg("传入的业务流水号不能为空");
            return pageResult;
        }

        //如果不为空，则根据业务流水号申请人表单信息表查询审核状态为审批失败的单个事项
        Datum d  = new Datum();
        //设置业务流水号
        d.setBusinessCode(businessCode); d.setState("3");//设置状态
        //有且只条数据
        List<Datum> datumList = datumMapper.select(d);

        //判断是否为空,为空则返回失败的信息
        if(CollectionUtils.isEmpty(datumList)){
            //如果为空则，返回错误信息
            pageResult.setCode(0);
            pageResult.setData("");
            pageResult.setMsg("该业务流水号不存在审批不通过的事项！");
            return pageResult;
        }

        //状态: 1审核中，2已审核，3审核失败
        //如果不为空，则获取数据修改该条流水呈下的单个事项的状态为 1审核中
        Datum datum = datumList.get(0);
        datum.setState("1");
        //修改数据库的状态
        int i = datumMapper.updateByPrimaryKeySelective(datum);
        if(i != 1){
            //如果为空则，返回错误信息，如果修改状态失败，返回失败的信息
            pageResult.setCode(0);
            pageResult.setData("");
            pageResult.setMsg("修改此业务流水号状态为不通过的事项的状态失败！");
            return pageResult;
        }

        //如果成功则返回成功的信息
        pageResult.setCode(1);
        pageResult.setData("");
        pageResult.setMsg("修改重新激活的事项的状态成功！");
        return pageResult;
    }

    //根据材料的id获取待办件的材料
    public PageResult getItemFileByFileId(String  fileId){
        //返回的页面实体类
        PageResult<Material> pageResult = new PageResult<>();
        Material m =  new Material();//页面返回材料实体类

        //如果材料id为空，则返回错误信息
        if(StringUtils.isEmpty(fileId)){
            pageResult.setCode(0);
            pageResult.setMsg("传入的材料id不能为空！");
            pageResult.setData(m);
            return pageResult;
        }

        Files files = fileMapper.selectByPrimaryKey(fileId);
        if(files == null){
            pageResult.setCode(0);
            pageResult.setMsg("该材料id对应的材料不存在！");
            pageResult.setData(m);
            return pageResult;
        }

        //设置返回的文件名称
        String fileExt = getFileExtName(files.getOriginalFileName());
        m.setMaterialName(files.getFileName() + fileExt);//材料的名称
        m.setMatterId(fileId);
        try{
            //材料的内容并内容转成base64编码
            String suffixPath = getFileSuffixPath(files.getFileUrl());
            String base64File = FileBase64ConvertUitl.encodeBase64File(pathPrefix + suffixPath);
            //设置返回的文件内容
            m.setMaterialContent(base64File.replaceAll("\r|\n", ""));
        }catch (Exception e){
            log.error("文件编码失败！");
        }

        //返回成功的信息
        pageResult.setCode(1);
        pageResult.setMsg("获取材料成功！");
        pageResult.setData(m);
        return pageResult;
    }

    //获取文件的后缀名.doc
    public String getFileExtName(String fileName){
        return fileName.substring(fileName.lastIndexOf("."), fileName.length());
    }
    //获取文件的后缀名doc
    public String getFileExtNameNoPoint(String fileName){
        return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
    }


    /**
     * 新增附件表的数据
     * @param enclosure 附件对象
     * @return 附件id
     */
    @Transactional
    public Integer updateEnclosure(Enclosure enclosure) {
        if(enclosure != null){
            Integer i = enclosureMapper.insertDate(enclosure);
            return enclosure.getId();
        }
        return null;
    }


    /**
     * 批量更新审批人表和申请人表单数据表的状态
     * @param approvers 审批人数据列表
     */
    public void updateApproverDate(List<Approver> approvers) {
        for (Approver approver : approvers) {
            updateOrInsertDate(approver);
            //更新申请人表单表
            Datum dat = new Datum();
            dat.setItemsId(approver.getItemId());dat.setBusinessCode(approver.getBusinessCode());
            List<Datum> datums = datumMapper.select(dat);
            if(!CollectionUtils.isEmpty(datums)) {
                Datum datum = datums.get(0);//有且只有一条数据
                if (datum != null) {
                    datum.setState(approver.getApproveState());//设置ro_datum表的状态
                    int count = datumMapper.updateByPrimaryKeySelective(datum);
                    if (count != 1){
                        log.error("更新申请人表单数据表失败,事项id为:{}",approver.getItemId());
                    }
                }
            }
            //更新工作流
            activitiService.activitiProceeds(String.valueOf(approver.getDepartmentId()),approver.getBusinessCode(),Integer.valueOf(approver.getApproveState()));
        }
    }


    /**
     * 批量更新审批人表和申请人表单数据表的状态
     * @param approvers 审批人数据列表
     */
    public void updateApproverAndDatum(List<Approver> approvers){

        //遍历更新对应的数据
        for (Approver approver : approvers) {
            String enclosureName = null;
            String fileUrl = null;
            //根据附件id查询附件
            if(approver.getEnclosureId() != null){
                Enclosure enclosure = enclosureMapper.selectByPrimaryKey(approver.getEnclosureId());
                enclosureName = enclosure.getEnclosureName();
                fileUrl = enclosure.getEnclosureUrl();
            }
            //更新对应
            PageResult pageResult = activitiService.activitiProceed(String.valueOf(approver.getDepartmentId()), approver.getBusinessCode(), Integer.valueOf(approver.getApproveState()),
                    approver.getApprover(), approver.getApproveOpinion(), approver.getLackMaterials(), enclosureName, fileUrl);
            if(pageResult.getCode() != 1){
                log.error("更新失败！,事项id为:{},错误信息为:{}",approver.getItemId(),pageResult.getMsg());
                continue;
            }
            //成功则更新工作流
            activitiService.activitiProceeds(String.valueOf(approver.getDepartmentId()),approver.getBusinessCode(),Integer.valueOf(approver.getApproveState()));
        }

    }


    /**
     *  更新或插入审批人数据
     * @param approver 审批人数据
     */
    @Transactional
    public void updateOrInsertDate(Approver approver){
        //根据事项id和业务流水号去审批人表查询数据，如果有更新，如果没有则插入新数据
        Approver a = new Approver();a.setItemId(approver.getItemId());a.setBusinessCode(approver.getBusinessCode());a.setDepartmentId(approver.getDepartmentId());
        List<Approver> approverList = approverMapper.select(a);
        if(CollectionUtils.isEmpty(approverList)){
            //如果为空，则证明数据库中不存在该条数据，插入一条新的数据
            int insert = approverMapper.insert(approver);
            if(insert != 1){
                log.error("插入失败！");
            }
        }
        if(!CollectionUtils.isEmpty(approverList)){
            //如果不为空，则更新审批人表中的数据
            Approver _approver = approverList.stream().findFirst().get();
            approver.setId(_approver.getId());
            //设置审批状态
            int update = approverMapper.updateByPrimaryKeySelective(approver);
            if(update != 1){
                log.error("更新数据失败,id为：{}",_approver.getId());
            }
        }
    }
}
