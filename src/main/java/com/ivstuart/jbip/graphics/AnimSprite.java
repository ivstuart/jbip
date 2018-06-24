package com.ivstuart.jbip.graphics;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan on 23/06/2018.
 */
public class AnimSprite extends Sprite {

    int counter = 0;
    int index = 0;
    private List<Image> imageList = new ArrayList<Image>();

    public List<Image> getImageList() {
        return imageList;
    }

    public void draw(Graphics2D g) {

        AffineTransform tx = new AffineTransform();
        tx.setToTranslation(x, y);
        g.drawImage(imageList.get(index), tx, null);
        counter++;
        if (counter % 2 == 0) {
            index++;
        }
        if (index >= imageList.size()) {
            index = 0;
        }
    }

    public boolean readyForRemoval() {
        if (counter > 20) {
            return true;
        }
        return false;
    }

}
