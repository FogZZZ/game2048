package io.github.fogzzz.game2048.client;

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
            model.move("left");
        }
        if (e.getKeyCode() ==  KeyEvent.VK_RIGHT) {
            model.move("right");
        }
        if (e.getKeyCode() ==  KeyEvent.VK_UP) {
            model.move("up");
        }
        if (e.getKeyCode() ==  KeyEvent.VK_DOWN) {
            model.move("down");
        }
        if (e.getKeyCode() == KeyEvent.VK_R) {
            model.randomMove();
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            model.autoMove();
        }

        if (model.getMaxTile() == WINNING_TILE) {
            view.repaint();
            view.showWinDialog();
        }

        view.repaint();
    }

    Tile[][] getGameTiles() {
        return model.getGameTiles();
    }

    int getScore() {
        return model.getScore();
    }

    private void resetGame() {
        model.resetGame();
    }

    public View getView() {
        return view;
    }
}
