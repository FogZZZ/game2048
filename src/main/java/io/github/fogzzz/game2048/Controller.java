package io.github.fogzzz.game2048;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@Component
@RequiredArgsConstructor
public class Controller extends KeyAdapter {
    private final Model model;
    @Setter
    private View view;
    private static final int WINNING_TILE = 2048;

    Tile[][] getGameTiles() {
        return model.getGameTiles();
    }

    int getScore() {
        return model.score;
    }

    void resetGame() {
        model.score = 0;
        model.maxTile = 0;

        model.resetGameTiles();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            resetGame();
        }

        if (e.getKeyCode() == KeyEvent.VK_Z) {
            model.rollback();
        }

        if (!model.canMove()) {
            view.showLostDialog();
            view.repaint();
            return;
        }

        if (e.getKeyCode() ==  KeyEvent.VK_LEFT) {
            model.left();
        }
        if (e.getKeyCode() ==  KeyEvent.VK_RIGHT) {
            model.right();
        }
        if (e.getKeyCode() ==  KeyEvent.VK_UP) {
            model.up();
        }
        if (e.getKeyCode() ==  KeyEvent.VK_DOWN) {
            model.down();
        }
        if (e.getKeyCode() == KeyEvent.VK_R) {
            model.randomMove();
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            model.autoMove();
        }

        if (model.maxTile == WINNING_TILE) {
            view.repaint();
            view.showWinDialog();
        }

        view.repaint();
    }

    public View getView() {
        return view;
    }
}
