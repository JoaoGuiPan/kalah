package com.jpan.kalah.service;

import com.jpan.kalah.common.CreateRepository;
import com.jpan.kalah.common.CreateService;
import com.jpan.kalah.common.UpdateRepository;
import com.jpan.kalah.common.UpdateService;
import com.jpan.kalah.model.GameMatch;
import org.springframework.stereotype.Service;

@Service
public class GameMatchService implements CreateService<GameMatch, GameMatch>,
        UpdateService<GameMatch, GameMatch, GameMatch> {

    private CreateRepository<GameMatch> createMatch;

    private UpdateRepository<GameMatch> updateMatch;

    public GameMatchService(
            CreateRepository<GameMatch> createMatch,
            UpdateRepository<GameMatch> updateMatch
    ) {
        this.createMatch = createMatch;
        this.updateMatch = updateMatch;
    }

    @Override
    public GameMatch create(GameMatch entity) {
        return this.createMatch.create(entity);
    }

    @Override
    public GameMatch update(GameMatch entity, GameMatch payload) {
        return this.updateMatch.update(entity);
    }
}
