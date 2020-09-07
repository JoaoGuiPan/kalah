package com.jpan.kalah.repository;

import com.jpan.kalah.common.CreateRepository;
import com.jpan.kalah.common.DeleteRepository;
import com.jpan.kalah.common.UpdateRepository;
import com.jpan.kalah.model.GameMatch;
import org.springframework.stereotype.Repository;

@Repository
public class GameMatchJpaRepository implements CreateRepository<GameMatch>, UpdateRepository<GameMatch>, DeleteRepository<GameMatch> {

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
    public void delete(GameMatch entity) {
        this.repository.delete(entity);
    }

    @Override
    public GameMatch update(GameMatch entity) {
        return this.repository.save(entity);
    }
}
