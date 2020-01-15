package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 标签service
 */
@Service
public class LabelService {

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 查询所有
     */
    public List<Label> findAll(){
        return labelDao.findAll();
    }


    /**
     *  查询一个
     */
    public Label findById(String id){
        return labelDao.findById(id).get();
    }

    /**
     * 添加
     */
    public void add(Label label){
        //获取分布式ID
        label.setId(idWorker.nextId()+"");
        labelDao.save(label);
    }

    /**
     * 修改
     */
    public void update(Label label){
        labelDao.save(label);
    }

    /**
     * 删除
     */
    public void deleteById(String id){
        labelDao.deleteById(id);
    }

    /**
     * 创建Specification对象
     */
    private Specification<Label> createSpecification(Map<String,Object> searchMap){
        return new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //设计集合存储所有条件
                List<Predicate> preList = new ArrayList<Predicate>();

                if(searchMap.get("labelname")!=null && !searchMap.get("labelname").equals("")){
                    // labelname like '%xxxx%'
                    preList.add( criteriaBuilder.like(root.get("labelname").as(String.class),"%"+searchMap.get("labelname")+"%"));
                }


                if(searchMap.get("state")!=null && !searchMap.get("state").equals("")){
                    // state = 'xxxx'
                    preList.add( criteriaBuilder.equal(root.get("state").as(String.class),searchMap.get("state")));
                }

                if(searchMap.get("recommend")!=null && !searchMap.get("recommend").equals("")){
                    // recommend = 'xxxx'
                    preList.add( criteriaBuilder.equal(root.get("recommend").as(String.class),searchMap.get("recommend")));
                }


                Predicate[] preArray = new Predicate[preList.size()];
                // labelname like '%xxxx%' and  state = 'xxxx' and  recommend = 'xxxx'
                return criteriaBuilder.and(preList.toArray(preArray));
            }
        };
    }


    /**
     * 条件搜索
     */
    public List<Label> findSearch(Map<String,Object> searchMap){
        //1.创建Specification对象
        Specification<Label> spec = createSpecification(searchMap);
        //2.条件查询
        return labelDao.findAll(spec);
    }

    /**
     * 分页+条件搜索
     */
    public Page<Label> findSearch(Map<String,Object> searchMap, int page, int size){
        //1.创建Specification对象
        Specification<Label> spec = createSpecification(searchMap);
        //2.条件查询
        //PageRequest.of(page,size): 返回Pageable的接口实现类 , page从0开始的
        return labelDao.findAll(spec, PageRequest.of(page-1,size));
    }
}
