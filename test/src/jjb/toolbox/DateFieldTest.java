/**
 * Copyright (c) 2001, JB Innovations
 * ALL RIGHTS RESERVED
 *
 * @author John J. Blum
 * File: DateFieldTest.java
 * @version v1.0
 * Date: 10 November 2001
 * Modification Date: 19 November 2001
 * @since Java 2
 */

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import javax.swing.JFrame;
import jjb.toolbox.swing.JDateField;
import jjb.toolbox.swing.event.CalendarEvent;
import jjb.toolbox.swing.event.CalendarListener;

public class DateFieldTest extends JFrame implements CalendarListener
{ 

  public DateFieldTest()
  {
    super("JDateField");

    //final JDateField dateField = new JDateField((new SimpleDateFormat("MM/dd/yyyy")).parse("2/29/2000",new ParsePosition(0)));
    final JDateField dateField = new JDateField();

    dateField.addCalendarListener(this);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(new Dimension(200,100));
    getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER));
    getContentPane().add(dateField);
    show();
  }

  public void calendarModified(CalendarEvent ca)
  {
    System.out.println("Date: "+ca.getDate());
  }

  public static void main(String[] args)
  {
    new DateFieldTest();
  }

}

