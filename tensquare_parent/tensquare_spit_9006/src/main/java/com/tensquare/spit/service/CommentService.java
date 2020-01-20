package com.tensquare.spit.service;

import com.tensquare.spit.dao.CommentDao;
import com.tensquare.spit.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.Date;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentDao commentDao;
    @Autowired
    private IdWorker idWorker;

    //查询所有
    public List<Comment> findAll(){
        return commentDao.findAll();
    }

    //根据id查询
    public Comment findById(String id) {
        return commentDao.findById(id).get();
    }

    //添加
    public void add(Comment comment) {

        comment.setId(idWorker.nextId()+"");
        comment.setParentid(idWorker.nextId()+"");
        comment.setPublishdate(new Date());

        commentDao.save(comment);

    }

    //修改
    public void update(Comment comment) {
        commentDao.save(comment);
    }

    //删除
    public void deleteById(String id) {
        commentDao.deleteById(id);
    }

    public Page<Comment> findByParentid(String parentid, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1,size);
        return commentDao.findByParentid(parentid,pageRequest);
    }
}
