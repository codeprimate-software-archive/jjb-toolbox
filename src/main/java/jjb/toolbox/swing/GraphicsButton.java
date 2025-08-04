/*
 * GraphicsButton.java (c) 2002.7.17
 *
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.3.5
 * @see javax.swing.JButton
 */

package jjb.toolbox.swing;

import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.JButton;

public class GraphicsButton extends JButton {

  private final GraphicsRenderer DEFAULT_RENDERER =
    new DefaultGraphicsRenderer(this);

  private static final Insets MARGIN = new Insets(0,0,0,0);

  private GraphicsRenderer gOp;

  /**
   * Creates an instance of the GraphicsButton class.
   */
  public GraphicsButton() {
    this(null);
  }

  /**
   * Creates an instace of the GraphicsButton class with the
   * specified GraphicsRenderer object used to render a defined
   * graphics operation.
   *
   * @param renderer is a jjb.toolbox.swing.GraphicsRenderer
   * object used to render the defined graphics operation on
   * this GraphicsButton component.
   */
  public GraphicsButton(GraphicsRenderer renderer) {
    setMargin(MARGIN);
    setGraphicsRenderer(renderer);
  }

  /**
   * Returns the graphics operation object for this GraphicsButton
   * component.
   *
   * @return a jjb.toolbox.swing.GraphicsRenderer object for
   * this component.
   */
  public GraphicsRenderer getGraphicsRenderer() {
    return gOp;
  }

  /**
   * overridden paintComponent method used to paint the UI
   * of the GraphicsButton component.
   *
   * @param g is a java.awt.Graphics object used to draw
   * the UI of the GraphicsButton.
   */
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    gOp.render(g);
  }

  /**
   * Sets the graphics renderer object for this GraphicsButton
   * component.
   *
   * @param graphicsOP is a jjb.toolbox.swing.GraphicsRenderer
   * object used to render graphics operations for this
   * component.
   */
  public final void setGraphicsRenderer(GraphicsRenderer renderer) {
    gOp = renderer;

    if (gOp == null)
      gOp = DEFAULT_RENDERER;
    else
      gOp.setCallback(this);
  }

}

