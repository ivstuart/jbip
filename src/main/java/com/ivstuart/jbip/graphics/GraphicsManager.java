/*
 * Created on Oct 13, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ivstuart.jbip.graphics;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ivan Stuart
 */
public class GraphicsManager {

    private static final Logger LOGGER = LogManager.getLogger();

    private static GraphicsManager INSTANCE = new GraphicsManager();

    private Map<String, Image> map = new HashMap<String, Image>();

    private boolean newGraphics = true;

    /**
     *
     */
    public GraphicsManager() {
        this.init();
    }

    public static GraphicsManager getInstance() {
        return INSTANCE;
    }

    /**
     * Make provided image transparent wherever color matches the provided color.
     *
     * @param im    BufferedImage whose color will be made transparent.
     * @param color Color in provided image which will be made transparent.
     * @return Image with transparency applied.
     */
    public static Image makeColorTransparent(final BufferedImage im, final Color color) {
        LOGGER.info("Transparency being applied to colour: " + color);

        final ImageFilter filter = new RGBImageFilter() {
            // the color we are looking for (white)... Alpha bits are set to opaque
            public int markerRGB = color.getRGB() | 0xFFFFFFFF;

            // LOGGER.info("Transparency being applied to colour rgb: "+markerRGB);

            public final int filterRGB(final int x, final int y, final int rgb) {
                if ((rgb | 0xFF000000) == markerRGB) {
                    // Mark the alpha bits as zero - transparent
                    return 0x00FFFFFF & rgb;
                } else {
                    // nothing to do
                    return rgb;
                }
            }
        };

        final ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
        return Toolkit.getDefaultToolkit().createImage(ip);
    }

    /**
     * Converts a given Image into a BufferedImage
     *
     * @param img The Image to be converted
     * @return The converted BufferedImage
     */
    public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }

    public Image getFlippedImage(GraphicsConfiguration gc, Image image) {
        return getScaledImage(gc, image, 1, -1);
    }

    public Image getImage(String name) {

        return map.get(name);
    }

    public Image getMirrorImage(GraphicsConfiguration gc, Image image) {
        return getScaledImage(gc, image, -1, 1);
    }

    private Image getScaledImage(GraphicsConfiguration gc, Image image, float x, float y) {

        // set up the transform
        AffineTransform transform = new AffineTransform();
        transform.scale(x, y);
        transform.translate((x - 1) * image.getWidth(null) / 2,
                (y - 1) * image.getHeight(null) / 2);

        // create a transparent (not translucent) image
        Image newImage = gc.createCompatibleImage(image.getWidth(null),
                image.getHeight(null), Transparency.BITMASK);

        // draw the transformed image
        Graphics2D g = (Graphics2D) newImage.getGraphics();
        g.drawImage(image, transform, null);
        g.dispose();

        return newImage;
    }

    public void init() {
        loadImage("background");
        loadImage("cloud");
        loadImage("extra");
        loadImage("hanger");
        loadImage("player1");
        loadImage("player1_ex1");
        loadImage("player1_ex2");
        loadImage("player1_ex3");

        loadImage("player2");
        loadImage("player2_ex1");
        loadImage("player2_ex2");
        loadImage("player2_ex3");

        loadImage("shot1");
        loadImage("shot2");


    }

    public Image loadImage(String filename) {
        if (newGraphics) {
            return loadImage(filename, true, true, 1);
        } else {
            return loadImage(filename, false, true, 4);
        }

    }

    /**
     * Gets an image from the images/ directory.
     */
    public Image loadImage(String filename, boolean isNewGraphics, boolean isTransparent, int scalingFactor) {
        URL aURL;
        try {
            String path = "";
            if (isNewGraphics) {
                path = "new-images/" + filename + ".png";
            } else {
                path = "images/" + filename + ".png";
            }
            aURL = ClassLoader.getSystemResources(path).nextElement();
        } catch (Exception e) {
            e.printStackTrace();
            return null; // TODO fix this
        }

        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(aURL);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int rgb = bufferedImage.getRGB(0, 0);

        Image image = bufferedImage.getScaledInstance(bufferedImage.getWidth() * scalingFactor, bufferedImage.getHeight() * scalingFactor, 2);

        if (isTransparent) {
            image = makeColorTransparent(toBufferedImage(image), new Color(rgb));
        }

        map.put(filename, image);

        return bufferedImage;
    }
}