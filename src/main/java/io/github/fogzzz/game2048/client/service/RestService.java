package io.github.fogzzz.game2048.client.service;

import io.github.fogzzz.game2048.client.dto.User;
import io.github.fogzzz.game2048.client.view.Tile;

public interface RestService {

    boolean checkUserName(String name);
    User registerUser(String name, String password);
    User loginUser(String name, String password);

    Tile[][] getGameTiles();
    int getScore();
    int getMaxTile();

    void resetGame();
    boolean canMove();
    void move(String direction);
    void rollback();
    void randomMove();
    void autoMove();
}
