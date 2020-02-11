package com.tensquare.search.dao;

import com.tensquare.search.pojo.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ArticleDao extends JpaRepository<Article,String>,JpaSpecificationExecutor<Article>{
}
