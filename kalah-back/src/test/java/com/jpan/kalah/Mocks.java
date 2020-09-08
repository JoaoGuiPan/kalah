package com.jpan.kalah;

import com.jpan.kalah.dto.GameMatchDto;

import static com.jpan.kalah.common.CONSTANTS.COMPUTER_PLAYER;

public class Mocks {

    public static final String PLAYER_ONE = "Player 1";
    public static final String PLAYER_TWO = "Player 2";

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

    public static GameMatchDto multiPlayerMatchTurnOneByPlayerOne() {
        final GameMatchDto gameMatch = new GameMatchDto(PLAYER_ONE, PLAYER_TWO);
        gameMatch.setCurrentTurnPlayer(PLAYER_ONE);
        return gameMatch;
    }

    public static GameMatchDto multiPlayerMatchTurnOneByPlayerTwo() {
        final GameMatchDto gameMatch = new GameMatchDto(PLAYER_ONE, PLAYER_TWO);
        gameMatch.setCurrentTurnPlayer(PLAYER_TWO);
        return gameMatch;
    }
}
