package com.yjs.service.materials.impl;


import com.yjs.bean.file.Files;
import com.yjs.bean.materials.Materials;
import com.yjs.dao.mapper.item.IIMaterialsMapper;
import com.yjs.service.materials.IMaterialsService;
import com.yjs.service.matter.service.MatterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service("materialsService")
public class MaterialsServiceImpl implements IMaterialsService {

    @Autowired
    IIMaterialsMapper mapper;

    @Override
    public List<Materials> queryMaterialsbyCode(String businessCode) {

        Example example = new Example(Materials.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("businessCode",businessCode);

        return mapper.selectByExample(example);
    }
}
