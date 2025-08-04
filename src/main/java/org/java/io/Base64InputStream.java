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
 * File: Base64InputStream.java
 * @version v1.0
 * Date: 19 March 2002
 * Modification Date: 19 March 2002
 * @since Java 2
 */

package org.java.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Base64InputStream extends FilterInputStream
{
  
  private static final int[] fromBase64 =
  {
    -1, -1, -1, -1, -1, -1, -1, -1,
    -1, -1, -1, -1, -1, -1, -1, -1,
    -1, -1, -1, -1, -1, -1, -1, -1,
    -1, -1, -1, -1, -1, -1, -1, -1,
    -1, -1, -1, -1, -1, -1, -1, -1,
    -1, -1, -1, 62, -1, -1, -1, 63,
    52, 53, 54, 55, 56, 57, 58, 59,
    60, 61, -1, -1, -1, -1, -1, -1,
    -1,  0,  1,  2,  3,  4,  5,  6,
    7,  8,  9, 10, 11, 12, 13, 14,
    15, 16, 17, 18, 19, 20, 21, 22,
    23, 24, 25, -1, -1, -1, -1, -1,
    -1, 26, 27, 28, 29, 30, 31, 32,
    33, 34, 35, 36, 37, 38, 39, 40,
    41, 42, 43, 44, 45, 46, 47, 48,
    49, 50, 51, -1, -1, -1, -1, -1
  };

  int i = 0;

  int[] ch = new int[4];

  public Base64InputStream(InputStream in)
  {
    super(in);
  }

  public int read(byte[] b, int off, int len) throws IOException
  {
    if (len > b.length - off)
      len = b.length - off;

    for (int i = 0; i < len; i++)
    {
      int ch = read();

      if (ch == -1)
        return i;

      b[i + off] = (byte)ch;
    }

    return len;
  }

  public int read(byte[] b) throws IOException
  {
    return read(b, 0, b.length);
  }

  public int read() throws IOException
  {
    int r;

    if (i == 0)
    {  // skip whitespace
      do
      {
        ch[0] = super.read();
        if (ch[0] == -1) return -1;
      }
      while (Character.isWhitespace((char)ch[0]));

      ch[1] = super.read();

      if (ch[1] == -1)
        return -1;

      i++;

      r = (fromBase64[ch[0]] << 2) | (fromBase64[ch[1]] >> 4);
    }
    else if (i == 1)
    {
      ch[2] = super.read();

      if (ch[2] == '=' || ch[2] == -1)
        return -1;

      i++;
      r = ((fromBase64[ch[1]] & 0x0F) << 4) | (fromBase64[ch[2]] >> 2);
    }
    else
    {
      ch[3] = super.read();

      if (ch[3] == '=' || ch[3] == -1)
        return -1;

      i = 0;
      r = ((fromBase64[ch[2]] & 0x03) << 6) | fromBase64[ch[3]];
    }

    return r;
  }

}

