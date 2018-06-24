/*
 */
package com.ivstuart.jbip.controller;


import com.ivstuart.jbip.model.Plane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;

/**
 * @author Ivan Stuart
 */
public class UserInputFactory {

    private static final Logger LOGGER = LogManager.getLogger();

    public static void create(JComponent component, Plane plane, String keys, boolean isPlayerOne) {

        setupKeyStroke(component, new Accelerate(plane), keys.substring(0, 1));
        setupKeyStroke(component, new AirBreak(plane), keys.substring(1, 2));

        setupKeyStroke(component, new RotateClockwise(plane), keys.substring(2, 3));
        setupKeyStroke(component, new RotateAntiClockwise(plane), keys.substring(3, 4));
        setupKeyStroke(component, new Shoot(plane), keys.substring(4, 5));

        if (isPlayerOne) {
            setupKeyStroke(component, new MusicOff(), "n");
            setupKeyStroke(component, new MusicOn(), "m");

            setupKeyStroke(component, new PauseGame(), "0");
            setupKeyStroke(component, new UnPauseGame(), "1");
        }

    }

    private static void setupKeyStroke(JComponent component, Action action, String key) {

        LOGGER.info("Adding key [" + key + "] with action [" + action.getClass().getName() + "]");

        component.getInputMap().put(KeyStroke.getKeyStroke(key.charAt(0)), key);
        component.getActionMap().put(key, action);

    }

}
