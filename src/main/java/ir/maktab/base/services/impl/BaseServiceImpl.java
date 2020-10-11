package ir.maktab.base.services.impl;

import ir.maktab.base.repository.BaseRepository;
import ir.maktab.base.services.BaseService;

import java.util.List;

public class BaseServiceImpl<E, PK extends Number, Repository extends BaseRepository<E, PK>> implements
        BaseService<E, PK> {

    protected Repository baseRepository;

    public BaseServiceImpl() {
    }

    public BaseServiceImpl(Repository baseRepository) {
        this.baseRepository = baseRepository;
    }

    @Override
    public E saveOrUpdate(E e) {
        return baseRepository.saveOrUpdate(e);
    }

    @Override
    public E findById(PK id) {
        return baseRepository.findById(id);
    }

    @Override
    public void deleteById(PK id) {
        baseRepository.deleteById(id);
    }

//    @Override
//    public void deleteAll() {
//        baseRepository.deleteAll();
//    }

    @Override
    public List<E> findAll() {
        return baseRepository.findAll();
    }


    protected void setRepository(Repository repository) {
        baseRepository = repository;
    }
}

