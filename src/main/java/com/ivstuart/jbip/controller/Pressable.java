/*
 */
package com.ivstuart.jbip.controller;


import com.ivstuart.jbip.model.Plane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @author Ivan Stuart
 */
public abstract class Pressable extends AbstractAction {

    private static final Logger LOGGER = LogManager.getLogger();

    private Plane plane;

    public Pressable(Plane plane) {
        this.plane = plane;
    }

    public Plane getPlane() {
        return plane;
    }

    public abstract void pressed();

    @Override
    public void actionPerformed(ActionEvent e) {
        LOGGER.debug("Pressed: " + e.getActionCommand());
        this.pressed();
    }


}
