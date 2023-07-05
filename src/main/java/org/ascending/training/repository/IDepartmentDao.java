package org.ascending.training.repository;

import org.ascending.training.model.Department;

import java.util.List;

public interface IDepartmentDao {
     // Create
     void save(Department department);

     // Retrieve
     public List<Department> getDepartments();

     //Update
     Department getById(Long id); //update = get + ... + save

     // Delete
     void delete(Department department);

     Department getDepartmentEagerBy(Long id);
}
