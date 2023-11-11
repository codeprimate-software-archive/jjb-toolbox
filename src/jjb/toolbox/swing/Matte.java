/**
 * Copyright (c) 2002, Code Primate
 * All Rights Reserved
 *
 * Matte paints a canvas similar to Adobe Photoshop for
 * displaying graphics and images.  The class uses double
 * bufferring for quick screen refreshes.
 *
 * @author John J. Blum
 * File: Matte.java
 * @version v1.0
 * Date: 12 May 2002
 * Modification Date: 18 July 2002
 */

package jjb.toolbox.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;

public class Matte extends JComponent
{

  private static final Color TILE_COLOR = new Color(204,204,204);

  private static final Dimension TILE_DIM = new Dimension(5,5);

  private Dimension      oldCanvasDim;

  private BufferedImage  offscreenBuffer;

  /**
   * Creates an instance of the Matte class to draw a matte
   * canvas on which to paint or draw images.
   */
  public Matte()
  {
    oldCanvasDim = new Dimension(0,0);
  }

  /**
   * paintComponent method paints the ui of the matte canvas.
   *
   * @param g is a Ljava.awt.Graphics object used to paint
   * the matte canvas.
   */
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);

    Dimension canvasDim = getSize();

    if (!canvasDim.equals(oldCanvasDim))
    {
      offscreenBuffer = new BufferedImage(canvasDim.width,canvasDim.height,BufferedImage.TYPE_INT_ARGB);
      oldCanvasDim = canvasDim;

      Graphics2D g2 = offscreenBuffer.createGraphics();

      int remainder = 0;

      for (int y = 0; y < canvasDim.height; y += TILE_DIM.height)
      {
        remainder = y % 2;

        for (int x = 0; x < canvasDim.width; x += TILE_DIM.width)
        {
          g2.setPaint(x % 2 == remainder ? Color.white : TILE_COLOR);
          g2.fill(new Rectangle2D.Double(x,y,TILE_DIM.width,TILE_DIM.height));
        }
      }
    }

    g.drawImage(offscreenBuffer,0,0,this);
  }

}

