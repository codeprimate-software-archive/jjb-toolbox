/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: CalendarEvent.java
 * @version v1.0
 * Date: 7 November 2001
 * Modification Date: 15 July 2002
 * @since Java 2
 */

package jjb.toolbox.swing.event;

import java.awt.Event;
import java.util.Calendar;
import java.util.Date;

public class CalendarEvent extends Event
{

  private static int id = 0;

  /**
   * Creates an instance of the CalendarEvent class signifying that
   * a JCalendar event occurred.  This object encapsulates
   * inforamation about the event such as the Date represented by
   * the JCalendar control.
   *
   * @param source is a Ljava.lang.Object referring to the source
   * of this calendar event.
   */
  public CalendarEvent(Object source)
  {
    this(source,null);
  }

  /**
   * Creates an instance of the CalendarEvent class to encapsulate
   * information about a data change in the JCalendar control.
   *
   * @param source is a Ljava.lang.Object referring to the source
   * of this generated event.
   * #param arg is a Ljava.lang.Object representation of the data
   * represented by the JCalendar control.  Usually this object is
   * of type Calendar or Date.
   */
  public CalendarEvent(Object source,
                       Object arg    )
  {
    super(source,id++,arg);
  }

  /**
   * getDate returns the Date for the current JCalendar control.
   *
   * @return a Ljava.util.Date object representing the JCalendar
   * controls current date.
   */
  public Date getDate()
  {
    if (arg instanceof Calendar)
      return ((Calendar) arg).getTime();
    else if (arg instanceof Date)
      return (Date) ((Date) arg).clone();
    else
      return null;
  }

}

