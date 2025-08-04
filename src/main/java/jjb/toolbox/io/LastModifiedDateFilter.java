/**
 * LastModifiedDateFilter.java (c) 17.4.2002
 *
 * The FileModificationDateFilter is a FileFilter type used for
 * filtering files based on time, whether a File was last modified
 * on a specified date, after a specified date, before a specified
 * date, or between a begin and end date.
 *
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.2.1
 * @see java.io.FileFilter
 * @since Java 2
 */

package jjb.toolbox.io;

import java.io.File;
import java.io.FileFilter;
import java.util.Date;
import jjb.toolbox.util.DateUtil;

public class LastModifiedDateFilter implements FileFilter {

  public static final RangeType AFTER   = new RangeType(0,"After");
  public static final RangeType BEFORE  = new RangeType(01,"Before");
  public static final RangeType BETWEEN = new RangeType(10,"Between");
  public static final RangeType ON      = new RangeType(11,"On");

  private Date date1;
  private Date date2;

  private RangeType  type;

  /**
   * Creates an instance of the LastModifiedDateFillter class to filter
   * files by their modification dates.  Files can be filtered after a certain
   * date, before a certain date, between a begin and end date, and on a
   * particular date.
   */
  public LastModifiedDateFilter() {
  }

  /**
   * Creates an instance of the LastModifiedDateFillter class to filter
   * files by their modification dates.  Files can be filtered after a certain
   * date, before a certain date, between a begin and end date, and on a
   * particular date.  This constructor defaults the filter type to AFTER
   * where a file's last modification must be after the "after" date parameter.
   *
   * @param after:Ljava.util.Date object specifying after this date.
   */
  public LastModifiedDateFilter(final Date after) {
    date1 = after;
    type = AFTER;
  }

  /**
   * Creates an instance of the LastModifiedDateFillter class to filter
   * files by their modification dates.  Files can be filtered after a certain
   * date, before a certain date, between a begin and end date, and on a
   * particular date.  This constructor defaults the filter type to BETWEEN
   * where a file's last modification must be after the "after" date parameter
   * and before the "before" date parameter.
   *
   * @param after:Ljava.util.Date object specifying after this date.
   * @param before:Ljava.util.Date object specifying before this date.
   */
  public LastModifiedDateFilter(final Date after,
                                final Date before) {
    this.date1 = after;
    this.date2 = before;
    type = BETWEEN;
  }

  /**
   * The RangeType class defines a typesafe enum enumerated type used by the 
   * LastModifiedDateFillter class to determine date range comparisons on
   * a file's modification date.
   */
  private static final class RangeType {

    private final int type;

    private final String description;

    public RangeType(int type,
                     String description) {
      this.type = type;
      this.description = description;
    }

    public boolean equals(Object obj) {
      if (obj == this)
        return true;
      if (!(obj instanceof RangeType))
        return false;

      final RangeType rt = (RangeType) obj;

      return rt.type == type;
    }

    public int hashCode(Object oj) {
      int result = 17;
      result = 37 * result + type;
      result = 37 * result + (description == null ? 0 
        : description.hashCode());
      return result;
    }

    public String toString() {
      final StringBuffer buffer = new StringBuffer("RangeType: ");
      buffer.append("{type=").append(type);
      buffer.append(", description=").append("description");
      buffer.append("}");
      return buffer.toString();
    }

  }  // End of Class

  /**
   * Tests whether or not the specified abstract pathname should be
   * included in a pathname list.
   *
   * @param pathname The abstract pathname to be tested.
   * @return <code>true</code> if and only if <code>pathname</code>
   * should be included.
   */
  public boolean accept(File pathname) {
    final Date fileDate = new Date(pathname.lastModified());

    if (type == AFTER)
      return fileDate.compareTo(date1) >= 0;
    else if (type == BEFORE)
      return fileDate.compareTo(date1) <= 0;
    else if (type == BETWEEN)
      return(fileDate.compareTo(date1) >= 0) && (fileDate.compareTo(date2) <= 0);
    else
      return fileDate.equals(date1);
  }

  /**
   * setAfterDate sets the date/time stamp that the File object's last
   * modification date must be on or after.
   *
   * @param dte java.util.Date object specifying the after time value.
   */
  public void setAfterDate(final Date dte) {
    date1 = new Date(dte.getTime());
    type = AFTER;
  }

  /**
   * setAfterDate sets the date/time stamp that the File object's last
   * modification date must be on or before.
   *
   * @param dte java.util.Date object specifying the before time value.
   */
  public void setBeforeDate(final Date dte) {
    date1 = new Date(dte.getTime());
    type = BEFORE;
  }

  /**
   * setAfterDate sets the begin and end date/time stamps that the File
   * object's last modification date must be between.
   *
   * @param beginDate java.util.Date object specifying the begin time
   * value.
   * @param endDate java.util.Date object specifying the end time value.
   */
  public void setBetweenDates(final Date beginDate,
                              final Date endDate   ) {
    if (beginDate.compareTo(endDate) > 0) {
      throw new IllegalArgumentException("The begin date ("
        +beginDate.toString()+") must come on or before the end date ("
        +endDate.toString()+").");
    }

    date1 = new Date(beginDate.getTime());
    date2 = new Date(endDate.getTime());
    type = BETWEEN;
  }

  /**
   * setAfterDate sets the date/time stamp that the File object's last
   * modification date must be on.
   *
   * @param dte java.util.Date object specifying the on time value.
   */
  public void setOnDate(final Date dte) {
    date1 = new Date(dte.getTime());
    type = ON;
  }

  /**
   * toString returns a String description of the filter's current state.
   *
   * @return a Ljava.lang.String object description of the filter's
   * current state.
   */
  public String toString() {
    final StringBuffer buffer = new StringBuffer("LastModifiedDateFilter: ");

    if (type == AFTER)
      buffer.append("After ").append(DateUtil.getDateTime(date1));
    else if (type == BEFORE)
      buffer.append("Before ").append(DateUtil.getDateTime(date1));
    else if (type == BETWEEN) {
      buffer.append("Between ").append(DateUtil.getDateTime(date1));
      buffer.append(" and ").append(DateUtil.getDateTime(date2));
    }
    else
      buffer.append("On ").append(DateUtil.getDateTime(date1));

    return buffer.toString();
  }

}

