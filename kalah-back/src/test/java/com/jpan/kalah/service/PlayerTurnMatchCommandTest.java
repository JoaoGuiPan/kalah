package com.jpan.kalah.service;

import com.jpan.kalah.common.UpdateRepository;
import com.jpan.kalah.model.GameMatch;
import com.jpan.kalah.model.PlayerTurn;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.jpan.kalah.Mocks.PLAYER_ONE;
import static com.jpan.kalah.Mocks.singlePlayerMatchTurnOneByPlayer;

@ExtendWith(MockitoExtension.class)
public class PlayerTurnMatchCommandTest {

    @Mock
    UpdateRepository<GameMatch> updateMatch;

    @Test
    void whenPlayerOneExecutesMoveThenSeedsWillBeSowed() {

        PlayerTurn turn = new PlayerTurn(PLAYER_ONE, 2);
        GameMatch match = singlePlayerMatchTurnOneByPlayer();

        PlayerTurnMatchCommand command = new PlayerTurnMatchCommand(this.updateMatch);

        GameMatch execute = command.execute(turn, match);

        Assertions.assertEquals(0, execute.getHouse(2).getNumberOfSeeds());
        Assertions.assertEquals(5, execute.getHouse(3).getNumberOfSeeds());
        Assertions.assertEquals(5, execute.getHouse(4).getNumberOfSeeds());
        Assertions.assertEquals(5, execute.getHouse(5).getNumberOfSeeds());
        Assertions.assertEquals(1, execute.getHouse(6).getNumberOfSeeds());
    }
}
