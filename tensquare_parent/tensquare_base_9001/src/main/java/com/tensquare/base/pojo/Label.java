package com.tensquare.base.pojo;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 标签实体类
 */
@Entity
@Table(name = "tb_label")
public class Label implements Serializable{

    @Id  // 注意：这里的id是程序的分布式id生成器分配的，不需要指定策略
    private String id;//编号
    private String labelname;//标签名称
    private String state;//状态
    private Long count;//使用数量
    private String recommend;//是否推荐
    private Long fans;//粉丝数

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabelname() {
        return labelname;
    }

    public void setLabelname(String labelname) {
        this.labelname = labelname;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public Long getFans() {
        return fans;
    }

    public void setFans(Long fans) {
        this.fans = fans;
    }
}
