package com.tensquare.search.service;

import com.tensquare.search.dao.ArticleDao;
import com.tensquare.search.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleDao articleDao;

    /**
     * 查询方法
     */
    public Page<Article> search(String keyword, int page, int size) {
        return articleDao.findByTitleOrContentLike(keyword,keyword, PageRequest.of(page-1,size));
    }
}
