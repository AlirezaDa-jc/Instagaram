package ir.maktab.services.impl;

import ir.maktab.MyApp;
import ir.maktab.Scan;
import ir.maktab.base.services.impl.BaseServiceImpl;
import ir.maktab.domains.Comment;
import ir.maktab.domains.Post;
import ir.maktab.domains.User;
import ir.maktab.repository.UserRepository;
import ir.maktab.repository.impl.UserRepositoryImpl;
import ir.maktab.services.PostService;
import ir.maktab.services.UserService;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;

public class UserServiceImpl extends BaseServiceImpl<User, Long, UserRepository> implements UserService {
    private static User user = new User();
    private Scan sc;
    private  PostService postService;
    private Consumer<Post> addLikeOrComment;
    public UserServiceImpl() {
        UserRepository repository = new UserRepositoryImpl();
        sc = MyApp.getSc();
        postService = MyApp.getPostService();
        //Consumer!
        addLikeOrComment = (c) -> {
            System.out.println(c);
            String choice = sc.getString("Comment Or Like Or Both Or Pass: ").toUpperCase();
            switch (choice) {
                case "LIKE":
                    c.addLike(user);
                    user.addPostLiked(c);
                    break;
                case "COMMENT":
                    addCommentToPost(c);
                    break;
                case "BOTH":
                    c.addLike(user);
                    addCommentToPost(c);
                    break;
                default:
            }
            if (!choice.equals("PASS")) {
                postService.saveOrUpdate(c);
            }
        };
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

    @Override
    public void displayFollowingsPosts() {
        displayFollowings();
        String userName = sc.getString("Username: ");
        User user = baseRepository.findByUserName(userName);
        user.getPosts()
                .forEach(addLikeOrComment);
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
        User user = new User();
        String userName = sc.getString("Username: ");
        user.setUserName(userName);
        String name = sc.getString("Name: ");
        user.setName(name);
        String password = sc.getString("Password: ");
        user.setPassword(password);
        baseRepository.saveOrUpdate(user);
        setUser(user);
        return true;
    }

    @Override
    public void displayDailyPosts() {
        Set<User> followings = user.getFollowings();
        followings.stream()
                .map(User::getPosts)
                .forEach(posts -> posts.stream()
                        .filter((c) -> c.getDate().compareTo(user.getDate()) > 0)
                        .forEach(addLikeOrComment));
    }

    private void addCommentToPost(Post c) {
        Comment comment = new Comment();
        String content = sc.getString("Comment: ");
        comment.setComment(content);
        comment.setUser(user);
        comment.setPost(c);
        System.out.println(comment);
    }


    @Override
    public User findByUsername(String userName) {
        return baseRepository.findByUserName(userName);
    }

    @Override
    public void findByUsername() {

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
