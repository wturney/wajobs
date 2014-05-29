package com.wtl.wawork.core.persistence.repository.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.wtl.wawork.core.persistence.repository.CrudRepository;

@Repository
public abstract class CrudRepositoryJpa<T> implements CrudRepository<T> {

    @Autowired
    protected EntityManager em;

    private Class<T> type;

    @SuppressWarnings("unchecked")
    public CrudRepositoryJpa() {
        super();

        final Type[] types = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments();
        if (types[0] instanceof ParameterizedType) {
            final ParameterizedType pt = (ParameterizedType) types[0];
            type = (Class<T>) pt.getRawType();
        } else {
            type = (Class<T>) types[0];
        }
    }

    @Override
    public void create(T entity) {
        em.persist(entity);
    }

    @Override
    public void delete(T entity) {
        em.remove(entity);
    }

    @Override
    public void deleteWithPrimaryKey(Object primaryKey) {
        T entity = em.find(type, primaryKey);
        em.remove(entity);
    }

    @Override
    public T find(Object primaryKey) {
        return em.find(type, primaryKey);
    }

    @Override
    public void update(T entity) {
        em.merge(entity);
    }

}