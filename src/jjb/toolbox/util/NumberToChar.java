/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: NumberToChar.java
 * @version v1.0
 * Date; 20 December 2001
 * Modification Date: 17 April 2002
 * @since Java 1.0
 */

package jjb.toolbox.util;

public class NumberToChar
{

  public static void main(String[] args)
  {
    if (args.length == 0)
      System.exit(0);

    for (int index = 0, len = args.length; index < len; index++)
    {
      final String strNum = args[index];

      try
      {
        final int num = Integer.parseInt(strNum);

        if (Character.MIN_VALUE <= num && num <= Character.MAX_VALUE)
          System.out.println(strNum+" is character '"+((char) num)+"'");
      }
      catch (NumberFormatException nfe)
      {
        System.out.println(strNum+" is not a valid number format!");
      }
    }
  }

}

