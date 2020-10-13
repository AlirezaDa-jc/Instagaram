package ir.maktab.base.repository.impl;

import ir.maktab.base.EntityManagerGenerator;
import ir.maktab.base.repository.BaseRepository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public abstract class BaseRepositoryImpl<E, PK extends Number> implements BaseRepository<E, PK> {
    protected abstract Class<E> getEntityClass();

    protected EntityManager em = EntityManagerGenerator.getEntityManager();

    @Override
    public void flush() {
        em.getTransaction().begin();
        em.clear();
        em.getTransaction().commit();
    }

    @Override
    public E saveOrUpdate(E e) {
        em.getTransaction().begin();
        if (em.contains(e)) {
            em.merge(e);
        } else {
            em.persist(e);
        }
        em.getTransaction().commit();
        return e;
    }

    @Override
    public E findById(PK id) {
        return em.find(getEntityClass(), id);
    }

    @Override
    public void deleteById(PK id) {
        E e = findById(id);
        delete(e);
    }

    @Override
    public List<E> findAll() {
        em.getTransaction().begin();
        TypedQuery<E> query = em.createQuery("select entity from " + getEntityClass().getName() + " entity", getEntityClass());
        List<E> resultList = query.getResultList();
        em.getTransaction().commit();
        return resultList;
    }


    @Override
    public void delete(E e) {
        em.getTransaction().begin();
        em.remove(e);
        em.getTransaction().commit();
    }

}
