package com.jpan.kalah.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static com.jpan.kalah.common.CONSTANTS.DEFAULT_STARTING_NUM_SEEDS_PER_HOUSE;

@Embeddable
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameHouse {

    private boolean isPlayerStash = false;

    @Min(0)
    private int numberOfSeeds = DEFAULT_STARTING_NUM_SEEDS_PER_HOUSE;

    @Min(0)
    private int index = 0;

    @NotNull
    @NotBlank
    private String playerName;

    @NotNull
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private GameHouse nextHouse;

    public void addSeeds(int numberOfSeeds) {
        this.numberOfSeeds += numberOfSeeds;
    }
}
