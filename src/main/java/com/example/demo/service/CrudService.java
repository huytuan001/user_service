package com.example.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CrudService<T, ID> {
    T get(ID id);

    List<T> findAll();

    Page<T> findAll(Pageable pageable);

    T create(T entity);

    T update(ID id, T entity);

    void delete(T entity);

    void deleteById(ID id);

    Long count();
}
