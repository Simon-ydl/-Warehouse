package com.tensquare.spit.dao;

import com.tensquare.spit.pojo.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentDao extends MongoRepository<Comment,String> {
    public Page<Comment> findByParentid(String parentId, Pageable pageable);
}
