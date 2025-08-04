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
 * File: SerializableSelection.java
 * @version v1.0
 * Date: 19 March 2002
 * Modification Date: 19 March 2002
 * @since Java 2
 */

package org.java.awt.datatransfer;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.Serializable;

public class SerializableSelection implements Transferable
{

  public static final DataFlavor serializableFlavor = new DataFlavor(java.io.Serializable.class,"Serializable Object");

  private static DataFlavor[] flavors = { serializableFlavor };

  private Serializable theObject;

  public SerializableSelection(Serializable object)
  {
    theObject = object;
  }

  public boolean isDataFlavorSupported(DataFlavor flavor)
  {
    return flavor.equals(serializableFlavor);
  }

  public synchronized Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException
  {
    if (flavor.equals(serializableFlavor))
      return theObject;
    else
      throw new UnsupportedFlavorException(flavor);
  }

  public DataFlavor[] getTransferDataFlavors()
  {
    return flavors;
  }

}

