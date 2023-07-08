package org.ascending.training.repository;

import org.ascending.training.model.Department;
import org.ascending.training.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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
        Transaction transaction = null;
        try {
            Session session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            session.save(department);
            transaction.commit();
            session.close();
        } catch(HibernateException e) {
            if(transaction != null) {
                logger.error("Save transaction failed, rolling back");
                transaction.rollback();
            }
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
            //Open a connection
            Session session = sessionFactory.openSession();
            //Execute a query
            String hql = "from Department";
            //Extract data from result set
            Query<Department> query = session.createQuery(hql);
            departments = query.list();
            //Close resources
            session.close();
        } catch(HibernateException e) {
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
            //Retrieve the object to be updated
            objectToUpdate = session.get(Department.class, id);
            session.close();
        } catch(HibernateException e) {
            logger.error("Open session exception or close session exception", e);
        }
        return objectToUpdate;
    }

    @Override
    public void delete(Department department) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Transaction transaction = null;
        try {
            Session session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.delete(department);
            transaction.commit();
            session.close();
        } catch(HibernateException e) {
            if(transaction != null) {
                logger.error("Delete transaction failed, rolling back");
                transaction.rollback();
            }
            logger.error("Open session exception or close session exception", e);
        }
    }

    @Override
    public Department getDepartmentEagerBy(Long id) {
        String hql = "FROM Department d LEFT JOIN FETCH d.employees where d.id = :Id"; //LEFT JOIN FETCH: HQL里面的left join
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Department> query = session.createQuery(hql);
            query.setParameter("Id", id);
            Department result = query.uniqueResult();
            session.close();
            return result;
        } catch (HibernateException e) {
            logger.error("failed to retrieve data record", e);
            session.close();
            return null;
        }    }
}
