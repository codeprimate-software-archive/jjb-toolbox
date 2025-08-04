/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * Bug: 11/19/2001
 * The JDateField component has a bug associated with using the Document object and manually
 * typing a month or day and setting the appropriate date part by calling the Document.remove
 * and Document.insertString methods.
 * The bug occurs when attempting to set the greatest common day in the month (the 28th) for
 * all months of the year regardless of leap year.  For example, if a user were to enter
 * 01/31/2001 and then manually type the MONTH in by pressing the 2 key on the keyboard when
 * the month is selected, the date field would display 02/31/2001, but the CalendarEvent
 * would contain the following date: 03/02/2001.  Hence, an inconsistency between state and
 * representation occurs.
 * To correct this, I call the setGreatestCommonDayForNonLeapYear() routine to set the day
 * to the 28th.  However, calling the Document.remove() and Document.insertString() methods
 * before the event can be finished processing by the key event, the text date representation
 * becomes scrambled.
 *
 * Bug: 20 December 2001
 * Fixed bug with following code snippet:
 * Integer.parseInt(KeyEvent.getKeyText(keyCode));
 * The key text for the numeric key pad on the keyboard will be in the following format:
 * "NumPad-2" when pressing the number 2 on the numeric key pad for example.
 *
 * Bug: 11/19/2001 Fixed!
 *
 * @author John J. Blum
 * File: JDateField.java
 * @version v1.0.2
 * Date: 8 November 2001
 * Modification Date: 17 April 2002
 * @since Java 2
 */

package jjb.toolbox.swing;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JTextField;
import javax.swing.event.MouseInputAdapter;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import jjb.toolbox.awt.event.KeyEventUtil;
import jjb.toolbox.swing.event.CalendarEvent;
import jjb.toolbox.swing.event.CalendarEventMulticaster;
import jjb.toolbox.swing.event.CalendarListener;

public class JDateField extends JTextField
{

  protected static final int[] NUM_DAYS_IN_MONTH = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

  protected static final DateFormat DATE_FORMAT  = new SimpleDateFormat("MM/dd/yyyy");
  protected static final DateFormat DAY_FORMAT   = new SimpleDateFormat("dd");
  protected static final DateFormat MONTH_FORMAT = new SimpleDateFormat("MM");
  protected static final DateFormat YEAR_FORMAT  = new SimpleDateFormat("yyyy");

  protected static final Toolkit TOOLKIT = Toolkit.getDefaultToolkit();

  private boolean           setTextCalled;

  private int               keyCode,
                            selectedCalendarField;

  protected final Calendar  calendar;

  private CalendarListener  calListener;

  /**
   * Creates an instance of the JDateField class component set to today's date.
   */
  public JDateField()
  {
    this(new Date());
  }

  /**
   * Creates an instance of the JDateField class component with the specified date
   * allowing users to specify dates using this date field component in applet/application
   * programs.
   *
   * @param date:Ljava.util.Date object used to set the content of the JDateField
   * component with a date value.
   */
  public JDateField(Date date)
  {
    super(7);

    setTextCalled = false;
    keyCode = KeyEvent.VK_UNDEFINED;
    calListener = null;
    calendar = new GregorianCalendar();
    calendar.setTime(date);
    setDate(date);
    setSelectedCalendarField(Calendar.MONTH);
    setSelectedTextColor(Color.white);
    setSelectionColor(new Color(10,36,106));
    addKeyListener(new DateKeyListener());

    DateMouseListener mouseListener = new DateMouseListener();

    addMouseListener(mouseListener);
    addMouseMotionListener(mouseListener);
  }

  /**
   * The DateDocument class is used by the JDateField component to format the JTextField
   * component as a date input field with slashes separating the month, day, and year.
   */
  private final class DateDocument extends PlainDocument
  {

    /**
     * insertString is used by the JDateField component to enter the specified
     * date content into the JTextField component of this date field at the
     * specified position with the given AttributeSet object.
     *
     * @param offs is an integer offset into the Document to insert content.
     * @param str:Ljava.lang.String content to insert into the Document of this
     * date field component.
     * @param a:Ljavax.swing.text.AttributeSet used to sytle the content. 
     * @exception BadLocationException  the given insert position is not a valid
     *   position within the document
     */
    public void insertString(int          offs,
                             String       str,
                             AttributeSet a    ) throws BadLocationException
    {
      if (!isValidDate(offs,str))
        TOOLKIT.beep();
      else
      {
        if (KeyEventUtil.isDigitKey(keyCode))
          super.insertString(offs,getDatePart(),a);
        else
          super.insertString(offs,str,a);
      }

      setSelectedCalendarField(selectedCalendarField);
    }

    /**
     * isValidDate determines whether the given String parameter at the specified
     * offset is a valid Date or Date part.
     *
     * @param str:Ljava.lang.String object representing the date.
     * @return a boolean value of true if the String parameter contains digits only,
     * false otherwise.
     */
    private boolean isValidDate(int    offs,
                                String str  )
    {
      boolean valid = true;  // innocent until proven guilty.

      char c = ' ';

      char[] chars = str.toCharArray();

      for (int index = 0, len = chars.length; index < len; index++ )
      {
        c = chars[index];

        switch (offs + index)
        {
        case 2:
        case 5:
          valid &= c == '/';
          break;
        default:
          valid &= Character.isDigit(c);
        }

        if (!valid)
          return false;
      }

      return true;
    }

    /**
     * remove is called by the JDateField component when a user types over or removes
     * text content from the JTextField component of this date field.
     *
     * @param offs is an integer offset into the Document specifying the start position
     * to remove content.
     * @param len is an integer specifying the number of characters to remove from
     * the date field.
     * @exception BadLocationException  the given insert position is not a valid
     *   position within the document
     */
    public void remove(int offs,
                       int len  ) throws BadLocationException
    {
      if (validRemoveKey())
        super.remove(offs,len);
    }

  } // End of Class

  /**
   * The DateKeyListener class is used by the JDateField component to track
   * LEFT, RIGHT, UP, AND DOWN arrow keyboard key press events.
   */
  private final class DateKeyListener extends KeyAdapter
  {

    private final Document DATEDOC = getDocument();

    /**
     * keyPressed is invoked when a key has been pressed.
     *
     * @param ke:Ljava.awt.event.KeyEvent encapsulating information about the
     * key pressed.
     */
    public void keyPressed(KeyEvent ke)
    {
      try
      {
        int selectionStart = getSelectionStart();

        switch (keyCode = ke.getKeyCode())
        {
        case KeyEvent.VK_BACK_SPACE:
        case KeyEvent.VK_DELETE:
          ke.consume();
          break;
        case KeyEvent.VK_HOME:
          setSelectedCalendarField(Calendar.MONTH);
          ke.consume();
          break;
        case KeyEvent.VK_LEFT:
          setSelectedCalendarField((selectedCalendarField == Calendar.DAY_OF_MONTH ? Calendar.MONTH : (selectedCalendarField == Calendar.MONTH ? Calendar.YEAR : Calendar.DAY_OF_MONTH)));
          ke.consume();
          break;
        case KeyEvent.VK_END:
          setSelectedCalendarField(Calendar.YEAR);
          ke.consume();
          break;
        case KeyEvent.VK_RIGHT:
          setSelectedCalendarField((selectedCalendarField == Calendar.YEAR ? Calendar.MONTH : (selectedCalendarField == Calendar.MONTH ? Calendar.DAY_OF_MONTH : Calendar.YEAR)));
          ke.consume();
          break;
        case KeyEvent.VK_UP:
        case KeyEvent.VK_DOWN:
          if (selectedCalendarField != Calendar.DAY_OF_MONTH)
            setGreatestCommonDayForNonLeapYear();

          calendar.roll(selectedCalendarField,(keyCode == KeyEvent.VK_UP ? true : false));
          DATEDOC.remove(selectionStart,(selectedCalendarField == Calendar.YEAR ? 4 : 2));
          DATEDOC.insertString(selectionStart,getDatePart(),null);
          fireCalendarEvent(new CalendarEvent(JDateField.this,calendar.getTime()));
          break;
        default:
          if (KeyEventUtil.isDigitKey(keyCode))
          {
            int currentValue = calendar.get(selectedCalendarField);
            int digit        = Integer.parseInt(String.valueOf(ke.getKeyChar()));
            int tempValue    = 0;

            switch (selectedCalendarField)
            {
            case Calendar.MONTH:
              setGreatestCommonDayForNonLeapYear();

              currentValue++;

              if ((tempValue = currentValue * 10 + digit) < 13)
                calendar.set(selectedCalendarField,tempValue-1);
              else
                calendar.set(selectedCalendarField,(digit > 0 ? digit-1 : digit));

              break;
            case Calendar.DAY_OF_MONTH:
              int month           = calendar.get(Calendar.MONTH);
              int maxDaysForMonth = NUM_DAYS_IN_MONTH[month];

              if (month == Calendar.FEBRUARY && ((GregorianCalendar) calendar).isLeapYear(calendar.get(Calendar.YEAR)))
                maxDaysForMonth += 1;

              if (digit == 0 && (currentValue * 10 + digit) > maxDaysForMonth)
                digit = 1;

              if ((tempValue = currentValue * 10 + digit) < maxDaysForMonth+1)
                calendar.set(selectedCalendarField,tempValue);
              else
                calendar.set(selectedCalendarField,digit);

              break;
            case Calendar.YEAR:
              setGreatestCommonDayForNonLeapYear();

              if (digit < 1)
                digit = 1;

              calendar.set(selectedCalendarField,digit*1000);
              break;
            }

            fireCalendarEvent(new CalendarEvent(JDateField.this,calendar.getTime()));
          }
        }
      }
      catch (BadLocationException ignore)
      {
      }
    }

  } // End of Class

  /**
   * The DateMouseListener class is used by the JDateField component to 
   * track mouse clicked/pressed events when the user sets focus to this date
   * field component.
   */
  private final class DateMouseListener extends MouseInputAdapter
  {

    /**
     * mouseClicked is invoked when a mouse button has been clicked on
     * the date field component.
     *
     * @param me:Ljava.awt.event.MouseEvent object capturing the mouse
     * clicked event in the date field component.
     */
    public void mouseClicked(MouseEvent me)
    {
      setSelectedCalendarField(Calendar.MONTH);
    }

    /**
     * mouseDragged is invoked when a mouse button has been pressed, held
     * and drug in the date field component.
     *
     * @param me:Ljava.awt.event.MouseEvent object capturing the mouse
     * clicked event in the date field component.
     */
    public void mouseDragged(MouseEvent me)
    {
      setSelectedCalendarField(Calendar.MONTH);
    }

    /**
     * mousePressed is invoked when a mouse button has been pressed on
     * the date field component.
     *
     * @param me:Ljava.awt.event.MouseEvent object capturing the mouse
     * pressed event in the date field component.
     */
    public void mousePressed(MouseEvent me)
    {
      setSelectedCalendarField(Calendar.MONTH);
    }

  } // End of Class

  /**
   * addCalendarListener adds the specified CalendarListener to the collection
   * of CalendarListener objects for this JDateField component to listen for
   * CalendarEvents.
   *
   * @param cl:Ljjb.toolbox.swing.event.CalendarListener object to add to the
   * collection of CalendarListeners for this date field component.
   */
  public void addCalendarListener(CalendarListener cl)
  {
    calListener = CalendarEventMulticaster.add(calListener,cl);
  }

  /**
   * Creates the default implementation of the model to be used at construction if one isn't
   * explicitly given. An instance of PlainDocument is returned.
   *
   * @return the default Ljavax.swing.text.Document object used to house the content for
   * this date field component.
   */
  protected Document createDefaultModel()
  {
    return new DateDocument();
  }

  /**
   * fireCalendarEvent propagates a CalendarEvent object to all CalendarListener
   * objects for this JDateField component.
   *
   * @param ce:Ljjb.toolbox.swing.event.CalendarEvent object containing information
   * about the calendar event which occurred in this date field component as a 
   * result of the date changing.
   */
  protected void fireCalendarEvent(CalendarEvent ce)
  {
    if (calListener != null)
      calListener.calendarModified(ce);
  }

  /**
   * getDate returns the date represented by this date field component as a
   * Date object.
   *
   * @return a Ljava.util.Date object encapsulating the date specified by this
   * date field component.
   */
  public Date getDate()
  {
    return calendar.getTime();
  }

  /**
   * getDatePart sets the specific date field (month, day of month, year) to the
   * value in the Calendar object formated into text using the specific DateFormat
   * objects for the month, day of month, and year.
   *
   * @return a Ljava.lang.String object representation of the date part value stored
   * in the Calendar object.
   */
  private String getDatePart()
  {
    switch (selectedCalendarField)
    {
    case Calendar.MONTH:
      return MONTH_FORMAT.format(calendar.getTime());
    case Calendar.DAY_OF_MONTH:
      return DAY_FORMAT.format(calendar.getTime());
    case Calendar.YEAR:
      return YEAR_FORMAT.format(calendar.getTime());
    default:
      return "--";
    }
  }

  /**
   * removeCalendarListener removes the specified CalendarListener from the collection
   * of CalendarListener objects for this JDateField component to listening for
   * CalendarEvents.
   *
   * @param oldCl:Ljjb.toolbox.swing.event.CalendarListener object to remove from the
   * collection of CalendarListeners for this date field component.
   */
  public void removeCalendarListener(CalendarListener oldCl)
  {
    calListener = CalendarEventMulticaster.remove(calListener,oldCl);
  }

  /**
   * setDate sets the JDateField component with the specified date by building
   * a String representation of the date value and calling setText.
   *
   * @param dte:Ljava.util.Date object used to set the contents of this
   * date field component.
   */
  public void setDate(Date dte)
  {
    setText(DATE_FORMAT.format(dte));
  }

  /**
   * setSelectedCalendarField selects the specified calendar field  given a calendar constant
   * (Calendar.MONTH, etc) indicating which part of the date (month, day, year) to
   * select.
   *
   * @param field is an Calendar constant value specifying the date part.
   */
  private void setSelectedCalendarField(int field)
  {
    selectedCalendarField = field;

    switch (selectedCalendarField)
    {
    default:
    case Calendar.MONTH:
      setSelectionStart(0);
      setSelectionEnd(2);
      break;
    case Calendar.DAY_OF_MONTH:
      setSelectionStart(3);
      setSelectionEnd(5);
      break;
    case Calendar.YEAR:
      setSelectionStart(6);
      setSelectionEnd(10);
      break;
    }
  }

  /**
   * setGreatestCommonDayForNonLeapYear sets the greatest day which all months
   * of the year have in common, namely, the 28th.
   *
   * @return a boolean of true if the day was set to GCD, false otherwise.
   * @throws Ljavax.swing.text.BadLocationException when the given insert position
   * is not a valid position within the document.
   */
  private boolean setGreatestCommonDayForNonLeapYear() throws BadLocationException
  {
    if (calendar.get(Calendar.DAY_OF_MONTH) > 28)
    {
      final Document DATEDOC = getDocument();

      int selectedField = selectedCalendarField;

      setSelectedCalendarField(Calendar.DAY_OF_MONTH);
      calendar.set(Calendar.DAY_OF_MONTH,28);
      DATEDOC.remove(3,2);
      DATEDOC.insertString(3,"28",null);
      setSelectedCalendarField(selectedField);

      return true;
    }

    return false;
  }

  /**
   * Overridden setText method sets the calendar to the specified date and then calls
   * super.setText to set the String representation of the date in the date field
   * component.  Note that the format of the date should follow: MM/dd/yyyy.
   *
   * @param text:Ljava.lang.String object representing the date value to set in
   * this date field component.
   */
  public void setText(String text)
  {
    setTextCalled = true;
    calendar.setTime(DATE_FORMAT.parse(text,new ParsePosition(0)));
    super.setText(text);
  }

  /**
   * validRemoveKey determines whether the user pressed the proper keyboard key to change
   * the contents of the date field component, thus change the date.
   *
   * @return a boolean value of true if the proper key was pressed to change the contents
   * of the date field component, false otherwise.
   */
  private boolean validRemoveKey()
  {
    switch (keyCode)
    {
    case KeyEvent.VK_DOWN:
    case KeyEvent.VK_UP:
      return true;
    default:
      if (setTextCalled)
      {
        setTextCalled = false;
        return true;
      }

      return KeyEventUtil.isDigitKey(keyCode);
    }
  }

}

