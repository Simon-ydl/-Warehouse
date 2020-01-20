package com.yjs.dao.mapper.item;

import com.yjs.bean.department.Department;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface IIDepartmentMapper extends Mapper<Department>, SelectByIdListMapper<Department,Integer> {
}
