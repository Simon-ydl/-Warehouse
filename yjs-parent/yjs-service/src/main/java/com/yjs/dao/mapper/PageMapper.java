package com.yjs.dao.mapper;

import java.util.List;

import com.yjs.bean.page.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Repository
@Mapper
public interface PageMapper {

	List<Object> findAllByMatterId(int matterId);

	Page findAllByMatterIdAndItemId(@Param("matterId") int matterId, @Param("itemId") String itemId);
}
