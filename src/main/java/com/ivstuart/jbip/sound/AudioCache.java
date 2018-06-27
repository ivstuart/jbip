/*
 */
package com.ivstuart.jbip.sound;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ivan Stuart
 */
public class AudioCache {

    static AudioCache INSTANCE = new AudioCache();

    private static Map<String, AudioClip> soundMap;

    /**
     *
     */
    public AudioCache() {
        super();
        soundMap = new HashMap<String, AudioClip>();
        init();
    }

    private static AudioClip getClip(String name) {
        return soundMap.get(name);
    }

    public static void loop(String name) {
        getClip(name).loop();
    }

    public static void play(String name) {
        getClip(name).play();
    }

    public static void stop(String name) {
        getClip(name).stop();
    }

    private void init() {
        loadSound("die", "player1_die.wav");
        loadSound("fly", "player1_fly.wav");
        loadSound("shoot", "player1_shoot.wav");

        loadSound("music1", "music1.wav");

    }

    private void loadSound(String key, String filename) {
        URL aURL;
        try {
            aURL = ClassLoader.getSystemResources("sounds/" + filename).nextElement();
            // aURL = AudioCache.class.getResource("../resources/sounds/" + filename);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        AudioClip clip = Applet.newAudioClip(aURL);

        soundMap.put(key, clip);
    }

}
