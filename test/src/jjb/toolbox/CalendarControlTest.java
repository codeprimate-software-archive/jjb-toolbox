/**
 * Copyright (c) 2001, JB Innovations
 * ALL RIGHTS RESERVED
 *
 * @author John J. Blum
 * File: CalendarControlTest.java
 * @version v1.0
 * Date: 27 November 2001
 * Modification Date: 5 December 2001
 * @since Java 2
 */

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import jjb.toolbox.swing.CalendarControl;
import jjb.toolbox.swing.event.CalendarEvent;
import jjb.toolbox.swing.event.CalendarListener;

public class CalendarControlTest extends JFrame implements CalendarListener
{

  public CalendarControlTest()
  {
    super("CalendarControl Test");

    final CalendarControl calendarControl = new CalendarControl();

    calendarControl.addCalendarListener(this);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(new Dimension(200,100));
    getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER));
    getContentPane().add(calendarControl);
    show();
  }

  public void calendarModified(CalendarEvent ce)
  {
    System.out.println("Date: "+ce.getDate());
  }

  public static void main(String[] args)
  {
    new CalendarControlTest();
  }

}

