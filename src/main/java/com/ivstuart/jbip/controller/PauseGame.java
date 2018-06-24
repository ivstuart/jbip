/*
 */
package com.ivstuart.jbip.controller;


import com.ivstuart.jbip.game.GameManager;

/**
 * @author Ivan Stuart
 */
public class PauseGame extends Pressable {

    /**
     *
     */
    public PauseGame() {
        super(null);
    }

    /*
     * (non-Javadoc)
     *
     * @see controller.Pressable#pressed()
     */
    @Override
    public void pressed() {
        GameManager.setPaused(true);
    }

}
