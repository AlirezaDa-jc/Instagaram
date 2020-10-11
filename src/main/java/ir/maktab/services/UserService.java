package ir.maktab.services;

import ir.maktab.base.services.BaseService;
import ir.maktab.domains.User;

public interface UserService extends BaseService<User,Long> {
    void displayDailyPosts();

    boolean signIn();

    User findByUsername(String userName);

    void findByUsername();

    void follow();

    void exit();

    boolean login();

    void displayFollowers();

    void displayFollowings();

    void displayFollowingsPosts();
}
