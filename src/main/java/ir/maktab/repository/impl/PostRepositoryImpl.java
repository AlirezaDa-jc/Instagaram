package ir.maktab.repository.impl;

import ir.maktab.base.repository.impl.BaseRepositoryImpl;
import ir.maktab.domains.Post;
import ir.maktab.domains.User;
import ir.maktab.repository.PostRepository;

import java.util.List;

public class PostRepositoryImpl extends BaseRepositoryImpl<Post,Long> implements PostRepository {
    @Override
    protected Class<Post> getEntityClass() {
        return Post.class;
    }

    @Override
    public List<Post> findPostsUserLiked(User u) {
//        em.getTransaction().begin();
//        TypedQuery<Long> query = em.createQuery(
//                "SELECT u FROM user_like u where u.user_id:name",
//                Long.class);
//
//        query.setParameter("name", u.getId());
//        if (query.getResultList().size() > 0) {
//            User u = query.getSingleResult();
//            System.out.println("Welcome BaCk!" + u.getName() + " ^_^ ");
//            em.getTransaction().commit();
//            return u;
//        }
//        em.getTransaction().rollback();
        //Todo
        return null;
    }
}
