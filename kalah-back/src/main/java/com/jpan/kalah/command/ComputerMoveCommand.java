package com.jpan.kalah.command;

import com.jpan.kalah.common.Command;
import com.jpan.kalah.dto.GameMatchDto;

import java.util.Random;

import static com.jpan.kalah.common.CONSTANTS.NUM_HOUSES;

public class ComputerMoveCommand implements Command {

    private final GameMatchDto currentMatch;

    public ComputerMoveCommand(final GameMatchDto currentMatch) {
        this.currentMatch = currentMatch;
    }

    @Override
    public void execute() {

        int houseIndex;

        do {
            houseIndex = selectHouse();
        } while (currentMatch.getHouse(houseIndex).getNumberOfSeeds() == 0);

        final PlayerMoveCommand command = new PlayerMoveCommand(houseIndex, currentMatch);

        command.execute();
    }

    private int selectHouse() {
        int min = NUM_HOUSES + 1;
        int max = (2 * min) - 1;
        return new Random().ints(min, max).findFirst().getAsInt();
    }
}
