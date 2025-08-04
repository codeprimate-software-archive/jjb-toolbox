/*
 * ImageUtil.java (c) 2001.11.7
 *
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.3.5
 */

package jjb.toolbox.awt;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Toolkit;
import java.awt.image.MemoryImageSource;

public class ImageUtil {

  protected static final Toolkit TOOLKIT = Toolkit.getDefaultToolkit();

  /**
   * Protected constructor used for subclassing the ImageUtil class.  Since
   * the ImageUtil class serves as a function class, an instance cannot
   * be created.
   */
  protected ImageUtil() {
  }

  /**
   * Generates two arrow images of the given size both pointing to the left.
   *
   * @param boundingBox java.awt.Dimension object specifying the bounding box
   * of the two left arrows.
   * @return a java.awt.Image object for the double left arrow.
   */
  public static Image getDoubleLeftArrowImage(Dimension boundingBox) {
    int width      = boundingBox.width;
    int height     = boundingBox.height;
    int halfWidth  = width / 2;
    int halfHeight = height / 2;

    Polygon p = new Polygon();

    p.addPoint(0,halfHeight);
    p.addPoint(halfWidth,0);
    p.addPoint(halfWidth,halfHeight);
    p.addPoint(width,0);
    p.addPoint(width,height);
    p.addPoint(halfWidth,halfHeight);
    p.addPoint(halfWidth,height);

    int[] pixels = PixelGenerator.getPixels(p.getBounds(),p);

    return TOOLKIT.createImage(new MemoryImageSource(width,height,pixels,0,width));
  }

  /**
   * Generates two arrow images of the given size both pointing to the right.
   *
   * @param boundingBox java.awt.Dimension object specifying the bounding box
   * of the two right arrows.
   * @return a java.awt.Image object for the double right arrow.
   */
  public static Image getDoubleRightArrowImage(Dimension boundingBox) {
    int width      = boundingBox.width;
    int height     = boundingBox.height;
    int halfWidth  = width / 2;
    int halfHeight = height / 2;

    Polygon p = new Polygon();

    p.addPoint(0,height);
    p.addPoint(0,0);
    p.addPoint(halfWidth,halfHeight);
    p.addPoint(halfWidth,0);
    p.addPoint(width,halfHeight);
    p.addPoint(halfWidth,height);
    p.addPoint(halfWidth,halfHeight);

    int[] pixels = PixelGenerator.getPixels(p.getBounds(),p);

    return TOOLKIT.createImage(new MemoryImageSource(width,height,pixels,0,width));
  }

  /**
   * Generates an arrow image of the given size pointing down.
   *
   * @param boundingBox java.awt.Dimension object specifying the bounding box
   * of the down arrow.
   * @return a java.awt.Image object for the down arrow.
   */
  public static Image getDownArrowImage(Dimension boundingBox) {
    int width      = boundingBox.width;
    int height     = boundingBox.height;
    int halfWidth  = width / 2;

    Polygon p = new Polygon();

    p.addPoint(0,0);
    p.addPoint(width,0);
    p.addPoint(halfWidth,height);

    int[] pixels = PixelGenerator.getPixels(p.getBounds(),p);

    return TOOLKIT.createImage(new MemoryImageSource(width,height,pixels,0,width));
  }

  /**
   * Generates an arrow image of the given size pointing left.
   *
   * @param boundingBox java.awt.Dimension object specifying the bounding box
   * of the left arrow.
   * @return a java.awt.Image object for the left arrow.
   */
  public static Image getLeftArrowImage(Dimension boundingBox) {
    int width      = boundingBox.width;
    int height     = boundingBox.height;
    int halfHeight = height / 2;

    Polygon p = new Polygon();

    p.addPoint(0,halfHeight);
    p.addPoint(width,0);
    p.addPoint(width,height);

    int[] pixels = PixelGenerator.getPixels(p.getBounds(),p);

    return TOOLKIT.createImage(new MemoryImageSource(width,height,pixels,0,width));
  }

  /**
   * Generates an arrow image of the given size pointing right.
   *
   * @param boundingBox java.awt.Dimension object specifying the bounding box
   * of the right arrow.
   * @return a java.awt.Image object for the right arrow.
   */
  public static Image getRightArrowImage(Dimension boundingBox) {
    int width      = boundingBox.width;
    int height     = boundingBox.height;
    int halfHeight = height / 2;

    Polygon p = new Polygon();

    p.addPoint(width,halfHeight);
    p.addPoint(0,0);
    p.addPoint(0,height);

    int[] pixels = PixelGenerator.getPixels(p.getBounds(),p);

    return TOOLKIT.createImage(new MemoryImageSource(width,height,pixels,0,width));
  }

  /**
   * Generates an arrow image of the given size pointing up.
   *
   * @param boundingBox: java.awt.Dimension object specifying the bounding box
   * of the up arrow.
   * @return a java.awt.Image object for the up arrow.
   */
  public static Image getUpArrowImage(Dimension boundingBox) {
    int width      = boundingBox.width;
    int height     = boundingBox.height;
    int halfWidth  = width / 2;

    Polygon p = new Polygon();

    p.addPoint(0,height);
    p.addPoint(halfWidth,0);
    p.addPoint(width,height);

    int[] pixels = PixelGenerator.getPixels(p.getBounds(),p);

    return TOOLKIT.createImage(new MemoryImageSource(width,height,pixels,0,width));
  }

}

