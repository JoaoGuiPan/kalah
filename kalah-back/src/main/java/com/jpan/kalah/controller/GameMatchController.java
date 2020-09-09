package com.jpan.kalah.controller;

import com.jpan.kalah.common.CreateService;
import com.jpan.kalah.common.ListRepository;
import com.jpan.kalah.common.UpdateRepository;
import com.jpan.kalah.common.UpdateService;
import com.jpan.kalah.dto.EntityDtoMapper;
import com.jpan.kalah.dto.GameMatchDto;
import com.jpan.kalah.dto.StartGameDto;
import com.jpan.kalah.dto.TurnDto;
import com.jpan.kalah.model.GameMatch;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static com.jpan.kalah.dto.EntityDtoMapper.toDto;

@Api(value = "Matches")
@RestController
@RequestMapping("matches")
public class GameMatchController {

    private final Logger logger = LoggerFactory.getLogger(GameMatchController.class);

    private final CreateService<StartGameDto, GameMatchDto> matchCreate;
    private final UpdateService<GameMatch, Integer, GameMatchDto> playTurn;
    private final ListRepository<GameMatch> matchList;
    private final UpdateRepository<GameMatch> updateMatch;

    public GameMatchController(CreateService<StartGameDto, GameMatchDto> matchCreate,
                               UpdateService<GameMatch, Integer, GameMatchDto> playTurn,
                               ListRepository<GameMatch> matchList,
                               UpdateRepository<GameMatch> updateMatch) {
        this.matchCreate = matchCreate;
        this.playTurn = playTurn;
        this.matchList = matchList;
        this.updateMatch = updateMatch;
    }

    @ApiOperation(value = "Create new Game.")
    @PostMapping
    GameMatchDto createGame(@RequestBody @Valid StartGameDto startGame) {
        logger.info("Creating new Game - " + startGame);
        final GameMatchDto match = matchCreate.create(startGame);
        logger.info("Match created. ID " + match.getId());
        return match;
    }

    @ApiOperation(value = "Fetch all Matches.")
    @GetMapping
    List<GameMatchDto> getAll() {
        logger.info("Fetching all Matches");
        return matchList.listAll().stream().map(EntityDtoMapper::toDto).collect(Collectors.toList());
    }

    @ApiOperation(value = "Fetch Match by id.")
    @GetMapping("/{match}")
    GameMatchDto getById(@PathVariable GameMatch match) {
        logger.info("Fetching Match ID " + match.getId());
        return toDto(match);
    }

    @ApiOperation(value = "Execute move by Match id.")
    @PutMapping("/{match}")
    GameMatchDto move(@PathVariable GameMatch match, @RequestBody TurnDto turn) {
        logger.info("Updating Match ID " + match.getId() + ". House moved is " + turn.getHouseMoved());
        return playTurn.update(match, turn.getHouseMoved());
    }

    @ApiOperation(value = "Deactivate Match id.")
    @GetMapping("/{match}/deactivate")
    void deactivate(@PathVariable GameMatch match) {
        if (match.isActive()) {
            match.setActive(false);
            updateMatch.update(match);
        }
    }
}
