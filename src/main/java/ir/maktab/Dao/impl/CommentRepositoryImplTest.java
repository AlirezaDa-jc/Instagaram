package ir.maktab.Dao.impl;

import ir.maktab.Dao.CommentRepository;
import ir.maktab.Dao.PostRepository;
import ir.maktab.Dao.UserRepository;
import ir.maktab.base.repository.impl.BaseRepositoryImpl;
import ir.maktab.domains.Comment;
import ir.maktab.domains.Post;
import ir.maktab.domains.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

class CommentRepositoryImplTest extends BaseRepositoryImpl<Comment,Long> {
    private static PostRepository postRepository;
    private static User user;
    private static Post firstPost;
    private static Post secondPost;
    private static long id;
    private List<Post> posts = new LinkedList<>();
    private static UserRepository userRepository;
    private static CommentRepository commentRepository;

    @BeforeAll
    static void beforeAll() {
        postRepository = new PostRepositoryImpl();
        user = new User();
        user.setUserName("Test");
        user.setPassword("test");
        user.setName("test");
        userRepository = new UserRepositoryImpl();
        userRepository.saveOrUpdate(user);
        firstPost = new Post();
        firstPost.setContent("content Post First");
        firstPost.setUser(user);
        firstPost.setDate(new Date());
        postRepository.saveOrUpdate(firstPost);
        commentRepository = new CommentRepositoryImpl();
        Comment comment = new Comment();
        comment.setComment("comment");
        comment.setUser(user);
        comment.setPost(firstPost);
    }

    @Test
    void findByUser() {
        Assertions.assertEquals(firstPost,commentRepository.findByUser(user).get(0));
    }

    @Override
    protected Class<Comment> getEntityClass() {
        return Comment.class;
    }
    @AfterAll
    static void afterAll() {
        userRepository.delete(user);
        postRepository.delete(firstPost);
    }
}