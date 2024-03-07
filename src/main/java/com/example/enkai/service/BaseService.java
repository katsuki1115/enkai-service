package com.example.enkai.service;

import java.util.List;

import com.example.enkai.common.DataNotFoundException;
import com.example.enkai.entity.User;

public interface BaseService<T> {
    public List<T> findAll();

    public T findById(Integer id) throws DataNotFoundException;

    public User save(T t);

    public void deleteById(Integer id);
}