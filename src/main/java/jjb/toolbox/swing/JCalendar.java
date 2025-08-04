/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: JCalendar.java
 * @version v1.1
 * Date: 4 November 2001
 * Modification Date: 18 July 2002
 * @since Java 2
 */

package jjb.toolbox.swing;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;
import jjb.toolbox.swing.GraphicsButton;
import jjb.toolbox.swing.GraphicsUtil;
import jjb.toolbox.swing.event.CalendarEvent;
import jjb.toolbox.swing.event.CalendarEventMulticaster;
import jjb.toolbox.swing.event.CalendarListener;
import jjb.toolbox.util.DateUtil;

public final class JCalendar extends JPanel
{

  private static final int[] DAYS_OF_WEEK =
  { 
    Calendar.SUNDAY,
    Calendar.MONDAY,
    Calendar.TUESDAY,
    Calendar.WEDNESDAY,
    Calendar.THURSDAY,
    Calendar.FRIDAY,
    Calendar.SATURDAY
  };

  private static final int[] MONTHS_OF_YEAR =
  {
    Calendar.JANUARY,
    Calendar.FEBRUARY,
    Calendar.MARCH,
    Calendar.APRIL,
    Calendar.MAY,
    Calendar.JUNE,
    Calendar.JULY,
    Calendar.AUGUST,
    Calendar.SEPTEMBER,
    Calendar.OCTOBER,
    Calendar.NOVEMBER,
    Calendar.DECEMBER
  };

  private static final int[] NUM_DAYS_IN_MONTH = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

  private static final Color NAVY_BLUE = new Color(10,36,106);

  private static final Dimension SIZE = new Dimension(200,155);

  private static final String[] DAYS = 
  {
    "Sun",
    "Mon",
    "Tue",
    "Wed",
    "Thu",
    "Fri",
    "Sat"
  };

  private Calendar          cal;

  private CalendarListener  calListener;

  private final Component   calView;

  private final JLabel      monthYear;
  private final JLabel      dateLabel;

  /**
   * Creates an instance of the JCalendar component to use as a
   * calendar control in an applet/application.
   */
  public JCalendar()
  {
    this(new Date());
  }

  /**
   * Creates an instance of the JCalendar component to use as a
   * calendar control in an applet/application initialized to
   * the specified Date object.
   *
   * @param dte is a Ljava.util.Date object used to set the
   * calView.
   */
  public JCalendar(Date dte)
  {
    cal = new GregorianCalendar();
    calListener = null;
    calView = new CalendarView();
    monthYear = new JLabel("",JLabel.CENTER);
    setDate(dte);
    dateLabel = new JLabel(DateUtil.getDayOfWeekDateTime(cal.getTime()),JLabel.CENTER);
    setSize(SIZE);
    buildUI();
  }

  /**
   * The CalendarView class is a Canvas component used to paint
   * the display the calendar component.
   */
  private final class CalendarView extends Canvas
  {
    private int                colWidth;
    private int                rowHeight;
    private int                halfColWidth;
    private int                halfRowHeight;

    private int                _index;
    private int                highIndex;
    private int                lowIndex;

    private int                maxAscent;

    private int[]              matrix;

    private Dimension          size;

    private final Font         f;
    private final Font         _f;

    private final FontMetrics  fm;
    private final FontMetrics  _fm;

    private Graphics           _g;

    private Image              offscreenImage;

    /**
     * Creates a new instance of the CalendarView component to
     * paint the display of the calendar.
     */
    public CalendarView()
    {
      _index = -1;
      highIndex = -1;
      lowIndex = -1;
      matrix = new int[42]; // 6 X 7
      f = new Font("Helvetica",Font.PLAIN,10);
      _f = new Font("Helvetica",Font.BOLD,14);
      fm = Toolkit.getDefaultToolkit().getFontMetrics(f);
      _fm = Toolkit.getDefaultToolkit().getFontMetrics(_f);
      maxAscent = fm.getMaxAscent();

      final CalendarViewController controller = new CalendarViewController();

      this.addMouseListener(controller);
      this.addMouseMotionListener(controller);
    }

    private final class CalendarViewController extends MouseInputAdapter
    {
      /**
       * mouseExited is called when the user moves the mouse
       * outside the CalendarView component of the JCalendar.
       *
       * @param me is a Ljava.awt.event.MouseEvent object
       * encapsulating information about the mouse exited
       * event.
       */
      public void mouseExited(MouseEvent me)
      {
        SwingUtilities.invokeLater(new Runnable()
        {
          /**
           * When an object implementing interface <code>Runnable</code>
           * is used to create a thread, starting the thread causes the
           * object's <code>run</code> method to be called in that
           * separately executing thread.
           * <p>
           * The general contract of the method <code>run</code>
           * is that it may take any action whatsoever.
           * </p>
           *
           * @see java.lang.Thread#run()
           */
          public void run()
          {
            _index = -1;
            CalendarView.this.repaint();
          }
        });
      }

      /**
       * mouseMoved is called when the user moves his/her mouse
       * over the calendar control, or more specifically, the
       * CalenderView object's screen space.  This will call
       * repaint if the user has positioned the mouse over a
       * valid day for the month shown.  A yellow box will
       * appear for such a day.
       *
       * @param me is a Ljava.awt.event.MouseEvent object
       * encapsulating information about the mouse moved
       * event.
       */
      public void mouseMoved(MouseEvent me)
      {
        final int index = getIndexForPoint(me.getPoint());

        if (_index != index && ((index == -1) || (index >= lowIndex && index <= highIndex)))
        {
          SwingUtilities.invokeLater(new Runnable()
          {
            /**
             * When an object implementing interface <code>Runnable</code>
             * is used to create a thread, starting the thread causes the
             * object's <code>run</code> method to be called in that
             * separately executing thread.
             * <p>
             * The general contract of the method <code>run</code> is that
             * it may take any action whatsoever.
             * </p>
             *
             * @see java.lang.Thread#run()
             */
            public void run()
            {
              _index = index;
              CalendarView.this.repaint();
            }
          });
        }
      }

      /**
       * mousePressed is fired when the user presses the mouse
       * button over a valid day for the month shown in the
       * calendar control.
       *
       * @param me is a Ljava.awt.event.MouseEvent object
       * encapsulating information about  the mouse pressed
       * event.
       */
      public void mousePressed(MouseEvent me)
      {
        final int index = getIndexForPoint(me.getPoint());

        if (index >= lowIndex && index <= highIndex)
        {
          SwingUtilities.invokeLater(new Runnable()
          {
            /**
             * When an object implementing interface <code>Runnable</code>
             * is used to create a thread, starting the thread causes the
             * object's <code>run</code> method to be called in that
             * separately executing thread.
             * <p>
             * The general contract of the method <code>run</code> is that
             * it may take any action whatsoever.
             *
             * @see java.lang.Thread#run()
             */
            public void run()
            {
              _index = -1;
              CalendarView.this.repaint();
            }
          });

          cal.set(Calendar.DAY_OF_MONTH,matrix[index]);
          fireCalendarEvent(new CalendarEvent(JCalendar.this,cal));
        }
      }
    }

    /**
     * getIndexForPoint translates the mouse point to a cell
     * in the calendar corresponding to a index in the matrix.
     *
     * @param pt is a Ljava.awt.Point object corresponding to
     * the location of the mouse event.
     * @return an integer index into the matrix to return the
     * day for the month shown.
     */
    private int getIndexForPoint(Point pt)
    {
      int x = (int) pt.getX();
      int y = (int) pt.getY();

      if ((y -= rowHeight) < 0)
        return -1;

      x /= colWidth;
      y /= rowHeight;

      return (y * 7) + x;
    }

    /**
     * init initializes the CalendarView component's offscreen
     * buffer and sets state information about size.
     *
     * @param size:Ljava.awt.Dimension object specifying the
     * size of the CalendarView object.
     */
    private void init(Dimension size)
    {
      this.size = size;
      colWidth = (int) Math.round(size.getWidth() / 7.0);
      rowHeight = (int) Math.round(size.getHeight() / 7.0);
      halfColWidth = (int) ((double) colWidth / 2.0);
      halfRowHeight = (int) ((double) rowHeight / 2.0);
      offscreenImage = this.createImage(size.width,size.height);
      _g = offscreenImage.getGraphics();
    }

    /**
     * Overriden update is called to refresh the view of the
     * calendar component.  The update method calls the paint
     * method for double buffering.
     *
     * @param g is a Ljava.awt.Graphics object used to draw
     * the user interface elements.
     */
    public void update(Graphics g)
    {
      paint(g);
    }

    /**
     * paint paints the UI of the CalenderView control.
     *
     * @param g:Ljava.awt.Graphics object used to draw the
     * user interface elements.
     */
    public void paint(Graphics g)
    {
      if (size == null)
        init(this.getSize());

      _g.setFont(f);
      _g.setColor(Color.white);
      _g.fillRect(0,0,size.width,size.height);

      String dayStr = null;

      _g.setColor(NAVY_BLUE);

      // Display Days of Week
      for (int index = 0, newx = 0, x = 0, y = (halfRowHeight + (maxAscent / 2)); index < 7; index++, x = (index * colWidth))
      {
        dayStr = DAYS[index];
        newx = x + (halfColWidth - (fm.stringWidth(dayStr) / 2));
        _g.drawString(dayStr,newx,y);
      }

      // Display Line
      _g.setColor(Color.black);
      _g.drawLine(5,rowHeight - 1,size.width - 5,rowHeight - 1);

      // Draw Calendar
      int month            = cal.get(Calendar.MONTH);
      int firstDayOfMonth  = getFirstDayOfMonth(cal.get(Calendar.DAY_OF_MONTH),cal.get(Calendar.DAY_OF_WEEK));
      int numDaysInMonth   = 0;
      int numDaysLastMonth = 0;

      if (month == Calendar.FEBRUARY && ((GregorianCalendar) cal).isLeapYear(cal.get(Calendar.YEAR)))
      {
        numDaysInMonth   = NUM_DAYS_IN_MONTH[Calendar.FEBRUARY] + 1;
        numDaysLastMonth = NUM_DAYS_IN_MONTH[Calendar.JANUARY];
      }
      else
      {
        numDaysInMonth = NUM_DAYS_IN_MONTH[month];

        int prevMonth = month - 1;

        if (prevMonth < 0)
          numDaysLastMonth = NUM_DAYS_IN_MONTH[Calendar.DECEMBER];
        else if (prevMonth == Calendar.FEBRUARY && ((GregorianCalendar) cal).isLeapYear(cal.get(Calendar.YEAR)))
          numDaysLastMonth = NUM_DAYS_IN_MONTH[Calendar.FEBRUARY] + 1;
        else
          numDaysLastMonth = NUM_DAYS_IN_MONTH[prevMonth];
      }

      boolean swtch = false;

      int day     = numDaysLastMonth - firstDayOfMonth + 1;
      int maxDays = numDaysLastMonth;
      int x       = 0;
      int y       = 0;

      Point cell = new Point();

      _g.setColor(Color.lightGray);
      dayStr = String.valueOf(day);
      lowIndex = -1;

      for (int index = 0, col = 0, row = 1; index < 42; index++, col = (index % 7), row = (index / 7) + 1)
      {
        cell.x = (col * colWidth);
        cell.y = (row * rowHeight);
        x = cell.x + (halfColWidth - (fm.stringWidth(dayStr) / 2));
        y = cell.y + (halfRowHeight + (maxAscent / 2));

        // Drawing Day
        if (swtch && day == cal.get(Calendar.DAY_OF_MONTH))
        {
          _g.setColor(NAVY_BLUE);
          _g.fillOval(cell.x,cell.y+2,colWidth,rowHeight);
          _g.setColor(Color.white);
          _g.drawString(dayStr,x,y);
          _g.setColor(Color.black);
        }
        else
        {
          if (day == (_index < 0 ? _index : matrix[_index]) && swtch)
          {
            x = cell.x + (halfColWidth - (_fm.stringWidth(dayStr) / 2));
            y = cell.y + (halfRowHeight + (_fm.getMaxAscent() / 2));

            _g.setFont(_f);
            _g.drawString(dayStr,x,y);
            _g.setFont(f);
          }
          else
            _g.drawString(dayStr,x,y);
        }

        //  Store and update day, reset dayStr variable.
        matrix[index] = day;
        day++;

        if (day > maxDays)
        {
          day = 1;
          dayStr = "01";
          maxDays = numDaysInMonth;
          swtch = !swtch;
          _g.setColor(swtch ? Color.black : Color.lightGray);

          if (lowIndex < 0)
            lowIndex = index;
          else
            highIndex = index;
        }
        else
          dayStr = (day < 10 ? "0"+day : String.valueOf(day));
      }

      g.drawImage(offscreenImage,0,0,null);
    }
  }

  /**
   * addCalendarListener adds the specified CalendarListener
   * to the collection of CalendarListener objects for this
   * calendar control to listen for CalendarEvents.
   *
   * @param cl is a Ljjb.toolbox.swing.event.CalendarListener
   * object to add to the collection of CalendarListeners for
   * this calendar control.
   */
  public void addCalendarListener(CalendarListener cl)
  {
    calListener = CalendarEventMulticaster.add(calListener,cl);
  }

  /**
   * buildUI constructs and lays out the components constituting
   * the calendar component.
   */
  private void buildUI()
  {
    calView.setSize(new Dimension(200,100));
    dateLabel.setPreferredSize(new Dimension(200,25));
    dateLabel.setFont(new Font("Helvetica",Font.BOLD,12));
    dateLabel.setForeground(Color.black);
    setBorder(BorderFactory.createLineBorder(Color.black,1));
    setLayout(new BorderLayout());
    add(buildTitleBar(),BorderLayout.NORTH);
    add(calView,BorderLayout.CENTER);
    add(dateLabel,BorderLayout.SOUTH);
  }

  /**
   * buildTitleBar sets the title of the calView control to
   * the Month, Year and adds back and forward buttons to
   * decrement and increment the month/year respectively.
   *
   * @return a Ljavax.swing.JPanel component containing the
   * control buttons for month and year and a label
   * containing the current month and year.
   */
  private JPanel buildTitleBar()
  {
    JPanel titleBar = new JPanel(new BorderLayout());

    titleBar.setBackground(NAVY_BLUE);
    titleBar.setPreferredSize(new Dimension(200,30));

    final Dimension buttonSize = new Dimension(18,18);

    final Insets margin = new Insets(1,1,1,1);

    JPanel leftButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,5,5));

    leftButtonPanel.setOpaque(false);
    titleBar.add(leftButtonPanel,BorderLayout.WEST);

    final JButton backYear = (JButton) leftButtonPanel.add(new GraphicsButton(GraphicsUtil.drawDoubleLeftArrow()));

    backYear.setMargin(margin);
    backYear.setPreferredSize(buttonSize);
    backYear.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent ae)
      {
        cal.roll(Calendar.YEAR,false);

        SwingUtilities.invokeLater(new Runnable()
        {
          public void run()
          {
            monthYear.setText(DateUtil.getMonthYear(cal.getTime()));
            calView.repaint();
            fireCalendarEvent(new CalendarEvent(JCalendar.this,cal));
          }
        });
      }
    });

    final JButton backMonth = (JButton) leftButtonPanel.add(new GraphicsButton(GraphicsUtil.drawLeftArrow()));

    backMonth.setMargin(margin);
    backMonth.setPreferredSize(buttonSize);
    backMonth.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent ae)
      {
        if (cal.get(Calendar.MONTH) == Calendar.JANUARY)
        {
          cal.roll(Calendar.YEAR,false);
          cal.roll(Calendar.MONTH,false);
        }
        else
          cal.roll(Calendar.MONTH,false);

        SwingUtilities.invokeLater(new Runnable()
        {
          public void run()
          {
            monthYear.setText(DateUtil.getMonthYear(cal.getTime()));
            calView.repaint();
            fireCalendarEvent(new CalendarEvent(JCalendar.this,cal));
          }
        });
      }
    });

    monthYear.setOpaque(false);
    monthYear.setForeground(Color.white);
    titleBar.add(monthYear,BorderLayout.CENTER);

    JPanel rightButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,5,5));

    rightButtonPanel.setOpaque(false);
    titleBar.add(rightButtonPanel,BorderLayout.EAST);

    final JButton forwardMonth = (JButton) rightButtonPanel.add(new GraphicsButton(GraphicsUtil.drawRightArrow()));

    forwardMonth.setMargin(margin);
    forwardMonth.setPreferredSize(buttonSize);
    forwardMonth.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent ae)
      {
        if (cal.get(Calendar.MONTH) == Calendar.DECEMBER)
        {
          cal.roll(Calendar.YEAR,true);
          cal.roll(Calendar.MONTH,true);
        }
        else
          cal.roll(Calendar.MONTH,true);

        SwingUtilities.invokeLater(new Runnable()
        {
          public void run()
          {
            monthYear.setText(DateUtil.getMonthYear(cal.getTime()));
            calView.repaint();
            fireCalendarEvent(new CalendarEvent(JCalendar.this,cal));
          }
        });
      }
    });

    final JButton forwardYear = (JButton) rightButtonPanel.add(new GraphicsButton(GraphicsUtil.drawDoubleRightArrow()));

    forwardYear.setMargin(margin);
    forwardYear.setPreferredSize(buttonSize);
    forwardYear.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent ae)
      {
        cal.roll(Calendar.YEAR,true);

        SwingUtilities.invokeLater(new Runnable()
        {
          public void run()
          {
            monthYear.setText(DateUtil.getMonthYear(cal.getTime()));
            calView.repaint();
            fireCalendarEvent(new CalendarEvent(JCalendar.this,cal));
          }
        });
      }
    });

    return titleBar;
  }

  /**
   * fireCalendarEvent propagates a CalendarEvent object to
   * all CalendarListener objects for this calendar control.
   *
   * @param ce is a Ljjb.toolbox.swing.event.CalendarEvent
   * object containing information about the calendar event
   * which occurred in this calendar control.
   */
  protected void fireCalendarEvent(CalendarEvent ce)
  {
    if (calListener != null)
      calListener.calendarModified(ce);
  }

  /**
   * getDate returns a Date object encapsulating the date
   * and time as viewed in the calView.
   *
   * @return a Ljava.util.Date object encapsulating the date
   * and time of this calView.
   */
  public Date getDate()
  {
    return (Date) (cal.getTime()).clone();
  }

  /**
   * getFirstDayOfMonth gets the week day (i.e. Sunday,
   * Monday, Tuesday, etc) that the first day of the month
   * falls on.
   *
   * @param dayOfMonth is an integer value specifying the
   * number of days from the beginning of the month.
   * @param dayOfWeek is an integer value specifying the
   * week day for the current day of the month.
   */
  private int getFirstDayOfMonth(int dayOfMonth,
                                 int dayOfWeek  )
  {
    int day = dayOfWeek - (dayOfMonth % DAYS_OF_WEEK.length) - 1;

    if (day < 0)
      day += 7;

    return DAYS_OF_WEEK[day];
  }

  /**
   * removeCalendarListener removes the specified listener
   * of this calendar control from the CalendarListeners
   * collection.
   *
   * @param cl is a Ljjb.toolbox.swing.event.CalendarListener
   * object being removed from the collection of
   * CalendarListener objects for this calendar control.
   */
  public void removeCalendarListener(CalendarListener cl)
  {
    calListener = CalendarEventMulticaster.remove(calListener,cl);
  }

  /**
   * The overridden requestFocus method transfers input focus
   * to the CalendarView component of this JCalandar component.
   */
  public void requestFocus()
  {
    calView.requestFocus();
  }

  /**
   * setDate sets the calView view to the current date
   * specified by the Date object.
   *
   * @param dte is a Ljava.util.Date object used to set the
   * day, month, and year view in this calView.
   */
  public void setDate(Date dte)
  {
    cal.setTime((Date) dte.clone());
    monthYear.setText(DateUtil.getMonthYear(cal.getTime()));
  }

}

