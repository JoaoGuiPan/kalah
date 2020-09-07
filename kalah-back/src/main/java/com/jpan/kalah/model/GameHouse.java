package com.jpan.kalah.model;

import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import static com.jpan.kalah.common.CONSTANTS.DEFAULT_STARTING_NUM_SEEDS_PER_HOUSE;

@Embeddable
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class GameHouse {

    private boolean isPlayerStash = false;

    @Min(0)
    private int numberOfSeeds = DEFAULT_STARTING_NUM_SEEDS_PER_HOUSE;

    @Min(0)
    private int index = 0;

    @NotEmpty
    @NotBlank
    private String playerName;

    @NotEmpty
    private GameHouse nextHouse;

    public void addSeeds(int numberOfSeeds) {
        this.numberOfSeeds += numberOfSeeds;
    }

    public void removeSeeds(int numberOfBeans) {
        this.numberOfSeeds -= numberOfBeans;
    }
}
