/*
 * GraphicsRenderer.java (c) 2001.11.7
 *
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.3.5
 * @see java.awt.Graphics
 */

package jjb.toolbox.swing;

import java.awt.Graphics;
import javax.swing.JComponent;

public interface GraphicsRenderer {

  /**
   * To be called by the JComponent object in it's paint method to render
   * the graphics operation defined by this GraphicsRenderer object.
   *
   * @param g java.awt.Graphics object used to draw/render the graphics
   * defined by this renderer.
   */
  public void render(Graphics g);

  /**
   * Sets the JComponent to be called back when rendering the graphics
   * operations.
   *
   * @param comp javax.swing.JComponent object used for callbacks and
   * graphics operations performed by this renderer.
   */
  public void setCallback(JComponent comp);

}

