package com.ivstuart.jbip.game;

import com.ivstuart.jbip.graphics.MainPanel;
import com.ivstuart.jbip.graphics.ScorePanel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Ivan on 20/12/2017.
 */
public class GameManager implements Runnable {

    static boolean paused;
    JFrame frame;
    MainPanel mainPanel;
    ScorePanel scorePanel;

    public static boolean isPaused() {
        return paused;
    }

    public static void setPaused(boolean paused) {
        GameManager.paused = paused;
    }

    public void startGame() {

        JFrame.setDefaultLookAndFeelDecorated(true);

        WelcomePage welcomePage = new WelcomePage();

        frame = new JFrame("Java Biplanes JBIP v1.0");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        Dimension dimension = new Dimension();
        dimension.setSize(640 * 2, 400 * 2);
        frame.setSize(dimension);


        mainPanel = new MainPanel();
        mainPanel.init();


        scorePanel = new ScorePanel();
        mainPanel.setScorePanel(scorePanel);

        frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
        frame.getContentPane().add(scorePanel, BorderLayout.SOUTH);

        frame.repaint();
        frame.setResizable(false);
        frame.setVisible(true);

        Thread thread = new Thread(this);
        thread.start();
    }

    public void run() {
        while (true) {
            if (!paused) {
                mainPanel.update();
                mainPanel.repaint();
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
