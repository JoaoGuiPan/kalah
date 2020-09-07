package com.jpan.kalah.service;

import com.jpan.kalah.command.ComputerMoveCommand;
import com.jpan.kalah.command.PlayerMoveCommand;
import com.jpan.kalah.common.CreateRepository;
import com.jpan.kalah.common.CreateService;
import com.jpan.kalah.common.UpdateRepository;
import com.jpan.kalah.common.UpdateService;
import com.jpan.kalah.dto.StartGameDto;
import com.jpan.kalah.model.GameMatch;
import org.springframework.stereotype.Service;

import java.util.Random;

import static com.jpan.kalah.common.CONSTANTS.COMPUTER_PLAYER;

@Service
public class GameMatchService implements CreateService<StartGameDto, GameMatch>,
        UpdateService<GameMatch, Integer, GameMatch> {

    final private CreateRepository<GameMatch> createMatch;
    final private UpdateRepository<GameMatch> updateMatch;

    public GameMatchService(
            CreateRepository<GameMatch> createMatch,
            UpdateRepository<GameMatch> updateMatch
    ) {
        this.createMatch = createMatch;
        this.updateMatch = updateMatch;
    }

    @Override
    public GameMatch create(StartGameDto entity) {
        final GameMatch gameMatch = new GameMatch(entity.getSouthPlayer(), entity.getNorthPlayer());

        setFirstTurnPlayer(entity, gameMatch);

        return this.createMatch.create(gameMatch);
    }

    private void setFirstTurnPlayer(StartGameDto entity, GameMatch gameMatch) {
        int startingToss = new Random().ints(0, 100).findFirst().getAsInt();
        gameMatch.setCurrentTurnPlayer(startingToss < 50 ? entity.getNorthPlayer() : entity.getSouthPlayer());
    }

    @Override
    public GameMatch update(GameMatch entity, Integer payload) {

        if (entity.getCurrentTurnPlayer().equalsIgnoreCase(COMPUTER_PLAYER)) {
            new ComputerMoveCommand(entity).execute();
        } else if (payload != null) {
            new PlayerMoveCommand(payload, entity).execute();
        }

        return updateMatch.update(entity);
    }
}
