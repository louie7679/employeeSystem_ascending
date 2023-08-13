package org.ascending.training.service;

import org.ascending.training.model.Employee;
import org.ascending.training.repository.IEmployeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private IEmployeeDao employeeDao;

    public void save(Employee employee) {
        employeeDao.save(employee);
    }

    public List<Employee> getEmployees() {
        return employeeDao.getEmployees();
    }

    public void delete(Employee employee) {
        employeeDao.delete(employee);
    }

    public Employee getBy(Long id) {
        return employeeDao.getById(id);
    }
}
