/*
 * DefaultGraphicsRenderer.java (c) 2001.11.7
 *
 * This class represents the no graphics operation.  It serves to be
 * used when a GraphicsOPIF object is required (as is the case when
 * the GraphicsOPIF variable is used polymorphically and is not checked
 * for a null reference).
 *
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.3.5
 * @see jjb.toolbox.swing.GraphicsRenderer
 */

package jjb.toolbox.swing;

import java.awt.Graphics;
import javax.swing.JComponent;

public class DefaultGraphicsRenderer extends AbstractGraphicsRenderer {

  DefaultGraphicsRenderer() {
  }

  /**
   * Creates an instance of the DefaultGraphicsOP class with the specified
   * JComponent callback object and object used for rendering the graphics
   * operation.
   *
   * @param component javax.swing.JComponent object used for callbacks and
   * rendering of the graphics operation.
   */
  public DefaultGraphicsRenderer(JComponent component) {
    super(component);
  }

  public DefaultGraphicsRenderer(JComponent component,
                                 GraphicsRenderer renderer) {
    super(component,renderer);
  }

  /**
   * To be called by the JComponent's paint method to render the graphics
   * operation defined by this GraphicsRenderer object.
   *
   * @param  g java.awt.Graphics object used to rendered the graphics
   * operation.
   * @throws java.lang.IllegalStateException if the callback component has
   * not been set.
   */
  public void render(Graphics g) {
    if (getCallback() == null) {
      throw new IllegalStateException("The component for this graphics operation"
        + " has not been initialized!");
    }

    if (getLayerRenderer() != null)
      getLayerRenderer().render(g);
  }

}

