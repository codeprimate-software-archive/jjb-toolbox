/**
 * FileComparatorFactory.java (c)2002.4.17
 *
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * Performs case-insensitive searches on various file attributes such as the
 * file's last modified date, location, name, size and type.
 *
 * @author John J. Blum
 * @version 2002.12.13
 * @see java.util.Comparator
 */

package jjb.toolbox.io;

import java.io.File;
import java.util.Comparator;
import java.util.Date;

public class FileComparatorFactory
{

  private static final LastModifiedComparator  lastModifiedComparatorASC  = new LastModifiedComparator(true);
  private static final LastModifiedComparator  lastModifiedComparatorDESC = new LastModifiedComparator(false);

  private static final FileNameComparator  fileNameComparatorASC  = new FileNameComparator(true);
  private static final FileNameComparator  fileNameComparatorDESC = new FileNameComparator(false);

  private static final FilePathComparator  filePathComparatorASC  = new FilePathComparator(true);
  private static final FilePathComparator  filePathComparatorDESC = new FilePathComparator(false);

  private static final FileSizeComparator  fileSizeComparatorASC  = new FileSizeComparator(true);
  private static final FileSizeComparator  fileSizeComparatorDESC = new FileSizeComparator(false);

  private static final FileTypeComparator  fileTypeComparatorASC  = new FileTypeComparator(true);
  private static final FileTypeComparator  fileTypeComparatorDESC = new FileTypeComparator(false);

  /**
   * Creates an instance of the FilenameComparator class to
   * compare two File objects by name.
   */
  private static final class FileNameComparator implements Comparator
  {

    private final boolean ascendingOrder;

    public FileNameComparator(boolean ascendingOrder)
    {
      this.ascendingOrder = ascendingOrder;
    }

    /**
     * compare compares two File objects by name for ordering.
     */
    public int compare(Object o1,
                       Object o2 )
    {
      return ((File) (ascendingOrder ? o1 : o2)).getName().toLowerCase().compareTo(((File) (ascendingOrder ? o2 : o1)).getName().toLowerCase());
    }

    /**
     * equals determines if this Comparator object is the same
     * as the parameter to this method.
     */
    public boolean equals(Object obj)
    {
      return super.equals(obj);
    }
  }

  /**
   * Creates an instance of the FilePathComparator class to
   * compare two File objects by path.
   */
  private static final class FilePathComparator implements Comparator
  {

    private final boolean ascendingOrder;

    public FilePathComparator(boolean ascendingOrder)
    {
      this.ascendingOrder = ascendingOrder;
    }

    /**
     * compare compares two File objects by path (location)
     * for ordering.
     */
    public int compare(Object o1,
                       Object o2 )
    {
      return ((File) (ascendingOrder ? o1 : o2)).getAbsolutePath().toLowerCase().compareTo(((File) (ascendingOrder ? o2 : o1)).getAbsolutePath().toLowerCase());
    }

    /**
     * equals determines if this Comparator object is the
     * same as the parameter to this method.
     */
    public boolean equals(Object obj)
    {
      return super.equals(obj);
    }
  }

  /**
   * Creates an instance of the FileSizeComparator class to
   * compare two File objects by their size.  Yes, size is
   * everything.
   */
  private static final class FileSizeComparator implements Comparator
  {

    private final boolean ascendingOrder;

    public FileSizeComparator(boolean ascendingOrder)
    {
      this.ascendingOrder = ascendingOrder;
    }

    /**
     * compare compares two File objects by size for ordering.
     */
    public int compare(Object o1,
                       Object o2 )
    {
      return (int) (((File) (ascendingOrder ? o1 : o2)).length() -
                   ((File) (ascendingOrder ? o2 : o1)).length());
    }

    /**
     * equals determines if this Comparator object is the same
     * as the parameter to this method.
     */
    public boolean equals(Object obj)
    {
      return super.equals(obj);
    }
  }

  /**
   * Creates an instance of the FileSizeComparator class to
   * compare two File objects by their type.
   */
  private static final class FileTypeComparator implements Comparator
  {

    private final boolean ascendingOrder;

    public FileTypeComparator(boolean ascendingOrder)
    {
      this.ascendingOrder = ascendingOrder;
    }

    /**
     * compare compares two File objects by type for ordering.
     */
    public int compare(Object o1,
                       Object o2 )
    {
      String filename1 = ((File) o1).getName();
      String filename2 = ((File) o2).getName();

      int index1 = filename1.indexOf(".");
      int index2 = filename2.indexOf(".");
      int temp   = index1;

      if ((ascendingOrder ? index1 : index2) < 0)
        return -1;

      if ((ascendingOrder ? index2 : index1) < 0)
        return 1;

      if (!ascendingOrder)
      {
        index1 = index2;
        index2 = temp;
      }

      return (ascendingOrder ? filename1 : filename2).substring(index1+1).toLowerCase().compareTo((ascendingOrder ? filename2 : filename1).substring(index2+1).toLowerCase());
    }

    /**
     * equals determines if this Comparator object is the same
     * as the parameter to this method.
     */
    public boolean equals(Object obj)
    {
      return super.equals(obj);
    }
  }

  /**
   * Creates an instance of the LastModifiedComparator class
   * to compare two File objects by last modifcation date.
   */
  private static final class LastModifiedComparator implements Comparator
  { 

    private final boolean  ascendingOrder;

    private final Date     dte1,
                           dte2;

    public LastModifiedComparator(boolean ascendingOrder)
    {
      this.ascendingOrder = ascendingOrder;
      dte1 = new Date();
      dte2 = new Date();
    }

    /**
     * compare compares two File objects by last modification for
     * ordering.
     *
     * Note the commented out code.  I am not sure why this does
     * not work as it retrieves the number of miliseconds since
     * the epoch for each File object calling lastModified method
     * and performing a subtraction.  If it is a negative value,
     * then the first file object o1 is older (or create before)
     * the second file object o2.  If the result of the
     * subtraction is zero, the file times are equal, and if the
     * result of the subtraction is positive, the file object o2
     * is older (create before) file object o1.
     *
     * Create Date object is the only way I know how to
     * successfully do the the comparison.
     */
    public int compare(Object o1,
                       Object o2 )
    {
      /**return (int) (((File) (ascendingOrder ? o1 : o2)).lastModified() -
                    ((File) (ascendingOrder ? o2 : o1)).lastModified());**/

      dte1.setTime(((File) (ascendingOrder ? o1 : o2)).lastModified());
      dte2.setTime(((File) (ascendingOrder ? o2 : o1)).lastModified());

      return dte1.compareTo(dte2);
    }

    /**
     * equals determines if this Comparator object is the same as
     * the parameter to this method.
     */
    public boolean equals(Object obj)
    {
      return super.equals(obj);
    }
  }

  /**
   * getFileNameComparator factory method returns a Comparator
   * object used to compare File objects by their name.
   *
   * @param ascenindingOrder is a boolean value indicating
   * true if the File objects should be sorted in an ascending
   * order by their name.
   * @return a Ljava.util.Comparator object used to compare
   * File objects by their name.
   */
  public static Comparator getFileNameComparator(boolean ascendingOrder)
  {
    return (ascendingOrder ? fileNameComparatorASC : fileNameComparatorDESC);
  }

  /**
   * getFilePathComparator factory method returns a Comparator
   * object used to compare File objects by their path
   * (location).
   *
   * @param ascenindingOrder is a boolean value indicating
   * true if the File objects should be sorted in an ascending
   * order by their path (location).
   * @return a Ljava.util.Comparator object used to compare
   * File objects by their path (location).
   */
  public static Comparator getFilePathComparator(boolean ascendingOrder)
  {
    return (ascendingOrder ? filePathComparatorASC : filePathComparatorDESC);
  }

  /**
   * getFileSizeComparator factory method returns a Comparator
   * object used to compare File objects by their size.
   *
   * @param ascenindingOrder is a boolean value indicating
   * true if the File objects should be sorted in an ascending
   * order by their size.
   * @return a Ljava.util.Comparator object used to compare
   * File objects by their size.
   */
  public static Comparator getFileSizeComparator(boolean ascendingOrder)
  {
    return (ascendingOrder ? fileSizeComparatorASC : fileSizeComparatorDESC);
  }

  /**
   * getFileTypeComparator factory method returns a Comparator
   * object used to compare File objects by their type.
   *
   * @param ascenindingOrder is a boolean value indicating
   * true if the File objects should be sorted in an ascending
   * order by their type.
   * @return a Ljava.util.Comparator object used to compare
   * File objects by their type.
   */
  public static Comparator getFileTypeComparator(boolean ascendingOrder)
  {
    return (ascendingOrder ? fileTypeComparatorASC : fileTypeComparatorDESC);
  }

  /**
   * getLastModifiedComparator factory method returns a
   * Comparator object used to compare File objects by when
   * they were last modified.
   *
   * @param ascenindingOrder is a boolean value indicating
   * true if the File objects should be sorted in an ascending
   * order according to the last modified date.
   * @return a Ljava.util.Comparator object used to compare
   * File objects by their last modified date.
   */
  public static Comparator getLastModifiedComparator(boolean ascendingOrder)
  {
    return (ascendingOrder ? lastModifiedComparatorASC : lastModifiedComparatorDESC);
  }

}

