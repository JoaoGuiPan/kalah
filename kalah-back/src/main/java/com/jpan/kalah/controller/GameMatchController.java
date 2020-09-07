package com.jpan.kalah.controller;

import com.jpan.kalah.common.CreateService;
import com.jpan.kalah.common.ListRepository;
import com.jpan.kalah.common.UpdateService;
import com.jpan.kalah.dto.StartGameDto;
import com.jpan.kalah.model.GameMatch;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(value = "Matches")
@RestController
@RequestMapping("matches")
public class GameMatchController {

    private final Logger logger = LoggerFactory.getLogger(GameMatchController.class);

    private final CreateService<StartGameDto, GameMatch> matchCreate;
    private final UpdateService<GameMatch, Integer, GameMatch> matchUpdate;
    private final ListRepository<GameMatch> matchList;

    public GameMatchController(CreateService<StartGameDto, GameMatch> matchCreate,
                               UpdateService<GameMatch, Integer, GameMatch> matchUpdate,
                               ListRepository<GameMatch> matchList
                               ) {
        this.matchCreate = matchCreate;
        this.matchUpdate = matchUpdate;
        this.matchList = matchList;
    }

    @ApiOperation(value = "Create new Game.")
    @PostMapping
    GameMatch createGame(@RequestBody @Valid StartGameDto startGame) {
        logger.info("Creating new Game - " + startGame);
        final GameMatch match = matchCreate.create(startGame);
        logger.info("Match created. ID " + match.getId());
        return match;
    }

    @ApiOperation(value = "Fetch all Matches.")
    @GetMapping
    List<GameMatch> getAll() {
        logger.info("Fetching all Matches");
        return matchList.listAll();
    }

    @ApiOperation(value = "Fetch Match by id.")
    @GetMapping("/{match}")
    GameMatch getById(@PathVariable GameMatch match) {
        logger.info("Fetching Match ID " + match.getId());
        return match;
    }

    @ApiOperation(value = "Execute move by Match id.")
    @PutMapping("/{match}")
    GameMatch move(@PathVariable GameMatch match, @RequestParam Integer houseMoved) {
        logger.info("Updating Match ID " + match.getId());
        return matchUpdate.update(match, houseMoved);
    }
}
