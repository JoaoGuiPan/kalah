package com.jpan.kalah.command;

import com.jpan.kalah.common.Command;
import com.jpan.kalah.dto.GameHouseDto;
import com.jpan.kalah.dto.GameMatchDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.jpan.kalah.Mocks.singlePlayerMatchTurnOneByComputer;
import static com.jpan.kalah.common.CONSTANTS.COMPUTER_PLAYER;

public class ComputerMoveCommandTest {

    @Test
    public void whenComputerTurnCommandIsExecutedThenRunComputerTurn() {

        GameMatchDto match = singlePlayerMatchTurnOneByComputer();
        Command command = new ComputerMoveCommand(match);

        command.execute();

        Assertions.assertTrue(atLeastOneHouseEmpty(match));
    }

    private boolean atLeastOneHouseEmpty(GameMatchDto match) {

        boolean atLeastOneHouseEmpty;

        GameHouseDto current = match.getFirstHouse();

        do {
            atLeastOneHouseEmpty = current.getPlayerName().equalsIgnoreCase(COMPUTER_PLAYER)
                    && !current.isPlayerStash() && current.getNumberOfSeeds() == 0;

            current = current.getNextHouse();
        } while (current != match.getFirstHouse() && !atLeastOneHouseEmpty);

        return atLeastOneHouseEmpty;
    }
}
