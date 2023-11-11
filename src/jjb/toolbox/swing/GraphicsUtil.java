/*
 * GraphicsUtil.java (c) 2002.7.17
 *
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.3.5
 */

package jjb.toolbox.swing;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import jjb.toolbox.awt.ImageUtil;

public class GraphicsUtil extends ImageUtil {

  /**
   * Protected constructor used to allow this class to be extended, but to
   * disallow any instances of being created.  This is a utility class that
   * has not internal state, thus creating an instance is a waste of memory
   * resources.
   */
  protected GraphicsUtil() {
  }

  /**
   * drawDoubleLeftArrow draws two arrow images pointing left.
   *
   * @return a jjb.toolbox.swing.GraphicsOP object used to render the
   * double left arrows.
   */
  public static GraphicsRenderer drawDoubleLeftArrow() {
    return new DefaultGraphicsRenderer() {
      public void render(Graphics g) {
        super.render(g);

        Graphics2D g2 = (Graphics2D) g;

        Dimension size = component.getSize();
        Dimension area = minimizeToSquareArea(getViewableArea());

        GeneralPath path = new GeneralPath();

        path.moveTo(0,area.height/2);
        path.lineTo(area.width/2,0);
        path.lineTo(area.width/2,area.height/2);
        path.lineTo(area.width,0);
        path.lineTo(area.width,area.height);
        path.lineTo(area.width/2,area.height/2);
        path.lineTo(area.width/2,area.height);
        path.closePath();
        path.transform(AffineTransform.getTranslateInstance(getXOffset(size,area),getYOffset(size,area)));

        g2.setPaint(component.getForeground());
        g2.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON));
        g2.fill(path);
      }
    };
  }

  /**
   * drawDoubleRightArrow draws two arrow images pointing right.
   *
   * @return a jjb.toolbox.swing.GraphicsOP object used to render the
   * double right arrows.
   */
  public static GraphicsRenderer drawDoubleRightArrow() {
    return new DefaultGraphicsRenderer() {
      public void render(Graphics g) {
        super.render(g);

        Graphics2D g2 = (Graphics2D) g;

        Dimension size = component.getSize();
        Dimension area = minimizeToSquareArea(getViewableArea());

        GeneralPath path = new GeneralPath();

        path.moveTo(0,area.height);
        path.lineTo(0,0);
        path.lineTo(area.width/2,area.height/2);
        path.lineTo(area.width/2,0);
        path.lineTo(area.width,area.height/2);
        path.lineTo(area.width/2,area.height);
        path.lineTo(area.width/2,area.height/2);
        path.closePath();
        path.transform(AffineTransform.getTranslateInstance(getXOffset(size,area),getYOffset(size,area)));

        g2.setPaint(component.getForeground());
        g2.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON));
        g2.fill(path);
      }
    };
  }

  /**
   * drawDownArrow draws an arrow image pointing down.
   *
   * @return a jjb.toolbox.swing.GraphicsOP object used to render the
   * down arrow.
   */
  public static GraphicsRenderer drawDownArrow() {
    return new DefaultGraphicsRenderer() {
      public void render(Graphics g) {
        super.render(g);

        Graphics2D g2 = (Graphics2D) g;

        Dimension size = component.getSize();
        Dimension area = minimizeToSquareArea(getViewableArea());

        GeneralPath path = new GeneralPath();

        path.moveTo(0.0f,0.0f);
        path.lineTo(area.width,0.0f);
        path.lineTo(area.width/2,area.height);
        path.closePath();
        path.transform(AffineTransform.getTranslateInstance(getXOffset(size,area),getYOffset(size,area)));

        g2.setPaint(component.getForeground());
        g2.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON));
        g2.fill(path);
      }
    };
  }

  /**
   * drawLeftArrow draws an arrow image pointing left.
   *
   * @return a jjb.toolbox.swing.GraphicsOP object used to render the
   * left arrow.
   */
  public static GraphicsRenderer drawLeftArrow() {
    return new DefaultGraphicsRenderer() {
      public void render(Graphics g) {
        super.render(g);

        Graphics2D g2 = (Graphics2D) g;

        Dimension size = component.getSize();
        Dimension area = minimizeToSquareArea(getViewableArea());

        GeneralPath path = new GeneralPath();

        path.moveTo(0.0f,area.width/2);
        path.lineTo(area.width,0.0f);
        path.lineTo(area.width,area.height);
        path.closePath();
        path.transform(AffineTransform.getTranslateInstance(getXOffset(size,area),getYOffset(size,area)));

        g2.setPaint(component.getForeground());
        g2.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON));
        g2.fill(path);
      }
    };
  }

  /**
   * drawLeftArrow draws an arrow image pointing right.
   *
   * @return a jjb.toolbox.swing.GraphicsOP object used to render the
   * right arrow.
   */
  public static GraphicsRenderer drawRightArrow() {
    return new DefaultGraphicsRenderer() {
      public void render(Graphics g) {
        super.render(g);

        Graphics2D g2 = (Graphics2D) g;

        Dimension size = component.getSize();
        Dimension area = minimizeToSquareArea(getViewableArea());

        GeneralPath path = new GeneralPath();

        path.moveTo(0.0f,0.0f);
        path.lineTo(area.width,area.height/2);
        path.lineTo(0.0f,area.height);
        path.closePath();
        path.transform(AffineTransform.getTranslateInstance(getXOffset(size,area),getYOffset(size,area)));

        g2.setPaint(component.getForeground());
        g2.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON));
        g2.fill(path);
      }
    };
  }

  /**
   * drawLeftArrow draws an arrow image pointing up.
   *
   * @return a jjb.toolbox.swing.GraphicsOP object used to render the
   * up arrow.
   */
  public static GraphicsRenderer drawUpArrow() {
    return new DefaultGraphicsRenderer() {
      public void render(Graphics g) {
        super.render(g);

        Graphics2D g2 = (Graphics2D) g;

        Dimension size = component.getSize();
        Dimension area = minimizeToSquareArea(getViewableArea());

        GeneralPath path = new GeneralPath();

        path.moveTo(0.0f,area.height);
        path.lineTo(area.width/2,0.0f);
        path.lineTo(area.width,area.height);
        path.closePath();
        path.transform(AffineTransform.getTranslateInstance(getXOffset(size,area),getYOffset(size,area)));

        g2.setPaint(component.getForeground());
        g2.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON));
        g2.fill(path);
      }
    };
  }

  /**
   * Returns the X difference of the dim parameter object as if it
   * were centered about the boudingDim parameter object.
   *
   * @param boundingDim java.awt.Dimension object used to define a region
   * around the other Dimension object.
   * @param dim java.awt.Dimension object centered about the boundingDim
   * object.
   * @return an integer specifying the X offset.
   */
  private static int getXOffset(Dimension boundingDim,
                                Dimension dim         ) {
    return (int) (((double) boundingDim.width - (double) dim.width) / 2.0);
  }

  /**
   * Returns the Y difference of the dim parameter object as if it
   * were centered about the boudingDim parameter object.
   *
   * @param boundingDim java.awt.Dimension object used to define a region
   * around the other Dimension object.
   * @param dim java.awt.Dimension object centered about the boundingDim
   * object.
   * @return an integer specifying the Y offset.
   */
  private static int getYOffset(Dimension boundingDim,
                                Dimension dim         ) {
    return (int) (((double) boundingDim.height - (double) dim.height) / 2.0);
  }

  /**
   * maximizeToSquareArea increases a Dimension object's width or height to the
   * greater of the two dimensions.
   *
   * @param dim: java.awt.Dimension object used for expansion.
   * @return a new Ljava.awt.Dimension object containing the new maximal
   * dimension for both width and height.
   */
  private static Dimension maximizeToSquareArea(Dimension dim) {
    int maxDim = Math.max(dim.width,dim.height);
    return new Dimension(maxDim,maxDim);
  }

  /**
   * minimizeToSquareArea reduces a Dimension object's width or height to the
   * smaller of the two dimensions.
   *
   * @param dim: java.awt.Dimension object used for reduction.
   * @return a new Ljava.awt.Dimension object containing the new minimal
   * dimension for both width and height.
   */
  private static Dimension minimizeToSquareArea(Dimension dim) {
    int minDim = Math.min(dim.width,dim.height);
    return new Dimension(minDim,minDim);
  }

}

