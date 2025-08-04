/**
 * Copyright (c) 2001, JB Innovations
 * ALL RIGHTS RESERVED
 *
 * @author John J. Blum
 * File: CalendarTest.java
 * @version v1.0
 * Date: 7 November 2001
 * Modification Date: 19 November 2001
 * @since Java 2
 */

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Date;
import javax.swing.JFrame;
import jjb.toolbox.swing.JCalendar;
import jjb.toolbox.swing.event.CalendarEvent;
import jjb.toolbox.swing.event.CalendarListener;

public class CalendarTest extends JFrame implements CalendarListener
{

  public CalendarTest()
  {
    super("JCalendar");

    final JCalendar cal = new JCalendar(new Date());

    cal.addCalendarListener(this);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(new Dimension(400,300));
    getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER));
    getContentPane().add(cal);
    show();
  }

  public void calendarModified(CalendarEvent ca)
  {
    System.out.println("Date: "+ca.getDate());
  }

  public static void main(String[] args)
  {
    new CalendarTest();
  }

}

