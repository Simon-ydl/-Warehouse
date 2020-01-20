package com.yjs.bean.datum;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

//搜索结果对应实体类
@Data
public class Datums {

    private Integer id;//id
    private String content;//申请材料表及信息
    private String state;//状态:0:成功,1审核中,2失败(总状态)
    private Date createAt;//创建时间
    private String businessCode;//业务编码
    private Integer itemsId;//事项id
    private String itemName;
    private Integer departmentId;
    private String departmentName;
    private Integer approveId;
    private String  approveName;
    private String  approveNumber;

}
