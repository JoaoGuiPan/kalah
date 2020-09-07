package com.jpan.kalah.common;

public interface DeleteRepository<T> {
    void delete(T entity);
}
