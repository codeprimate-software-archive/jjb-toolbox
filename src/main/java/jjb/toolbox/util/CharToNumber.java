/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: CharToNumber.java
 * @version v1.0
 * Date: 20 December 2001
 * Modification Date: 17 April 2002
 */

package jjb.toolbox.util;

public final class CharToNumber
{

  public static void main(String[] args)
  {
    if (args.length == 0)
      System.exit(0);

    for (int index = 0, len = args.length; index < len; index++)
    {
      final char c = args[index].charAt(0);

      System.out.println("Character "+c+" has int value = "+((int) c));
    }
  }

}

