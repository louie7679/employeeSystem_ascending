package org.ascending.training.repository;

import org.ascending.training.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
        return false;
    }

    @Override
    public User getUserByEmail(String email) {
        return null;
    }

    @Override
    public User getUserById(Long id) {
        return null;
    }

    @Override
    public User getUserByCredentials(String email, String password) {
        String hql = "FROM User as u where (lower(u.email) = :email or lower(u.name) = :email) and u.password = :password";

        try{
            Session session = sessionFactory.openSession();
            Query<User> query = session.createQuery(hql);
            query.setParameter("email", email.toLowerCase().trim());
            query.setParameter("password", password);
            return query.uniqueResult();
        } catch (Exception e) {
            logger.error("error: {}", e.getMessage());
            throw new UserNotFoundException("can't find user record with email = " + email + ", password = " + password);
        }
    }
}
