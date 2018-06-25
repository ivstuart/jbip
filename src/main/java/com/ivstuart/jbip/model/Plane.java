package com.ivstuart.jbip.model;

import com.ivstuart.jbip.graphics.GraphicsManager;
import com.ivstuart.jbip.graphics.MainPanel;
import com.ivstuart.jbip.graphics.Sprite;
import com.ivstuart.jbip.sound.AudioCache;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;

/**
 * Created by Ivan on 20/12/2017.
 */
public class Plane {

    private static final Logger LOGGER = LogManager.getLogger();
    private final MainPanel mainPanel;

    private Sprite sprite;
    private int throttle;
    private int rotation; // degrees

    private boolean isFlying = false;
    private boolean isStalled = false;

    private int countShots;
    private int maxShots = 2;

    private float AIR_FRICTION = 0.96F;

    private float gravity = 2.5F;
    private float lift = 2.0F;

    private float restartVelocity = 16.0F;
    private float stallVelocity = 8.0F;

    public Plane() {
        this.mainPanel = null;
    }

    public Plane(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    public boolean isFlying() {
        return isFlying;
    }

    public void setFlying(boolean flying) {
        isFlying = flying;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public int getThrottle() {
        return throttle;
    }

    public void setThrottle(int throttle) {
        this.throttle = throttle;
        this.updateVelocity();
        // AudioCache.loop("fly");
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public void rotate(int rotation) {
        this.rotation += rotation;
        this.rotation %= 360;
        this.updateVelocity();
    }

    public void updateVelocity() {

        if (sprite != null) {
            float cos, sin;
            if (sprite.isPlayerOne()) {
                cos = (float) Math.cos(rotation * Math.PI / 180.0);
                sin = (float) Math.sin(rotation * Math.PI / 180.0);
            } else {
                cos = -(float) Math.cos(rotation * Math.PI / 180.0);
                sin = (float) Math.sin(-rotation * Math.PI / 180.0);
            }

            // Thrust
            float dx = sprite.getDx() + cos * throttle;
            float lift = Math.abs(cos * this.lift);

            if (isStalled) {
                lift = 0;
                throttle = 0;
            }

            float dy = sprite.getDy() + sin * throttle + gravity - lift;

            float velocity = (float) Math.sqrt((dx * dx) + (dy * dy));

            // Stalling
            if (velocity < stallVelocity && !sprite.isOnGround()) {
                isStalled = true;
            }

            if (isStalled) {
                if (velocity > restartVelocity) {
                    isStalled = false;
                }
            }

            if (!isStalled) {
                dx = cos * velocity;
                dy = sin * velocity;
            }

            // Drag
            dx *= AIR_FRICTION;
            dy *= AIR_FRICTION;


            sprite.setDx(dx);
            sprite.setDy(dy);

            sprite.setAngle(rotation * Math.PI / 180.0);

            LOGGER.debug("Rotation: " + rotation + " dx: " + sprite.getDx() + " dy: " + sprite.getDy());
        }

    }

    public void shoot() {
        if (countShots >= maxShots) {
            LOGGER.info("You can not shoot you have already have " + countShots + " shots in field of view");
            return;
        }

        if (sprite.isOnGround()) {
            LOGGER.info("You can not shoot you are on the ground");
            return;
        }

        // TODO check is on ground in which case can not fire

        countShots++;

        AudioCache.play("shoot");

        Image shotImage = null;

        if (sprite.isPlayerOne()) {
            shotImage = GraphicsManager.getInstance().getImage("shot1");
        } else {
            shotImage = GraphicsManager.getInstance().getImage("shot2");
        }

        Sprite shot = new Sprite(shotImage);
        shot.setX(sprite.getX());
        shot.setY(sprite.getY());
        shot.setPlayerOne(sprite.isPlayerOne());

        if (sprite.isPlayerOne()) {
            shot.setDx((float) Math.cos(rotation * Math.PI / 180.0) * 30);
            shot.setDy((float) Math.sin(rotation * Math.PI / 180.0) * 30);
        } else {
            shot.setDx(-(float) Math.cos(rotation * Math.PI / 180.0) * 30);
            shot.setDy((float) Math.sin(-rotation * Math.PI / 180.0) * 30);
        }

        shot.update(3);
        shot.setPlane(this);
        shot.setShot(true);

        mainPanel.getSprites().add(shot);

    }

    public void reduceShots() {
        countShots--;
    }

    public void setDeath() {

        if (sprite.isPlayerOne()) {
            LOGGER.info("Player one has been killed");
        } else {
            LOGGER.info("Player two has been killed");
        }

        mainPanel.getSprites().remove(sprite);

        mainPanel.createExplosion(sprite);

    }

    public void setStalled(boolean stalled) {
        this.isStalled = stalled;
    }
}
