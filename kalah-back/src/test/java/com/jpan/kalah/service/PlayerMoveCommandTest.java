package com.jpan.kalah.service;

import com.jpan.kalah.command.PlayerMoveCommand;
import com.jpan.kalah.model.GameMatch;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.jpan.kalah.Mocks.singlePlayerMatchTurnOneByPlayer;

public class PlayerMoveCommandTest {

    @Test
    void whenPlayerOneExecutesMoveThenSeedsWillBeSowed() {

        GameMatch match = singlePlayerMatchTurnOneByPlayer();

        PlayerMoveCommand command = new PlayerMoveCommand(2, match);

        command.execute();

        Assertions.assertEquals(0, match.getHouse(2).getNumberOfSeeds());
        Assertions.assertEquals(5, match.getHouse(3).getNumberOfSeeds());
        Assertions.assertEquals(5, match.getHouse(4).getNumberOfSeeds());
        Assertions.assertEquals(5, match.getHouse(5).getNumberOfSeeds());
        Assertions.assertEquals(1, match.getHouse(6).getNumberOfSeeds());
    }
}
