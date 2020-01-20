package com.yjs.bean.dto;

import lombok.Data;

/**
 * 返回数据的对象实体类
 */
@Data
public class Material {
    private String materialName;//材料名称
    private String materialContent;//材料下载地址
    private String matterId;//材料的id
}
