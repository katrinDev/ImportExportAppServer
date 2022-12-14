package org.project.interfaces;

import java.util.List;

public interface DAO<T> {

    List<T> findAll();

    T findById(int id);

    int save(T obj);

    void update(T obj);

    void delete(T obj);
}
