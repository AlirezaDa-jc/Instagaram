package ir.maktab.base.repository;

import java.util.List;

public interface BaseRepository<E, PK extends Number> {


    E saveOrUpdate(E e);

    E findById(PK id);

    void deleteById(PK id);

    List<E> findAll();

    void delete(E e);

    void resetCache();

     void  refresh(Object c);
}
