/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: URLEncoderUtil.java
 * @version v1.0
 * Date: 20 September 2001
 * Modification Date: 15 April 2003
 * @see java.net.URLEncoder
 */

package jjb.toolbox.net;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public final class URLEncoderUtil
{
  
  private static final String UTF_8 = "UTF-8";

  /**
   * Default private constructor for class, enforcing the
   * non-instantiability property.
   */
  private URLEncoderUtil()
  {
  }

  /**
   * encode translates a URL String into a x-www-form-urlencoded
   * formatted String value using the URLEncoder.encode() method.
   *
   * @param url is a Ljava.lang.String representing the URL to
   * encode.
   * @return a Ljava.lang.String representing a
   * x-www-form-urlencoded URL.
   * @throws Ljava.net.MalformedURLException
   */
  public static final String encode(String url)
      throws MalformedURLException, UnsupportedEncodingException
  {
    new URL(url);
    return URLEncoder.encode(url, UTF_8);
  }

  /**
   * main is the executable method of this class used to invoke
   * the URLEncoderUtil object at the command line as a utility
   * to convert URLs to x-www-form-urlencoded format.
   *
   * @param args is a Ljava.lang.String array containing
   * arguments to this program.
   */
  public static void main(String[] args) throws Exception
  {
    if (args == null || args.length < 1)
    {
      System.out.println("usage: java URLEncoderUtil \"<url>\" [{\"<url>\"}*]");
      return;
    }

    for (int index = 0; index < args.length; index++)
      System.out.println(encode(args[index]));
  }

}

