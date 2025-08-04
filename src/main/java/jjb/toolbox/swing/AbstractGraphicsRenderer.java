/*
 * AbstractGraphicsRenderer.java (c) 2001.11.7
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
import java.awt.Insets;
import javax.swing.JComponent;

public abstract class AbstractGraphicsRenderer implements GraphicsRenderer {

  // The GraphicsRenderer object used to paint background
  // or layer beneath this GraphicsRenderer's layer.
  protected GraphicsRenderer layerRenderer;

  // Callback component
  protected JComponent component;

  AbstractGraphicsRenderer() {
  }

  /**
   * Constructor used by subclasses to create an instance of the
   * AbstractGraphicsRenderer class, initialized with the JComponent
   * object used during callbacks, to perform graphic drawing
   * operations.  Callbacks on the JComponent object are invoked to
   * acquire information about the component (such as size) in order
   * to carry out the drawing operation.
   */
  public AbstractGraphicsRenderer(JComponent component) {
    setCallback(component);
  }
  
  public AbstractGraphicsRenderer(JComponent component,
                                  GraphicsRenderer renderer) {
    setCallback(component);
    setLayerRenderer(renderer);
  }

  /**
   * Returns the JComponent object for which graphic drawing operations
   * are rendered and callbacks are invoked.
   *
   * @return the javax.swing.JComponent callback object for this
   * GraphicsRenderer objecct.
   */
  public JComponent getCallback() {
    return component;
  }

  /**
   * Returns the background renderer used by this GraphicsRenderer
   * to render the layer beneath the layer painted by this renderer.
   *
   * @returns the jjb.toolbox.swing.GraphicsRenderer object used to
   * paint the layer beneath (background) this renderer's layer.
   */
  public GraphicsRenderer getLayerRenderer() {
    return layerRenderer;
  }

  /**
   * getViewableArea returns the area of the JComponent object valid for
   * rendering the graphics operation.  Note that the Insets object for
   * the JComponent object account for the UI of the JComponent.
   *
   * @return java.awt.Dimension  specifying the viewable painting area.
   */
  public Dimension getViewableArea() {
    Dimension size = component.getSize();
    Insets insets = component.getInsets();
    int offsetWidth = 0;
    int offsetHeight = 0;

    if (insets != null) {
      offsetWidth += insets.left + insets.right;
      offsetHeight += insets.top + insets.bottom;
    }

    return new Dimension(size.width - offsetWidth, size.height - offsetHeight);
  }

  /**
   * Sets the JComponent callback value when rendering the graphics operations.
   *
   * @param  comp The new callback value
   */
  public final void setCallback(JComponent component) {
    if (component == null)
      throw new NullPointerException("The callback component cannot be null!");
    this.component = component;
  }

  /**
   * Sets the layer renderer beneath this renderer's layer (background) with
   * the specified parameter.
   *
   * @param renderer is the jjb.toolbox.swing.GraphicsRenderer object used
   * to paint the layer beneath this renderer's layer, in other words, the
   * background.
   */
  public final void setLayerRenderer(GraphicsRenderer renderer) {
    layerRenderer = renderer;
  }

}

