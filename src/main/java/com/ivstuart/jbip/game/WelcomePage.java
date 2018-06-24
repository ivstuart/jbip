/*
 * Created on Nov 5, 2004
 *
 */
package com.ivstuart.jbip.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Ivan Stuart
 */
public class WelcomePage {

    public static final int FONT_SIZE = 14;
    public final String message = "Welcome to JBIP.\n\n"
            + "The aim of this game is to defeat your opponent by shooting their\n"
            + "biplane. \n"
            + "\nControls are:\n" + "Player One             Player Two\n"
            + "----------                  -----------\n"
            + "    s     <throttle up>         i\n"
            + "    x     <air breaks>          k\n"
            + "    z     <turn clockwise>      j\n"
            + "    c     <turn anti-clockwise> h\n"
            + " space bar <shoot>              #\n"
            + "\n\n" + "    n   <music  off>\n"
            + "    m   <music  on >\n" + "    0   <pause  game>\n"
            + "    1   <resume game>\n";
    private final JButton ok;
    private Font courier = new Font("Courier", Font.BOLD, FONT_SIZE);
    private JFrame frame;

    private Container pane;

    /**
     * @param manager
     */
    public WelcomePage() {

        JFrame.setDefaultLookAndFeelDecorated(true);

        frame = new JFrame("Welcome Page - Instructions");

        pane = frame.getContentPane();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();

        frame.setVisible(true);

        JTextArea ta = new JTextArea();
        ta.setFont(courier);
        ta.setText(message);
        ta.setEditable(false);

        // TODO Auto-generated constructor stub
        ok = new JButton("DONE");

        ok.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                frame.dispose();
            }

        });

        pane.add(ta, BorderLayout.WEST);
        pane.add(ok, BorderLayout.SOUTH);

        frame.pack();
    }

    /**
     *
     */
    public void toFront() {
        // TODO Auto-generated method stub
        frame.toFront();
    }

}
