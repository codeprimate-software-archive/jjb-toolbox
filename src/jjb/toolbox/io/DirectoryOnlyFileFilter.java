/**
 * DirectoryOnlyFileFilter.java (c)2002.5.12
 *
 * Copyright (c) 2002, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2002.12.12
 * @see java.io.FileFilter
 * @since Java 2
 */

package jjb.toolbox.io;

import java.io.File;
import java.io.FileFilter;

public final class DirectoryOnlyFileFilter implements FileFilter
{

  private static final DirectoryOnlyFileFilter INSTANCE
    = new DirectoryOnlyFileFilter();
  private static final DirectoryOnlyFileFilter SHOW_HIDDEN_INSTANCE
    = new DirectoryOnlyFileFilter(true);

  private final boolean showHidden;

  /**
   * Creates an instance of the DirectoryOnlyFileFilter class
   * to return a file list that consist of directories only.
   * The  constructor is private to enforce the
   * non-instantiability property.  Default constructor
   * for class.
   */
  private DirectoryOnlyFileFilter()
  {
    showHidden = false;
  }

  /**
   * Creates an instance of the DirectoryOnlyFileFilter class
   * to return a file list that consist of directories only.
   * The  constructor is private to enforce the
   * non-instantiability property.
   *
   * @param showHidden is a boolean value indicating if
   * the DirectoryOnlyFileFilter class should exclude or include
   * hidden directories within the file system.
   */
  private DirectoryOnlyFileFilter(boolean showHidden)
  {
    this.showHidden = showHidden;
  }

  /**
   * accept returns true if the File object represented
   * by pathname is a directory (not a file) and is not
   * hidden, or is a directory and showHidden is true.
   *
   * @param pathname is a Ljava.io.File object used to
   * determine if the file system object denoted by
   * pathname is indeed a directory.
   * @return a boolean value indicating true if the File
   * object is a directory, false if it is a file or
   * some other file system object.
   */
  public boolean accept(File pathname)
  {
    return pathname.isDirectory() && (showHidden || !pathname.isHidden());
  }

  /**
   * getInstance returns a single instance of the
   * DirectoryOnlyFileFilter class making it accessible
   * to all system objects.
   *
   * @return a Ljava.io.FileFilter instance of the 
   * DirectoryOnlyFileFilter.
   */
  public static final FileFilter getInstance()
  {
    return INSTANCE;
  }

  /**
   * The overloaded getInstance returns an instance
   * of the FileOnlyFileFilter class based on whether
   * the caller wants to show hidden files or not.
   *
   * @param showHidden is a boolean value indicating
   * if the DirectoryOnlyFileFilter class should exclude
   * or include hidden directories within the file
   * system.
   */
  public static final FileFilter getInstance(boolean showHidden)
  {
    if (showHidden)
      return SHOW_HIDDEN_INSTANCE;
    else
      return INSTANCE;
  }

}

