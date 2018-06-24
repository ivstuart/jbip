package com.ivstuart.jbip.graphics;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Ivan on 20/12/2017.
 */
public class ScorePanel extends JPanel {

    private int playerOneScore = 0;
    private int playerTwoScore = 0;

    private int fontSize = 40;
    private Font font = new Font("Arial", Font.PLAIN, fontSize);

    public ScorePanel() {

        setPreferredSize(new Dimension(640 * 2, 100));
        this.setBackground(Color.BLACK);
        this.setForeground(Color.CYAN);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setFont(font);

        g.drawString(String.format("%02d", playerOneScore), 0, 40);
        g.drawString(String.format("%02d", playerTwoScore), 610 * 2, 40);

    }

    public int getPlayerTwoScore() {
        return playerTwoScore;
    }

    public void setPlayerTwoScore(int playerTwoScore) {
        this.playerTwoScore = playerTwoScore;
    }

    public int getPlayerOneScore() {
        return playerOneScore;
    }

    public void setPlayerOneScore(int playerOneScore) {
        this.playerOneScore = playerOneScore;
    }

    public void incrementScore(boolean playerOne) {
        if (playerOne) {
            playerOneScore++;
        } else {
            playerTwoScore++;
        }
        this.repaint();
    }
}
