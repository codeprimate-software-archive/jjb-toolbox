/**
 * Copyright (c) 2002, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: NetscapeStatusBar.java
 * @version v1.0
 * Date: 13 July 2002
 * Modification Date: 15 July 2002
 * @since Java 2
 */

package jjb.toolbox.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.JComponent;

public class NetscapeStatusBar extends JComponent
{

  private boolean        paintBar;
  private boolean        run;
  private boolean        running;

  private double         barX;
  private double         barWidth;

  private BufferedImage  buffer;

  private Color          barColor;
  private Color          backgroundColor;
  private Color          c1;
  private Color          c2;

  private Dimension      canvasSize;
  private Dimension      componentSize;

  private Graphics2D     g2;

  /**
   * Creates an instance of the NetscapeStatusBar class to
   * track the progress of an operation.
   */
  public NetscapeStatusBar()
  {
    paintBar = false;
    run = false;
    running = false;
    barColor = new Color(56,73,101);
    canvasSize = new Dimension(0,0);
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
      c1 = backgroundColor;
      c2 = barColor;

      while (run)
      {
        barX += increment;

        if ((barX + barWidth) >= bufferWidth && moveRight)
        {
          barX = bufferWidth;
          increment = -increment;
          c1 = barColor;
          c2 = backgroundColor;
          moveRight = false;
        }
        else if (barX <= 0 && !moveRight)
        {
          barX = -barWidth;
          increment = -increment;
          c1 = backgroundColor;
          c2 = barColor;
          moveRight = true;
        }

        repaint();

        try
        {
          Thread.sleep(10);
        }
        catch (InterruptedException ignore)
        {
        }
      }

      paintBar = false;
      repaint();

      synchronized (NetscapeStatusBar.this)
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

    g2.setPaint(backgroundColor);
    g2.fill(new Rectangle2D.Double(0.0,0.0,buffer.getWidth(),buffer.getHeight()));

    if (paintBar)
    {
      g2.setPaint(new GradientPaint((float) (barX),0.0f,c1,(float) (barX+barWidth),0.0f,c2));
      g2.fill(new Rectangle2D.Double(barX,0.0,barWidth,buffer.getHeight()));
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

}

