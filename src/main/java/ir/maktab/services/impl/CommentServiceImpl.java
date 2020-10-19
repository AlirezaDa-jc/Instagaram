package ir.maktab.services.impl;

import ir.maktab.Dao.CommentRepository;
import ir.maktab.Dao.impl.CommentRepositoryImpl;
import ir.maktab.MyApp;
import ir.maktab.Scan;
import ir.maktab.base.services.impl.BaseServiceImpl;
import ir.maktab.domains.Comment;
import ir.maktab.domains.Post;
import ir.maktab.domains.User;
import ir.maktab.services.CommentService;

import java.util.List;

public class CommentServiceImpl extends BaseServiceImpl<Comment, Long, CommentRepository> implements CommentService {
    private final Scan sc;
    public CommentServiceImpl() {
        CommentRepository repository = new CommentRepositoryImpl();
        super.setRepository(repository);
        sc = MyApp.getSc();
    }

    @Override
    public void addCommentToPost(Post c, User user) {
        Comment comment = new Comment();
        String content = sc.getString("Comment: ");
        comment.setComment(content);
        comment.setUser(user);
        comment.setPost(c);
    }

    @Override
    public void delete(Comment comment) {
        baseRepository.delete(comment);
    }

    @Override
    public List<Post> getCommentedPosts() {
        User u = UserServiceImpl.getUser();
        return baseRepository.findByUser(u);
    }
}
