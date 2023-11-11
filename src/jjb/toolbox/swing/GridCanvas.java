/**
 * GridCanvas.java (c) 2002.12.12
 *
 * Copyright (c) 2002, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.2.21
 */

package jjb.toolbox.swing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import javax.swing.JComponent;

public class GridCanvas extends JComponent {

  // Size of a Grid Cell in Pixels
  private static final int GRID_CELL_SIZE = 10;
  // Size of Grid Line Width
  private static final int GRID_LINE_WIDTH = 1;
  
  private int gridCellSize = GRID_CELL_SIZE;
  private int gridLineWidth = GRID_LINE_WIDTH;
  
  private Color gridLineColor = Color.lightGray;

  public GridCanvas() {
    setBackground(Color.white);
  }
  
  public static final class GridPoint {
    private final int x;
    private final int y;
    
    private GridPoint(int x, int y) {
      this.x = x;
      this.y = y;
    }
    
    public boolean equals(Object obj) {
      if (obj == this)
        return true;
      if (!(obj instanceof GridPoint))
        return false;
       
      final GridPoint gp = (GridPoint) obj;
      
      return (gp.x == x && gp.y == y);
    }
    
    public int hashCode() {
      int result = 37;
      result = result * 17 + x;
      result = result * 17 + y;
      return result;
    }

    public String toString() {
      final StringBuffer buffer = new StringBuffer();
      buffer.append("(");
      buffer.append(x).append(",").append(y);
      buffer.append(")");
      return buffer.toString();
    }
  }

  public int getGridCellSize() {
    return gridCellSize;
  }

  public GridPoint getGridCoordinates(Point pt) {
    return new GridPoint(
      (int) pt.x / gridCellSize,(int) pt.y / gridCellSize);
  }

  public Color getGridLineColor() {
    return gridLineColor;
  }

  public int getGridLineWidth() {
    return gridLineWidth;
  }
  
  public void setGridCellSize(int size) {
    if (size < gridLineWidth) {
      throw new IllegalArgumentException("The grid cell size must be"
        +" greater than or equal to grid line width: "+gridLineWidth);
    }
    gridCellSize = size;
  }

  public void setGridLineColor(Color gridLineColor) {
    if (gridLineColor == null)
      throw new NullPointerException("null is not a valid color.");
    this.gridLineColor = gridLineColor;
  }

  public void setGridLineWidth(int width) {
    if (width < 1) {
      throw new IllegalArgumentException("The grid line width must be"
      +" greater than 1.");
    }
    gridLineWidth = width;
  }
  
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    
    Graphics2D g2 = (Graphics2D) g;

    g2.setStroke(new BasicStroke(gridLineWidth));
    g2.setColor(gridLineColor);

    final int height = (int) getHeight();
    final int width  = (int) getWidth();

    for (int index = gridCellSize, dim = Math.max(width,height);
         index < dim; index += gridCellSize) {
      // increase x, draw verticle line
      g2.draw(new Line2D.Double(index,0,index,height));
      // increase y, draw horizontal line
      g2.draw(new Line2D.Double(0,index,width,index));
    }
  }

}

