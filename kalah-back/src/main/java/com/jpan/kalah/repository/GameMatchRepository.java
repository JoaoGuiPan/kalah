package com.jpan.kalah.repository;

import com.jpan.kalah.model.GameMatch;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GameMatchRepository extends CrudRepository<GameMatch, Long> {
    @Override
    List<GameMatch> findAll();
}
