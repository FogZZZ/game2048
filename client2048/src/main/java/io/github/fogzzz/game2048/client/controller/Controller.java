package io.github.fogzzz.game2048.client.controller;

import io.github.fogzzz.game2048.client.dto.User;
import io.github.fogzzz.game2048.client.service.RestService;
import io.github.fogzzz.game2048.client.service.UserService;
import io.github.fogzzz.game2048.client.view.Tile;
import io.github.fogzzz.game2048.client.view.View;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@Component
@RequiredArgsConstructor
public class Controller extends KeyAdapter {
    private final RestService restService;
    @Qualifier("userClientService")
    private final UserService userService;
    @Setter
    private View view;
    private static final int WINNING_TILE = 2048;

    private User user;

    public void loginUser() {
        while (true) {
            String name = view.showEnterNameDialog();
            if (name == null) {
                view.showGuestDialog();
                this.user = new User("Гость");
                view.repaint();
                return;
            }

            if (name.isBlank()) continue;

            this.user = new User(name);
            boolean userExists = userService.checkUserName(name);

            String message = userExists ? "Введите пароль:" : "Вы новый пользователь, задайте пароль:";
            String password = view.showEnterPasswordDialog(message);
            if (password == null || password.isBlank()) continue;

            User updatedUser = userService.sendCredentials(this.user, password, userExists);
            if (updatedUser == null) {
                view.showAuthFailureDialog();
                continue;
            }

            this.user.setMaxScore(updatedUser.getMaxScore());
            view.repaint();
            return;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            resetGame();
        }

        if (e.getKeyCode() == KeyEvent.VK_Z) {
            restService.rollback();
        }

        if (!restService.canMove()) {
            User updatedUser = userService.saveMaxScore(user);
            this.user.setMaxScore(updatedUser.getMaxScore());
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
            User updatedUser = userService.saveMaxScore(user);
            this.user.setMaxScore(updatedUser.getMaxScore());
            view.showWinDialog();
        }

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

    public void errorExit(String errorMsg) {
        view.setErrorExit(true);
        view.showErrorDialog(errorMsg);
        System.exit(1);
    }
}
