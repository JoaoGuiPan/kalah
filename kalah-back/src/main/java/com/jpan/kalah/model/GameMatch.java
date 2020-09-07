package com.jpan.kalah.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.jpan.kalah.common.CONSTANTS.*;

@Data
@Entity
public class GameMatch {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "game_match_seq")
    @SequenceGenerator(name = "game_match_seq", allocationSize = 1)
    Long id;

    @Min(DEFAULT_STARTING_NUM_SEEDS_PER_HOUSE)
    private int startingNumberOfSeedsPerHouse = DEFAULT_STARTING_NUM_SEEDS_PER_HOUSE;

    @Embedded
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @NotNull
    private GameHouse firstHouse;

    @Embedded
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @NotNull
    private GameHouse lastHouse;

    @NotNull
    @NotBlank
    private String southPlayer;

    @NotNull
    @NotBlank
    private String northPlayer = COMPUTER_PLAYER;

    private String currentTurnPlayer;

    private Date lastTurnTimestamp;

    private boolean isActive = true;

    private String winner;

    // match vs computer
    public GameMatch(final String southPlayer) {
        this.southPlayer = southPlayer;
        this.populateBoard();
    }

    // match vs computer
    public GameMatch(final String southPlayer, final int startingNumberOfSeedsPerHouse) {
        this.southPlayer = southPlayer;
        this.startingNumberOfSeedsPerHouse = startingNumberOfSeedsPerHouse;
        this.populateBoard();
    }

    // multiplayer match
    public GameMatch(final String southPlayer, final String northPlayer) {
        this.southPlayer = southPlayer;
        this.northPlayer = northPlayer;
        this.populateBoard();
    }

    // multiplayer match
    public GameMatch(final String southPlayer, final String northPlayer, final int startingNumberOfSeedsPerHouse) {
        this.southPlayer = southPlayer;
        this.northPlayer = northPlayer;
        this.startingNumberOfSeedsPerHouse = startingNumberOfSeedsPerHouse;
        this.populateBoard();
    }

    @JsonGetter
    public List<GameHouse> getBoard() {
        List<GameHouse> board = new ArrayList<>();

        GameHouse current = this.firstHouse;

        do {
            board.add(current);
            current = current.getNextHouse();
        } while (current != this.firstHouse);

        return board;
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
            return this.getHouse((2 * NUM_HOUSES) - selected.getIndex());
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

        if (current != null) {
            do {
                if (current.getPlayerName().equalsIgnoreCase(this.getCurrentTurnPlayer()) && !current.isPlayerStash()) {
                    remainingSeeds += current.getNumberOfSeeds();
                }
                current = current.getNextHouse();
            } while (current != this.firstHouse);
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
                .numberOfSeeds(isPlayerStash ? 0 : this.startingNumberOfSeedsPerHouse)
                .build();

        if (this.firstHouse == null) {
            this.firstHouse = gameHouse;
        } else {
            this.lastHouse.setNextHouse(gameHouse);
        }

        this.lastHouse = gameHouse;
        this.lastHouse.setNextHouse(this.firstHouse);
    }

    private void populateBoard() {

        int indexOffset = -NUM_HOUSES;

        final List<String> players = Arrays.asList(this.southPlayer, this.northPlayer);
        for (int p = 0; p < players.size(); p++) {

            indexOffset += (NUM_HOUSES + p);

            for (int i = 0; i <= NUM_HOUSES; i++) {

                this.addHouse(players.get(p), i == (NUM_HOUSES), i + indexOffset);
            }
        }
    }
}
