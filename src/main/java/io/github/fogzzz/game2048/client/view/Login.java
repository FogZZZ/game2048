package io.github.fogzzz.game2048.client.view;

import io.github.fogzzz.game2048.client.Controller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.*;

import static javax.swing.JOptionPane.QUESTION_MESSAGE;

@Service
@RequiredArgsConstructor
public class Login {
    private final Controller controller;

    public void loginUser(Game game) {
        String name = JOptionPane.showInputDialog(game,
                "Введите свой никнейм:",
                "Вход в игру",
                QUESTION_MESSAGE);

        boolean userExists = controller.checkUserName(name);
        String message = userExists ? "Введите пароль:" : "Вы новый пользователь, задайте пароль:";
        String password = JOptionPane.showInputDialog(game,
                    message,
                    "Вход в игру",
                    QUESTION_MESSAGE);
        if (userExists) {
            controller.loginUser(name, password);
        } else {
            controller.registerUser(name, password);
        }
    }


}
