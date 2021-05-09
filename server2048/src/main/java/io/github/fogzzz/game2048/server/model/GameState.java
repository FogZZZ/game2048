package io.github.fogzzz.game2048.server.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class GameState {
    public static final int FIELD_WIDTH = 4;

    private Tile[][] gameTiles;
    private int score;
    private int maxTile;
}

