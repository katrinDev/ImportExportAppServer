package org.project.interfaces;

import java.util.List;

public interface DAO<T> {

    List<T> findAll();

    T findById(int id);

    void save(T obj);

    void update(T obj);

    void delete(T obj);
}
