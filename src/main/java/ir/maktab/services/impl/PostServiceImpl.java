package ir.maktab.services.impl;

import ir.maktab.Dao.PostRepository;
import ir.maktab.Dao.impl.PostRepositoryImpl;
import ir.maktab.MyApp;
import ir.maktab.Scan;
import ir.maktab.base.services.impl.BaseServiceImpl;
import ir.maktab.domains.Comment;
import ir.maktab.domains.Post;
import ir.maktab.domains.User;
import ir.maktab.services.PostService;
import ir.maktab.services.UserService;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;


public class PostServiceImpl extends BaseServiceImpl<Post,Long, PostRepository> implements PostService {
    private Scan sc;
    private Consumer<Post> addLikeOrComment;
    private Consumer<Post> displayPost;
    private UserService userService;
    public PostServiceImpl() {
        PostRepository postRepository = new PostRepositoryImpl();
        sc = MyApp.getSc();
        userService = MyApp.getUserService();
        //Consumer!
        displayPost = (c) -> {
            if (c.getImage() == null){
                System.out.println(c);
            }else {
                byte[] img = c.getImage();
                try {
                    FileOutputStream fos = new FileOutputStream("output.jpg");
                    fos.write(img);
                    fos.close();
                    Desktop desktop = Desktop.getDesktop();
                    desktop.open(new File("output.jpg"));
                    System.out.println(c);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        addLikeOrComment = (c) -> {
            String choice = sc.getString("Comment Or Like Or Both Or Pass: ").toUpperCase();
            User user = UserServiceImpl.getUser();
            switch (choice) {
                case "LIKE":
                    c.addLike(user);
                    user.addPostLiked(c);
                    break;
                case "COMMENT":
                    addCommentToPost(c,user);
                    break;
                case "BOTH":
                    c.addLike(user);
                    addCommentToPost(c,user);
                    break;
                default:
            }
            if (!choice.equals("PASS")) {
                baseRepository.saveOrUpdate(c);
            }
            File file = new File("output.jpg");
            file.delete();
        };
        super.setRepository(postRepository);
    }

    @Override
    public void save() {
        String content = sc.getString("Contents Of Post: ");
        Post post = new Post();
        char choice = sc.getString("Add Image: Y/N : ").toUpperCase().charAt(0);
        if(choice == 'Y'){
            String path = sc.getString("Path: ").toLowerCase();
            File file = new File(path);
            byte[] bFile = new byte[(int) file.length()];
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                fileInputStream.read(bFile);
                fileInputStream.close();
                post.setImage(bFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        post.setContent(content);
        post.setUser(UserServiceImpl.getUser());
        post.setDate(new Date());
        baseRepository.saveOrUpdate(post);
    }

    @Override
    public void displayLikedPosts() {
        User u = UserServiceImpl.getUser();
        Set<Post> postsLiked = u.getPostsLiked();
        if(postsLiked != null){
            int i = 0;
            Iterator<Post> it = postsLiked.iterator();
            while(it.hasNext() && i < 5){
                System.out.println(it.next());
                i++;
            }
        }else{
            System.out.println("No Post Liked!");
        }
    }

    @Override
    public void displayFollowingsPosts() {
        userService.displayFollowings();
        String userName = sc.getString("Username: ");
        User user = userService.findByUserName(userName);
        user.getPosts()
                .forEach(displayPost.andThen(addLikeOrComment));
    }

    @Override
    public void displayTrends() {
        int max = baseRepository.findMaximumLike();
        List<Post> trends = baseRepository.findTrends(max);
        trends.forEach(displayPost.andThen(addLikeOrComment));
    }

    @Override
    public void edit() {
        List<Post> all = UserServiceImpl.getUser().getPosts();
        all.forEach(displayPost);
        int id  = Integer.parseInt(sc.getString("Post ID: "));
        id--;
        Post post = all.get(id);
        if(post == null) return;
        String choice = sc.getString("Edit Content Or Delete Post?: (content,delete): ").toLowerCase();
        switch (choice){
            case "content":
                updateContent(post);
                break;
            case "delete":
               baseRepository.delete(post);
                break;
            default:
                System.out.println("Invalid Input!");
        }
    }

    private void updateContent(Post post) {
        String content = sc.getString("New Content: ");
        post.setContent(content);
        saveOrUpdate(post);
    }

    @Override
    public void displayDailyPosts() {
        try {
            User user = UserServiceImpl.getUser();
            Set<User> followings = user.getFollowings();
            followings.stream()
                    .map(User::getPosts)
                    .forEach(posts -> posts.stream()
                            .filter((c) -> c.getDate().compareTo(user.getDate()) > 0)
                            .forEach(displayPost.andThen(addLikeOrComment)));
        }catch (Exception ex){
            System.out.println("No Posts!");
        }
    }

    private void addCommentToPost(Post c,User user) {
        Comment comment = new Comment();
        String content = sc.getString("Comment: ");
        comment.setComment(content);
        comment.setUser(user);
        comment.setPost(c);
    }
//    @Override
//    public void displayCommentedPosts() {
//        User u = UserServiceImpl.getUser();
//        Set<Comment> comments = u.getComments();
//        if(comments != null){
//            int i = 0;
//            Iterator<Comment> it = comments.iterator();
//            while(it.hasNext() && i < 5){
//                System.out.println(it.next().getPost());
//                i++;
//            }
//        }else{
//            System.out.println("No Post Commented");
//        }
//    }

    @Override
    public void displayUsersPosts() {
        User u = UserServiceImpl.getUser();
        displayPosts(u);
    }

    private void displayPosts(User u) {
        List<Post> posts = u.getPosts();
        posts.forEach(displayPost.andThen(addLikeOrComment));
    }

}
