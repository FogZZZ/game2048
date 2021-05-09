package io.github.fogzzz.game2048.server.service;

import io.github.fogzzz.game2048.server.model.Move;

public interface MoveAnalysisService {
    Move getBestMove();
}
