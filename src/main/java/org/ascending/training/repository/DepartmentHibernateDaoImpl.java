package org.ascending.training.repository;

import org.ascending.training.model.Department;
import org.ascending.training.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class DepartmentHibernateDaoImpl implements IDepartmentDao{
    private static final Logger logger = LoggerFactory.getLogger(DepartmentHibernateDaoImpl.class);
    @Override
    public void save(Department department) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try {
            Session session = sessionFactory.openSession();
            session.save(department);
            session.close();
        } catch (HibernateException e) {
            logger.error("Open session exception or close session exception", e);
        }
    }

    @Override
    public List<Department> getDepartments() {
        logger.info("Start to getDepartments from Postgres via Hibernate.");
        //Prepare the required data model
        List<Department> departments = new ArrayList<>();

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        try {
            Session session = sessionFactory.openSession();

            String hql = "from Department";
            Query<Department> query = session.createQuery(hql);

            departments = query.list();

            session.close();
        } catch (HibernateException e) {
            logger.error("Open session exception or close session exception", e);
        }

        logger.info("Get departments {}", departments);
        return departments;
    }

    @Override
    public Department getById(Long id) {
        Department objectToUpdate = null;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();

            // Retrieve the object to be updated
            objectToUpdate = session.get(Department.class, id);

            session.getTransaction().commit();
            session.close();
        } catch (HibernateException e) {
            logger.error("Open session exception or close session exception", e);
        }
        return objectToUpdate;
    }

    @Override
    public void delete(Department department) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.delete(department);
            session.getTransaction().commit();
            session.close();
        } catch(HibernateException e) {
            logger.error("Open session exception or close session exception", e);
        }
    }
}
