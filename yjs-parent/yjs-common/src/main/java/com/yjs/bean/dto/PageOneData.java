package com.yjs.bean.dto;

import com.yjs.bean.agent.Agent;
import lombok.Data;

import java.util.List;


//返回的页面对应的data中一条数据实体类
@Data
public class PageOneData {
    private String businessCode;//业务流水号
    private String matterId;//事项所属的主题码
    private String itemId;//事项的id用于在部门处理室完成之后修改事项的状态
    private String departmentId;//部门id
    private DtoApplication applicant;//申请人姓名和信息
    private Agent agent;//经办对象信息
    private List<DtoFile> materials;//事项申请人提交的材料的数组对象
    private FormData formData;//申请人表单数据
}
