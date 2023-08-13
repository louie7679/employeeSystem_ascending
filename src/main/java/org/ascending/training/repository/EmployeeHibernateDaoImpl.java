package org.ascending.training.repository;

import org.ascending.training.model.Employee;
import org.ascending.training.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeHibernateDaoImpl implements IEmployeeDao{
    private static final Logger logger = LoggerFactory.getLogger(EmployeeHibernateDaoImpl.class);

    @Override
    public void save(Employee employee) {
        Transaction transaction = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            session.save(employee);
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();
            logger.error("failed to insert record", e);
            session.close();
        }
    }

    @Override
    public List<Employee> getEmployees() {
        List<Employee> result = new ArrayList<>();
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session s = sessionFactory.openSession();
        String hql = "FROM Employee";
        try {
            Query<Employee> query = s.createQuery(hql);
            result = query.list();
            s.close();
        } catch (HibernateException e) {
            logger.error("Session close exception try again", e);
            s.close();
        }
        return result;
    }

    @Override
    public Employee getById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "FROM Employee e where id = :Id";
        try {
            Query<Employee> query = session.createQuery(hql);
            query.setParameter("Id", id);
            Employee result = query.uniqueResult();
            session.close();
            return result;
        } catch (HibernateException e) {
            logger.error("Session close exception try again", e);
            session.close();
            return null;
        }
    }

    @Override
    public void delete(Employee employee) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Transaction transaction;
        try {
            Session session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.delete(employee);
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            logger.error("Unable to delete department or unable to close session", e);
        }
    }
}