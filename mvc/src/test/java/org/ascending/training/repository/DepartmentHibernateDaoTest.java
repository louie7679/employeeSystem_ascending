package org.ascending.training.repository;

import org.ascending.training.model.Department;
import org.ascending.training.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class DepartmentHibernateDaoTest {
    @Mock
    private SessionFactory mockSessionFactory;

    @Mock
    private Session mockSession;

    @Mock
    private Query mockQuery;

    private IDepartmentDao departmentDao;

    @Before
    public void setUp() {
        initMocks(this);
        departmentDao = new DepartmentHibernateDaoImpl();
    }

    @Test
    public void getDepartmentsTest_happyPath() {
        Department department = new Department(1, "Zhang3", "random", "US");
        List<Department> result = List.of(department);

        try (MockedStatic mockedStatic = mockStatic(HibernateUtil.class)) {
            mockedStatic.when(HibernateUtil::getSessionFactory).thenReturn(mockSessionFactory);

            when(mockSessionFactory.openSession()).thenReturn(mockSession);
            when(mockSession.createQuery(any(String.class))).thenReturn(mockQuery);
            when(mockQuery.list()).thenReturn(result);
            doNothing().when(mockSession).close();

            List<Department> actualResult = departmentDao.getDepartments();
            assertEquals(result, actualResult);
        }
    }

    @Test
    public void getDepartmentsTest_getHibernateException() {
        Department department = new Department(1, "Zhang3", "random", "US");
        List<Department> result = List.of(department);

        try (MockedStatic mockedStatic = mockStatic(HibernateUtil.class)) {
            mockedStatic.when(HibernateUtil::getSessionFactory).thenReturn(mockSessionFactory);

            when(mockSessionFactory.openSession()).thenReturn(mockSession);
            when(mockSession.createQuery(any(String.class))).thenReturn(mockQuery);
            when(mockQuery.list()).thenReturn(result);
            doThrow(HibernateException.class).doNothing().when(mockSession).close();

            assertThrows(HibernateException.class, () -> departmentDao.getDepartments());

        }
    }
}
