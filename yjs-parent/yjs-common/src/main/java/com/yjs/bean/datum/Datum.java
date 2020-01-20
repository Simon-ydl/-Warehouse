package com.yjs.bean.datum;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

//入户数据库表对应实体类
@Table(name = "ro_datum")
@Data
public class Datum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;//id
    @Column(name = "content")
    private String content;//申请材料表及信息
    @Column(name = "state")
    private String state;//状态:0:成功,1审核中,2失败(总状态)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_at")
    private Date createAt;//创建时间
    @Column(name = "business_code")
    private String businessCode;//业务编码
    @Column(name = "items_id")
    private Integer itemsId;//事项id

    public void setCreate() {
        long d=System.currentTimeMillis()+30*60*1000;
        Date date=new Date(d);
        createAt=date;
    }
}
