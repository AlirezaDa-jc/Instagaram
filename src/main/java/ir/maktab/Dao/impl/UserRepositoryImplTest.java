package ir.maktab.Dao.impl;

import ir.maktab.Dao.UserRepository;
import ir.maktab.base.repository.impl.BaseRepositoryImpl;
import ir.maktab.domains.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class UserRepositoryImplTest extends BaseRepositoryImpl<User,Long> {
    private static User user;
    private static UserRepository userRepository;
    @BeforeAll
    static void beforeAll(){
        user = new User();
        user.setUserName("TestUser");
        user.setName("user");
        user.setPassword("user");
        userRepository= new UserRepositoryImpl();
        userRepository.saveOrUpdate(user);
    }

    @Test
    void findByUserName() {
        Assertions.assertEquals(user,userRepository.findByUserName(user.getUserName()));
    }

    @Test
    void userLogin() {
        Assertions.assertEquals(user,userRepository.userLogin(user.getUserName(),user.getPassword()));
    }

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }
    @AfterAll
    static void  afterAll(){
        userRepository.delete(user);
    }
}