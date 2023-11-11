/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: JTimeField.java
 * @version v1.0
 * Date: 19 November 2001
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JTextField;
import javax.swing.event.MouseInputAdapter;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import jjb.toolbox.awt.event.KeyEventUtil;

public class JTimeField extends JTextField
{

  protected static final DateFormat HOUR_FORMAT = new SimpleDateFormat("hh");
  protected static final DateFormat MIN_FORMAT  = new SimpleDateFormat("mm");
  protected static final DateFormat TIME_FORMAT = new SimpleDateFormat("hh:mm a");

  protected static final Toolkit TOOLKIT = Toolkit.getDefaultToolkit();

  private int       keyCode,
                    selectedTimeField;

  private Calendar  calendar;

  /**
   * Creates an instance of the JTimeField class component.
   */
  public JTimeField()
  {
    this(new Date());
  }

  /**
   * Creates an instance of the JTimeField class component to represent time
   * in forms of the applets/applications for users to modify.
   */
  public JTimeField(Date time)
  {
    super(5);

    keyCode = KeyEvent.VK_UNDEFINED;
    selectedTimeField = Calendar.HOUR;
    calendar = Calendar.getInstance();
    calendar.setTime(time);
    setText(TIME_FORMAT.format(time));
    setSelectedTextColor(Color.white);
    setSelectionColor(new Color(10,36,106));
    addKeyListener(new TimeKeyListener());

    TimeMouseListener mouseListener = new TimeMouseListener();

    addMouseListener(mouseListener);
    addMouseMotionListener(mouseListener);
  }

  /**
   * The TimeDocument class is the default document used by the JTimeField component
   * to represent time in a standard JTextField component.
   */
  private final class TimeDocument extends PlainDocument
  {

    /**
     * Inserts some content into the document.
     * Inserting content causes a write lock to be held while the
     * actual changes are taking place, followed by notification
     * to the observers on the thread that grabbed the write lock.
     * <p>
     * This method is thread safe, although most Swing methods
     * are not. Please see
     * <A HREF="http://java.sun.com/products/jfc/swingdoc-archive/threads.html">Threads
     * and Swing</A> for more information.
     *
     * @param offs the starting offset >= 0
     * @param str the string to insert; does nothing with null/empty strings
     * @param a the attributes for the inserted content
     * @exception BadLocationException  the given insert position is not a valid
     *   position within the document
     * @see Document#insertString
     */
    public void insertString(int          offs,
                             String       str, 
                             AttributeSet a    ) throws BadLocationException
    {
      if (!isValidTime(offs,str))
        TOOLKIT.beep();
      else
      {
        //if (KeyEventUtil.isDigitKey(keyCode) || selectedTimeField == Calendar.AM_PM)
        if (validKey())
          super.insertString(offs,getTimePart(),a);
        else
          super.insertString(offs,str,a);
      }

      setSelectedTimeField(selectedTimeField);
    }

    /**
     * isValidTime determines whether the given String parameter at the specified
     * offset is a valid Time or Time part.
     *
     * @param str:Ljava.lang.String object representing the date.
     * @return a boolean value of true if the String parameter contains digits only,
     * false otherwise.
     */
    private boolean isValidTime(int    offs,
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
          valid &= c == ':';
          break;
        case 5:
          valid &= c == ' ';
          break;
        case 6:
          valid &= (c == 'A' || c == 'P' || c == 'a' || c == 'p');
          break;
        case 7:
          valid &= c == 'M';
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
     * Removes some content from the document.
     * Removing content causes a write lock to be held while the
     * actual changes are taking place.  Observers are notified
     * of the change on the thread that called this method.
     * <p>
     * This method is thread safe, although most Swing methods
     * are not. Please see
     * <A HREF="http://java.sun.com/products/jfc/swingdoc-archive/threads.html">Threads
     * and Swing</A> for more information.
     *
     * @param offs the starting offset >= 0
     * @param len the number of characters to remove >= 0
     * @exception BadLocationException  the given remove position is not a valid
     *   position within the document
     * @see Document#remove
     */
    public void remove(int offs,
                       int len  ) throws BadLocationException
    {
      if (validKey())
      {
        boolean isDigitKey = KeyEventUtil.isDigitKey(keyCode);

        if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN)
          ;
        else if (!isDigitKey && (selectedTimeField == Calendar.HOUR || selectedTimeField == Calendar.MINUTE))
          return;
        else if (isDigitKey && selectedTimeField == Calendar.AM_PM)
          return;
        else if ((keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_P) && (selectedTimeField != Calendar.AM_PM))
          return;

        super.remove(offs,len);
      }
    }

  } // End of Class

  /**
   * The TimeKeyListener is used by the JTimeField component to track key events
   * targeted at editing and traversing the time field component.
   */
  private final class TimeKeyListener extends KeyAdapter
  {

    private final Document TIMEDOC = getDocument();

    /**
     * Invoked when a key has been pressed.
     */
    public void keyPressed(KeyEvent e)
    {
      try
      {
        int selectionStart = getSelectionStart();

        switch (keyCode = e.getKeyCode())
        {
        case KeyEvent.VK_BACK_SPACE:
        case KeyEvent.VK_DELETE:
          e.consume();
          break;
        case KeyEvent.VK_HOME:
          setSelectedTimeField(Calendar.HOUR);
          e.consume();
          break;
        case KeyEvent.VK_LEFT:
          setSelectedTimeField((selectedTimeField == Calendar.HOUR ? Calendar.AM_PM : (selectedTimeField == Calendar.AM_PM ? Calendar.MINUTE : Calendar.HOUR)));
          e.consume();
          break;
        case KeyEvent.VK_END:
          setSelectedTimeField(Calendar.AM_PM);
          e.consume();
          break;
        case KeyEvent.VK_RIGHT:
          setSelectedTimeField((selectedTimeField == Calendar.HOUR ? Calendar.MINUTE : (selectedTimeField == Calendar.MINUTE ? Calendar.AM_PM : Calendar.HOUR)));
          e.consume();
          break;
        case KeyEvent.VK_UP:
        case KeyEvent.VK_DOWN:
          if (selectedTimeField == Calendar.AM_PM)
          {
            if (calendar.get(Calendar.AM_PM) == Calendar.AM)
              calendar.set(Calendar.HOUR_OF_DAY,calendar.get(Calendar.HOUR_OF_DAY) + 12);
            else
              calendar.set(calendar.HOUR_OF_DAY,calendar.get(Calendar.HOUR_OF_DAY) - 12);
          }
          else
            calendar.roll(selectedTimeField,(keyCode == KeyEvent.VK_UP ? true : false));

          TIMEDOC.remove(selectionStart,2);
          TIMEDOC.insertString(selectionStart,getTimePart(),null);
          break;
        default:
          if (validKey())
          {
            if (KeyEventUtil.isDigitKey(keyCode))
            {
              int digit        = Integer.parseInt(KeyEvent.getKeyText(keyCode));
              int currentValue = calendar.get(selectedTimeField);
              int tempVal      = 0;

              switch (selectedTimeField)
              {
              case Calendar.HOUR:
                digit = (currentValue == 0 && digit < 1 ? 1 : digit);

                if ((tempVal = currentValue * 10 + digit) < 13)
                  calendar.set(selectedTimeField,tempVal);
                else
                  calendar.set(selectedTimeField,digit);

                break;
              case Calendar.MINUTE:
                if ((tempVal = currentValue * 10 + digit) < 60)
                  calendar.set(selectedTimeField,tempVal);
                else
                  calendar.set(selectedTimeField,digit);

                break;
              }
            }
            else
              calendar.set(Calendar.HOUR_OF_DAY,(keyCode == KeyEvent.VK_A ? calendar.get(Calendar.HOUR) : calendar.get(Calendar.HOUR)+12));
          }
        }
      }
      catch (BadLocationException ignore)
      {
      }
    }

  } // End of Class

  /**
   * The TimeMouseListener class is used by the JTimeField component to 
   * track mouse clicked/pressed events when the user sets focus to this time
   * field component.
   */
  private final class TimeMouseListener extends MouseInputAdapter
  {

    /**
     * mouseClicked is invoked when a mouse button has been clicked on
     * the time field component.
     *
     * @param me:Ljava.awt.event.MouseEvent object capturing the mouse
     * clicked event in the time field component.
     */
    public void mouseClicked(MouseEvent me)
    {
      setSelectedTimeField(Calendar.HOUR);
    }

    /**
     * mouseDragged is invoked when a mouse button has been pressed, held
     * and drug in the time field component.
     *
     * @param me:Ljava.awt.event.MouseEvent object capturing the mouse
     * clicked event in the time field component.
     */
    public void mouseDragged(MouseEvent me)
    {
      setSelectedTimeField(Calendar.HOUR);
    }

    /**
     * mousePressed is invoked when a mouse button has been pressed on
     * the time field component.
     *
     * @param me:Ljava.awt.event.MouseEvent object capturing the mouse
     * pressed event in the time field component.
     */
    public void mousePressed(MouseEvent me)
    {
      setSelectedTimeField(Calendar.HOUR);
    }

  } // End of Class

  /**
   * createDefaultModel creates the default implementation of the model to be used at
   * construction if one isn't explicitly given.  An instance of PlainDocument is
   * returned.
   *
   * @return a Ljavax.swing.text.Document object representing the default model
   * implementation.
   */
  protected Document createDefaultModel()
  {
    return new TimeDocument();
  }

  /**
   * getTime returns the time represented in the time field component encapsulated
   * in a Date object.  Note that the date of the time field component is set to the
   * month, day and year for which the Date object containing the time was set to.
   *
   * @return a Ljava.util.Date object containing the time represented by this time
   * field component.
   */
  public Date getTime()
  {
    return calendar.getTime();
  }

  /**
   * getTimePart sets the selected time field to contain the correct value depending
   * upon whether hour or minute is selected.
   *
   * @param str:Ljava.lang.String value representing the hour of day, or the minute
   * within the hour.
   * @return a Ljava.lang.String object containing the formated parameter.
   */
  private String getTimePart()
  {
    switch (selectedTimeField)
    {
    case Calendar.HOUR:
      return HOUR_FORMAT.format(calendar.getTime());
    case Calendar.MINUTE:
      return MIN_FORMAT.format(calendar.getTime());
    case Calendar.AM_PM:
      return (calendar.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM");
    default:
      return "--";
    }
  }

  /**
   * setSelectedTimeField sets the selected field in the time to the designated
   * parameter.
   *
   * @param field is an integer enumerated value specifying the time field to 
   * select.
   */
  private void setSelectedTimeField(int field)
  {
    selectedTimeField = field;

    switch (selectedTimeField)
    {
    case Calendar.HOUR:
      setSelectionStart(0);
      setSelectionEnd(2);
      break;
    case Calendar.MINUTE:
      setSelectionStart(3);
      setSelectionEnd(5);
      break;
    case Calendar.AM_PM:
      setSelectionStart(6);
      setSelectionEnd(8);
      break;
    }
  }

  /**
   * setTime sets the time of the time field component to that of the Date object
   * parameter.
   *
   * @param time:Ljava.util.Date object containing the time information to set
   * this time field component with the time.
   */
  public void setTime(Date time)
  {
    calendar.setTime(time);
    setText(TIME_FORMAT.format(time));
  }

  /**
   * validKey determines whether the user pressed the proper keyboard key to change
   * the contents of the time field component, thus change the time.
   *
   * @return a boolean value of true if the proper key was pressed to change the contents
   * of the time field component, false otherwise.
   */
  private boolean validKey()
  {
    switch (keyCode)
    {
    case KeyEvent.VK_A:
    case KeyEvent.VK_P:
    case KeyEvent.VK_DOWN:
    case KeyEvent.VK_UP:
      return true;
    default:
      return KeyEventUtil.isDigitKey(keyCode);
    }
  }

}

