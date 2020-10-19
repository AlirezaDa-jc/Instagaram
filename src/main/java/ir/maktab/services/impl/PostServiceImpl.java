package ir.maktab.services.impl;

import ir.maktab.Dao.PostRepository;
import ir.maktab.Dao.impl.PostRepositoryImpl;
import ir.maktab.MyApp;
import ir.maktab.Scan;
import ir.maktab.base.services.impl.BaseServiceImpl;
import ir.maktab.domains.Comment;
import ir.maktab.domains.Post;
import ir.maktab.domains.User;
import ir.maktab.services.CommentService;
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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;


public class PostServiceImpl extends BaseServiceImpl<Post, Long, PostRepository> implements PostService {
    private final Scan sc;
    private final Consumer<Post> addLikeOrComment;
    private final Consumer<Post> displayPost;
    private final UserService userService;
    private final CommentService commentService;
    private final User user;

    public PostServiceImpl() {
        PostRepository postRepository = new PostRepositoryImpl();
        sc = MyApp.getSc();
        userService = MyApp.getUserService();
        commentService = new CommentServiceImpl();
        user = UserServiceImpl.getUser();
        //Consumer!
        displayPost = (c) -> {
            if (c.getImage() != null) {
                byte[] img = c.getImage();
                File file = new File("output.jpg");
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(img);
                    fos.close();
                    Desktop desktop = Desktop.getDesktop();
                    desktop.open(new File("output.jpg"));
                    System.out.println(c);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (c.getVideo() != null) {
                byte[] video = c.getVideo();
                File file = new File("output.mp4");
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(video);
                    fos.close();
                    Desktop desktop = Desktop.getDesktop();
                    desktop.open(new File("output.mp4"));
                    System.out.println(c);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println(c);
            }
        };
        addLikeOrComment = (c) -> {
            String choice = sc.getString("Comment Or Like Or Both Or Pass: ").toUpperCase();
            switch (choice) {
                case "LIKE":
                    c.addLike(user);
                    user.addPostLiked(c);
                    break;
                case "COMMENT":
                    commentService.addCommentToPost(c, user);
                    break;
                case "BOTH":
                    c.addLike(user);
                    commentService.addCommentToPost(c, user);
                    break;
                default:
            }
            if (!choice.equals("PASS")) {
                baseRepository.saveOrUpdate(c);
            }

        };
        super.setRepository(postRepository);
    }

    @Override
    public void save() {
        String content = sc.getString("Contents Of Post: ");
        Post post = new Post();
        char choice = sc.getString("Add Media Y/N:").toUpperCase().charAt(0);
        if (choice=='Y') {
            String mediaChoice = sc.getString("Image or Video:").toUpperCase();
            String path = sc.getString("Path: ");
            File file = new File(path);
            byte[] bFile = new byte[(int) file.length()];
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                fileInputStream.read(bFile);
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            switch (mediaChoice) {
                case "IMAGE":
                    post.setImage(bFile);
                    break;
                case "VIDEO":
                    post.setVideo(bFile);
            }
        }
        post.setContent(content);
        UserServiceImpl.getUser().addPost(post);
        post.setUser(UserServiceImpl.getUser());
        post.setDate(new Date());
        baseRepository.saveOrUpdate(post);
    }

    @Override
    public void displayLikedPosts() {
        User u = UserServiceImpl.getUser();
        Set<Post> postsLiked = u.getPostsLiked();
        if (postsLiked != null) {
            int i = 0;
            Iterator<Post> it = postsLiked.iterator();
            while (it.hasNext() && i < 5) {
                it.forEachRemaining(displayPost.andThen(addLikeOrComment).andThen
                        ((c) -> deleteOutputFile()));
                i++;
            }
        } else {
            System.out.println("No Post Liked!");
        }
    }

    @Override
    public void displayFollowingsPosts() {
        userService.displayFollowings();
        String userName = sc.getString("Username: ");
        User user = userService.findByUserName(userName);
        user.getPosts()
                .forEach(displayPost.andThen(addLikeOrComment).andThen
                        ((c) -> deleteOutputFile()));
        deleteOutputFile();
    }

    private void deleteOutputFile() {
        File file = new File("output.jpg");
        file.delete();
    }

    @Override
    public void displayTrends() {
        int max = baseRepository.findMaximumLike();
        List<Post> trends = baseRepository.findTrends(max);
        trends.forEach(displayPost.andThen(addLikeOrComment).andThen
                ((c) -> deleteOutputFile()));
        deleteOutputFile();
    }

    @Override
    public void edit() {
        List<Post> all = UserServiceImpl.getUser().getPosts();
        all.forEach(displayPost);
        int id = Integer.parseInt(sc.getString("Post ID: "));
        id--;
        try {
            Post post = all.get(id);
            if (post == null) return;
            String choice = sc.getString("Edit Content Or Delete Post?: (content,delete,deleteComment): ")
                    .toLowerCase();
            switch (choice) {
                case "content":
                    updateContent(post);
                    break;
                case "delete":
                    baseRepository.delete(post);
                    baseRepository.resetCache();
                    all.remove(post);
                    break;
                case "deletecomment":
                    all.remove(post);
                    deleteComment(post);
                    all.add(post);
                    break;
                default:
                    System.out.println("Invalid Input!");
            }
            UserServiceImpl.getUser().setPosts(all);
        } catch (IndexOutOfBoundsException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void deleteComment(Post post) {
        List<Comment> comments = post.getComments();
        comments.forEach(System.out::println);
        int id = Integer.parseInt(sc.getString("Comment ID:"));
        id--;
        try {
        Comment comment = comments.get(id);
        commentService.delete(comment);
        comments.remove(comment);
        post.setComments(comments);
        baseRepository.saveOrUpdate(post);
        } catch (Exception ex) {
            System.out.println("Invalid ID!");
        }
    }

    private void updateContent(Post post) {
        String content = sc.getString("New Content: ");
        post.setContent(content);
        saveOrUpdate(post);
    }

    @Override
    public void displayDailyPosts() {
        User user = UserServiceImpl.getUser();
        Set<User> followings = user.getFollowings();
        AtomicInteger i = new AtomicInteger();
        followings.stream()
                .map(User::getPosts)
                .forEach(posts -> {
                    posts.stream()
                            .filter((c) -> c.getDate().compareTo(user.getDate()) > 0)
                            .forEach(displayPost.andThen(addLikeOrComment).andThen((c) -> i.getAndIncrement()).andThen
                                    ((c) -> deleteOutputFile()));
                });
        if (i.get() == 0) {
            System.out.println("No Posts Till Now!");
        }
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
        posts.forEach(displayPost.andThen(addLikeOrComment).andThen
                ((c) -> deleteOutputFile()));
        deleteOutputFile();
    }

    @Override
    public void displayCommentedPosts() {
        List<Post> commentedPosts = commentService.getCommentedPosts();
        commentedPosts.forEach(displayPost.andThen(addLikeOrComment));
    }
}
