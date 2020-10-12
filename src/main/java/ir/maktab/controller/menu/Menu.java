package ir.maktab.controller.menu;

import ir.maktab.MyApp;
import ir.maktab.Scan;
import ir.maktab.services.CommentService;
import ir.maktab.services.PostService;
import ir.maktab.services.UserService;

public class Menu {
    private int choice;
    private static Scan sc;
    private UserService userService;
    private PostService postService;
    private CommentService commentService;

    public Menu() {
        sc = MyApp.getSc();
        userService = MyApp.getUserService();
        postService = MyApp.getPostService();
        commentService = MyApp.getCommentService();
    }


    public void setChoice() {
        try {
            choice = Integer.parseInt(sc.getString("Enter A Number: "));
        } catch (NumberFormatException ignored) {
        }
    }

    public void display() {
        System.out.println("To Check Daily Posts Press 1 ");
        System.out.println("To Follow Someone Press 2");
        System.out.println("To See Posts Of One Of Your Followings Press 3");
        System.out.println("To Post To Your Profile Press 4");
        System.out.println("To See The Posts You Liked Press 5");
        System.out.println("To See Posts You Commented Press 6");
        System.out.println("To See Your Followers Press 7");
        System.out.println("To See Ones You Follow Press 8");
        System.out.println("To See Your Posts Press 9");
        System.out.println("To Edit Your Profile Press 10");
        System.out.println("To See Trends Press 11");
        System.out.println("To Edit Or Delete Your Posts Press 12");
        System.out.println("To Log Out Press 13");
    }


    public void function() {
        boolean flag = true;
        while (flag) {
            try {
                display();
                setChoice();
                switch (choice) {
                    case 1:
                        postService.displayDailyPosts();
                        break;
                    case 2:
                        userService.follow();
                        break;
                    case 3:
                        postService.displayFollowingsPosts();
                        break;
                    case 4:
                        postService.save();
                        break;
                    case 5:
                        postService.displayLikedPosts();
                        break;
                    case 6:
                        commentService.displayCommentedPosts();
                        break;
                    case 7:
                        userService.displayFollowers();
                        break;
                    case 8:
                        userService.displayFollowings();
                        break;
                    case 9:
                        postService.displayUsersPosts();
                        break;
                    case 10:
                        userService.edit();
                        break;
                    case 11:
                        postService.displayTrends();
                        break;
                    case 12:
                        postService.edit();
                        break;
                    case 13:
                        userService.exit();
                        flag = false;
                        break;
                }
            } catch (NullPointerException ex) {
                System.out.println("Invalid Inputs!");
            }
        }
    }
}
