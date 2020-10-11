package ir.maktab.repository;

import ir.maktab.base.repository.BaseRepository;
import ir.maktab.domains.Comment;
import ir.maktab.domains.Post;
import ir.maktab.domains.User;

import java.util.List;

public interface CommentRepository extends BaseRepository<Comment,Long> {
    List<Post> findByUser(User u);
}
