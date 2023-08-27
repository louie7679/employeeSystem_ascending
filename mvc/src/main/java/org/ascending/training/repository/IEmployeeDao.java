package org.ascending.training.repository;

import org.ascending.training.model.Employee;

import java.util.List;

public interface IEmployeeDao {
    void save(Employee employee);

    List<Employee> getEmployees();

    Employee getById(Long id);

    void delete(Employee employee);

}
