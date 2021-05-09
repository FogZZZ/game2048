package io.github.fogzzz.game2048.server.service;

import io.github.fogzzz.game2048.server.model.GameState;
import io.github.fogzzz.game2048.server.model.Tile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static io.github.fogzzz.game2048.server.model.GameState.FIELD_WIDTH;

@Service
@RequiredArgsConstructor
public class GameStateServiceImpl implements GameStateService {

    private final GameState gameState;

    private Stack<Tile[][]> previousStates = new Stack<>();
    private Stack<Integer> previousScores = new Stack<>();
    private Stack<Integer> previousMaxTiles = new Stack<>();

    @PostConstruct
    public void init() {
        resetGameTiles();
    }

    @Override
    public Tile[][] getGameTiles() {
        return gameState.getGameTiles();
    }

    @Override
    public void resetGameTiles() {
        Tile[][] gameTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];
        gameState.setGameTiles(gameTiles);
        for (int i = 0; i < gameTiles.length; i++) {
            for (int j = 0; j < gameTiles[i].length; j++) {
                gameTiles[i][j] = new Tile();
            }
        }
        addTile();
        addTile();
    }

    public void addTile() {
        List<Tile> emptyTiles = getEmptyTiles();
        if (emptyTiles.isEmpty()) {
            return;
        }
        Tile emptyTile = emptyTiles.get((int)(Math.random() * emptyTiles.size()));
        emptyTile.setValue(Math.random() < 0.9 ? 2 : 4);
    }

    public List<Tile> getEmptyTiles() {
        List<Tile> emptyTiles = new ArrayList<>();
        Tile[][] gameTiles = gameState.getGameTiles();
        for (int i = 0; i < gameTiles.length; i++) {
            for (int j = 0; j < gameTiles[i].length; j++) {
                if (gameTiles[i][j].isEmpty()) {
                    emptyTiles.add(gameTiles[i][j]);
                }
            }
        }
        return emptyTiles;
    }

    @Override
    public boolean canMove() {
        if (!getEmptyTiles().isEmpty()) {
            return true;
        }

        Tile[][] gameTiles = gameState.getGameTiles();
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                if (j != FIELD_WIDTH-1 && gameTiles[i][j].getValue() == gameTiles[i][j+1].getValue()) {
                    return true;
                }
                if (i != FIELD_WIDTH-1 && gameTiles[i][j].getValue() == gameTiles[i+1][j].getValue()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void saveState() {
        Tile[][] savedTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                savedTiles[i][j] = new Tile(gameState.getGameTiles()[i][j].getValue());
            }
        }
        previousStates.push(savedTiles);
        previousScores.push(gameState.getScore());
        previousMaxTiles.push(gameState.getMaxTile());
    }

    @Override
    public Tile[][] getPreviousState(int depth) {
        return previousStates.get(previousStates.size()-depth);
    }

    @Override
    public void rollback() {
        if (!previousStates.empty() && !previousScores.empty()) {
            gameState.setGameTiles(previousStates.pop());
            gameState.setScore(previousScores.pop());
            gameState.setMaxTile(previousMaxTiles.pop());
        }
    }

    @Override
    public int getScore() {
        return gameState.getScore();
    }

    @Override
    public void setScore(int score) {
        gameState.setScore(score);
    }

    @Override
    public int getMaxTile() {
        return gameState.getMaxTile();
    }

    @Override
    public void setMaxTile(int maxTile) {
        gameState.setMaxTile(maxTile);
    }
}
