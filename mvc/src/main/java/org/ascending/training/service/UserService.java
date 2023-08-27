package org.ascending.training.service;

import org.ascending.training.model.User;
import org.ascending.training.repository.IUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    IUserDao userDao;
    public User getUserByCredentials(String email, String password) throws Exception {
        return userDao.getUserByCredentials(email, password);
    }

    public User getUserById(Long id) {
        return userDao.getUserById(id);
    }
}
