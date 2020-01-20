package com.tensquare.spit.service;

import com.tensquare.spit.dao.SpitDao;
import com.tensquare.spit.pojo.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import sun.util.resources.cldr.id.CurrencyNames_id;
import util.IdWorker;

import java.util.Date;
import java.util.List;

@Service
public class SpitService {

    @Autowired
    private SpitDao spitDao;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private MongoTemplate mongoTemplate;

     //查询所有
     public List<Spit> findAll(){
     return spitDao.findAll();
     }

     /**
     * 根据 id 查询
     */
    public Spit findById(String id){
        return spitDao.findById(id).get();
    }
    /**
     * 添加
     */
    public void add(Spit spit){
        //获取雪花ID
        spit.setId(idWorker.nextId()+"");
        spit.setPublishtime(new Date());//日期
        spit.setThumbup(0);//点赞数
        spit.setVisits(0);//流览量
        spit.setShare(0);//分享数
        spit.setComment(0);//回复数
        spit.setState("1");//状态值
        String flag = spit.getParentid();
        //判断是否是转载,或者承接某个评论,如果是,父件回复量+1
        if(flag != null && !flag.equals("")){
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));

            mongoTemplate.updateFirst(query,new Update().inc("comment",1),"spit");
            mongoTemplate.updateFirst(query,new Update().inc("visits",1),"spit");
            mongoTemplate.updateFirst(query,new Update().inc("share",1),"spit");


        }
        spitDao.save(spit);
    }
    /**
     * 修改
     */
    public void update(Spit spit){
        spitDao.save(spit);
    }
    /**
     * 删除
     */
    public void deleteById(String id){
        spitDao.deleteById(id);
    }

    //根据上级ID查询且分页
    public Page<Spit> findByParentid(String parentId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page-1, size);
        return spitDao.findByParentid(parentId, pageRequest);
    }

    //点赞
    public void updateThumbup(String id) {

        /*老办法,需要每个都查一遍,且对比一遍
        Spit spit = spitDao.findById(id).get();
        spit.setThumbup(spit.getThumbup() + 1);
        update(spit);*/

        //mongodb方法
        //mongodb语句:
        //db.spit.update({"_id":"1062914639060287488"},{$inc:{"thumbup":NumberInt(1)}})
        //创建条件对象,使用id添加条件
        Query query = new Query();
        //{"_id":"1062914639060287488"}
        query.addCriteria(Criteria.where("_id").is(id));
        //创建更新对象
        Update update = new Update();
        //{$inc:{"thumbup":NumberInt(1)}
        update.inc("thumbup",1);
        //执行更新
        //db.spit.update():拼凑语句
        mongoTemplate.updateFirst(query,update,"spit");
    }

    //取消点赞
    public void cancelThumbup(String id) {
        //mongodb方法
        //mongodb语句:
        //db.spit.update({"_id":"1062914639060287488"},{$inc:{"thumbup":NumberInt(1)}})
        //创建条件对象,使用id添加条件
        Query query = new Query();
        //{"_id":"1062914639060287488"}
        query.addCriteria(Criteria.where("_id").is(id));
        //创建更新对象
        Update update = new Update();
        //{$inc:{"thumbup":NumberInt(1)}
        update.inc("thumbup",-1);
        //执行更新
        //db.spit.update():拼凑语句
        mongoTemplate.updateFirst(query,update,"spit");
    }
}
