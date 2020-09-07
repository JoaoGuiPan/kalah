package com.jpan.kalah.service;

import com.jpan.kalah.model.GameMatch;
import com.jpan.kalah.model.PlayerTurn;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.jpan.kalah.Mocks.singlePlayerMatchTurnOneByComputer;
import static com.jpan.kalah.common.CONSTANTS.COMPUTER_PLAYER;

public class ComputerTurnCommandTest {

    @Test
    public void whenComputerTurnCommandIsExecutedThenCreateComputerTurn() {

        final ComputerTurnCommand command = new ComputerTurnCommand();
        GameMatch currentMatch = singlePlayerMatchTurnOneByComputer();
        PlayerTurn turn = command.execute(currentMatch);

        Assertions.assertEquals(COMPUTER_PLAYER, turn.getPlayerName());
        Assertions.assertTrue(turn.getHouseIndex() > currentMatch.getNumberOfHouses());

        int lastHouseIndex = 2 * (currentMatch.getNumberOfHouses() + 1) - 1;
        Assertions.assertTrue(turn.getHouseIndex() < lastHouseIndex);
    }
}
