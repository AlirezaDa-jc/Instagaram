package ir.maktab.Dao.impl;

import ir.maktab.Dao.PostRepository;
import ir.maktab.Dao.UserRepository;
import ir.maktab.base.repository.impl.BaseRepositoryImpl;
import ir.maktab.domains.Post;
import ir.maktab.domains.User;
import org.junit.jupiter.api.*;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PostRepositoryImplTest extends BaseRepositoryImpl<Post, Long> {
    private static PostRepository postRepository;
    private static User user;
    private static Post firstPost;
    private static Post secondPost;
    private static long id;
    private List<Post> posts = new LinkedList<>();
    private static UserRepository userRepository;

    @Override
    protected Class<Post> getEntityClass() {
        return Post.class;
    }

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
        secondPost = new Post();
        secondPost.setContent("content Post Second");
        secondPost.setUser(user);
        secondPost.setDate(new Date());
    }

    @Order(1)
    @Test
    void testSaveOrUpdate() {
        postRepository.saveOrUpdate(firstPost);
        postRepository.saveOrUpdate(secondPost);
        id = firstPost.getId();
        posts.add(firstPost);
        posts.add(secondPost);
    }

    @Order(2)
    @Test
    void testFindById() {
        assertEquals(firstPost, postRepository.findById(id));
    }

    @Order(7)
    @Test
    void testDeleteById() {
        postRepository.deleteById(secondPost.getId());
    }

    @Order(3)
    @Test
    void testFindAll() {
        assertEquals(9, postRepository.findAll().size());
    }

    @Order(6)
    @Test
    void testDelete() {
        postRepository.delete(firstPost);
    }

    @Order(4)
    @Test
    void findMaximumLike() {
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setUserName("i" + new Date());
            user.setName("i" + new Date());
            user.setPassword("i" + new Date());
            userRepository.saveOrUpdate(user);
            firstPost.addLike(user);
            userRepository.delete(user);
        }
        assertEquals(10, postRepository.findMaximumLike());
    }

    @Order(5)
    @Test
    void findTrends() {
        assertEquals(firstPost, postRepository.findTrends(10).get(0));
    }

    @AfterAll
    static void afterAll() {
        userRepository.delete(user);
    }

}