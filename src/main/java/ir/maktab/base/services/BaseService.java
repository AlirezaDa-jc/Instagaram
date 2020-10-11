package ir.maktab.base.services;

import java.io.Serializable;
import java.util.List;

public interface BaseService<E, PK extends Serializable> {

    E saveOrUpdate(E e);

    E findById(PK id);

    void deleteById(PK id);

//    void deleteAll();

    List<E> findAll();

}

