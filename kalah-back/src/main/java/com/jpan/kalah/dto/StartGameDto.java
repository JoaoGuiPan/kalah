package com.jpan.kalah.dto;

import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Value;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static com.jpan.kalah.common.CONSTANTS.*;
import static com.jpan.kalah.common.CONSTANTS.DEFAULT_STARTING_NUM_SEEDS_PER_HOUSE;

@Value
@ToString
@NoArgsConstructor
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
