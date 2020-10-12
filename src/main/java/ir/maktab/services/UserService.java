package ir.maktab.services;

import ir.maktab.base.services.BaseService;
import ir.maktab.domains.User;

public interface UserService extends BaseService<User,Long> {
    boolean signIn();

    User findByUserName(String userName);

    void follow();

    void exit();

    boolean login();

    void displayFollowers();

    void displayFollowings();

    void edit();
}
