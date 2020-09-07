package com.jpan.kalah.common;

public interface CreateRepository<T> {
    T create(T entity);
}
