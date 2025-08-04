/**
 * Copyright (c) 2002, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: DualStatusBar.java
 * @version v1.0
 * Date: 15 July 2002
 * Modification Date: 15 July 2002
 * @since Java 2
 */

package jjb.toolbox.swing;

import java.awt.Shape;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.JComponent;

public class DualStatusBar extends JComponent
{

  private boolean        paintBar;
  private boolean        run;
  private boolean        running;

  private double         barLeftX;
  private double         barRightX;
  private double         barWidth;
  private double         offset1;
  private double         offset2;

  private BufferedImage  buffer;

  private Color          backgroundColor;
  private Color          barColor;
  private Color          c1;
  private Color          c2;

  private Dimension      canvasSize;
  private Dimension      componentSize;

  private Graphics2D     g2;

  /**
   * Creates an instance of the DualStatusBar to track
   * the progress of an operation.
   */
  public DualStatusBar()
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
      barWidth = Math.min(bufferWidth * 0.35,100.0);
      barLeftX = -barWidth;
      barRightX = bufferWidth;
      c1 = backgroundColor;
      c2 = barColor;

      while (run)
      {
        barLeftX += increment;
        barRightX -= increment;

        if (barLeftX >= (bufferWidth/2) && moveRight)
        {
          barLeftX = bufferWidth/2;
          barRightX = bufferWidth/2 - barWidth;
          increment = -increment;
          c1 = barColor;
          c2 = backgroundColor;
          moveRight = false;
        }
        else if (barLeftX <= -barWidth && !moveRight)
        {
          barLeftX = -barWidth;
          barRightX = bufferWidth;
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

      synchronized (DualStatusBar.this)
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
      final double bufferWidth  = buffer.getWidth();
      final double bufferHeight = buffer.getHeight();

      final Shape originalClip = g2.getClip();

      g2.clip(new Rectangle2D.Double(0.0,0.0,bufferWidth/2,bufferHeight));
      g2.setPaint(new GradientPaint((float) barLeftX,0.0f,c1,(float) (barLeftX+barWidth),0.0f,c2));
      g2.fill(new Rectangle2D.Double(barLeftX,0.0,barWidth,bufferHeight));
      g2.setClip(originalClip);
      g2.clip(new Rectangle2D.Double(bufferWidth/2,0.0,bufferWidth/2,bufferHeight));
      g2.setPaint(new GradientPaint((float) barRightX,0.0f,c2,(float) (barRightX+barWidth),0.0f,c1));
      g2.fill(new Rectangle2D.Double(barRightX,0.0,barWidth,bufferHeight));
      g2.setClip(originalClip);
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

