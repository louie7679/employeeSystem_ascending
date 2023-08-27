package org.ascending.training.repository;

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
        assertEquals(0, departmentDao.getDepartments().size());
    }
}
