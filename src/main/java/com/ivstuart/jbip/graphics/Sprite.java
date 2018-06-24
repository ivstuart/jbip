package com.ivstuart.jbip.graphics;

import com.ivstuart.jbip.model.Plane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Sprite {

    private static final Logger LOGGER = LogManager.getLogger();

    protected Image image;

    // position (pixels)
    protected float x;
    protected float y;
    // velocity (pixels per millisecond)
    protected float dx;
    protected float dy;
    // dimensions of sprite
    protected int height;
    protected int width;
    private double angle;

    private boolean isHanger = false;
    private Plane plane = null;
    private boolean isShot = false;
    private boolean isCloud = false;
    private boolean isBallon = false;
    private boolean isPlane = false;
    private boolean isDispose = false;
    private boolean isPlayerOne = false;

    public Sprite() {

    }

    /**
     * Creates a new Sprite object with the specified Animation.
     */
    public Sprite(Image myImage) {
        image = myImage;
        this.setSize();
    }

    public boolean isPlayerOne() {
        return isPlayerOne;
    }

    public void setPlayerOne(boolean playerOne) {
        isPlayerOne = playerOne;
    }

    public boolean isHanger() {
        return isHanger;
    }

    public void setHanger(boolean hanger) {
        this.isHanger = hanger;
    }

    public boolean isShot() {
        return isShot;
    }

    public void setShot(boolean shot) {
        isShot = shot;
    }

    public Plane getPlane() {
        return plane;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public float getDx() {
        return dx;
    }

    public void setDx(float dx) {
        this.dx = dx;
    }

    public float getDy() {
        return dy;
    }

    public void setDy(float dy) {
        this.dy = dy;
    }

    /**
     * @param g
     */
    public void draw(Graphics2D g) {

        AffineTransform tx = new AffineTransform();
        tx.setToTranslation(x, y);
        tx.rotate(angle, width / 2, height / 2);
        g.drawImage(image, tx, null);

    }

    /**
     * @return Returns the height.
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return Returns the width.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets this Sprite's current x position.
     */
    public int getX() {
        return Math.round(x);
    }

    /**
     * Sets this Sprite's current x position.
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Gets this Sprite's current y position.
     */
    public int getY() {
        return Math.round(y);
    }

    /**
     * Sets this Sprite's current y position.
     */
    public void setY(float y) {
        this.y = y;
    }

    public boolean isCollision(Sprite sp) {
        int spX = sp.getX();
        int spY = sp.getY();

        // check if the two sprites' boundaries intersect
        return (x + width > spX && y + height > spY && x < spX + sp.getWidth() && y < spY
                + sp.getHeight());

    }

    protected void setSize() {
        if (image != null) {
            height = image.getHeight(null);
            width = image.getWidth(null);
        }
    }

    /**
     * Updates this Sprite's Animation and its position based on the velocity.
     */
    public void update(long elapsedTime) {
        x += dx * elapsedTime;
        y += dy * elapsedTime;
        // if animation update when need to

        if (isShot) {
            return;
        }

        // Wrap x direction world.
        if (x > 640 * 2) {
            x = x - 640 * 2 - this.width;
        } else if (x < -this.width) {
            x = x + 640 * 2;
        }

        if (isPlane) {
            if (y > 630) {
                plane.setDeath();
            } else if (y < 0) {
                y = 0;
                LOGGER.info("Plane has stalled");
                plane.setThrottle(0);
            }
        }

        if (isBallon) {
            if (y < 0) {
                y = 0;
            }
        }


    }

    public float getVelocitySquared() {
        return dx * dx + dy * dy;
    }

    public boolean readyForRemoval() {
        if (isDispose) {
            return true;
        }

        if (isShot) {
            if (x < 0 || x > 640 * 2) {
                return true;
            }
            // TODO tweak this.
            if (y < 0 || y > 640) {
                return true;
            }
            return false;
        }
        return false;
    }

    public void dispose() {
        LOGGER.info("Disposing of sprite for plane " + plane);
        if (plane != null) {
            plane.reduceShots();
        }
    }

    public boolean isCloud() {
        return isCloud;
    }

    public void setCloud(boolean cloud) {
        isCloud = cloud;
    }

    public boolean isBallon() {
        return isBallon;
    }

    public void setBallon(boolean ballon) {
        this.isBallon = ballon;
    }

    public boolean isPlane() {
        return isPlane;
    }

    public void setPlane(boolean plane) {
        this.isPlane = plane;
    }

    public void setDispose(boolean dispose) {
        this.isDispose = dispose;
    }

    public boolean isOnGround() {
        return y == 630;
    }
}
