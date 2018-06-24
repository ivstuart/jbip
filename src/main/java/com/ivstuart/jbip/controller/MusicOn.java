/*
 * Created on Nov 4, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ivstuart.jbip.controller;


import com.ivstuart.jbip.sound.AudioCache;

/**
 * @author Ivan Stuart
 *         <p>
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class MusicOn extends Pressable {

    /**
     *
     */
    public MusicOn() {
        super(null);
    }

    /*
     * (non-Javadoc)
     *
     * @see controller.Pressable#pressed()
     */
    @Override
    public void pressed() {
        // TODO Auto-generated method stub
        AudioCache.loop("music1");
    }

}
