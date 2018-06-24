package com.ivstuart.jbip.graphics;

import com.ivstuart.jbip.controller.UserInputFactory;
import com.ivstuart.jbip.model.Plane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan on 20/12/2017.
 */
public class MainPanel extends JPanel {

    private static final Logger LOGGER = LogManager.getLogger();

    private ScorePanel scorePanel;
    private List<Sprite> sprites;
    private Plane planeOne;
    private Plane planeTwo;

    private int respawnCounter = 0;

    public List<Sprite> getSprites() {
        return sprites;
    }

    public void setSprites(List<Sprite> sprites) {
        this.sprites = sprites;
    }

    public void init() {

        this.setBackground(Color.BLACK);

        sprites = new ArrayList<Sprite>();

        Sprite background = new Sprite(GraphicsManager.getInstance().getImage("background"));
        background.setY(360);
        sprites.add(background);

        spawnPlayerOne();

        spawnPlayerTwo();

        spawnBallon();

        spawnCloud(40, 200, 1.0F);
        spawnCloud(60, 200, 3.0F);
        spawnCloud(80, 200, 5.0F);


        // TODO Decide if really need to draw this again or not.
        Sprite hanger = new Sprite(GraphicsManager.getInstance().getImage("hanger"));
        hanger.setX(560);
        hanger.setY(550);
        hanger.setHanger(true);
        sprites.add(hanger);

        int scalingFactor = 2;
        setPreferredSize(new Dimension(640 * scalingFactor, 400 * scalingFactor));
        setFocusable(true);

        initPlayerOneKeys();

        // TODO write AI player for computer opponent.
        initPlayerTwoKeys();


    }

    private void spawnCloud(int x, int y, float dx) {
        Sprite cloud1 = new Sprite(GraphicsManager.getInstance().getImage("cloud"));
        cloud1.setCloud(true);
        cloud1.setX(x);
        cloud1.setY(y);
        cloud1.dx = dx;
        sprites.add(cloud1);
    }

    private void spawnBallon() {
        Sprite ballon = new Sprite(GraphicsManager.getInstance().getImage("extra"));
        ballon.setX(580);
        ballon.setY(560);
        ballon.setBallon(true);
        ballon.dx = (float) (Math.random() - 0.5) * 3;
        ballon.dy = -1.0F;
        sprites.add(2, ballon);
    }

    private void spawnPlayerTwo() {
        Sprite player2 = new Sprite(GraphicsManager.getInstance().getImage("player2"));
        player2.setX(1200);
        player2.setY(630);
        player2.setPlane(true);
        sprites.add(2, player2);
        if (planeTwo == null) {
            planeTwo = new Plane(this);
        }
        player2.setPlane(planeTwo);
        planeTwo.setSprite(player2);
        planeTwo.setRotation(0);

    }

    private void spawnPlayerOne() {
        Sprite player1 = new Sprite(GraphicsManager.getInstance().getImage("player1"));
        player1.setX(40);
        player1.setY(630);
        player1.setPlane(true);
        player1.setPlayerOne(true);
        sprites.add(1, player1);

        if (planeOne == null) {
            planeOne = new Plane(this);

        }
        player1.setPlane(planeOne);
        planeOne.setSprite(player1);
        planeOne.setRotation(0);

    }

    public void initPlayerOneKeys() {
        UserInputFactory.create(this, planeOne, "sxzc ", true);
    }

    public void initPlayerTwoKeys() {
        UserInputFactory.create(this, planeTwo, "ikjl#", false);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        requestFocusInWindow();

        paintSky(g);

        for (int index = 0; index < sprites.size(); index++) {

            Sprite aSprite = sprites.get(index);

            aSprite.draw((Graphics2D) g);

        }

        this.collisionDetection();

    }

    private void paintSky(Graphics g) {
        int height = 20;
        for (int i = 0; i < 20; i++) {
            g.setColor(new Color(i * 10, i * 10, 255));
            g.fillRect(0, 0 + i * height, this.getWidth(), i * (height + 1));
        }
    }

    public void update() {

        boolean isPlayerOneDead = true;
        boolean isPlayerTwoDead = true;

        boolean isBallonDead = true;

        for (int index = 0; index < sprites.size(); index++) {

            Sprite aSprite = sprites.get(index);

            if (aSprite.isPlane()) {
                if (aSprite.isPlayerOne()) {
                    isPlayerOneDead = false;
                } else {
                    isPlayerTwoDead = false;
                }
            }

            if (aSprite.isBallon()) {
                isBallonDead = false;
            }

            aSprite.update(1);

            if (aSprite.readyForRemoval()) {
                LOGGER.info("Removing sprite index " + index);
                aSprite.dispose();
                sprites.remove(index);
            }
        }

        if (isPlayerOneDead || isPlayerTwoDead) {
            respawnCounter++;
            if (respawnCounter > 50) {
                respawnCounter = 0;
                if (isPlayerOneDead) {
                    this.spawnPlayerOne();
                }
                if (isPlayerTwoDead) {
                    this.spawnPlayerTwo();
                }
            }
        }

        if (isBallonDead && Math.random() > 0.997) {
            this.spawnBallon();
        }
    }

    private void collisionDetection() {

        for (int index = 1; index < sprites.size(); index++) {

            Sprite aSprite = sprites.get(index);

            if (aSprite.isCloud()) {
                continue;
            }

            for (int innerIndex = index + 1; innerIndex < sprites.size(); innerIndex++) {
                Sprite anotherSprite = sprites.get(innerIndex);

                if (anotherSprite.isCloud()) {
                    continue;
                }

                if (aSprite.isCollision(anotherSprite)) {

                    LOGGER.debug("A collision occurred between " + index + " and " + innerIndex);

                    this.collision(aSprite, anotherSprite);

                }
            }
        }

    }

    private void collision(Sprite aSprite, Sprite anotherSprite) {

        // if both shots ignore collision
        if (aSprite.isShot() && anotherSprite.isShot()) {
            LOGGER.info("Shots collision nothing to do");
            return;
        }

        if (aSprite.isHanger() && anotherSprite.isBallon()) {
            LOGGER.info("Hanger ballon collision nothing to do");
            return;
        }

        if (aSprite.isHanger() && anotherSprite.isShot()) {
            LOGGER.info("Hanger collision with shot dispose of shot");
            anotherSprite.setDispose(true);
            return;
        }

        if (aSprite.isPlane() && anotherSprite.isHanger()) {
            LOGGER.info("Plane collision hanger results in death of plane");
            aSprite.getPlane().setDeath();
            return;
        }

        if (aSprite.isPlane() && anotherSprite.isShot()) {
            LOGGER.info("Plane collision with shot results in death of plane");

            scorePanel.incrementScore(anotherSprite.isPlayerOne());

            aSprite.getPlane().setDeath();
            return;
        }

        // Plane to plane collision
        if (aSprite.isPlane() && anotherSprite.isPlane()) {
            // Both killed no change in score
            LOGGER.info("Plane collision with plane results in death of both planes");
            aSprite.getPlane().setDeath();
            anotherSprite.getPlane().setDeath();
            return;

        }

        // Ballon collision with shot
        if (aSprite.isBallon() && anotherSprite.isShot()) {
            LOGGER.info("Ballon collision with shot results in ballon going pop");
            scorePanel.incrementScore(anotherSprite.isPlayerOne());
            createExplosion(aSprite);
            aSprite.setDispose(true);
            anotherSprite.setDispose(true);
            return;
        }

        // Plane collision with Ballon
        if (aSprite.isPlane() && anotherSprite.isBallon()) {
            LOGGER.info("Plane collision with ballon results in ballon going pop");
            scorePanel.incrementScore(aSprite.isPlayerOne());
            createExplosion(anotherSprite);
            anotherSprite.setDispose(true);
            return;
        }

        // Ballon with a plane
        if (aSprite.isBallon() && anotherSprite.isPlane()) {
            LOGGER.info("Ballon collision with plane results in ballon going pop");
            scorePanel.incrementScore(anotherSprite.isPlayerOne());
            createExplosion(aSprite);
            aSprite.setDispose(true);
            return;
        }

    }

    public void setScorePanel(ScorePanel scorePanel) {
        this.scorePanel = scorePanel;
    }

    public void createExplosion(Sprite sprite) {
        AnimSprite explosion = new AnimSprite();
        explosion.getImageList().add(GraphicsManager.getInstance().getImage("player1_ex1"));
        explosion.getImageList().add(GraphicsManager.getInstance().getImage("player1_ex2"));
        explosion.getImageList().add(GraphicsManager.getInstance().getImage("player1_ex3"));
        explosion.setX(sprite.getX());
        explosion.setY(sprite.getY());
        this.getSprites().add(explosion);
    }
}
