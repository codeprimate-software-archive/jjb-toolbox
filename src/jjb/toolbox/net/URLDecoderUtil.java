/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: URLDecoderUtil.java
 * @version v1.0
 * Date: 20 September 2001
 * Modification Date: 15 April 2003
 * @see java.net.URLDecoder
 */

package jjb.toolbox.net;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;

public final class URLDecoderUtil
{

  private static final String UTF_8 = "UTF-8";

  /**
   * Default private constructor for class, enforcing the
   * non-instantiability property.
   */
  private URLDecoderUtil()
  {
  }

  /**
   * decode decodes a "x-www-form-urlencoded" to a String
   * value using the URLDecoder.decode() method.
   *
   * @param url is a Ljava.lang.String representing the
   * encoded-URL to decode.
   * @return a Ljava.lang.String decoded URL.
   * @throws Ljava.net.MalformedURLException
   */
  public static final String decode(String url)
      throws MalformedURLException, UnsupportedEncodingException
  {
    new URL(url);
    return URLDecoder.decode(url, UTF_8);
  }

  /**
   * main is the executable method of this class to invoke
   * the URLDecoderUtil object at the command line as a
   * utility to convert URLs from the x-www-form-urlencoded
   * format.
   *
   * @param args is a [Ljava.lang.String array containing
   * arguments to this program.
   */
  public static void main(String[] args) throws Exception
  {
    if (args == null || args.length < 1)
    {
      System.out.println("usage: java URLDecoderUtil \"<encoded-url>\" [{\"<encoded-url>\"}*]");
      return;
    }

    for (int index = 0; index < args.length; index++)
      System.out.println(decode(args[index]));
  }

}

