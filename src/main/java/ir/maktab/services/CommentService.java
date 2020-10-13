package ir.maktab.services;

import ir.maktab.base.services.BaseService;
import ir.maktab.domains.Comment;
import ir.maktab.domains.Post;
import ir.maktab.domains.User;

public interface CommentService extends BaseService<Comment,Long> {
    void displayCommentedPosts();

    void addCommentToPost(Post post, User user);

    void delete(Comment comment);
}
