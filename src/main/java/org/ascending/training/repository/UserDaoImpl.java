package org.ascending.training.repository;

import org.ascending.training.model.User;
import org.ascending.training.repository.exception.UserNotFoundException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements IUserDao{
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public SessionFactory sessionFactory;

    @Override
    public boolean save(User user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            session.close();
            return true;
        } catch (HibernateException e) {
            if (transaction != null) {
                logger.error("Save transaction failed, rolling back");
                transaction.rollback();
            }
            logger.error("Session close exception try again", e);
            session.close();
            return false;
        }
    }

    @Override
    public User getUserByEmail(String email) {
        Session session = sessionFactory.openSession();
        String hql = "FROM User u where email = :email";
        try {
            Query<User> query = session.createQuery(hql);
            query.setParameter("email", email);
            User result = query.uniqueResult();
            session.close();
            return result;
        } catch (HibernateException e) {
            logger.error("Session close exception try again", e);
            session.close();
            return null;
        }
    }

    @Override
    public User getUserById(Long id) {
        Session session = sessionFactory.openSession();
        String hql = "FROM User u where id = :Id";
        try {
            Query<User> query = session.createQuery(hql);
            query.setParameter("Id", id);
            User result = query.uniqueResult();
            session.close();
            return result;
        } catch (HibernateException e) {
            logger.error("Session close exception try again", e);
            session.close();
            return null;
        }    }

    @Override
    public User getUserByCredentials(String email, String password) throws UserNotFoundException {
        // String hql = "FROM User as u where (lower(u.email) = :email or lower(u.name) = :email) and u.password = :password";
        String hql = "FROM User as u where lower(u.email) = :email and u.password = :password";
        logger.info(String.format("User email: %s, password: %s", email, password));

        try{
            Session session = sessionFactory.openSession();
            Query<User> query = session.createQuery(hql);
            query.setParameter("email", email.toLowerCase().trim());
            query.setParameter("password", password);
            return query.uniqueResult();
        } catch(Exception e) {
            logger.error("error: {}", e.getMessage());
            throw new UserNotFoundException("can't find user record with email = " + email + ", password = " + password);
        }
    }
}
