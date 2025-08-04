/**
 * Copyright (c) 2002, O'Rielly & Associates
 * All Rights Reserved
 *
 * @author Elliotte Rusty Harold
 * File: LineCat.java
 * @version v1.0
 * Date: 8 July 2002
 * Modification Date: 8 July 2002
 * @since Java 2
 */

package org.java.util;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

public class LineCat
{

  public static void main(String[] args)
  {
    String thisLine = null;

    // Loop across the arguments
    for (int i = 0; i < args.length; i++)
    {
      // Open the file for reading.
      try
      {
        LineNumberReader br = new LineNumberReader(new FileReader(args[i]));

        while ((thisLine = br.readLine()) != null)
          System.out.println(br.getLineNumber()+": "+thisLine);
      }
      catch (IOException ioe)
      {
        ioe.printStackTrace();
      }
    }
  }

}

