package ir.maktab.services;

import ir.maktab.base.services.BaseService;
import ir.maktab.domains.Post;

public interface PostService extends BaseService<Post, Long> {
    void save();

    void displayLikedPosts();

    void displayDailyPosts();

    //    void displayCommentedPosts();

    void displayUsersPosts();

    void displayFollowingsPosts();

    void displayTrends();

    void edit();

}
