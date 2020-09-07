package com.jpan.kalah.common;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchPageRepository<F, T> {
    Page<T> filterBy(F filter, Pageable pageable);
}
