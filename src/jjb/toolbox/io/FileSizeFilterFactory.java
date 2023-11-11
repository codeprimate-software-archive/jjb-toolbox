/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: FileSizeFilterFactory.java
 * @version v1.0.1
 * Date: 24 October 2001
 * Modification Date: 17 April 2002
 */

package jjb.toolbox.io;

import java.io.File;
import java.io.FileFilter;

public class FileSizeFilterFactory
{

  /**
   * This constructor allows the FileSizeFilterFactory to be extended
   * to include new methods for building FileFilter objects for specific
   * tasks.
   */
  protected FileSizeFilterFactory()
  {
  }

  /**
   * getIsFileSizeFilter returns a FileFilter object used to include File
   * objects equal to the specified size;
   *
   * @param fileSize is a long value indicating the only size the File
   * object can be to be accepted by this filter.
   * @return a Ljava.io.FileFilter object excluding File objects that are
   * not equal to the file size specified.
   */
  public static final FileFilter getIsFileSizeFilter(final long fileSize)
  {
    return new FileFilter()
    {
      /**
       * Tests whether or not the specified abstract pathname should be
       * included in a pathname list.
       *
       * @param  pathname  The abstract pathname to be tested
       * @return  <code>true</code> if and only if <code>pathname</code>
       *          should be included
       */
      public boolean accept(File pathname)
      {
        return pathname.length() == fileSize;
      }

      /**
       * toString returns a String description of the filter's current state.
       *
       * @return a Ljava.lang.String object description of the filter's current
       * state.
       */
      public String toString()
      {
        return "Equal to "+fileSize+" bytes";
      }
    };
  }

  /**
   * getMaxFileSizeFilter returns a FileFilter object used to include File
   * objects smaller than the specified size;
   *
   * @param maxFileSize is a long value indicating the maximum size the File
   * object can be to be accepted by this filter.
   * @return a Ljava.io.FileFilter object excluding File objects larger than
   * the specified maximum file size.
   */
  public static final FileFilter getMaxFileSizeFilter(final long maxFileSize)
  {
    return new FileFilter()
    {
      /**
       * Tests whether or not the specified abstract pathname should be
       * included in a pathname list.
       *
       * @param  pathname  The abstract pathname to be tested
       * @return  <code>true</code> if and only if <code>pathname</code>
       *          should be included
       */
      public boolean accept(File pathname)
      {
        return pathname.length() <= maxFileSize;
      }

      /**
       * toString returns a String description of the filter's current state.
       *
       * @return a Ljava.lang.String object description of the filter's current
       * state.
       */
      public String toString()
      {
        return "Less Than Equal to "+maxFileSize+" bytes";
      }
    };
  }

  /**
   * getMinFileSizeFilter returns a FileFilter object used to include File
   * objects larger than the specified size;
   *
   * @param minFileSize is a long value indicating the minimum size the File
   * object must be to be accepted by this filter.
   * @return a Ljava.io.FileFilter object excluding File objects smaller than
   * the specified minimum file size.
   */
  public static final FileFilter getMinFileSizeFilter(final long minFileSize)
  {
    return new FileFilter()
    {
      /**
       * Tests whether or not the specified abstract pathname should be
       * included in a pathname list.
       *
       * @param  pathname  The abstract pathname to be tested
       * @return  <code>true</code> if and only if <code>pathname</code>
       *          should be included
       */
      public boolean accept(File pathname)
      {
        return pathname.length() >= minFileSize;
      }

      /**
       * toString returns a String description of the filter's current state.
       *
       * @return a Ljava.lang.String object description of the filter's current
       * state.
       */
      public String toString()
      {
        return "Greater Than Equal to "+minFileSize+" bytes";
      }
    };
  }

}

