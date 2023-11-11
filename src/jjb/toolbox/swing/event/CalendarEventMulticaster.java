/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: CalendarEventMulticaster.java
 * @version v1.0
 * Date: 7 November 2001
 * Modification Date: 17 April 2002
 * @since Java 2
 */

package jjb.toolbox.swing.event;

import java.awt.AWTEventMulticaster;

public class CalendarEventMulticaster extends AWTEventMulticaster implements CalendarListener
{

  /**
   * Creates an instance of the CalendarEventMulticaster class to encompass
   * many CalendarListeners interested in events from the JCalendar control.
   *
   * @param a:Ljjb.toolbox.swing.event.CalendarListener object listening for
   * JCalendar control CalendarEvents.
   * @param b:Ljjb.toolbox.swing.event.CalendarListener object listening for
   * JCalendar control CalendarEvents.
   */
  protected CalendarEventMulticaster(CalendarListener a,
                                     CalendarListener b )
  {
    super(a,b);
  }

  /**
   * add adds the specified CalendarListener to the group of CalendarListeners
   * managed by this object.
   *
   * @param a:Ljjb.toolbox.swing.event.CalendarListener object, usually a 
   * CalendarEventMulticaster object, used to cast events to multiple CalendarListener
   * objects.
   * @param b:Ljjb.toolbox.swing.event.CalendarListener object being added to
   * the group of CalendarListeners managed by this CalendarEventMulticaster object.
   */
  public static CalendarListener add(CalendarListener a,
                                     CalendarListener b )
  {
    return (CalendarListener) addInternal(a,b);
  }

  /**
   * calendarModified is called to notify the CalendarListeners when the JCalendar
   * control's represented date has been modified.
   *
   * @param ca:Ljjb.toolbox.swing.event.CalendarEvent object used to encapsulate
   * information about the JCalendar control's date change.
   */
  public void calendarModified(CalendarEvent ca)
  {
    ((CalendarListener) a).calendarModified(ca);
    ((CalendarListener) b).calendarModified(ca);
  }

  /**
   * remove removes the specified CalendarListener from the group of CalendarListeners
   * managed by this object.
   *
   * @param a:Ljjb.toolbox.swing.event.CalendarListener object, usually a 
   * CalendarEventMulticaster object, used to cast events to multiple CalendarListener
   * objects.
   * @param b:Ljjb.toolbox.swing.event.CalendarListener object being removed from
   * the group of CalendarListeners managed by this CalendarEventMulticaster object.
   */
  public static CalendarListener remove(CalendarListener l,
                                        CalendarListener oldl)
  {
    return (CalendarListener) removeInternal(l,oldl);
  }

}

