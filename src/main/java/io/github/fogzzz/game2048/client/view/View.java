package io.github.fogzzz.game2048.client.view;

import io.github.fogzzz.game2048.client.Controller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;

import static java.lang.String.format;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;

@Component
@RequiredArgsConstructor
public class View extends JPanel {
    private static final Color BG_COLOR = new Color(0xbbada0);
    private static final String FONT_NAME = "Arial";
    private static final int TILE_SIZE = 96;
    private static final int TILE_MARGIN = 12;

    private final Controller controller;

    private boolean isErrorExit;

    @PostConstruct
    public void init() {
        setFocusable(true);
        addKeyListener(controller);

        controller.setView(this);
    }

    public String showEnterNameDialog() {
        return JOptionPane.showInputDialog(this,
                "Введите свой никнейм:",
                "Вход в игру",
                QUESTION_MESSAGE);
    }

    public void showGuestDialog() {
        JOptionPane.showMessageDialog(this,
                "Вы вошли как Гость, рекорд не будет сохранен.",
                "Вход в игру",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public String showEnterPasswordDialog(String message) {
        return JOptionPane.showInputDialog(this,
                message,
                "Вход в игру",
                QUESTION_MESSAGE);
    }

    public void showAuthFailureDialog() {
        JOptionPane.showMessageDialog(this,
                "Введен неверный пароль, попробуйте еще раз.",
                "Вход в игру",
                JOptionPane.WARNING_MESSAGE);
    }

    public void setErrorExit(boolean isErrorExit) {
        this.isErrorExit = isErrorExit;
    }

    @Override
    public void paint(Graphics g) {
        if (!isErrorExit) {
            super.paint(g);
            g.setColor(BG_COLOR);
            g.fillRect(0, 0, this.getSize().width, this.getSize().height);
            for (int x = 0; x < 4; x++) {
                for (int y = 0; y < 4; y++) {
                    drawTile(g, controller.getGameTiles()[y][x], x, y);
                }
            }

            g.drawString("Score: " + controller.getScore(), 140, 465);
            if (controller.getUserName() != null) {
                g.drawString(format("%s Max: %d", controller.getUserName(), controller.getMaxScore()),
                        50, 500);
            }
        }
    }

    public void showLostDialog() {
        JOptionPane.showMessageDialog(this, "You've lost :(");
    }

    public void showWinDialog() {
        JOptionPane.showMessageDialog(this, "You've won!");
    }

    public void showErrorDialog(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    private void drawTile(Graphics g2, Tile tile, int x, int y) {
        Graphics2D g = ((Graphics2D) g2);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int value = tile.getValue();
        int xOffset = offsetCoors(x);
        int yOffset = offsetCoors(y);
        g.setColor(tile.getTileColor());
        g.fillRoundRect(xOffset, yOffset, TILE_SIZE, TILE_SIZE , 8, 8);
        g.setColor(tile.getFontColor());
        final int size = value < 100 ? 36 : value < 1000 ? 32 : 24;
        final Font font = new Font(FONT_NAME, Font.BOLD, size);
        g.setFont(font);

        String s = String.valueOf(value);
        final FontMetrics fm = getFontMetrics(font);

        final int w = fm.stringWidth(s);
        final int h = -(int) fm.getLineMetrics(s, g).getBaselineOffsets()[2];

        if (value != 0)
            g.drawString(s, xOffset + (TILE_SIZE - w) / 2, yOffset + TILE_SIZE - (TILE_SIZE - h) / 2 - 2);
    }

    private static int offsetCoors(int arg) {
        return arg * (TILE_MARGIN + TILE_SIZE) + TILE_MARGIN;
    }
}
