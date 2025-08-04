/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: PixelGenerator.java
 * @version v1.0
 * Date: 8 November 2001
 * Modification Date: 17 April 2002
 * @since Java 1.0
 */

package jjb.toolbox.awt;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Shape;

public class PixelGenerator
{

  protected PixelGenerator()
  {
  }

  /**
   * getPixels creates a flattened pixel array (one-dimensional array of pixel data)
   * representing the Shape object in black and the bounding box as transparent.  This
   * overloaded version of getPixel calls the getPixel(Dimension, Shape) method.
   *
   * @param boundingBox:Ljava.awt.Rectangle object representing the bounding box around
   * the Shape object (constituting the Shape object's canvas).  Note that the bounding
   * box will be transparent.
   * @param geometricShape:Ljav.awt.Shape object for which pixel data will be generated
   * (represented by the black pixels).
   * @return a integer array containing the pixel data.
   */
  public static int[] getPixels(Rectangle boundingBox,
                                Shape     geometricShape)
  {
    return getPixels(new Dimension((int) boundingBox.getWidth(),(int) boundingBox.getHeight()),geometricShape);
  }

  /**
   * getPixels creates a flattened pixel array (one-dimensional array of pixel data)
   * representing the Shape object in black and the canvas as transparent.
   *
   * @param canvasSize:Ljava.awt.Dimension object representing the size of the canvas
   * for which the geometric shape will be drawn (note that areas of the canvas not
   * contained by the Shape obejct will be transparent).
   * @param geometricShape:Ljav.awt.Shape object for which pixel data will be generated
   * (represented by the black pixels).
   * @return a integer array containing the pixel data.
   */
  public static int[] getPixels(Dimension canvasSize,
                                Shape     geometricShape)
  {
    int width  = canvasSize.width;
    int height = canvasSize.height;

    int[] pixels = new int[width * height];

    for (int x = width; --x >= 0; )
    {
      for (int y = height; --y >= 0; )
      {
        if (geometricShape.contains(x,y))
          pixels[y * width + x] = 0xff000000;
        else
          pixels[y * width + x] = 0x00000000;
      }
    }

    return pixels;
  }

}

