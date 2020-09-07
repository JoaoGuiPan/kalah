package com.jpan.kalah.common;

public interface UpdateRepository<T> {
    T update(T entity);
}
