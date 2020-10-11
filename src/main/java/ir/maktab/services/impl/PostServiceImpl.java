package ir.maktab.services.impl;

import ir.maktab.MyApp;
import ir.maktab.Scan;
import ir.maktab.base.services.impl.BaseServiceImpl;
import ir.maktab.domains.Comment;
import ir.maktab.domains.Post;
import ir.maktab.domains.User;
import ir.maktab.repository.PostRepository;
import ir.maktab.repository.impl.PostRepositoryImpl;
import ir.maktab.services.PostService;
import ir.maktab.services.UserService;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;


public class PostServiceImpl extends BaseServiceImpl<Post,Long, PostRepository> implements PostService {
    private Scan sc;
    public PostServiceImpl() {
        PostRepository postRepository = new PostRepositoryImpl();
        sc = MyApp.getSc();
        super.setRepository(postRepository);
    }

    @Override
    public void insert() {
        String content = sc.getString("Contents Of Post: ");
        Post post = new Post();
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
        Set<Post> posts = u.getPosts();
        posts.forEach(System.out::println);
    }

}
