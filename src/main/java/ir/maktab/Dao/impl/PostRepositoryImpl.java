package ir.maktab.Dao.impl;

import ir.maktab.Dao.PostRepository;
import ir.maktab.base.repository.impl.BaseRepositoryImpl;
import ir.maktab.domains.Post;

import javax.persistence.TypedQuery;
import java.util.List;


public class PostRepositoryImpl extends BaseRepositoryImpl<Post,Long> implements PostRepository {
    @Override
    protected Class<Post> getEntityClass() {
        return Post.class;
    }

    @Override
    public int findMaximumLike() {
        em.getTransaction().begin();
        TypedQuery<Integer> query = em.createQuery(
                "SELECT max(likesUser.size) FROM Post u",
                Integer.class);

        if (query.getResultList().size() > 0) {
            Integer u = query.getResultList().get(0);
            em.getTransaction().commit();
            return u;
        }
        em.getTransaction().rollback();
        return -1;
    }

    @Override
    public List<Post> findTrends(int max) {
        em.getTransaction().begin();
        TypedQuery<Post> query = em.createQuery(
                "SELECT u FROM Post u where u.likesUser.size>:avg",
                Post.class);
        query.setParameter("avg",max/2);
        if (query.getResultList().size() > 0) {
            java.util.List<Post> resultList = query.getResultList();
            em.getTransaction().commit();
            return resultList;
        }
        em.getTransaction().rollback();
        return null;
    }
}
