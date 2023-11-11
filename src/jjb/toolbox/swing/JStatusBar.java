/**
 * Copyright (c) 2002, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: JStatusBar.java
 * @version v1.0
 * Date: 15 July 2002
 * Modification Date: 15 July 2002
 * @since Java 2
 */

package jjb.toolbox.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.JComponent;

public class JStatusBar extends JComponent
{

  private static final int LEAD = 5;

  private boolean        paintBar;
  private boolean        run;
  private boolean        running;

  private double         barX;
  private double         barWidth;

  private int            baseline;
  private int            offset;

  private BufferedImage  buffer;

  private Color          barColor;
  private Color          backgroundColor;
  private Color          bc1;
  private Color          bc2;
  private Color          tc1;
  private Color          tc2;

  private Dimension      canvasSize;
  private Dimension      componentSize;

  private Font           theFont;

  private FontMetrics    fm;

  private Graphics2D     g2;

  private String         message;
  private String         msg1;
  private String         msg2;

  /**
   * Creates an instance of the JStatusBar class to track
   * the progress of an operation.
   */
  public JStatusBar()
  {
    paintBar = false;
    run = false;
    running = false;
    barColor = new Color(56,73,101);
    tc1 = Color.black;
    canvasSize = new Dimension(0,0);
    theFont = null;
    setBorder(BorderFactory.createLoweredBevelBorder());
  }

  private final class WorkerThread extends Thread
  {
    public void run()
    {
      boolean moveRight = true;

      double bufferWidth = buffer.getWidth();
      double increment   = 1;

      paintBar = true;
      barWidth = Math.min(bufferWidth * 0.50,100.0);
      barX = -barWidth;
      bc1 = backgroundColor;
      bc2 = barColor;

      while (run)
      {
        barX += increment;

        if (barX >= bufferWidth && moveRight)
        {
          barX = bufferWidth;
          increment = -increment;
          bc1 = barColor;
          bc2 = backgroundColor;
          moveRight = false;
        }
        else if (barX <= -barWidth && !moveRight)
        {
          barX = -barWidth;
          increment = -increment;
          bc1 = backgroundColor;
          bc2 = barColor;
          moveRight = true;
        }

        determineMessageColor();
        repaint();

        try
        {
          Thread.sleep(10);
        }
        catch (InterruptedException ignore)
        {
        }
      }

      msg1 = message;
      msg2 = "";
      tc1 = Color.black;
      paintBar = false;
      repaint();

      synchronized (JStatusBar.this)
      {
        running = false;
      }
    }
  }

  public synchronized void begin()
  {
    if (!running)
    {
      running = true;
      run = true;
      new WorkerThread().start();
    }
  }

  private final void determineMessageColor()
  {
    final char[] messageChar = message.toCharArray();

    if (messageChar.length == 0)
      return;

    int index     = 0;
    int charWidth = fm.charWidth(messageChar[index]);
    int temp      = 0;

    boolean has = barX <= (temp = (LEAD + charWidth / 2)) &&  temp <= (barX + barWidth);

    tc1 = (has ? Color.white : Color.black);
    tc2 = (tc1.equals(Color.white) ? Color.black : Color.white);

    offset = 0;

    do
    {
      offset += charWidth;
    }
    while (++index < messageChar.length &&
           (barX <= (temp = (LEAD + offset + (charWidth = fm.charWidth(messageChar[index])) / 2)) &&
            temp <= (barX + barWidth)) == has);

    msg1 = String.valueOf(messageChar,0,index);
    msg2 = String.valueOf(messageChar,index,messageChar.length-index);
  }

  public void end()
  {
    run = false;
  }

  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);

    if (!canvasSize.equals(componentSize = getSize()))
    {
      buffer = new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_ARGB);
      g2 = buffer.createGraphics();
      g2.clip(g.getClip());
      g2.setBackground(backgroundColor = ((Graphics2D) g).getBackground());
      canvasSize = componentSize;
    }

    if (fm == null || !g.getFont().equals(theFont))
    {
      fm = g.getFontMetrics();
      baseline = buffer.getHeight() - fm.getMaxDescent() - 2;
      theFont = g.getFont();
    }

    g2.setPaint(backgroundColor);
    g2.fill(new Rectangle2D.Double(0.0,0.0,buffer.getWidth(),buffer.getHeight()));

    if (paintBar)
    {
      g2.setPaint(new GradientPaint((float) (barX),0.0f,bc1,(float) (barX+barWidth),0.0f,bc2));
      g2.fill(new Rectangle2D.Double(barX,0.0,barWidth,buffer.getHeight()));
    }

    if (!msg1.equals(""))
    {
      g2.setPaint(tc1);
      g2.drawString(msg1,LEAD,baseline);
    }

    if (!msg2.equals(""))
    {
      g2.setPaint(tc2);
      g2.drawString(msg2,LEAD+offset,baseline);
    }

    g.drawImage(buffer,0,0,null);
  }

  public void setColor(Color barColor)
  {
    if (barColor == null)
      this.barColor = new Color(56,73,101);
    else
      this.barColor = barColor;
  }

  public void setMessage(String message)
  {
    if (message == null)
      message = "";
    else
      this.message = message;

    msg1 = message.trim();
    msg2 = "";

    if (isVisible())
      repaint();
  }

}

