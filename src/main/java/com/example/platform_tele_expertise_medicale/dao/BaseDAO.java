package com.example.platform_tele_expertise_medicale.dao;

import java.util.List;

public interface BaseDAO<T, ID> {
    void save(T entity);
    T findById(ID id);
    List<T> findAll();
    void update(T entity);
    void delete(ID id);
}