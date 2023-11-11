/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: FileTypeFilter.java
 * @version v1.0
 * Date: 23 October 2001
 * Modification Date: 2 June 2002
 */

package jjb.toolbox.io;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class FileTypeFilter implements FileFilter
{

  private boolean  inclusive;

  private Set      fileTypes = new TreeSet();

  /**
   * Creates an instance of the FileTypeFilter class to filter File objects
   * based on type, which is determined by their extension.  By default, any
   * File object with the specified file types, stored in the fileTypes Set
   * object, are accepted.
   */
  public FileTypeFilter()
  {
    this(true);
  }

  /**
   * Creates an instance of the FileTypeFilter class to filter File objects
   * based on type, which is determined by their extension.  File objects
   * will be included if the inclusive parameter is true and the File objects
   * type is a member of the fileTypes Set object, excluded otherwise.
   *
   * @param inclusive is a boolean value indicating true if File objects
   * having a file type contained by this filter are accepted or rejected.
   */
  public FileTypeFilter(boolean inclusive)
  {
    this.inclusive = inclusive;
  }

  /**
   * accept determines whether the specified File object should be
   * included or excluded in the list of files returned by file.listFiles
   * method.
   *
   * @param file:Ljava.io.File object tested for acceptance by this filter.
   * @return a boolean value of true if the filter accepts the File object,
   * false otherwise.
   */
  public boolean accept(File file)
  {
    if (fileTypes.size() == 0)
      return true;

    String filename = file.getName();

    int index = filename.indexOf(".");

    if (index < 0)
      return true; // No type, accept file by default.

    String fileType = filename.substring(index+1);

    boolean containsFile = fileTypes.contains(fileType.trim().toUpperCase());

    return (inclusive && containsFile) || (!inclusive && !containsFile);
  }

  /**
   * addFileType adds the specified file type to the Set of file types if
   * the file type is not already specified.
   *
   * @param fileType:Ljava.lang.String representation of the file extension.
   * @return a boolean value indicating whether the file type was successfully
   * added to this filter.
   */
  public boolean addFileType(String fileType)
  {
    if (fileType == null || (fileType = fileType.trim()).equals(""))
      return false;

    return fileTypes.add(fileType = fileType.toUpperCase());
  }

  /**
   * asList returns the file types of this filter in a List object.
   *
   * @return a Ljava.util.List object containing the file types of this
   * filter.
   */
  public List asList()
  {
    return new ArrayList(fileTypes);
  }

  /**
   * isInclusive returns a boolean value of true if the file types
   * specified by this filter are types of File objects accepted by this
   * filter.
   *
   * @return a boolean value indicating whether File object types contained
   * by this filter are inclusive (accepted) or exclusive (rejected).
   */
  public boolean isInclusive()
  {
    return inclusive;
  }

  /**
   * removeFileType removes the specified file type from the Set of
   * file types.
   *
   * @param fileType:Ljava.lang.String representing the file type to remove
   * from this filter.
   */
  public void removeFileType(String fileType)
  {
    fileTypes.remove(fileType.trim().toUpperCase());
  }

  /**
   * setInclusive sets whether File objects having one of the file types
   * in the Set of file types for this filter are accepted.
   *
   * @param inclusive is a boolean value indicating whether the file types
   * of this filter are to be inclusive or exclusive.
   */
  public void setInclusive(boolean inclusive)
  {
    this.inclusive = inclusive;
  }

  /**
   * size returns the number of file types in the Set contained by this
   * filter.
   *
   * @return an integer value indicating the number of file types contained
   * by this filter.
   */
  public int size()
  {
    return fileTypes.size();
  }

  /**
   * toArray constructs an Object array containing all the file types
   * in this filter.
   *
   * @return a [Ljava.lang.Object array containing the file types of this
   * filter.
   */
  public Object[] toArray()
  {
    return fileTypes.toArray();
  }

  /**
   * toArray constructs an Object array, of the specified type, containing
   * all the file types in this filter.
   *
   * @param a:[Ljava.lang.Object array to hold the file types of this filter.
   * @return a [Ljava.lang.Object array of the specified type with all the
   * file types in this filter.
   */
  public Object[] toArray(Object[] a)
  {
    return fileTypes.toArray(a);
  }

  /**
   * toString return a comma-delimited list of file types in alpabetic
   * order and whether these file types are inclusive or exclusive for
   * this filter.
   *
   * @return a Ljava.lang.String representation of this filter.
   */
  public String toString()
  {
    StringBuffer str = new StringBuffer();

    str.append(inclusive ? "Include: " : "Exclude: ");

    Iterator iterator = fileTypes.iterator();

    for (int index = 0; iterator.hasNext(); index++)
    {
      if (index > 0)
        str.append(", ");

      str.append(iterator.next());
    }

    return str.toString();
  }

}

