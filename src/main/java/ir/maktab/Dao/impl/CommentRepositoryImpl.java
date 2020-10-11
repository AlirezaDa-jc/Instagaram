package ir.maktab.Dao.impl;

import ir.maktab.base.repository.impl.BaseRepositoryImpl;
import ir.maktab.domains.Comment;
import ir.maktab.domains.Post;
import ir.maktab.domains.User;
import ir.maktab.Dao.CommentRepository;

import javax.persistence.TypedQuery;
import java.util.List;

public class CommentRepositoryImpl extends BaseRepositoryImpl<Comment,Long> implements CommentRepository {
    @Override
    protected Class<Comment> getEntityClass() {
        return Comment.class;
    }

    @Override
    public List<Post> findByUser(User u) {
        em.getTransaction().begin();
        TypedQuery<Post> query = em.createQuery(
                "SELECT u.post FROM Comment u where u.user=:userName",
                Post.class);

        query.setParameter("userName", u);
        List<Post> resultList = query.getResultList();
        em.getTransaction().commit();
        if (resultList.size() > 0) {
            return resultList;
        }
        return null;
    }
}
