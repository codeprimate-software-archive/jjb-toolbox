/**
 * Copyright (c) 2002, Code Primate
 * All Rights Reserved
 *
 * The ImageFileFilter class is used to test whether or not
 * the specified abstract pathname refers to an image file
 * that is supported by Java (namely, gif and jpg/jpeg) and
 * therefore should be included in a pathname list.
 *
 * @author John J. Blum
 * File: ImageFileFilter.java
 * @version v1.0
 * Date: 12 May 2002
 * Modification Date: 12 May 2002
 * @see java.io.FileFilter
 * @since Java 2
 */

package jjb.toolbox.io;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class ImageFileFilter implements FileFilter
{

  private static final String[] SUPPORTED_IMAGE_EXTENSIONS =
  {
    "gif",
    "jpg",
    "jpeg"
  };

  private static final List EXTENSIONS = Collections.unmodifiableList(Arrays.asList(SUPPORTED_IMAGE_EXTENSIONS));

  private static final ImageFileFilter INSTANCE = new ImageFileFilter();

  /**
   * Private constuctor used to enforce the Singleton
   * property.
   */
  private ImageFileFilter()
  {
  }

  /**
   * accept returns true for only those File objects that
   * refer to image files supported by the Java Platform
   * (namely, gif and jpg/jpeg).
   *
   * @param pathname is a Ljava.io.File object refering to
   * a filesystem file.
   * @return a boolean value indicating whether the
   * specified abstract pathname should be included in a
   * pathname list.
   */
  public boolean accept(File pathname)
  {
    return EXTENSIONS.contains(FileUtil.getFileExtension(pathname));
  }

  /**
   * getInstance returns the single instance of the
   * ImageFileFilter object making it accessible to
   * all system objects.
   *
   * @return a Ljava.io.FileFilter instance of the 
   * ImageFileFilter.
   */
  public static final FileFilter getInstance()
  {
    return INSTANCE;
  }

  /**
   * toString returns a String listing all the supported
   * image formats.
   *
   * @return a Ljava.lang.String reepresentation of the
   * supported image formats by this ImageFileFilter.
   */
  public String toString()
  {
    StringBuffer strBuffer = new StringBuffer();

    strBuffer.append("Supported Image Formats: ");

    for (Iterator i = EXTENSIONS.iterator(); i.hasNext(); )
    {
      strBuffer.append(i.next());

      if (i.hasNext())
        strBuffer.append(",");
    }

    return strBuffer.toString();
  }

}

