/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: CalendarListener.java
 * @version v1.0
 * Date: 7 November 2001
 * Modification Date: 17 April 2002
 * @since Java 2
 */

package jjb.toolbox.swing.event;

import java.util.EventListener;

public interface CalendarListener extends EventListener
{

  /**
   * calendarModified is called to notify the CalendarListeners when the JCalendar
   * control's represented date has been modified.
   *
   * @param ca:Ljjb.toolbox.swing.event.CalendarEvent object used to encapsulate
   * information about the JCalendar control's date change.
   */
  public void calendarModified(CalendarEvent ca);

}

