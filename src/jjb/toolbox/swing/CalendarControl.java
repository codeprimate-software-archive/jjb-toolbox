/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: CalendarControl.java
 * @version v1.0.1
 * Date: 19 November 2001
 * Modification Date: 18 July 2002
 * @since Java 2
 */

package jjb.toolbox.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import jjb.toolbox.awt.ImageUtil;
import jjb.toolbox.swing.EButton;
import jjb.toolbox.swing.JCalendar;
import jjb.toolbox.swing.JDateField;
import jjb.toolbox.swing.event.CalendarEvent;
import jjb.toolbox.swing.event.CalendarEventMulticaster;
import jjb.toolbox.swing.event.CalendarListener;

public class CalendarControl extends JPanel
{

  private CalendarListener  calListener;

  private JButton           showCalendar;

  private JCalendar         calendar;

  private JDateField        dateField;

  private JPopupMenu        popupMenu;

  /**
   * Creates a new instance of the CalendarControl class component.
   */
  public CalendarControl()
  {
    calListener = null;
    buildUI();
  }

  /**
   * The CalendarAdapter class serves to listen for CalendarEvents
   * from the JCalendar & JDateField components of this
   * CalendarControl, thus synchronizing each component with the
   * other.
   */
  private final class CalendarAdapter implements CalendarListener
  {
    /**
     * calendarModified is called to notify the CalendarListeners
     * when the JCalendar control's represented date has been
     * modified.
     *
     * @param ca is a Ljjb.toolbox.swing.event.CalendarEvent object
     * used to encapsulate information about the JCalendar control's
     * date change.
     */
    public void calendarModified(CalendarEvent ca)
    {
      if (ca.target instanceof JCalendar)
        dateField.setDate(ca.getDate());
      else
        calendar.setDate(ca.getDate());

      fireCalendarEvent(ca);
    }
  }

  /**
   * addCalendarListener adds the specified CalendarListener to
   * the collection of CalendarListener objects for this
   * CalendarControl component to listen for CalendarEvents.
   *
   * @param cl:Ljjb.toolbox.swing.event.CalendarListener object
   * to add to the collection of CalendarListeners for this
   * CalendarControl component.
   */
  public void addCalendarListener(CalendarListener cl)
  {
    calListener = CalendarEventMulticaster.add(calListener,cl);
  }

  /**
   * buildUI constructs and lays out the components that
   * constitute the user interface to this calendar control.
   */
  private void buildUI()
  {
    showCalendar = new EButton(new ImageIcon(ImageUtil.getDownArrowImage(new Dimension(10,10))))
    {
      private final Dimension size = new Dimension(21,21);

      public Dimension getMaximumSize()   { return size; }
      public Dimension getMinimumSize()   { return size; }
      public Dimension getPreferredSize() { return size; }
    };

    calendar = new JCalendar();
    dateField = new JDateField();
    popupMenu = new JPopupMenu();
    popupMenu.setBorder(BorderFactory.createLineBorder(new Color(10,36,106),1));
    popupMenu.setLightWeightPopupEnabled(true);
    popupMenu.add(calendar);
    popupMenu.setSize(calendar.getSize());

    addAncestorListener(new AncestorListener()
    {
      public void ancestorAdded(AncestorEvent event)
      {
        popupMenu.setVisible(false);
      }
      public void ancestorRemoved(AncestorEvent event)
      {
        popupMenu.setVisible(false);
      }
      public void ancestorMoved(AncestorEvent event)
      {
        popupMenu.setVisible(false);
      }
    });

    showCalendar.addActionListener(new ActionListener()
    {
      private final Dimension calDim = calendar.getSize();

      public void actionPerformed(ActionEvent e)
      {
        if (!popupMenu.isVisible())
        {
          final Dimension dim = showCalendar.getSize();

          final Point pt = showCalendar.getLocationOnScreen();

          int locX = pt.x - (calDim.width - dim.width);
          int locY = pt.y + dim.height;

          popupMenu.setLocation(locX,locY);
          popupMenu.setVisible(true);
          calendar.requestFocus();
        }
        else
          popupMenu.setVisible(false);
      }
    });

    final CalendarListener calAdapter = new CalendarAdapter();

    calendar.addCalendarListener(calAdapter);
    dateField.addCalendarListener(calAdapter);

    dateField.addFocusListener(new FocusAdapter()
    {
      public void focusGained(FocusEvent fe)
      {
        popupMenu.setVisible(false);
      }
    });

    setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
    add(dateField);
    add(showCalendar);
  }

  /**
   * getDate returns a Date object encapsulating the date and
   * time of the calendar component.
   *
   * @return a Ljava.util.Date object encapsulating the date
   * and time of the calendar.
   */
  public Date getDate()
  {
    return calendar.getDate();
  }

  /**
   * fireCalendarEvent propagates a CalendarEvent object to
   * all CalendarListener objects for this CalendarControl
   * component.
   *
   * @param ce is a Ljjb.toolbox.swing.event.CalendarEvent
   * object containing information about the calendar event
   * which occurred in this CalendarControl component as a
   * result of the date changing.
   */
  protected void fireCalendarEvent(CalendarEvent ce)
  {
    if (calListener != null)
      calListener.calendarModified(ce);
  }

  /**
   * removeCalendarListener removes the specified
   * CalendarListener from the collection of CalendarListener
   * objects for this CalendarControl component listening for
   * CalendarEvents.
   *
   * @param oldCl is a Ljjb.toolbox.swing.event.CalendarListener
   * object to remove from the collection of CalendarListeners
   * for this CalendarControl component.
   */
  public void removeCalendarListener(CalendarListener oldCl)
  {
    calListener = CalendarEventMulticaster.remove(calListener,oldCl);
  }

  /**
   * setDate sets the calendar component's current date
   * specified by the Date object.
   *
   * @param dte:Ljava.util.Date object used to set the day,
   * month, and year of the calendar component.
   */
  public void setDate(Date dte)
  {
    calendar.setDate(dte);
  }

  /**
   * The overridden setEnabled method sets the CalendarControl
   * component to enabled with a boolean value of true and
   * disabled with a boolean value of false.
   *
   * @param enabled is a boolean value indicating true to
   * enable this CalendarControl component, false to disable
   * it.
   */
  public void setEnabled(boolean enabled)
  {
    super.setEnabled(enabled);
    dateField.setEnabled(enabled);
    showCalendar.setEnabled(enabled);
  }

}

