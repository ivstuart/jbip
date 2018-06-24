/*
 */
package com.ivstuart.jbip.controller;


import com.ivstuart.jbip.game.GameManager;

/**
 * @author Ivan Stuart
 */
public class UnPauseGame extends Pressable {

    /**
     *
     */
    public UnPauseGame() {
        super(null);
    }

    /*
     * (non-Javadoc)
     *
     * @see controller.Pressable#pressed()
     */
    @Override
    public void pressed() {
        GameManager.setPaused(false);
    }

}
