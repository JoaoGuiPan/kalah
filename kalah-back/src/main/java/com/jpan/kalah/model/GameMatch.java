package com.jpan.kalah.model;

import com.jpan.kalah.dto.EntityDtoMapper;
import com.jpan.kalah.dto.GameHouseDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.jpan.kalah.common.CONSTANTS.COMPUTER_PLAYER;
import static com.jpan.kalah.common.CONSTANTS.DEFAULT_STARTING_NUM_SEEDS_PER_HOUSE;

@Data
@Entity
@NoArgsConstructor
public class GameMatch {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "game_match_seq")
    @SequenceGenerator(name = "game_match_seq", allocationSize = 1)
    Long id = null;

    @Min(DEFAULT_STARTING_NUM_SEEDS_PER_HOUSE)
    private int startingNumberOfSeedsPerHouse = DEFAULT_STARTING_NUM_SEEDS_PER_HOUSE;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "game_match_houses")
    private List<GameHouse> board = new ArrayList<>();

    @NotNull
    @NotBlank
    private String southPlayer = null;

    @NotNull
    @NotBlank
    private String northPlayer = COMPUTER_PLAYER;

    private String currentTurnPlayer = null;

    private Date lastTurnTimestamp = null;

    private boolean isActive = true;

    private String winner = null;

    public void populateBoard(final List<GameHouseDto> board) {
        this.board = board.stream().map(EntityDtoMapper::fromDto).collect(Collectors.toList());
    }
}
