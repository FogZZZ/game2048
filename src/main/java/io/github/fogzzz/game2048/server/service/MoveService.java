package io.github.fogzzz.game2048.server.service;

import io.github.fogzzz.game2048.server.model.Move;

public interface MoveService {
    void setMoveAnalysisService(MoveAnalysisService moveAnalysisService);

    Move[] getBasicMoves();
    void move(String direction);

    void rollback();
    void randomMove();
    void autoMove();
}
