package com.jpan.kalah.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static com.jpan.kalah.common.CONSTANTS.COMPUTER_PLAYER;
import static com.jpan.kalah.common.CONSTANTS.DEFAULT_STARTING_NUM_SEEDS_PER_HOUSE;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StartGameDto {

    @NotNull
    @NotBlank
    String southPlayer = null;

    @NotNull
    @NotBlank
    String northPlayer = COMPUTER_PLAYER;

    /**
     * according to traditional rules, as mentioned in https://en.wikipedia.org/wiki/Kalah
     */
    @Min(3)
    @Max(6)
    int startingNumberOfSeedsPerHouse = DEFAULT_STARTING_NUM_SEEDS_PER_HOUSE;
}
