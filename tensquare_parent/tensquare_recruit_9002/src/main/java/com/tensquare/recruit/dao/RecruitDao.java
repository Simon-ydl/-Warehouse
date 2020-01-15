package com.tensquare.recruit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.recruit.pojo.Recruit;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface RecruitDao extends JpaRepository<Recruit,String>,JpaSpecificationExecutor<Recruit>{

    /**
     *  推荐职位
     *  查询状态为 2 并以创建日期降序排序，查询前 4 条记录
     */
    List<Recruit> findTop4ByStateOrderByCreatetimeDesc(String state);

    /**
     *  最新职位
     *  查询状态不为 0 并以创建日期降序排序，查询前 12 条记录
     */
    List<Recruit> findTop12ByStateNotOrderByCreatetimeDesc(String state);
}
