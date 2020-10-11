package ir.maktab.services.impl;

import ir.maktab.base.services.impl.BaseServiceImpl;
import ir.maktab.domains.Comment;
import ir.maktab.domains.Post;
import ir.maktab.domains.User;
import ir.maktab.Dao.CommentRepository;
import ir.maktab.Dao.impl.CommentRepositoryImpl;
import ir.maktab.services.CommentService;

import java.util.List;

public class CommentServiceImpl extends BaseServiceImpl<Comment,Long, CommentRepository> implements CommentService {
    public CommentServiceImpl() {
        CommentRepository repository = new CommentRepositoryImpl();
        super.setRepository(repository);
    }

    @Override
    public void displayCommentedPosts() {
        User u = UserServiceImpl.getUser();
        List<Post> posts = baseRepository.findByUser(u);
        posts.forEach(System.out::println);
    }
}
