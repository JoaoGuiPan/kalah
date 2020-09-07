package com.jpan.kalah;

import com.jpan.kalah.model.GameMatch;

import static com.jpan.kalah.common.CONSTANTS.COMPUTER_PLAYER;

public class Mocks {

    public static final String PLAYER_ONE = "Player 1";
    public static final String PLAYER_TWO = "Player 2";

    public static GameMatch singlePlayerMatchTurnOneByComputer() {
        final GameMatch gameMatch = new GameMatch(PLAYER_ONE);
        gameMatch.setCurrentTurnPlayer(COMPUTER_PLAYER);
        return gameMatch;
    }

    public static GameMatch singlePlayerMatchTurnOneByPlayer() {
        final GameMatch gameMatch = new GameMatch(PLAYER_ONE);
        gameMatch.setCurrentTurnPlayer(PLAYER_ONE);
        return gameMatch;
    }

    public static GameMatch multiPlayerMatchTurnOneByPlayerOne() {
        final GameMatch gameMatch = new GameMatch(PLAYER_ONE, PLAYER_TWO);
        gameMatch.setCurrentTurnPlayer(PLAYER_ONE);
        return gameMatch;
    }

    public static GameMatch multiPlayerMatchTurnOneByPlayerTwo() {
        final GameMatch gameMatch = new GameMatch(PLAYER_ONE, PLAYER_TWO);
        gameMatch.setCurrentTurnPlayer(PLAYER_TWO);
        return gameMatch;
    }
}
