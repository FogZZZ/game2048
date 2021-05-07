package io.github.fogzzz.game2048.client.view;

import io.github.fogzzz.game2048.client.Controller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
@RequiredArgsConstructor
public class Game extends JFrame {

    private final Controller controller;
    private final Login login;

    public void start() {
        this.setTitle("2048");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(450, 540);
        this.setResizable(false);

        this.add(controller.getView());

        this.setLocationRelativeTo(null);
        this.setVisible(true);

        login.loginUser(this);
    }
}
