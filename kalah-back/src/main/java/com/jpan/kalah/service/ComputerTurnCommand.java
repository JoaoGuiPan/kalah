package com.jpan.kalah.service;

import com.jpan.kalah.model.GameMatch;
import com.jpan.kalah.model.PlayerTurn;
import org.springframework.stereotype.Service;

import java.util.Random;

import static com.jpan.kalah.common.CONSTANTS.COMPUTER_PLAYER;

@Service
public class ComputerTurnCommand {

    PlayerTurn execute(GameMatch currentMatch) {
        int houseIndex = selectHouse(currentMatch);
        return new PlayerTurn(COMPUTER_PLAYER, houseIndex);
    }

    private int selectHouse(GameMatch currentMatch) {
        int min = currentMatch.getNumberOfHouses() + 1;
        int max = (2 * min) - 1;
        return new Random().ints(min, max).findFirst().getAsInt();
    }
}
