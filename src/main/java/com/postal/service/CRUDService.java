package com.postal.service;

import com.postal.exception.PostalRepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * Created by brakhar on 09.03.15.
 */
public abstract class CRUDService<T extends Serializable, ID extends Serializable>{

    final static Logger logger = LoggerFactory.getLogger(CRUDService.class);

    private JpaRepository repository;

    private Class<T> clazz;

    public CRUDService() {
    }

    public CRUDService(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<T> getList(){
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public boolean isExists(ID entityId){
        return repository.exists(entityId);
    }

    @Transactional(readOnly = true)
    public T getEntityById(ID entityId){
        return (T) repository.getOne(entityId);
    }

    @Transactional()
    public T insert(T entity) throws PostalRepositoryException {
        try {
            return (T) repository.save(entity);
        }catch (Exception ex){
            throw new PostalRepositoryException(ex.getMessage());
        }
    }

    @Transactional()
    public T update(T entity) throws PostalRepositoryException {
        try {
            return (T) repository.save(entity);
        }catch (Exception ex){
            throw new PostalRepositoryException(ex.getMessage());
        }
    }

    @Transactional()
    public void delete(ID id) throws PostalRepositoryException {
        try {
            repository.delete(id);
        }catch (Exception ex){
            throw new PostalRepositoryException(ex.getMessage());
        }
    }

    @Transactional()
    public Page<T> findAll(Pageable pageable){return repository.findAll(pageable);}

    public JpaRepository getRepository() {
        return repository;
    }
}
