package io.github.fogzzz.game2048.server.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class Tile {

    @Getter
    @Setter
    private int value;

    public boolean isEmpty() {
        return value == 0;
    }

}
