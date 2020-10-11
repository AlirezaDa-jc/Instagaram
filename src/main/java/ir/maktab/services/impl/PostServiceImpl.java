package ir.maktab.services.impl;

import ir.maktab.MyApp;
import ir.maktab.Scan;
import ir.maktab.base.services.impl.BaseServiceImpl;
import ir.maktab.domains.Post;
import ir.maktab.domains.User;
import ir.maktab.repository.PostRepository;
import ir.maktab.repository.impl.PostRepositoryImpl;
import ir.maktab.services.PostService;

import java.util.Date;

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
    public void getLikedPosts() {
        User u = UserServiceImpl.getUser();
        baseRepository.findPostsUserLiked(u);


    }
}
