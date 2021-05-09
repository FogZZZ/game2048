package io.github.fogzzz.game2048.server.service;

import io.github.fogzzz.game2048.server.model.Move;
import io.github.fogzzz.game2048.server.model.MoveEfficiency;
import io.github.fogzzz.game2048.server.model.Tile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import static io.github.fogzzz.game2048.server.model.GameState.FIELD_WIDTH;
import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class MoveAnalysisServiceImpl implements MoveAnalysisService {

    private final GameStateService gameStateService;
    private final MoveService moveService;

    private static final int ANALYSIS_DEPTH = 5;

    @PostConstruct
    public void initMoveService() {
        moveService.setMoveAnalysisService(this);
    }

    @Override
    public Move getBestMove() {
        int numOfVariants = (int)Math.pow(4, ANALYSIS_DEPTH);
        Map<Move, Move> firstSteps = new HashMap<>();
        PriorityQueue<MoveEfficiency> possibleMoves = new PriorityQueue<>(numOfVariants, Collections.reverseOrder());

        Move[][] complexMoves = new Move[numOfVariants][ANALYSIS_DEPTH];
        BigInteger bi = new BigInteger("0", 4);

        for (int i = 0; i < numOfVariants; i++) {
            String s = bi.toString(4);
            String format = format("%%%ds", ANALYSIS_DEPTH);
            s = format(format, s).replace(' ', '0');
            for (int j = 0; j < s.length(); j++) {
                int indexOfBasics = Character.digit(s.charAt(j), 10);
                complexMoves[i][j] = moveService.getBasicMoves()[indexOfBasics];
            }
            bi = bi.add(new BigInteger("1"));
        }

        for (int i = 0; i < numOfVariants; i++) {
            final int finI = i;
            Move complexMove = () -> {
                for (int j = 0; j < ANALYSIS_DEPTH; j++) {
                    complexMoves[finI][j].move();
                }
            };
            possibleMoves.offer(getMoveEfficiency(complexMove));
            firstSteps.put(complexMove, complexMoves[finI][0]);
        }

        Move bestComplexMove = possibleMoves.poll().getMove();
        return firstSteps.get(bestComplexMove);
    }

    private MoveEfficiency getMoveEfficiency(Move move) {
        move.move();
        MoveEfficiency moveEfficiency = new MoveEfficiency(-1, 0, move);
        if (hasBoardChanged()) {
            moveEfficiency = new MoveEfficiency(gameStateService.getEmptyTiles().size(), gameStateService.getScore(), move);
        }
        for (int i = 0; i < ANALYSIS_DEPTH; i++) {
            moveService.rollback();
        }
        return moveEfficiency;
    }

    private boolean hasBoardChanged() {
        int sumAfter = 0, sumBefore = 0;
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                sumAfter += gameStateService.getGameTiles()[i][j].getValue();
            }
        }
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                Tile[][] previousState = gameStateService.getPreviousState(ANALYSIS_DEPTH);
                sumBefore += previousState[i][j].getValue();
            }
        }
        return sumAfter != sumBefore;
    }
}
