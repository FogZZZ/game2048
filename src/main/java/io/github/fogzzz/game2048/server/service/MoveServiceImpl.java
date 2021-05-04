package io.github.fogzzz.game2048.server.service;

import io.github.fogzzz.game2048.server.model.Move;
import io.github.fogzzz.game2048.server.model.Tile;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MoveServiceImpl implements MoveService {

    private final GameStateService gameStateService;
    @Setter
    private MoveAnalysisService moveAnalysisService;

    private boolean isSaveNeeded = true;

    @Getter
    public Move[] basicMoves = new Move[] {this::left, this::up, this::right, this::down};

    @Override
    public void move(String direction) {
        switch (direction) {
            case "left": left(); break;
            case "up": up(); break;
            case "right": right(); break;
            case "down": down(); break;
            default: throw new RuntimeException("incorrect direction");
        }
    }

    private void left() {
        if (isSaveNeeded) {
            gameStateService.saveState();
            isSaveNeeded = false;
        }

        boolean changed = false;
        Tile[][] gameTiles = gameStateService.getGameTiles();
        for (int i = 0; i < gameTiles.length; i++) {
            changed |= compressTiles(gameTiles[i]);
            changed |= mergeTiles(gameTiles[i]);
        }

        if (changed) {
            gameStateService.addTile();
        }
        isSaveNeeded = true;
    }

    private boolean compressTiles(Tile[] tiles) {
        boolean changed = false;
        int[] compressed = new int[tiles.length];
        int k = 0;
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i].getValue() > 0) {
                compressed[k] = tiles[i].getValue();
                k++;
            }
        }

        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i].getValue() != compressed[i]) {
                changed = true;
                tiles[i].setValue(compressed[i]);
            }
        }
        return changed;
    }

    private boolean mergeTiles(Tile[] tiles) {
        boolean changed = false;
        for (int i = 0; i < tiles.length-1; i++) {
            int value = tiles[i].getValue();
            if (value != 0 && value == tiles[i+1].getValue()) {
                changed = true;
                tiles[i].setValue(value * 2);

                int score = gameStateService.getScore();
                int maxTile = gameStateService.getMaxTile();
                gameStateService.setScore(score + tiles[i].getValue());
                gameStateService.setMaxTile(tiles[i].getValue() > maxTile ? tiles[i].getValue() : maxTile);

                for (int j = i+1; j < tiles.length-1; j++) {
                    tiles[j].setValue(tiles[j+1].getValue());
                }
                tiles[tiles.length-1].setValue(0);
            }
        }
        return changed;
    }

    private void up() {
        gameStateService.saveState();
        isSaveNeeded = false;

        rotate();
        rotate();
        rotate();
        left();
        rotate();
    }

    private void right() {
        gameStateService.saveState();
        isSaveNeeded = false;

        rotate();
        rotate();
        left();
        rotate();
        rotate();
    }

    private void down() {
        gameStateService.saveState();
        isSaveNeeded = false;

        rotate();
        left();
        rotate();
        rotate();
        rotate();
    }

    private void rotate() {
        Tile[][] gameTiles = gameStateService.getGameTiles();
        int len = gameTiles.length;
        int[][] rotated = new int[len][len];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                rotated[j][len-i-1] = gameTiles[i][j].getValue();
            }
        }

        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                gameTiles[i][j].setValue(rotated[i][j]);
            }
        }
    }

    @Override
    public void rollback() {
        gameStateService.rollback();
    }

    @Override
    public void randomMove() {
        int n = ((int)(Math.random()*100))%4;
        switch (n) {
            case 0: left(); break;
            case 1: up(); break;
            case 2: right(); break;
            case 3: down(); break;
        }
    }

    @Override
    public void autoMove() {
         moveAnalysisService.getBestMove().move();
    }
}
