package io.company.brewcraft.repository;

import java.util.List;

public interface BaseRepository<T, V> {
    List<T> findAll();

    V save(T entity);

    T findById(V id);

    int deleteById(V id);
}
