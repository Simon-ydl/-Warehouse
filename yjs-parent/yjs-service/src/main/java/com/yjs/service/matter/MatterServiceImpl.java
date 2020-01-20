package com.yjs.service.matter;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yjs.bean.matter.Matter;
import com.yjs.bean.test.Model;
import com.yjs.dao.mapper.IMatterMapper;
import com.yjs.dao.mapper.ITestMapper;
import com.yjs.service.matter.service.IMatterApp;
import com.yjs.service.test.service.ITestApp;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("matterService")
public class MatterServiceImpl implements IMatterApp {

    @Resource
    IMatterMapper mapper;

    @Override
    public List<Matter> query(Matter model) {
        return mapper.select(model);
    }

    @Override
    public int insert(Matter model) {
        return mapper.insert(model);
    }


    @Override
    public PageInfo queryHelperPageInfo(int pageNum, int pageSize, Map<String,String> searchMap, String orderBy) {

        PageHelper.startPage(pageNum, pageSize,orderBy);

        Example example = new Example(Matter.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("deleted",0);

        if(searchMap.containsKey("name")){
            criteria.andLike("name","%"+searchMap.get("name")+"%");
        }

        if(searchMap.containsKey("accept")){
            criteria.andLike("accept","%"+searchMap.get("accept")+"%");
        }

        List<String> businessIds= new ArrayList<>();
        businessIds.add("2");
        businessIds.add("3");

        criteria.andIn("iszx",businessIds);

        return new PageInfo<Matter>(mapper.selectByExample(example)) ;
    }

    @Override
    public Matter queryMatterByItemId(String itemId) {

        Example example = new Example(Matter.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("matterCode",itemId);
        return mapper.selectOneByExample(example);
    }

}
