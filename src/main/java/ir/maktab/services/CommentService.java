package ir.maktab.services;

import ir.maktab.base.services.BaseService;
import ir.maktab.domains.Comment;

public interface CommentService extends BaseService<Comment,Long> {
    void displayCommentedPosts();
}
