package com.jpan.kalah.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.Min;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameHouseDto {

    private boolean isPlayerStash = false;

    private int numberOfSeeds = 0;

    @Min(0)
    private int index = 0;

    private String playerName;

    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private GameHouseDto nextHouse;

    public void addSeeds(int numberOfSeeds) {
        this.numberOfSeeds += numberOfSeeds;
    }
}
