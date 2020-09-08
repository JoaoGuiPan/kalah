package com.jpan.kalah.dto;

import com.jpan.kalah.model.GameHouse;
import com.jpan.kalah.model.GameMatch;

import java.util.List;
import java.util.stream.Collectors;

public class EntityDtoMapper {

    public static GameMatch fromDto(final GameMatchDto dto) {
        final GameMatch match = new GameMatch();

        match.setId(dto.getId());
        match.setStartingNumberOfSeedsPerHouse(dto.getStartingNumberOfSeedsPerHouse());
        match.setSouthPlayer(dto.getSouthPlayer());
        match.setNorthPlayer(dto.getNorthPlayer());
        match.setCurrentTurnPlayer(dto.getCurrentTurnPlayer());
        match.setLastTurnTimestamp(dto.getLastTurnTimestamp());
        match.setActive(dto.isActive());
        match.setWinner(dto.getWinner());
        match.populateBoard(dto.getBoard());

        return match;
    }

    public static GameMatchDto toDto(final GameMatch match) {
        final GameMatchDto dto = new GameMatchDto();

        dto.setId(match.getId());
        dto.setStartingNumberOfSeedsPerHouse(match.getStartingNumberOfSeedsPerHouse());
        dto.setSouthPlayer(match.getSouthPlayer());
        dto.setNorthPlayer(match.getNorthPlayer());
        dto.setCurrentTurnPlayer(match.getCurrentTurnPlayer());
        dto.setLastTurnTimestamp(match.getLastTurnTimestamp());
        dto.setActive(match.isActive());
        dto.setWinner(match.getWinner());

        final List<GameHouseDto> board = match.getBoard().stream()
                .map(EntityDtoMapper::toDto)
                .collect(Collectors.toList());
        dto.setBoard(board);

        return dto;
    }

    public static GameHouse fromDto(final GameHouseDto dto) {
        final GameHouse house = new GameHouse();

        house.setPlayerStash(dto.isPlayerStash());
        house.setNumberOfSeeds(dto.getNumberOfSeeds());
        house.setIndex(dto.getIndex());
        house.setPlayerName(dto.getPlayerName());

        return house;
    }

    public static GameHouseDto toDto(final GameHouse house) {
        final GameHouseDto dto = new GameHouseDto();

        dto.setPlayerStash(house.isPlayerStash());
        dto.setNumberOfSeeds(house.getNumberOfSeeds());
        dto.setIndex(house.getIndex());
        dto.setPlayerName(house.getPlayerName());

        return dto;
    }

}
