package com.jpan.kalah.common;

public interface CreateService<E, O> {
    O create(E entity);
}
