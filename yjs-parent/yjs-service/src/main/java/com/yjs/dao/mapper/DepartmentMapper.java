package com.yjs.dao.mapper;

import com.yjs.bean.department.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface DepartmentMapper {
    Department findByDepartmentId(int departmentId);

    List<Department> findByBusinessCode(String businessCode);

    Department findByItemsId(int itemsIds);

}
