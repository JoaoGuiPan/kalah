package com.jpan.kalah.command;

import com.jpan.kalah.common.Command;
import com.jpan.kalah.dto.GameHouseDto;
import com.jpan.kalah.dto.GameMatchDto;

import java.util.Date;

import static com.jpan.kalah.common.CONSTANTS.TIED;
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

            if (!selected.isPlayerStash() && houseBelongsToPlayer(selected) && selected.getNumberOfSeeds() > 0) {
                boolean finalSeedIsStash = sowSeeds(selected);
                endTurn(currentMatch, finalSeedIsStash);
            }
        }
    }

    private boolean sowSeeds(GameHouseDto selected) {

        boolean finalSeedIsStash = false;

        GameHouseDto next = null;

        int seedsToSow = selected.getNumberOfSeeds();

        for (int currentSeed = 1; currentSeed <= seedsToSow; currentSeed++) {

            next = requireNonNullElse(next, selected).getNextHouse();

            if (houseBelongsToPlayer(next) || (!houseBelongsToPlayer(next) && !next.isPlayerStash())) {

                // is final seed, is empty, belongs to player, is not stash and opposite is > 0
                if (currentSeed == seedsToSow && canCaptureOpposingSeeds(next)) {
                    captureOpposingSeeds(next);
                } else {
                    next.addSeeds(1);
                    if (currentSeed == seedsToSow) {
                        finalSeedIsStash = next.isPlayerStash();
                    }
                }

            } else if (!houseBelongsToPlayer(next) && next.isPlayerStash()) {
                // cannot add seed to other player's stash, will add to the next house
                seedsToSow++;
            }
        }

        selected.setNumberOfSeeds(0);

        return finalSeedIsStash;
    }

    private boolean canCaptureOpposingSeeds(GameHouseDto next) {
        return next.getNumberOfSeeds() == 0 && houseBelongsToPlayer(next) && !next.isPlayerStash()
                && currentMatch.getOpposingHouse(next).getNumberOfSeeds() > 0;
    }

    private void captureOpposingSeeds(GameHouseDto next) {
        GameHouseDto opposingHouse = currentMatch.getOpposingHouse(next);
        GameHouseDto playerStash = currentMatch.getPlayerStash(currentMatch.getCurrentTurnPlayer());
        playerStash.addSeeds(opposingHouse.getNumberOfSeeds() + 1);
        opposingHouse.setNumberOfSeeds(0);
    }

    private boolean houseBelongsToPlayer(GameHouseDto house) {
        return house.getPlayerName().equalsIgnoreCase(currentMatch.getCurrentTurnPlayer());
    }

    private void endTurn(GameMatchDto currentMatch, boolean finalSeedIsStash) {

        if (currentMatch.checkGameOver()) {

            currentMatch.setActive(false);

            currentMatch.stashRemainingSeeds();

            int southPlayerFinalScore = getPlayerFinalScore(currentMatch.getSouthPlayer(), currentMatch);
            int northPlayerFinalScore = getPlayerFinalScore(currentMatch.getNorthPlayer(), currentMatch);

            String winner = southPlayerFinalScore == northPlayerFinalScore ? TIED
                    : southPlayerFinalScore > northPlayerFinalScore
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
