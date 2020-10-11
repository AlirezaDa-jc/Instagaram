package ir.maktab.repository;

import ir.maktab.base.repository.BaseRepository;
import ir.maktab.domains.Post;
import ir.maktab.domains.User;

import java.util.List;

public interface PostRepository extends BaseRepository<Post,Long> {
    List<Post> findPostsUserLiked(User u);

    List<Post> findPostsUserCommented(User u);
}
