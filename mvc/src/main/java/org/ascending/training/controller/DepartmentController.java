package org.ascending.training.controller;

import org.ascending.training.model.Department;
import org.ascending.training.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/department")
public class DepartmentController {
    private final Logger logger = LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    private DepartmentService departmentService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Department> getDepartments() {
        logger.info("This is department controller");
        List<Department> departments = departmentService.getDepartments();
        return departments;
    }

    @RequestMapping(value = "/{Id}", method = RequestMethod.GET)
    public Department getDepartmentById(@PathVariable(name = "Id") Long id) {
        logger.info("This is department controller, get by {}", id);
        return departmentService.getBy(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH, params = {"name"})
    public Department updateDepartmentName(@PathVariable("id") Long id, @RequestParam("name") String name) {
        logger.info("pass in variable id: {} and name: {}", id.toString(), name);
        Department d = departmentService.getBy(id);
        d.setName(name);
        d = departmentService.update(d);
        return d;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public void create(@RequestBody Department department) {
        logger.info("Post a new object {}", department.getName());
        departmentService.save(department);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteDepartment(@PathVariable("id") Long id) {
        logger.info("Deleting department with id: {}", id);
        Department d = departmentService.getBy(id);
        departmentService.delete(d);
    }
}
