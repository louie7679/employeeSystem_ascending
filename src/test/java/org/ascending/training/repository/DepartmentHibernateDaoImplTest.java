package org.ascending.training.repository;

import junit.framework.TestCase;
import org.ascending.training.model.Department;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class DepartmentHibernateDaoImplTest{
    private DepartmentHibernateDaoImpl departmentHibernateDao;
    private Department d1;

    @Before
    public void setUp() {
        departmentHibernateDao = new DepartmentHibernateDaoImpl();
        d1 = new Department();
        d1.setId(1);
        d1.setName("hr");
        d1.setDescription("random description");
        d1.setLocation("US");
        departmentHibernateDao.save(d1);
    }

    @After
    public void tearDown() {
        //departmentHibernateDao.delete(d1);
    }

    @Test
    public void getDepartmentsTest() {
        assertEquals(1, departmentHibernateDao.getDepartments().size());
        departmentHibernateDao.delete(d1);
    }

    @Test
    public void deleteDepartmentsTest() {
        departmentHibernateDao.delete(d1);
        assertEquals(0, departmentHibernateDao.getDepartments().size());
    }
}