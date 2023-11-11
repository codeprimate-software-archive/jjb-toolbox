/**
 * FileUtil.java (c)2002.5.12
 *
 * Copyright (c) 2002, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version v2002.11.24
 * @since Java 2
 */

package jjb.toolbox.io;

import java.io.File;

public final class FileUtil
{

  /**
   * Private contructor used to enforce non-instantiability.
   * The FileUtil class is merely an utility class.
   */
  private FileUtil()
  {
  }

  /**
   * Returns a file's extension, such as "txt", "jpg", etc,
   * given the String representation of the file's name.
   *
   * @param filename is the name of the file for which the
   * extension will be determined and returned.
   * @return the extension of the file denoted by the
   * filename parameter, or an empty String if the file
   * has no extension.
   * @throws java.lang.NullPointerException if the filename
   * parameter is null.
   */
  public static final String getFileExtension(String filename)
  {
    if (filename == null)
      throw new NullPointerException("The filename parameter cannot be null.");

    int index = -1;

    if ((index = filename.indexOf(".")) == -1)
      return "";

    try
    {
      return filename.substring(index+1).trim();
    }
    catch (IndexOutOfBoundsException ignore)
    {
      return "";
    }
  }

  /**
   * Returns the file's extension, such as "txt", "jpg", etc.
   *
   * @param file is a Ljava.io.File object used to determine
   * the file extension of the file.
   * @return a Ljava.lang.String representation of the file's
   * extension or an empty String if the file has no extension.
   * @throws Ljava.lang.NullPointerException if the File
   * object parameter is null.
   * @see getFileExtension(:String)
   */
  public static final String getFileExtension(File file)
  {
    if (file == null)
      throw new NullPointerException("The file parameter cannot be null.");
    return getFileExtension(file.getName());
  }

  /**
   * Returns the path of the file's absolute/relative path and name
   * denoted by the String representation.
   *
   * @param pathname is the String representation of the file's
   * absolute/relative path and name.
   * @return a String representation of only the path pulled out.
   * @throws java.lang.NullPointerException if the pathname parameter
   * is null.
   */
  public static final String getFileLocation(String pathname)
  {
    if (pathname == null)
      throw new NullPointerException("The pathname parameter cannot be null.");

    int index = pathname.lastIndexOf(File.separator);

    return (index > -1 ? pathname.substring(0,index)+File.separator : "");
  }
  /**
   * Returns a file's location in the file system of the OS.
   *
   * @param file is a Ljava.io.File object used to determine
   * the file location of the file.
   * @return a Ljava.lang.String representation of the file's
   * location or an empty String if the file has no location.
   * @throws Ljava.lang.NullPointerException if the File
   * object parameter is null.
   * @see getFileLocation(:String)
   */
  public static final String getFileLocation(File file)
  {
    if (file == null)
      throw new NullPointerException("The file parameter cannot be null.");
    return getFileLocation(file.getAbsolutePath());
  }

}

