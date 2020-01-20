package com.tensquare.qa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.qa.pojo.Problem;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ProblemDao extends JpaRepository<Problem,String>,JpaSpecificationExecutor<Problem>{

    /**
     * 最新问题
     * @param labelid
     * @param pageable
     * @return
     */
    @Query("select p from Problem p where id in (select problemid from Pl where labelid = ?1) order by replytime desc")
    Page<Problem> findNewListByLabelId(String labelid, Pageable pageable);

    /**
     * 热门问题
     * @param labelid
     * @param pageable
     * @return
     */
    @Query("select p from Problem p where id in (select problemid from Pl where labelid = ?1) order by reply desc")
    Page<Problem> findHotListByLabelId(String labelid, Pageable pageable);

    /**
     * 等待回答
     * @param labelid
     * @param pageable
     * @return
     */
    @Query("select p from Problem p where id in (select problemid from Pl where labelid = ?1) and reply = 0 order by createtime desc ")
    Page<Problem> findWaitListByLabelId(String labelid, Pageable pageable);
}