package io.github.fogzzz.game2048.client;

public interface Model {

    Tile[][] getGameTiles();
    int getScore();
    int getMaxTile();

    void resetGameTiles();
    void resetGame();

    boolean canMove();
    void move(String direction);
    void rollback();
    void randomMove();
    void autoMove();
}
