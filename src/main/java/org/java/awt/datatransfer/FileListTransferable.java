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
 * File: FileListTransferable.java
 * @version v1.0
 * Date: 19 March 2002
 * Modification Date: 19 March 2002
 * @since Java 2
 */

package org.java.awt.datatransfer;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileListTransferable implements Transferable
{

  private static DataFlavor[] flavors = {  DataFlavor.javaFileListFlavor,DataFlavor.stringFlavor };

  private List fileList;

  public FileListTransferable(Object[] files)
  {
    fileList = new ArrayList(Arrays.asList(files));
  }

  public DataFlavor[] getTransferDataFlavors()
  {
    return flavors;
  }

  public boolean isDataFlavorSupported(DataFlavor flavor)
  {
    return Arrays.asList(flavors).contains(flavor);
  }

  public synchronized Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException
  {
    if (flavor.equals(DataFlavor.javaFileListFlavor))
      return fileList;
    else if (flavor.equals(DataFlavor.stringFlavor))
      return fileList.toString();
    else
      throw new UnsupportedFlavorException(flavor);
  }

}

