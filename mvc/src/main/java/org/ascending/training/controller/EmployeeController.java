package org.ascending.training.controller;

import org.ascending.training.model.Employee;
import org.ascending.training.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/employee")
public class EmployeeController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Employee> getEmployees() {
        logger.info("This is employee controller");
        List<Employee> employees = employeeService.getEmployees();
        return employees;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public void create(@RequestBody Employee employee) {
        logger.info("Post a new object {}", employee.getName());
        employeeService.save(employee);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteEmployee(@PathVariable("id") Long id) {
        logger.info("Deleting employee with id: {}", id);
        Employee e = employeeService.getBy(id);
        employeeService.delete(e);
    }
}
