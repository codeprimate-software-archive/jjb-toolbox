/**
 * Copyright (c) 2002, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: FileOnlyFileFilter.java
 * @version v1.1
 * Date: 3 June 2002
 * Modification Date: 10 July 2002
 * @since Java 2
 * @see java.io.FileFilter
 */

package jjb.toolbox.io;

import java.io.File;
import java.io.FileFilter;

public final class FileOnlyFileFilter implements FileFilter
{

  private static final FileOnlyFileFilter INSTANCE             = new FileOnlyFileFilter();
  private static final FileOnlyFileFilter SHOW_HIDDEN_INSTANCE = new FileOnlyFileFilter(true);

  private final boolean showHidden;

  /**
   * Creates an instance of the FileOnlyFileFilter class
   * to return a file list that consist of files only.
   * The  constructor is private to enforce the
   * non-instantiability property.  Default constructor
   * for class.
   */
  private FileOnlyFileFilter()
  {
    showHidden = false;
  }

  /**
   * Creates an instance of the FileOnlyFileFilter class
   * to return a file list that consist of files only.
   * The  constructor is private to enforce the
   * non-instantiability property.
   *
   * @param showHidden is a boolean value indicating if
   * the FileOnlyFileFitler class should exclude or include
   * hidden files within the file system.
   */
  private FileOnlyFileFilter(boolean showHidden)
  {
    this.showHidden = showHidden;
  }

  /**
   * accept returns true if the File object represented
   * by pathname is a file (not a directory) and is not
   * hidden, or is a file and showHidden is true.
   * 
   * @param pathname is a Ljava.io.File object used to
   * determine if the file system object denoted by
   * pathname is indeed file.
   * @return a boolean value indicating true if the File
   * object is a file, false if it is a directory or
   * some other file system object.
   */
  public boolean accept(File pathname)
  {
    return pathname.isFile() && (showHidden || !pathname.isHidden());
  }

  /**
   * getInstance returns the single instance of the
   * FileOnlyFileFilter class making it accessible
   * to all system objects.
   *
   * @return a Ljava.io.FileFilter instance of the 
   * FileOnlyFileFilter.
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
   * if the FileOnlyFileFitler class should exclude
   * or include hidden files within the file system.
   */
  public static final FileFilter getInstance(boolean showHidden)
  {
    if (showHidden)
      return SHOW_HIDDEN_INSTANCE;
    else
      return INSTANCE;
  }

}

