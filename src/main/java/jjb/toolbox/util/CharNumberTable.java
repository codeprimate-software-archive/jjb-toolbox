/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: CharNumberTable.java
 * @version v1.0.1
 * Date: 20 December 2001
 * Modification Date: 17 April 2002
 * @since Java 1.0
 */

package jjb.toolbox.util;

public class CharNumberTable
{

  public static void main(String[] args)
  {
    if (args.length == 0)
      System.exit(0);

    try
    {
      final int charMinValue = Math.abs(Integer.parseInt(args[0]));
      final int charMaxValue = Math.abs(Integer.parseInt(args[1]));

      System.out.println("Character\tNumber");

      for (int index = charMinValue; index < charMaxValue; index++)
        System.out.println("'"+((char) index)+"'\t\t"+index);
    }
    catch (NumberFormatException nfe)
    {
      System.out.println(args[0]+" is not a valid numeric value!");
    }
  }

}

