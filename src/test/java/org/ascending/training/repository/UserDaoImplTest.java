package org.ascending.training.repository;

import org.ascending.training.ApplicationBootstrap;
import org.ascending.training.model.User;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBootstrap.class)
public class UserDaoImplTest {
    @Autowired
    private IUserDao userHibernateDao;

//    @Before
//    public void setUp() {
//        User user = new User();
//        user.setId(1);
//        user.setName("Tengfei");
//        user.setPassword("password123");
//        user.setEmail("louie7679@gmail.com");
//    }
}
