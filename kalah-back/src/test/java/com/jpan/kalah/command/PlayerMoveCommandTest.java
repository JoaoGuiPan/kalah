package com.jpan.kalah.command;

import com.jpan.kalah.dto.GameHouseDto;
import com.jpan.kalah.dto.GameMatchDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.jpan.kalah.Mocks.PLAYER_ONE;
import static com.jpan.kalah.Mocks.singlePlayerMatchTurnOneByPlayer;
import static com.jpan.kalah.common.CONSTANTS.COMPUTER_PLAYER;

public class PlayerMoveCommandTest {

    @Test
    void whenPlayerOneExecutesMoveThenSeedsWillBeSowed() {

        GameMatchDto match = singlePlayerMatchTurnOneByPlayer();

        PlayerMoveCommand command = new PlayerMoveCommand(2, match);

        command.execute();

        Assertions.assertEquals(0, match.getHouse(2).getNumberOfSeeds());
        Assertions.assertEquals(5, match.getHouse(3).getNumberOfSeeds());
        Assertions.assertEquals(5, match.getHouse(4).getNumberOfSeeds());
        Assertions.assertEquals(5, match.getHouse(5).getNumberOfSeeds());
        Assertions.assertEquals(1, match.getHouse(6).getNumberOfSeeds());
    }

    @Test
    void whenPlayerOneExecutesMoveOnEmptyHouseThenSeedsWillBeCaptured() {

        GameMatchDto match = singlePlayerMatchTurnOneByPlayer();

        GameHouseDto houseOne = match.getHouse(0);
        houseOne.setNumberOfSeeds(1);

        GameHouseDto houseTwo = match.getHouse(1);
        houseTwo.setNumberOfSeeds(0);

        PlayerMoveCommand command = new PlayerMoveCommand(0, match);

        command.execute();

        Assertions.assertEquals(5, match.getPlayerStash(PLAYER_ONE).getNumberOfSeeds());
    }

    @Test
    void whenPlayerOneExecutesMoveOverComputerStashThenSeedsWillBeSowedOnNextHouse() {

        GameMatchDto match = singlePlayerMatchTurnOneByPlayer();

        GameHouseDto houseSix = match.getHouse(5);
        houseSix.setNumberOfSeeds(8);

        PlayerMoveCommand command = new PlayerMoveCommand(5, match);

        command.execute();

        Assertions.assertEquals(5, match.getHouse(0).getNumberOfSeeds());
    }

    @Test
    void whenPlayerOneHasZeroSeedsInTheFieldThenDeclareWinner() {

        GameMatchDto match = singlePlayerMatchTurnOneByPlayer();

        GameHouseDto houseOne = match.getHouse(0);
        houseOne.setNumberOfSeeds(0);

        GameHouseDto houseTwo = match.getHouse(1);
        houseTwo.setNumberOfSeeds(0);

        GameHouseDto houseThree = match.getHouse(2);
        houseThree.setNumberOfSeeds(0);

        GameHouseDto houseFour = match.getHouse(3);
        houseFour.setNumberOfSeeds(0);

        GameHouseDto houseFive = match.getHouse(4);
        houseFive.setNumberOfSeeds(0);

        GameHouseDto houseSix = match.getHouse(5);
        houseSix.setNumberOfSeeds(1);

        PlayerMoveCommand command = new PlayerMoveCommand(5, match);

        command.execute();

        Assertions.assertEquals(COMPUTER_PLAYER, match.getWinner());
        Assertions.assertFalse(match.isActive());
    }
}
