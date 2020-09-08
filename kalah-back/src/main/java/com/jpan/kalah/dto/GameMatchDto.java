package com.jpan.kalah.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.jpan.kalah.common.CONSTANTS.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameMatchDto {

    private Long id;

    @Min(DEFAULT_STARTING_NUM_SEEDS_PER_HOUSE)
    private int startingNumberOfSeedsPerHouse = DEFAULT_STARTING_NUM_SEEDS_PER_HOUSE;

    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @NotNull
    private GameHouseDto firstHouse;

    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @NotNull
    private GameHouseDto lastHouse;

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

    // vs computer
    public GameMatchDto(final String southPlayer) {
        this.southPlayer = southPlayer;
        this.populateBoard();
    }

    public GameMatchDto(final String southPlayer, final String northPlayer) {
        this.southPlayer = southPlayer;
        this.northPlayer = northPlayer;
        this.populateBoard();
    }

    public GameMatchDto(final String southPlayer, final String northPlayer, final List<GameHouseDto> board) {
        this.southPlayer = southPlayer;
        this.northPlayer = northPlayer;
        this.setBoard(board);
    }

    @JsonGetter
    public List<GameHouseDto> getBoard() {
        final List<GameHouseDto> board = new ArrayList<>();

        GameHouseDto current = this.firstHouse;

        do {
            board.add(current);
            current = current.getNextHouse();
        } while (current != this.firstHouse);

        return board;
    }

    @JsonSetter
    public void setBoard(final List<GameHouseDto> board) {
        for (final GameHouseDto house: board) {
            this.addHouse(house.getPlayerName(), house.isPlayerStash(), house.getIndex(), house.getNumberOfSeeds());
        }
    }

    public GameHouseDto getHouse(final int index) {
        GameHouseDto current = this.firstHouse;

        do {
            if (current.getIndex() == index) {
                return current;
            }
            current = current.getNextHouse();
        } while (current != this.firstHouse);

        return null;
    }

    public GameHouseDto getOpposingHouse(GameHouseDto selected) {
        return this.getHouse((2 * NUM_HOUSES) - selected.getIndex());
    }

    public GameHouseDto getPlayerStash(String playerName) {
        GameHouseDto current = this.firstHouse;

        do {
            if (current.isPlayerStash() && current.getPlayerName().equalsIgnoreCase(playerName)) {
                return current;
            }
            current = current.getNextHouse();
        } while (current != this.firstHouse);

        return null;
    }

    public boolean checkGameOver() {

        int northPlayerRemainingSeeds = 0;
        int southPlayerRemainingSeeds = 0;

        GameHouseDto current = this.firstHouse;

        if (current != null) {
            do {
                if (!current.isPlayerStash()) {
                    if (current.getPlayerName().equalsIgnoreCase(northPlayer)) {
                        northPlayerRemainingSeeds += current.getNumberOfSeeds();
                    } else {
                        southPlayerRemainingSeeds += current.getNumberOfSeeds();
                    }
                }
                current = current.getNextHouse();
            } while (current != this.firstHouse);
        }

        return northPlayerRemainingSeeds == 0 || southPlayerRemainingSeeds == 0;
    }

    public void stashRemainingSeeds() {

        GameHouseDto current = this.firstHouse;
        if (this.firstHouse != null) {
            do {
                if (!current.isPlayerStash()) {
                    GameHouseDto playerStash = this.getPlayerStash(current.getPlayerName());
                    playerStash.addSeeds(current.getNumberOfSeeds());
                }
                current = current.getNextHouse();
            } while (current != this.firstHouse);
        }
    }

    private void addHouse(final String playerName, final boolean isPlayerStash, final int index, final int numberOfSeeds) {
        final GameHouseDto gameHouse = GameHouseDto.builder()
                .playerName(playerName)
                .isPlayerStash(isPlayerStash)
                .index(index)
                .numberOfSeeds(numberOfSeeds)
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

                boolean isPlayerStash = i == (NUM_HOUSES);
                int numberOfSeeds = isPlayerStash ? 0 : this.startingNumberOfSeedsPerHouse;
                this.addHouse(players.get(p), isPlayerStash, i + indexOffset, numberOfSeeds);
            }
        }
    }
}
