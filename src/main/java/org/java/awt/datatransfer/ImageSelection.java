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
 * File: ImageSelection.java
 * @version v1.0
 * Date: 19 March 2002
 * Modification Date: 19 March 2002
 * @since Java 2
 */

package org.java.awt.datatransfer;

import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

class ImageSelection implements Transferable
{

  public static final DataFlavor imageFlavor = new DataFlavor(java.awt.Image.class, "AWT Image");

  private static DataFlavor[] flavors = { imageFlavor };

  private Image theImage;

  public ImageSelection(Image image)
  {
    theImage = image;
  }

  public DataFlavor[] getTransferDataFlavors()
  {
    return flavors;
  }

  public boolean isDataFlavorSupported(DataFlavor flavor)
  {
    return flavor.equals(imageFlavor);
  }

  public synchronized Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException
  {
    if (flavor.equals(imageFlavor))
      return theImage;
    else
      throw new UnsupportedFlavorException(flavor);
  }

}

