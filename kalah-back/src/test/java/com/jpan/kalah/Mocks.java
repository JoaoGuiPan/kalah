package com.jpan.kalah;

import com.jpan.kalah.dto.GameMatchDto;

import static com.jpan.kalah.common.CONSTANTS.COMPUTER_PLAYER;

public class Mocks {

    public static final String PLAYER_ONE = "Player 1";

    public static GameMatchDto singlePlayerMatchTurnOneByComputer() {
        final GameMatchDto gameMatch = new GameMatchDto(PLAYER_ONE);
        gameMatch.setCurrentTurnPlayer(COMPUTER_PLAYER);
        return gameMatch;
    }

    public static GameMatchDto singlePlayerMatchTurnOneByPlayer() {
        final GameMatchDto gameMatch = new GameMatchDto(PLAYER_ONE);
        gameMatch.setCurrentTurnPlayer(PLAYER_ONE);
        return gameMatch;
    }
}
