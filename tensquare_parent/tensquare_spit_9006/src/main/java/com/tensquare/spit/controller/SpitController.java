package com.tensquare.spit.controller;

import com.tensquare.spit.pojo.Spit;
import com.tensquare.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin
@RequestMapping("/spit")
public class SpitController {

    @Autowired
    private SpitService spitService;
    @Autowired
    private RedisTemplate redisTemplate;

    //查询全部
    @RequestMapping(method= RequestMethod.GET)
    public Result findAll(){
        return new Result(true, StatusCode.OK,"查询成功",spitService.findAll());
    }

    //根据ID查询
    @RequestMapping(value="/{id}",method= RequestMethod.GET)
    public Result findById(@PathVariable String id){
        return new Result(true,StatusCode.OK,"查询成功 ",spitService.findById(id));
    }

    //增加
    @RequestMapping(method=RequestMethod.POST)
    public Result add(@RequestBody Spit spit ){
        spitService.add(spit);
        return new Result(true,StatusCode.OK,"增加成功");
    }

    //修改
    @RequestMapping(value="/{id}",method= RequestMethod.PUT)
    public Result update(@RequestBody Spit spit, @PathVariable String id ){
        spit.setId(id); spitService.update(spit);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    //删除
    @RequestMapping(value="/{id}",method= RequestMethod.DELETE)
    public Result delete(@PathVariable String id ){
        spitService.deleteById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    //根据上级ID查询且分页
    @RequestMapping(value="/comment/{parentId}/{page}/{size}",method= RequestMethod.GET)
    public Result findByParentid(@PathVariable String parentId ,@PathVariable int page ,@PathVariable int size ){
        Page<Spit> pageList = spitService.findByParentid(parentId, page, size);
        return new Result(true,
                StatusCode.OK, "查询成功",
                new PageResult<Spit>(pageList.getTotalElements(), pageList.getContent()));
    }

    //吐槽点赞
    @RequestMapping(value = "/thumbup/{spitid}",method = RequestMethod.PUT)
    public Result thumbup(@PathVariable String spitid){
        //服务更新改动,控制模块也要改动
        //spitService.updateThumbup(spitid);
        //return new Result(true,StatusCode.OK,"点赞成功");

        //模拟用户id,后面会添加上
        String userid = "1001";

        //从redis查询用户是否有过相同操作
        String flag = (String) redisTemplate.opsForValue().get("spit_thumbup_"+userid+"_"+spitid);
        if (flag != null){
            //取消点赞
            spitService.cancelThumbup(spitid);
            //清除记录
            redisTemplate.delete("spit_thumbup_"+userid+"_"+spitid);
            //返回结果
            return new Result(true,StatusCode.OK,"取消点赞成功");
        }else{
            //点赞
            spitService.updateThumbup(spitid);
            //添加记录
            redisTemplate.opsForValue().set("spit_thumbup_"+userid+"_"+spitid,"1",5, TimeUnit.MINUTES);
            //返回结果
            return new Result(true,StatusCode.OK,"点赞成功");
        }
    }

}
