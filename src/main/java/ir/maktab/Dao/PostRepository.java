package ir.maktab.Dao;

import ir.maktab.base.repository.BaseRepository;
import ir.maktab.domains.Post;

import java.util.List;


public interface PostRepository extends BaseRepository<Post,Long> {

    int findMaximumLike();

    List<Post> findTrends(int max);
}
