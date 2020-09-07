package com.jpan.kalah.common;

public interface UpdateService<E, P, O> {
    O update(E entity, P payload);
}
