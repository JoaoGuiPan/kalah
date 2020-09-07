package com.jpan.kalah.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Arrays;

import static com.jpan.kalah.common.CONSTANTS.DEFAULT_NUM_HOUSES;
import static com.jpan.kalah.common.CONSTANTS.DEFAULT_STARTING_NUM_SEEDS_PER_HOUSE;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class GameMatch {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "game_match_seq")
    @SequenceGenerator(name = "game_match_seq", allocationSize = 1)
    Long id;

    @Min(DEFAULT_NUM_HOUSES)
    private int numberOfHouses = DEFAULT_NUM_HOUSES;

    @Min(DEFAULT_STARTING_NUM_SEEDS_PER_HOUSE)
    private int startingNumberOfSeedsPerHouse = DEFAULT_STARTING_NUM_SEEDS_PER_HOUSE;

    @NotEmpty
    private GameHouse firstHouse;

    @NotEmpty
    private GameHouse lastHouse;

    @NotEmpty
    @NotBlank
    private String northPlayer;

    @NotEmpty
    @NotBlank
    private String southPlayer;

    private String currentTurnPlayer;

    private boolean isActive = true;

    private String winner;

    public GameMatch(final String northPlayer, final String southPlayer) {
        this.northPlayer = northPlayer;
        this.southPlayer = southPlayer;
        this.populateBoard();
    }

    public GameHouse getHouse(final int index) {
        GameHouse current = this.firstHouse;

        do {
            if (current.getIndex() == index) {
                return current;
            }
            current = current.getNextHouse();
        } while (current != this.firstHouse);

        return null;
    }

    public GameHouse getOpposingHouse(GameHouse selected) {
        if (!selected.isPlayerStash()) {
            // TODO EXPLAIN REASONING
            return this.getHouse((2 * this.numberOfHouses) - selected.getIndex());
        }

        // no opposing house if player stash
        return null;
    }

    public GameHouse getPlayerStash(String playerName) {
        GameHouse current = this.firstHouse;

        do {
            if (current.isPlayerStash() && current.getPlayerName().equalsIgnoreCase(playerName)) {
                return current;
            }
            current = current.getNextHouse();
        } while (current != this.firstHouse);

        return null;
    }

    public boolean checkGameOver() {

        int remainingSeeds = 0;

        GameHouse current = this.firstHouse;
        if (this.firstHouse != null) {
            while (current.getPlayerName().equalsIgnoreCase(this.getCurrentTurnPlayer())
                    && !current.isPlayerStash() && current != this.firstHouse) {
                remainingSeeds += current.getNumberOfSeeds();
                current = current.getNextHouse();
            }
        }

        return remainingSeeds == 0;
    }

    public void stashRemainingSeeds() {

        GameHouse current = this.firstHouse;
        if (this.firstHouse != null) {
            do {
                GameHouse playerStash = this.getPlayerStash(current.getPlayerName());
                playerStash.addSeeds(current.getNumberOfSeeds());
                current = current.getNextHouse();
            } while (current != this.firstHouse);
        }
    }

    private void addHouse(final String playerName, final boolean isPlayerStash, final int index) {
        final GameHouse gameHouse = GameHouse.builder()
                .playerName(playerName)
                .isPlayerStash(isPlayerStash)
                .index(index)
                .numberOfSeeds(this.startingNumberOfSeedsPerHouse)
                .build();

        if (this.firstHouse == null) {
            this.firstHouse = gameHouse;
        } else {
            this.lastHouse.setNextHouse(gameHouse);
        }

        this.lastHouse = gameHouse;
        this.lastHouse.setNextHouse(gameHouse);
    }

    private void populateBoard() {
        int indexOffset = -DEFAULT_NUM_HOUSES;
        for (final String playerName: Arrays.asList(this.southPlayer, this.northPlayer)) {
            indexOffset += DEFAULT_NUM_HOUSES;
            for (int i = 0; i <= this.numberOfHouses; i++) {
                this.addHouse(playerName, i == this.numberOfHouses, i + indexOffset);
            }
        }
    }
}
