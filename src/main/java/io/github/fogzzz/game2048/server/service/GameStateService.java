package io.github.fogzzz.game2048.server.service;


import io.github.fogzzz.game2048.server.model.Tile;

import java.util.List;

public interface GameStateService {

    Tile[][] getGameTiles();

    void addTile();

    void resetGameTiles();

    List<Tile> getEmptyTiles();

    boolean canMove();

    void saveState();

    Tile[][] getPreviousState(int depth);

    void rollback();

    int getScore();

    void setScore(int score);

    int getMaxTile();

    void setMaxTile(int maxTile);

}
