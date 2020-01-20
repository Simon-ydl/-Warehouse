package com.yjs.service.department.Service;

import com.yjs.bean.department.Department;

import java.util.List;

public interface DepartmentService {
    Department findByDepartmentId(int departmentId);

    List<Department> findByBusinessCode(String businessCode);

    Department findByItemsId(int itemsIds);

}
