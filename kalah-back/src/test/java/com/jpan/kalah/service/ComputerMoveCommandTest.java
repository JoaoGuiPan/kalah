package com.jpan.kalah.service;

import com.jpan.kalah.command.ComputerMoveCommand;
import com.jpan.kalah.common.Command;
import com.jpan.kalah.dto.GameMatchDto;
import org.junit.jupiter.api.Test;

import static com.jpan.kalah.Mocks.singlePlayerMatchTurnOneByComputer;

public class ComputerMoveCommandTest {

    @Test
    public void whenComputerTurnCommandIsExecutedThenCreateComputerTurn() {

        GameMatchDto match = singlePlayerMatchTurnOneByComputer();
        Command command = new ComputerMoveCommand(match);
        command.execute();

        // TODO SPY selectHouse() METHOD

//        Assertions.assertTrue(houseIndex > NUM_HOUSES);
//        int lastHouseIndex = 2 * (NUM_HOUSES + 1) - 1;
//        Assertions.assertTrue(houseIndex < lastHouseIndex);
    }
}
