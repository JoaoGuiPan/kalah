package com.jpan.kalah.command;

import com.jpan.kalah.common.Command;
import com.jpan.kalah.model.GameHouse;
import com.jpan.kalah.model.GameMatch;

import java.util.Date;

import static java.util.Objects.requireNonNullElse;

public class PlayerMoveCommand implements Command {

    private final int houseIndex;
    private final GameMatch currentMatch;

    public PlayerMoveCommand(final int houseIndex, final GameMatch currentMatch) {
        this.houseIndex = houseIndex;
        this.currentMatch = currentMatch;
    }

    @Override
    public void execute() {

        if (currentMatch.isActive() && currentMatch.getWinner() == null) {

            GameHouse selected = currentMatch.getHouse(houseIndex);

            sowSeeds(selected);

            endTurn(currentMatch);
        }
    }

    private void sowSeeds(GameHouse selected) {

        if (!selected.isPlayerStash() && houseBelongsToPlayer(selected) && selected.getNumberOfSeeds() > 0) {

            GameHouse next = null;

            int seedsToSow = selected.getNumberOfSeeds();

            for (int i = 0; i < seedsToSow; i++) {

                next = requireNonNullElse(next, selected).getNextHouse();

                if (houseBelongsToPlayer(next) || (!houseBelongsToPlayer(next) && !next.isPlayerStash())) {

                    // is final seed, is not stash and is empty
                    if (i == seedsToSow - 1 && !next.isPlayerStash() && next.getNumberOfSeeds() == 0) {
                        captureOpposingSeeds(next);
                    } else {
                        next.addSeeds(1);
                    }

                } else if (!houseBelongsToPlayer(next) && next.isPlayerStash()) {
                    // cannot add seed to other player's stash, will add to the next house
                    seedsToSow++;
                }
            }

            selected.setNumberOfSeeds(0);
        }
    }

    private void captureOpposingSeeds(GameHouse next) {
        GameHouse opposingHouse = currentMatch.getOpposingHouse(next);

        int seedsToAdd = opposingHouse.getNumberOfSeeds() + 1;

        currentMatch.getPlayerStash(currentMatch.getCurrentTurnPlayer()).addSeeds(seedsToAdd);

        opposingHouse.setNumberOfSeeds(0);
    }

    private boolean houseBelongsToPlayer(GameHouse house) {
        return house.getPlayerName().equalsIgnoreCase(currentMatch.getCurrentTurnPlayer());
    }

    private void endTurn(GameMatch currentMatch) {

        if (currentMatch.checkGameOver()) {

            currentMatch.setActive(false);

            currentMatch.stashRemainingSeeds();

            String winner = getPlayerFinalScore(currentMatch.getSouthPlayer(), currentMatch)
                                > getPlayerFinalScore(currentMatch.getNorthPlayer(), currentMatch)
                            ? currentMatch.getSouthPlayer()
                            : currentMatch.getNorthPlayer();

            currentMatch.setWinner(winner);

        } else {

            currentMatch.setCurrentTurnPlayer(
                    currentMatch.getSouthPlayer().equalsIgnoreCase(currentMatch.getCurrentTurnPlayer())
                            ? currentMatch.getNorthPlayer()
                            : currentMatch.getSouthPlayer()
            );

            currentMatch.setLastTurnTimestamp(new Date());
        }
    }

    private int getPlayerFinalScore(String playerName, GameMatch currentMatch) {
        return currentMatch.getPlayerStash(playerName).getNumberOfSeeds();
    }
}
