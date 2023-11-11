/**
 * Copyright (c) 2002, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: PieChart3D.java
 * @version v1.0
 * Date: 12 March 2002
 * Modification Date: 17 April 2002
 * @since Java 2
 */

package jjb.toolbox.awt.chart;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import javax.swing.JPanel;

public class PieChart3D extends JPanel
{

  private static final double PIE_DEPTH = 20.0;
  private static final double SHADE     = 0.75;

  private final PieChartModelIF  pieChartModel;

  public PieChart3D(PieChartModelIF model)
  {
    if (model == null)
      throw new NullPointerException("The pie chart model cannot be null.");

    pieChartModel = model;
    setBackground(Color.white);
  }

  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);

    final Graphics2D g2 = (Graphics2D) g;

    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

    int start        = 0;
    int extent       = 0;
    int angle        = 0;
    int width        = getWidth();
    int height       = width/2;

    Arc2D arc = null;

    Color c = null;

    PieSliceIF slice = null;

    Point2D startPt = null;

    for (Iterator slices = pieChartModel.getSlices(); slices.hasNext(); )
    {
      slice = (PieSliceIF) slices.next();

      if (slice.getValue() > 0.0)
      {
        extent = (int) (slices.hasNext() ? (360.0 * slice.getPercent()) : 360 - start);
        //g2.setColor(Color.black);
        //g2.draw();
        g2.setColor(c = slice.getColor());
        g2.fill(arc = new Arc2D.Double(0,0,width,height,start,extent,Arc2D.PIE));

        if ((start + extent) > 180)
        {
          if (start < 180)
          {
            angle = 180;
            startPt = new Point2D.Double(0,height/2);
          }
          else
          {
            angle = start;
            startPt = arc.getStartPoint();
          }

          GeneralPath gp = new GeneralPath();

          gp.moveTo((float) startPt.getX(),(float) startPt.getY());
          gp.lineTo((float) startPt.getX(),(float) (startPt.getY()+PIE_DEPTH));
          gp.append(arc = new Arc2D.Double(0,PIE_DEPTH,width,height,angle,(start + extent - angle),Arc2D.OPEN),true);
          startPt = arc.getEndPoint();
          gp.lineTo((float) startPt.getX(),(float) (startPt.getY()-PIE_DEPTH));
          gp.append(new Arc2D.Double(0,0,width,height,(start+extent),-(start + extent - angle),Arc2D.OPEN),true);
          gp.closePath();
          //g2.setColor(Color.black);
          //g2.draw(gp);
          g2.setColor(new Color((int) (c.getRed() * SHADE),(int) (c.getGreen() * SHADE),(int) (c.getBlue() * SHADE)));
          g2.fill(gp);
        }

        start += extent;
      }
    }
  }

}

