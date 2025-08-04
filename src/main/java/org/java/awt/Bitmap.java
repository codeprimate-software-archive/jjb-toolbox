/**
 * Copyright (c) 2001, Sun Microsystems, Inc.
 * ALL RIGHTS RESERVED
 *
 * This class was taken from the Core Java 2 Volume II - Advanced
 * Features book, Prentice Hall, 2000
 * ISBN 0-13-081934-4
 * Chapter 7 - Advanced AWT
 *
 * @author Cay S. Horstmann & Gary Cornell
 * Modified By: John J. Blum
 * File: Bitmap.java
 * @version v1.0
 * Date: 19 March 2002
 * Modification Date: 19 March 2002
 * @since Java 2
 */

package org.java.awt;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.Serializable;

class Bitmap implements Serializable
{

  private int    type;
  private int    width;
  private int    height;

  private Object data;

  public Bitmap(BufferedImage image)
  {
    type = image.getType();
    width = image.getWidth();
    height = image.getHeight();

    final WritableRaster raster = image.getRaster();

    data = raster.getDataElements(0, 0, width, height, null);
  }

  public BufferedImage getImage()
  {
    final BufferedImage image = new BufferedImage(width, height, type);

    final WritableRaster raster = image.getRaster();

    raster.setDataElements(0, 0, width, height, data);

    return image;
  }

}

