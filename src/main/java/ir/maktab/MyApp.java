package ir.maktab;

import ir.maktab.services.PostService;
import ir.maktab.services.UserService;
import ir.maktab.services.impl.PostServiceImpl;
import ir.maktab.services.impl.UserServiceImpl;
import ir.maktab.controller.menu.Menu;

public class MyApp {
    private static Scan sc = Scan.getInstance();
    private static UserService userService = new UserServiceImpl();
    private static PostService postService = new PostServiceImpl();

    public static PostService getPostService() {
        return postService;
    }

    public static UserService getUserService() {
        return userService;
    }

    public static Scan getSc() {
        return sc;
    }

    public static void main(String[] args) {
        while (true)
        if(loginOrSignIn())
        startApp();
    }

    private static boolean loginOrSignIn() {
        String choice = sc.getString("Sign In Or Login Or Exit: ");
        switch (choice.toUpperCase()) {
            case "SIGN IN":
                userService.signIn();
                return true;
            case "LOGIN":
                if (userService.login()) {
                    return true;
                }
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
