/**
 * Copyright (c) 2001, Sun Microsystems, Inc.
 * ALL RIGHTS RESERVED
 *
 * This class was taken from the Core Java 2 Volume II - Advanced
 * Features book, Prentice Hall, 2000
 * ISBN 0-13-081934-4
 * Chapter 7 - Advanced AWT
 *
 * BASE64 encoding encodes 3 bytes into 4 characters.
 * |11111122|22223333|33444444|
 * Each set of 6 bits is encoded according to the
 * toBase64 map. If the number of input bytes is not
 * a multiple of 3, then the last group of 4 characters
 * is padded with one or two = signs. Each output line
 * is at most 76 characters.
 *
 * @author Cay S. Horstmann & Gary Cornell
 * Modified By: John J. Blum
 * File: Base64OutputStream.java
 * @version v1.0
 * Date: 19 March 2002
 * Modification Date: 19 March 2002
 * @since Java 2
 */

package org.java.io;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Base64OutputStream extends FilterOutputStream
{

  private static final char[] toBase64 =
  {
    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
    'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
    'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
    'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
    'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
    'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
    'w', 'x', 'y', 'z', '0', '1', '2', '3',
    '4', '5', '6', '7', '8', '9', '+', '/'
  };

  private int col = 0;
  private int i   = 0;

  private int[] inbuf = new int[3];

  public Base64OutputStream(OutputStream out)
  {
    super(out);
  }

  public void write(int c) throws IOException
  {
    inbuf[i] = c;
    i++;

    if (i == 3)
    {
      super.write(toBase64[(inbuf[0] & 0xFC) >> 2]);
      super.write(toBase64[((inbuf[0] & 0x03) << 4) |
                           ((inbuf[1] & 0xF0) >> 4)]);
      super.write(toBase64[((inbuf[1] & 0x0F) << 2) |
                           ((inbuf[2] & 0xC0) >> 6)]);
      super.write(toBase64[inbuf[2] & 0x3F]);

      col += 4;
      i = 0;

      if (col >= 76)
      {
        super.write('\n');
        col = 0;
      }
    }
  }

  public void flush() throws IOException
  {
    if (i == 1)
    {
      super.write(toBase64[(inbuf[0] & 0xFC) >> 2]);
      super.write(toBase64[(inbuf[0] & 0x03) << 4]);
      super.write('=');
      super.write('=');
    }
    else if (i == 2)
    {
      super.write(toBase64[(inbuf[0] & 0xFC) >> 2]);
      super.write(toBase64[((inbuf[0] & 0x03) << 4) |
                           ((inbuf[1] & 0xF0) >> 4)]);
      super.write(toBase64[(inbuf[1] & 0x0F) << 2]);
      super.write('=');
    }

    i = 0;
  }

}

