/**
 * RenameFile.java (c) 2002.10.21
 *
 * The RenameFile class renames the specified
 * file on the OS filesystem to the specified
 * filename.
 *
 * Copyright (c) 2002, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.2.21
 */
 
package jjb.toolbox.util;

import java.io.File;

public class RenameFile {

  public static void rename(final File fromFile,
                            final String toName ) {
    String path = fromFile.getParent();

    //System.out.println("Path: "+path);

    if (path == null)
      path = "";
    else
      path = (path.endsWith(File.separator) ? path : path+File.separator);

    fromFile.renameTo(new File(path+toName));
  }

  public static void main(String[] args) {
    if (args.length != 2) {
      System.err.println("\n>java RenameFile <from file> <to file>");
      System.exit(1);
    }

    final File fromFile = new File(args[0]);

    rename(fromFile,args[1]);
  }

}

