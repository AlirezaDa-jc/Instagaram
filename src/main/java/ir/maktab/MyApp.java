package ir.maktab;

import ir.maktab.controller.menu.Menu;
import ir.maktab.services.CommentService;
import ir.maktab.services.PostService;
import ir.maktab.services.UserService;
import ir.maktab.services.impl.CommentServiceImpl;
import ir.maktab.services.impl.PostServiceImpl;
import ir.maktab.services.impl.UserServiceImpl;

public class MyApp {
    private static Scan sc = Scan.getInstance();
    private static UserService userService = new UserServiceImpl();
    private static PostService postService = new PostServiceImpl();
    private static CommentService commentService = new CommentServiceImpl();

    public static PostService getPostService() {
        return postService;
    }

    public static UserService getUserService() {
        return userService;
    }

    public static CommentService getCommentService() {
        return commentService;
    }

    public static Scan getSc() {
        return sc;
    }

    public static void main(String[] args) {
        while (true)
        if(loginOrSignIn()) {
            userService = new UserServiceImpl();
            postService = new PostServiceImpl();
            commentService = new CommentServiceImpl();
            startApp();
        }
    }

    private static boolean loginOrSignIn() {
        String choice = sc.getString("Sign In Or Login Or Exit: ");
        switch (choice.toUpperCase()) {
            case "SIGN IN":
                return userService.signIn();
            case "LOGIN":
                if (userService.login()) {
                    return true;
                }
                break;
            case "EXIT":
                System.exit(0);
                break;
            default:
                return false;
        }
        return false;

    }

    private static void startApp() {
        Menu menu = new Menu();
        menu.function();
    }
}
