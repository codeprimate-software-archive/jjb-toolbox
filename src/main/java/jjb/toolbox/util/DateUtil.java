/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: DateUtil.java
 * @version v1.0
 * Date: 21 October 2001
 * Modification Date: 3 May 2002
 * @since Java 1.0
 */

package jjb.toolbox.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil
{

  private static final DateFormat dateTime          = new SimpleDateFormat("MM/dd/yyyy h:mm a");
  private static final DateFormat dayMonthYear      = new SimpleDateFormat("dd MMM yyyy");
  private static final DateFormat dayOfWeekDate     = new SimpleDateFormat("EEE, MMM d, yyyy");
  private static final DateFormat dayOfWeekDateTime = new SimpleDateFormat("EEE, MM/dd/yyyy h:mm a");
  private static final DateFormat monthYear         = new SimpleDateFormat("MMMMM, yyyy");

  protected DateUtil()
  {
  }

  /**
   * getDateTime formats a Date object by the following format:
   * Month/Day/Year hour:minute (AM/PM).  (i.e. 05/27/1974 1:02 AM)
   *
   * @param date:Ljava.util.Date object used to format into a Date and
   * Time String.
   * @return a Ljava.lang.String representation of the Date object.
   */
  public static final String getDateTime(Date date)
  {
    return dateTime.format(date);
  }

  /**
   * getDayMonthYear formats a Date object by the following format:
   * Day MonthText Year.  (i.e. 2 May 2002)
   *
   * @param date:Ljava.util.Date object used to format into a Day,
   * Month and Year String.
   * @return a Ljava.lang.String representation of the Date object.
   */
  public static final String getDayMonthYear(Date date)
  {
    return dayMonthYear.format(date);
  }

  /**
   * getDayOfWeekDate formats a Date object by the following format:
   * Day of Week, MonthText Day, Year.  (i.e. Thursday, May 2, 2002)
   *
   * @param date:Ljava.util.Date object used to format into a Day of Week,
   * Month Day, Year String.
   * @return a Ljava.lang.String representation of the Date object.
   */
  public static final String getDayOfWeekDate(Date dte)
  {
    return dayOfWeekDate.format(dte);
  }

  /**
   * getDayOfWeekDateTime formats a Date object by the following format:
   * Day of Week, Month/Day/Year hour:minute (AM/PM).
   * (i.e. Wed, 05/27/1974 1:02 AM).
   *
   * @param date:Ljava.util.Date object used to format into a Day of Week,
   * Date and Time String.
   * @return a Ljava.lang.String representation of the Date object.
   */
  public static final String getDayOfWeekDateTime(Date date)
  {
    return dayOfWeekDateTime.format(date);
  }

  /**
   * getMonthYear formats a Date object by the following format:
   * Month, Year.
   *
   * @param date:Ljava.util.Date object used to format into a Month,
   * Year String.
   * @return a Ljava.lang.String representation of the Date object.
   */
  public static final String getMonthYear(Date date)
  {
    return monthYear.format(date);
  }

}

