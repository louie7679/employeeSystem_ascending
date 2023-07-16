package org.ascending.training.service;

import org.ascending.training.model.Department;
import org.ascending.training.repository.IDepartmentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    private IDepartmentDao departmentDao;

    public void save(Department department) {
        departmentDao.save(department);
    }

    public List<Department> getDepartments() {
        return departmentDao.getDepartments();
    }

    public Department update(Department department) {
        return departmentDao.getById(department.getId());
    }

    public void delete(Department department) {
        departmentDao.delete(department);
    }

    public Department getDepartmentEager(long id) {
        return departmentDao.getDepartmentEagerBy(id);
    }
}
