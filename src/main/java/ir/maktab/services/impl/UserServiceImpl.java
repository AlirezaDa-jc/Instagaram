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

    public static void setUser(User user) {
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
        followers.forEach(System.out::println);
    }

    @Override
    public void displayFollowings() {
        Set<User> followings = user.getFollowings();
        followings.forEach(System.out::println);
    }

    @Override
    public void edit() {
        String choice = sc.getString("Choose The One You Want To Edit:(name,password,username) : ").toLowerCase();
        switch (choice){
            case "username":
                updateUserName();
                break;
            case "name":
                updateName();
                break;
            case "password":
                updatePassword();
                break;
            default:
                System.out.println("Invalid Input!");
        }
    }

    @Override
    public void unFollow() {
        displayFollowings();
        String userName = sc.getString("Username Of User You Want To UnFollow");
        User following = findByUserName(userName);
        user.removeFollowing(following);
        following.removeFollower(user);
        saveOrUpdate(following);
        saveOrUpdate(user);
        System.out.println("Done");
    }

    private void updatePassword() {
        String currentPassword = sc.getString("Current Password: ");
        if(!currentPassword.equals(user.getPassword())){
            System.out.println("Wrong Password");
            return;
        }
        String password = sc.getString("Password: ");
        user.setPassword(password);
        saveOrUpdate(user);
    }

    private void updateName() {
        String name = sc.getString("Name: ");
        user.setName(name);
        saveOrUpdate(user);
    }

    private void updateUserName() {
        String userName = sc.getString("UserName: ");
        user.setUserName(userName);
        try{
            saveOrUpdate(user);
        }catch (Exception ex){
            System.out.println("Error While Updating");
        }
    }


    @Override
    public boolean signIn() {
        try {
            User user = new User();
            String userName = sc.getString("Username: ");
            user.setUserName(userName);
            String name = sc.getString("Name: ");
            user.setName(name);
            String password = sc.getString("Password: ");
            user.setPassword(password);
            user.setDate(new Date());
            baseRepository.saveOrUpdate(user);
            setUser(user);
            return true;
        }catch (Exception ex){
            System.out.println("Invalid Or Duplicate Entries!");
            return false;
        }
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
        System.out.println("Following: " + following);
    }

    @Override
    public void exit() {
        user.setDate(new Date());
        baseRepository.saveOrUpdate(user);
    }

}
