package ir.maktab.services;

import ir.maktab.base.services.BaseService;
import ir.maktab.domains.Comment;
import ir.maktab.domains.Post;
import ir.maktab.domains.User;

import java.util.List;

public interface CommentService extends BaseService<Comment,Long> {
    List<Post> getCommentedPosts();

    void addCommentToPost(Post post, User user);

    void delete(Comment comment);
}
