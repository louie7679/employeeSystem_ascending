package org.example.repository;

import org.example.repository.DepartmentJDBCDaoImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class DepartmentJDBCDaoImplTest {
    DepartmentJDBCDaoImpl departmentDao;

    @Before
    public void setup() {
        departmentDao = new DepartmentJDBCDaoImpl();
    }

    @After
    public void teardown() {
        departmentDao = null;
    }

    @Test
    public void getDepartmentsTest() {
        DepartmentJDBCDaoImpl departmentDao = new DepartmentJDBCDaoImpl();
        assertEquals(0, departmentDao.getDepartments().size());
    }
}
