package ir.maktab.services;

import ir.maktab.base.services.BaseService;
import ir.maktab.domains.Post;

public interface PostService extends BaseService<Post,Long> {
    void insert();

    void displayLikedPosts();

    void displayCommentedPosts();

    void displayUsersPosts();
}
