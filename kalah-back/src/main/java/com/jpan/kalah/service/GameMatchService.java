package com.jpan.kalah.service;

import com.jpan.kalah.command.ComputerMoveCommand;
import com.jpan.kalah.command.PlayerMoveCommand;
import com.jpan.kalah.common.*;
import com.jpan.kalah.dto.GameMatchDto;
import com.jpan.kalah.dto.StartGameDto;
import com.jpan.kalah.model.GameMatch;
import org.springframework.stereotype.Service;

import java.util.Random;

import static com.jpan.kalah.common.CONSTANTS.COMPUTER_PLAYER;
import static com.jpan.kalah.dto.EntityDtoMapper.fromDto;
import static com.jpan.kalah.dto.EntityDtoMapper.toDto;

@Service
public class GameMatchService implements CreateService<StartGameDto, GameMatchDto>,
        UpdateService<GameMatch, Integer, GameMatchDto> {

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
    public GameMatchDto create(StartGameDto entity) {

        final GameMatchDto matchDto = new GameMatchDto(entity.getSouthPlayer(), entity.getNorthPlayer());

        setFirstTurnPlayer(entity, matchDto);

        final GameMatch match = createMatch.create(fromDto(matchDto));

        return toDto(match);
    }

    @Override
    public GameMatchDto update(GameMatch entity, Integer payload) {

        Command command;

        GameMatchDto dto = toDto(entity);

        if (entity.getCurrentTurnPlayer().equalsIgnoreCase(COMPUTER_PLAYER)) {
            command = new ComputerMoveCommand(dto);
        } else {
            command = new PlayerMoveCommand(payload, dto);
        }

        command.execute();

        final GameMatch match = updateMatch.update(fromDto(dto));

        return toDto(match);
    }

    private void setFirstTurnPlayer(StartGameDto entity, GameMatchDto gameMatch) {
        int startingToss = new Random().ints(0, 100).findFirst().getAsInt();
        gameMatch.setCurrentTurnPlayer(startingToss < 50 ? entity.getNorthPlayer() : entity.getSouthPlayer());
    }
}
