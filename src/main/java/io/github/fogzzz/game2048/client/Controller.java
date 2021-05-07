package io.github.fogzzz.game2048.client;

import io.github.fogzzz.game2048.client.dto.User;
import io.github.fogzzz.game2048.client.service.RestService;
import io.github.fogzzz.game2048.client.view.Tile;
import io.github.fogzzz.game2048.client.view.View;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@Component
@RequiredArgsConstructor
public class Controller extends KeyAdapter {
    private final RestService restService;
    @Setter
    private View view;
    private static final int WINNING_TILE = 2048;

    private User user;

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            resetGame();
        }

        if (e.getKeyCode() == KeyEvent.VK_Z) {
            restService.rollback();
        }

        if (!restService.canMove()) {
            view.showLostDialog();
            view.repaint();
            return;
        }

        if (e.getKeyCode() ==  KeyEvent.VK_LEFT) {
            restService.move("left");
        }
        if (e.getKeyCode() ==  KeyEvent.VK_RIGHT) {
            restService.move("right");
        }
        if (e.getKeyCode() ==  KeyEvent.VK_UP) {
            restService.move("up");
        }
        if (e.getKeyCode() ==  KeyEvent.VK_DOWN) {
            restService.move("down");
        }
        if (e.getKeyCode() == KeyEvent.VK_R) {
            restService.randomMove();
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            restService.autoMove();
        }

        if (restService.getMaxTile() == WINNING_TILE) {
            view.repaint();
            view.showWinDialog();
        }

        view.repaint();
    }

    public Tile[][] getGameTiles() {
        return restService.getGameTiles();
    }

    public int getScore() {
        return restService.getScore();
    }

    private void resetGame() {
        restService.resetGame();
    }

    public View getView() {
        return view;
    }

    public void registerUser(String name, String password) {
        this.user = restService.registerUser(name, password);
        view.repaint();
    }

    public void loginUser(String name, String password) {
        this.user = restService.loginUser(name, password);
        view.repaint();
    }

    public String getUserName() {
        if (user == null) {
            return "";
        }
        return user.getName();
    }

    public Integer getMaxScore() {
        if (user == null || user.getMaxScore() == null) {
            return 0;
        }
        return user.getMaxScore();
    }

    public boolean checkUserName(String name) {
        return restService.checkUserName(name);
    }

    public void errorExit(String errorMsg) {
        view.showErrorDialog(errorMsg);
        System.exit(1);
    }
}
