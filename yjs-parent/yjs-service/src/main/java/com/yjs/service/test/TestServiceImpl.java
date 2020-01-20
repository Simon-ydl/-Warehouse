package com.yjs.service.test;

import com.yjs.bean.test.Model;
import com.yjs.dao.mapper.ITestMapper;
import com.yjs.service.test.service.ITestApp;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("testService")
public class TestServiceImpl implements ITestApp{

    @Resource
    ITestMapper mapper;

    @Override
    public List<Model> query(Model model) {
        return mapper.select(model);
    }

    @Override
    public int insert(Model model) {
        return mapper.insert(model);
    }
}
