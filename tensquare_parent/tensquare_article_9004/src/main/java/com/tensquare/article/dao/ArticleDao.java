package com.tensquare.article.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.article.pojo.Article;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ArticleDao extends JpaRepository<Article,String>,JpaSpecificationExecutor<Article>{

    @Modifying
    @Query("update Article a set a.state = '1' where a.id = ?1")
    public void examine(String id);

    @Modifying
    @Query("update Article a set a.thumbup = coalesce(thumbup,0)+1 where a.id = ?1")
    public void thumbup(String id);
}
