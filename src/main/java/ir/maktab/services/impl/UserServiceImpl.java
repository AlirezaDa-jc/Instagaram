package ir.maktab.services.impl;

import ir.maktab.Dao.UserRepository;
import ir.maktab.Dao.impl.UserRepositoryImpl;
import ir.maktab.MyApp;
import ir.maktab.Scan;
import ir.maktab.base.services.impl.BaseServiceImpl;
import ir.maktab.domains.User;
import ir.maktab.services.PostService;
import ir.maktab.services.UserService;

import java.util.Date;
import java.util.Set;

public class UserServiceImpl extends BaseServiceImpl<User, Long, UserRepository> implements UserService {
    private static User user = new User();
    private Scan sc;
    private  PostService postService;
    public UserServiceImpl() {
        UserRepository repository = new UserRepositoryImpl();
        sc = MyApp.getSc();
        postService = MyApp.getPostService();

        super.setRepository(repository);
    }

    public UserServiceImpl(UserRepository repository) {
        super(repository);
    }

    public static User getUser() {
        return user;
    }

    public void setUser(User user) {
        UserServiceImpl.user = user;
    }

    @Override
    public boolean login() {
        String userName = sc.getString("Username: ");
        String password = sc.getString("Password: ");
        user = baseRepository.userLogin(userName, password);
        return user != null;
    }

    @Override
    public void displayFollowers() {
        Set<User> followers = user.getFollowers();
        iteratingUsersSet(followers);
    }

    @Override
    public void displayFollowings() {
        Set<User> followings = user.getFollowings();
        iteratingUsersSet(followings);
    }

    private void iteratingUsersSet(Set<User> users) {
        if (users != null) {
            users.forEach(System.out::println);
        } else {
            System.out.println("No Post Commented");
        }
    }

    @Override
    public boolean signIn() {
        String userName = sc.getString("Username: ");
        user.setUserName(userName);
        String name = sc.getString("Name: ");
        user.setName(name);
        String password = sc.getString("Password: ");
        user.setPassword(password);
        baseRepository.saveOrUpdate(user);
        return true;
    }

    @Override
    public User findByUserName(String userName) {
        return baseRepository.findByUserName(userName);
    }

    @Override
    public void follow() {
        String userName = sc.getString("Username: ");
        User following = baseRepository.findByUserName(userName);
        following.addFollower(user);
        baseRepository.saveOrUpdate(following);
        baseRepository.saveOrUpdate(user);
    }

    @Override
    public void exit() {
        user.setDate(new Date());
        baseRepository.saveOrUpdate(user);
    }

}
