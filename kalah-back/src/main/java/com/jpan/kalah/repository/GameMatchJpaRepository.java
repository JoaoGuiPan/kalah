package com.jpan.kalah.repository;

import com.jpan.kalah.common.CreateRepository;
import com.jpan.kalah.common.ListRepository;
import com.jpan.kalah.common.UpdateRepository;
import com.jpan.kalah.model.GameMatch;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GameMatchJpaRepository implements CreateRepository<GameMatch>, UpdateRepository<GameMatch>, ListRepository<GameMatch> {

    private GameMatchRepository repository;

    public GameMatchJpaRepository(
            GameMatchRepository repository
    ) {
        this.repository = repository;
    }

    @Override
    public GameMatch create(GameMatch entity) {
        return this.repository.save(entity);
    }

    @Override
    public GameMatch update(GameMatch entity) {
        return this.repository.save(entity);
    }

    @Override
    public List<GameMatch> listAll() {
        return this.repository.findAll();
    }
}
