package com.yjs.service.department.Service.Impl;

import com.yjs.bean.department.Department;
import com.yjs.dao.mapper.DepartmentMapper;
import com.yjs.service.department.Service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    DepartmentMapper departmentMapper;

    @Override
    public Department findByDepartmentId(int departmentId) {
        Department department = departmentMapper.findByDepartmentId(departmentId);
        return department;
    }

    /**
     * 根据业务流水号查询部门信息
     * @param businessCode
     * @return
     */
    @Override
    public List<Department> findByBusinessCode(String businessCode) {
        List<Department> departmentList = departmentMapper.findByBusinessCode(businessCode);
        return departmentList;
    }

    /**
     * 根据事项id查询部门信息
     * @param itemsIds
     * @return
     */
    @Override
    public Department findByItemsId(int itemsIds) {
        Department department = departmentMapper.findByItemsId(itemsIds);
        return department;
    }

}
