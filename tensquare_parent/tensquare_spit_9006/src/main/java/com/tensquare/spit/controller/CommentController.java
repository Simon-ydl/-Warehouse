package com.tensquare.spit.controller;

import com.tensquare.spit.pojo.Comment;
import com.tensquare.spit.service.CommentService;
import com.tensquare.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private RedisTemplate redisTemplate;

    //查询全部
    @RequestMapping(method= RequestMethod.GET)
    public Result findAll(){
        return new Result(true, StatusCode.OK,"查询成功",commentService.findAll());
    }

    //查询全部
    @RequestMapping(value = "/{id}",method= RequestMethod.GET)
    public Result findById(@PathVariable String id ){
        return new Result(true, StatusCode.OK,"查询成功",commentService.findById(id));
    }

    //添加
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Comment comment){
        commentService.add(comment);
        return new Result(true, StatusCode.OK,"添加成功");
    }

    //修改
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public Result update(@RequestBody Comment comment,@PathVariable String id){
        comment.setId(id);
        commentService.update(comment);
        return new Result(true, StatusCode.OK,"修改成功");
    }

    //删除
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id){
        commentService.deleteById(id);
        return new Result(true, StatusCode.OK,"删除成功");
    }

    //根据上级ID分页查询
    @RequestMapping(value = "/comment/{parentid}/{page}/{size}",method = RequestMethod.GET)
    public Result findByParentid (@PathVariable String parentid,@PathVariable int page,@PathVariable int size){
        Page<Comment> commentPage = commentService.findByParentid(parentid,page,size);
        return new Result(true, StatusCode.OK,"查询成功",new PageResult<Comment>(commentPage.getTotalElements(),commentPage.getContent()));
    }
}
