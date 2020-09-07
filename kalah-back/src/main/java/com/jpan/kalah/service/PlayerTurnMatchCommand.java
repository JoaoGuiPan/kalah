package com.jpan.kalah.service;

import com.jpan.kalah.common.UpdateRepository;
import com.jpan.kalah.model.GameHouse;
import com.jpan.kalah.model.GameMatch;
import com.jpan.kalah.model.PlayerTurn;
import org.springframework.stereotype.Service;

import java.util.Date;

import static java.util.Objects.requireNonNullElse;

@Service
public class PlayerTurnMatchCommand {

    private final UpdateRepository<GameMatch> updateMatch;

    public PlayerTurnMatchCommand(UpdateRepository<GameMatch> updateMatch) {
        this.updateMatch = updateMatch;
    }

    public GameMatch execute(PlayerTurn turn, GameMatch currentMatch) {

        if (currentMatch.isActive() && currentMatch.getWinner() == null) {

            currentMatch.setCurrentTurnPlayer(turn.getPlayerName());

            GameHouse selected = currentMatch.getHouse(turn.getHouseIndex());

            sowSeeds(turn, selected, currentMatch);

            endTurn(currentMatch);

            this.updateMatch.update(currentMatch);

            return currentMatch;
        }

        // TODO THROW EXCEPTION
        return null;
    }

    private void sowSeeds(PlayerTurn turn, GameHouse selected, GameMatch currentMatch) {
        if (!selected.isPlayerStash() && houseBelongsToPlayer(turn, selected) && selected.getNumberOfSeeds() > 0) {

            GameHouse next = null;

            int seedsToSow = selected.getNumberOfSeeds();

            for (int i = 0; i < seedsToSow; i++) {

                next = requireNonNullElse(next, selected).getNextHouse();

                if (houseBelongsToPlayer(turn, next) || (!houseBelongsToPlayer(turn, next) && !next.isPlayerStash())) {

                    // is final seed, is not stash and is empty
                    if (i == seedsToSow - 1 && !next.isPlayerStash() && next.getNumberOfSeeds() == 0) {
                        captureOpposingSeeds(turn, currentMatch, next);
                    } else {
                        next.addSeeds(1);
                    }

                } else if (!houseBelongsToPlayer(turn, next) && next.isPlayerStash()) {
                    // cannot add seed to other player's stash, will add to the next house
                    seedsToSow++;
                }
            }

            selected.setNumberOfSeeds(0);
        }
    }

    private void captureOpposingSeeds(PlayerTurn turn, GameMatch currentMatch, GameHouse next) {
        GameHouse opposingHouse = currentMatch.getOpposingHouse(next);

        int seedsToAdd = opposingHouse.getNumberOfSeeds() + 1;

        currentMatch.getPlayerStash(turn.getPlayerName()).addSeeds(seedsToAdd);

        opposingHouse.setNumberOfSeeds(0);
    }

    private boolean houseBelongsToPlayer(PlayerTurn turn, GameHouse house) {
        return house.getPlayerName().equalsIgnoreCase(turn.getPlayerName());
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
