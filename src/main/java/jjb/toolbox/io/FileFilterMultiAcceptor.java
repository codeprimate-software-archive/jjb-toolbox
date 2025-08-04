/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: FileFilterMultiAcceptor.java
 * @version beta2.0
 * Date: 25 October 2001
 * Modification Date: 17 April 2002
 * @since Java 1.0
 */

package jjb.toolbox.io;

import java.io.File;
import java.io.FileFilter;

public class FileFilterMultiAcceptor implements FileFilter
{

  protected final FileFilter  a,
                              b;

  /**
   * Creates a new instance of the FileFilterMultiAcceptor class to
   * chain FileFilter objects together for a composite File object
   * filter.
   *
   * @param a:Ljava.io.FileFilter is a File object filter.
   * @param b:Ljava.io.FileFilter is a File object filter.
   */
  public FileFilterMultiAcceptor(FileFilter a,
                                 FileFilter b )
  {
    this.a = a;
    this.b = b;
  }

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
    return a.accept(pathname) && b.accept(pathname);
  }

  /**
   * add adds the specified new FileFilter object to the chain of
   * filters
   *
   * @param filter:Ljava.io.FileFilter object used to link the new
   * FileFilter object.
   * @param newFilter:Ljava.io.FileFilter object representing the 
   * new file filter to chain to the current set of filters.
   * @return a Ljava.io.FileFilter object pointing to the begin of
   * the filter chain.
   */
  public static FileFilter add(FileFilter filter,
                               FileFilter newFilter)
  {
    if (filter == null)
      return newFilter;
    if (newFilter == null)
      return filter;

    return new FileFilterMultiAcceptor(filter,newFilter);
  }

  /**
   * remove removes the specified old FileFilter object from
   * the chain of file filters.
   *
   * @param oldFilter:Ljava.io.FileType object filter to remove
   * from the chain of file filters.
   * @return a Ljava.io.FileFilter object pointing to the new
   * filter chain.
   */
  private FileFilter remove(FileFilter oldFilter)
  {
    if (oldFilter == a)
      return b;
    if (oldFilter == b)
      return a;

    FileFilter a2 = removeInternal(a,oldFilter);
    FileFilter b2 = removeInternal(b,oldFilter);

    if (a2 == a && b2 == b)
      return this;  // The filter is not here.

    return add(a2,b2);
  }

  /**
   * remove removes the specified FileFilter object from the chain
   * of file filters refrenced by filter.
   *
   * @param filter:Ljava.io.FileFilter object referencing the beginning
   * of the file filter chain (last FileFilter object added to the chain).
   * @param oldFilter:Ljava.io.FileFilter object to remove from the
   * file filter chain.
   * @return a Ljava.io.FileFilter object pointing to the new
   * filter chain.
   */
  public static FileFilter remove(FileFilter filter,
                                  FileFilter oldFilter)
  {
    return removeInternal(filter,oldFilter);
  }

  /**
   * removeInternal removes the specified FileFilter object from the chain
   * of file filters refrenced by filter.
   *
   * @param filter:Ljava.io.FileFilter object referencing the beginning
   * of the file filter chain (last FileFilter object added to the chain).
   * @param oldFilter:Ljava.io.FileFilter object to remove from the
   * file filter chain.
   * @return a Ljava.io.FileFilter object pointing to the new
   * filter chain.
   */
  private static FileFilter removeInternal(FileFilter filter,
                                           FileFilter oldFilter)
  {
    if (filter == oldFilter || filter == null)
      return null;

    if (filter instanceof FileFilterMultiAcceptor)
      return ((FileFilterMultiAcceptor) filter).remove(oldFilter);

    return filter;  // The filter is not here.
  }

}

