package com.ivstuart.jbip.controller;

import com.ivstuart.jbip.model.Plane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Ivan on 21/06/2018.
 */
public class Shoot extends Pressable {

    private static final Logger LOGGER = LogManager.getLogger();

    public Shoot(Plane plane) {
        super(plane);
    }

    public void pressed() {
        // Can only shoot while in the air
        this.getPlane().shoot();
    }

}
