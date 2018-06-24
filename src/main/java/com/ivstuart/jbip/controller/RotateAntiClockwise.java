package com.ivstuart.jbip.controller;

import com.ivstuart.jbip.model.Plane;

/**
 * Created by Ivan on 21/06/2018.
 */
public class RotateAntiClockwise extends Pressable {

    public RotateAntiClockwise(Plane plane) {
        super(plane);
    }

    public void pressed() {
        this.getPlane().rotate(-10);
    }

}
