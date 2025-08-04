/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: CStatusBar.java
 * @version v1.0.1
 * Date: 3 May 2001
 * Modification Date: 15 April 2003
 * @since Java 1.0
 */

package jjb.toolbox.awt;

import java.awt.*;

public class CStatusBar extends Canvas
{

  private boolean       runStatus          = false;

  private int           sm1Length;

  private Color         statusMessageColor = Color.black,
                        statusBarColor     = new Color(0,0,128), // Dark Blue
                        sm1Color           = null,
                        sm2Color           = null;

  private Dimension     canvasSize         = new Dimension(0,0);

  private Font          defaultFont        = new Font("TimesRoman",Font.PLAIN,12),
                        statusMessageFont  = defaultFont;

  private FontMetrics   fontMetrics;

  private Graphics      offscreenGraphics;

  private Image         offscreenImage;

  private Rectangle     bar;

  private String        statusMessage      = null,
                        sm1                = null,
                        sm2                = null;

  /**
   * Constructs a new CStatusBar GUI component with no message, and default
   * message color and font.
   */
  public CStatusBar()
  {
    this(null,null,null);
  }

  /**
   * Constructs a new CStatusBar GUI component with the specified initial message
   * and default color and font.
   *
   * @param initialMessage:Ljava.lang.String object representing the message to
   * display in the status bar.
   */
  public CStatusBar(String initialMessage)
  {
    this(initialMessage,null,null);
  }

  /**
   * Constructs a new CStatusBar GUI component with the specified initial message,
   * the specified color for the message text, and default font.
   *
   * @param initialMessage:Ljava.lang.String object representing the message to
   * display in the status bar.
   * @param messageColor:Ljava.awt.Color - the color of the message text.
   */
  public CStatusBar(String initialMessage,
                    Color  messageColor   )
  {
    this(initialMessage,messageColor,null);
  }

  /**
   * Constructs a new CStatusBar GUI component with the specified initial message,
   * specified font for the message text, and the default color.
   *
   * @param initialMessage:Ljava.lang.String object representing the message to
   * display in the status bar.
   * @param messageFont:Ljava.awt.Font object used to set the font of the message
   * text.
   */
  public CStatusBar(String initialMessage,
                    Font   messageFont    )
  {
    this(initialMessage,null,messageFont);
  }

  /**
   * Constructs a new CStatusBar GUI component with the specified initial message
   * the specified message color, and specified message font.
   *
   * @param initialMessage:Ljava.lang.String object representing the message to
   * display in the status bar.
   * @param messageColor:Ljava.awt.Color - the color of the message text.
   * @param messageFont:Ljava.awt.Font object used to set the font of the message
   * text.
   */
  public CStatusBar(String initialMessage,
                    Color  messageColor,
                    Font   messageFont    )
  {
    bar = new Rectangle(0,0,0,0);

    setStatusMessage(initialMessage);
    setStatusMessageColor((messageColor == null ? statusMessageColor : messageColor));
    setFont((messageFont == null ? defaultFont : messageFont));
  }

  /**
   * StatusRunner activates the status bar calculating the position of the moving
   * blue bar, and the text color variations.
   */
  private final class StatusRunner extends Thread
  {

    private boolean FORWARD = true;

    /**
     * run method of the Thread class.  A logic for the thread exists
     * in this method.
     */
    public void run()
    {
      Graphics g = getGraphics();

      //repaint();
      update(g);

      bar.x = 0;
      bar.y = 2;
      bar.height = canvasSize.height - 4;

      while (runStatus)
      {
        if (FORWARD)
        {
          if (bar.width < (canvasSize.width/3))
            bar.width++;
          else if (bar.x < canvasSize.width)
            bar.x++;
          else
            FORWARD = false;
        }
        else
        {
          if (bar.x > 0)
            bar.x--;
          else if (bar.width > 0)
            bar.width--;
          else
            FORWARD = true;
        }

        setMessageColor();
        //repaint();
        update(g);

        try { Thread.sleep(10); }
        catch (InterruptedException ignore) {}
      }

      bar.x = 0;
      bar.width = 0;
      sm1 = statusMessage;
      sm1Color = statusMessageColor;
      sm2 = null;

      //repaint();
      update(g);
    }

  } // End of Class

  /**
   * paint paints the presentation of the status bar.
   *
   * @param g:Ljava.awt.Graphics object used perform the graphics operations.
   */
  public void paint(Graphics g)
  {
    Dimension dim = getSize();

    if (!dim.equals(canvasSize))
    {
      offscreenImage = createImage(dim.width,dim.height);
      offscreenGraphics = offscreenImage.getGraphics();
      offscreenGraphics.setFont(statusMessageFont);
      canvasSize = dim;
    }

    // Background
    offscreenGraphics.setColor(Color.lightGray);
    offscreenGraphics.fillRect(0,0,dim.width,dim.height);

    // Border
    offscreenGraphics.setColor(SystemColor.controlLtHighlight);
    offscreenGraphics.drawLine(dim.width-1,1,dim.width-1,dim.height-1);   // Right Edge
    offscreenGraphics.drawLine(0,dim.height-1,dim.width,dim.height-1);    // Bottom Edge
    offscreenGraphics.setColor(SystemColor.controlDkShadow);
    offscreenGraphics.drawLine(0,1,dim.width,1);                          // Top Edge
    offscreenGraphics.drawLine(0,1,0,dim.height-1);                       // Left Edge

    offscreenGraphics.setClip(2,2,canvasSize.width-2,canvasSize.height-2);

    if (runStatus)
    {
      offscreenGraphics.setColor(statusBarColor);
      offscreenGraphics.fillRect(bar.x,bar.y,bar.width,bar.height);
    }

    if (statusMessage != null)
    {
      int x = 5;
      //int y = ((dim.height/2) + (fontMetrics.getMaxAscent()/2));
      int y = (dim.height - fontMetrics.getMaxDescent());

      offscreenGraphics.setColor(sm1Color);
      offscreenGraphics.drawString(sm1,x,y);

      if (sm2 != null)
      {
        offscreenGraphics.setColor(sm2Color);
        offscreenGraphics.drawString(sm2,x+sm1Length,y);
      }
    }

    g.drawImage(offscreenImage,0,0,null);
  }

  /**
   * setFont sets the font of the message text
   *
   * @param f:Ljava.awt.Font object used to specify the font of the message
   * text.
   */
  public void setFont(Font f)
  {
    super.setFont(f);

    statusMessageFont = (f == null ? defaultFont : f);
    fontMetrics = getFontMetrics(f);
  }

  /**
   * setMessageColor sets the text color of the message such that any text
   * displaying in the area of the blue bar is white, and all text not in
   * the area of the blue bar is messageColor (or default color: black).
   */
  private void setMessageColor()
  {
    sm1 = null;
    sm2 = null;
    sm1Length = 0;

    if (statusMessage == null) return;

    char[] messageChar = statusMessage.toCharArray();

    if (messageChar.length == 0) return;

    int index     = 0,
        charWidth = fontMetrics.charWidth(messageChar[index]),
        y         = (bar.height - bar.y) / 2; // 1/2 bar height.

    boolean has = bar.contains((charWidth / 2),y);

    if (has == true)
    {
      sm1Color = Color.white;
      sm2Color = statusMessageColor;
    }
    else
    {
      sm1Color = statusMessageColor;
      sm2Color = Color.white;
    }

    sm1 = "";

    do
    {
      sm1 += String.valueOf(messageChar[index]);
      sm1Length += charWidth;
    }
    while (++index < messageChar.length &&
           (bar.contains((sm1Length + (charWidth = fontMetrics.charWidth(messageChar[index])) / 2),y) == has));

    if (index < messageChar.length)
    {
      sm2 = "";

      for (int i = index; i < messageChar.length; i++)
        sm2 += String.valueOf(messageChar[i]);
    }
  }

  /**
   * setStatusBarColor sets the color of the status bar's moving bar.
   *
   * @param c:Ljava.awt.Color object used to specify the color of the
   * moving bar.
   */
  public void setStatusBarColor(Color c)
  {
    statusBarColor = c;
  }

  /**
   * setStatusMessage set the message text that will be displayed in the
   * status bar.
   *
   * @param message:Ljava.lang.String object representing the text of the
   * message.
   */
  public void setStatusMessage(String message)
  {
    statusMessage = message;
    sm1 = message;
  }

  /**
   * setStatusMessageColor sets the color of the message text.
   *
   * @param c:Ljava.awt.Color object used to specify the color of
   * the message text.
   */
  public void setStatusMessageColor(Color c)
  {
    statusMessageColor = c;
  }

  /**
   * setStatusMessageFont sets the font of the message text.  This method
   * calls setFont.
   *
   * @param f:Ljava.awt.Font object used to specify the font of the message
   * text.
   * @see setFont
   */
  public void setStatusMessageFont(Font f)
  {
    setFont(f);
  }

  /**
   * start activates the bar.
   */
  public synchronized void start()
  {
    if (!runStatus)
    {
      runStatus = true;
      new StatusRunner().start();
    }
  }

  /**
   * stop deactivates the bar.
   */
  public void stop()
  {
    runStatus = false;
  }

  /**
   * update is the overridden version the Component.update method
   * to handle double buffering.
   *
   * @param g:Ljava.awt.Graphics object used to paint the face of the
   * status bar.
   */
  public void update(Graphics g)
  {
    paint(g);
  }

}

