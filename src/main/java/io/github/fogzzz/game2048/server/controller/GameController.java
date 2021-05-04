package io.github.fogzzz.game2048.server.controller;

import io.github.fogzzz.game2048.server.model.Tile;
import io.github.fogzzz.game2048.server.service.GameStateService;
import io.github.fogzzz.game2048.server.service.MoveService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class GameController {

    private final MoveService moveService;
    private final GameStateService gameStateService;

    @GetMapping("game_tiles")
    public Tile[][] getGameTiles() {
        return gameStateService.getGameTiles();
    }

    @GetMapping("score")
    public int getScore() {
        return gameStateService.getScore();
    }

    @GetMapping("max_tile")
    public int getMaxTile() {
        return gameStateService.getMaxTile();
    }

    @GetMapping("can_move")
    public boolean canMove() {
        return gameStateService.canMove();
    }

    @PostMapping("reset_game")
    public void resetGame() {
        gameStateService.setScore(0);
        gameStateService.setMaxTile(0);
        gameStateService.resetGameTiles();
    }

    @PostMapping("set_score")
    public void setScore(@Param("score") int score) {
        gameStateService.setScore(score);
    }

    @PostMapping("set_max_tile")
    public void setMaxTile(@Param("max_tile") int maxTile) {
        gameStateService.setMaxTile(maxTile);
    }

    @PostMapping("move")
    public void move(@Param("direction") String direction) {
        moveService.move(direction);
    }

    @PostMapping("rollback")
    public void rollback() {
        moveService.rollback();
    }

    @PostMapping("random_move")
    public void randomMove() {
        moveService.randomMove();
    }

    @PostMapping("auto_move")
    public void autoMove() {
        moveService.autoMove();
    }
}
