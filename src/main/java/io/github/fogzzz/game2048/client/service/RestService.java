package io.github.fogzzz.game2048.client.service;

import io.github.fogzzz.game2048.client.dto.User;
import io.github.fogzzz.game2048.client.dto.UserForRegistration;
import io.github.fogzzz.game2048.client.view.Tile;

public interface RestService {

    boolean checkUserName(String name);
    User registerUser(UserForRegistration user);
    User loginUser(User user);
    User saveMaxScore(User user);

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
