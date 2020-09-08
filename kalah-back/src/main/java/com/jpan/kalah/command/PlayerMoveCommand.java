package com.jpan.kalah.command;

import com.jpan.kalah.common.Command;
import com.jpan.kalah.dto.GameHouseDto;
import com.jpan.kalah.dto.GameMatchDto;

import java.util.Date;

import static java.util.Objects.requireNonNullElse;

public class PlayerMoveCommand implements Command {

    private final int houseIndex;
    private final GameMatchDto currentMatch;

    public PlayerMoveCommand(final int houseIndex, final GameMatchDto currentMatch) {
        this.houseIndex = houseIndex;
        this.currentMatch = currentMatch;
    }

    @Override
    public void execute() {

        if (currentMatch.isActive() && currentMatch.getWinner() == null) {

            GameHouseDto selected = currentMatch.getHouse(houseIndex);

            boolean finalSeedIsStash = sowSeeds(selected);

            endTurn(currentMatch, finalSeedIsStash);
        }
    }

    private boolean sowSeeds(GameHouseDto selected) {

        boolean finalSeedIsStash = false;

        if (!selected.isPlayerStash() && houseBelongsToPlayer(selected) && selected.getNumberOfSeeds() > 0) {

            GameHouseDto next = null;

            int seedsToSow = selected.getNumberOfSeeds();

            for (int i = 0; i < seedsToSow; i++) {

                next = requireNonNullElse(next, selected).getNextHouse();

                if (houseBelongsToPlayer(next) || (!houseBelongsToPlayer(next) && !next.isPlayerStash())) {

                    // is final seed, is empty, belongs to player and is not stash
                    if (i == seedsToSow - 1 && canCaptureOpposingSeeds(next)) {
                        captureOpposingSeeds(next);
                    } else {
                        next.addSeeds(1);
                        if (i == seedsToSow - 1) {
                            finalSeedIsStash = next.isPlayerStash();
                        }
                    }

                } else if (!houseBelongsToPlayer(next) && next.isPlayerStash()) {
                    // cannot add seed to other player's stash, will add to the next house
                    seedsToSow++;
                }
            }

            selected.setNumberOfSeeds(0);
        }

        return finalSeedIsStash;
    }

    private boolean canCaptureOpposingSeeds(GameHouseDto next) {
        return next.getNumberOfSeeds() == 0 && houseBelongsToPlayer(next) && !next.isPlayerStash();
    }

    private void captureOpposingSeeds(GameHouseDto next) {
        GameHouseDto opposingHouse = currentMatch.getOpposingHouse(next);

        int seedsToAdd = opposingHouse.getNumberOfSeeds() + 1;

        currentMatch.getPlayerStash(currentMatch.getCurrentTurnPlayer()).addSeeds(seedsToAdd);

        opposingHouse.setNumberOfSeeds(0);
    }

    private boolean houseBelongsToPlayer(GameHouseDto house) {
        return house.getPlayerName().equalsIgnoreCase(currentMatch.getCurrentTurnPlayer());
    }

    private void endTurn(GameMatchDto currentMatch, boolean finalSeedIsStash) {

        if (currentMatch.checkGameOver()) {

            currentMatch.setActive(false);

            currentMatch.stashRemainingSeeds();

            String winner = getPlayerFinalScore(currentMatch.getSouthPlayer(), currentMatch)
                                > getPlayerFinalScore(currentMatch.getNorthPlayer(), currentMatch)
                            ? currentMatch.getSouthPlayer()
                            : currentMatch.getNorthPlayer();

            currentMatch.setWinner(winner);

        } else {

            currentMatch.setCurrentTurnPlayer(getCurrentTurnPlayer(currentMatch, finalSeedIsStash));

            currentMatch.setLastTurnTimestamp(new Date());
        }
    }

    private String getCurrentTurnPlayer(GameMatchDto currentMatch, boolean finalSeedIsStash) {

        if (finalSeedIsStash) {
            return currentMatch.getCurrentTurnPlayer();
        }

        return currentMatch.getSouthPlayer().equalsIgnoreCase(currentMatch.getCurrentTurnPlayer())
                ? currentMatch.getNorthPlayer()
                : currentMatch.getSouthPlayer();
    }

    private int getPlayerFinalScore(String playerName, GameMatchDto currentMatch) {
        return currentMatch.getPlayerStash(playerName).getNumberOfSeeds();
    }
}
