/**
 * Copyright (c) 2002, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: DefaultPieChartModel.java
 * @version v1.0
 * Date: 19 March 2002
 * Modification Date: 17 April 2002
 * @since Java 2
 */

package jjb.toolbox.awt.chart;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DefaultPieChartModel implements PieChartModelIF
{

  private static final Color DEFAULT_COLOR = Color.red;

  private int         totalValue;

  private final List  pieSlices;

  /**
   * Creates an instance of the DefaultPieChartModel class to
   * store comparitive information as percentages (slice of the
   * pie) to be viewed in a Pie Chart.
   */
  public DefaultPieChartModel()
  {
    totalValue = 0;
    pieSlices = new ArrayList();
  }

  private final class DefaultPieSlice implements PieSliceIF
  {
    private double  value; 
    private Color   c;
    private String  label;

    public DefaultPieSlice(double value,
                           String label,
                           Color  c     )
    {
      this.value = value;
      this.label = label;
      this.c = c;
      totalValue += value;
    }

    public Color getColor()
    {
      return c;
    }

    public String getLabel()
    {
      return label;
    }

    public double getPercent()
    {
      return value / totalValue;
    }

    public double getValue()
    {
      return value;
    }

    public void setColor(Color c)
    {
      this.c = (c == null ? DEFAULT_COLOR : c);
    }

    public void setLabel(String label)
    {
      this.label = (label == null ? "" : label);
    }

    public void setValue(double value)
    {
      if (value < 0)
        throw new IllegalArgumentException(value+" is an invalid value for this pie slice.  The value must be greater than equal to zero.");

      this.value = value;
    }
  }

  /**
   * addSlice adds a slice to the pie maintained by this
   * model with the specified value.
   *
   * @param value is a double value indicating the numeric
   * quantity of the slice.
   * @throws Ljava.lang.IllegalArgumentException if the
   * value parameter is not greater than 0.
   */
  public void addSlice(double value)
  {
    addSlice(value,"",DEFAULT_COLOR);
  }

  /**
   * addSlice adds a slice to the pie maintained by this 
   * model with the specified value and label.
   *
   * @param value is a double value indicating the numeric
   * quantity of the slice.
   * @param label is a Ljava.lang.String object specifying
   * a name for the slice in the pie.
   * @throws Ljava.lang.IllegalArgumentException if the
   * value parameter is not greater than 0.
   */
  public void addSlice(double value,
                       String label )
  {
    addSlice(value,label,DEFAULT_COLOR);
  }

  /**
   * addSlice adds a slice to the pie maintained by this 
   * model with the specified value, label and color used
   * by the view when displaying the pie maintained by
   * this model.
   *
   * @param value is a double value indicating the numeric
   * quantity of the slice.
   * @param label is a Ljava.lang.String object specifying
   * a name for the slice in the pie.
   * @param c is a Ljava.awt.Color object specifying the
   * pie slice color when displayed in the view.s
   * @throws Ljava.lang.IllegalArgumentException if the
   * value parameter is not greater than 0.
   */
  public void addSlice(double value,
                       String label,
                       Color  c     )
  {
    if (value < 0)
      throw new IllegalArgumentException(value+" is an invalid value for this pie slice.  The value must be greater than equal to zero.");

    pieSlices.add(new DefaultPieSlice(value,(label == null ? "" : label),(c == null ? DEFAULT_COLOR : c)));
  }

  /**
   * getNumberOfSlices returns the number of slices in the
   * pie chart.
   *
   * @return an integer value specifying the number of slices
   * in the pie.
   */
  public int getNumberOfSlices()
  {
    return pieSlices.size();
  }

  /**
   * getSlice returns the pie slice in the pie chart indexed
   * by the index parameter.
   *
   * @param index is an integer value specifying the index
   * of the pie slice in the pie chart, traversing the slices
   * counter-clockwise starting at zero degrees.
   */
  public PieSliceIF getSlice(int index)
  {
    return (PieSliceIF) pieSlices.get(index);
  }

  /**
   * getSlices returns an iterator over which to traverse
   * the pie slices constituting the pie chart for this
   * model.
   *
   * @return a Ljava.util.Iterator object used to traverse
   * the pie slices in counter-clockwise order starting at
   * zero degrees.
   */
  public Iterator getSlices()
  {
    return pieSlices.iterator();
  }

  /**
   * removes the specified slice from the Pie Chart Model
   * and updates the view to reflect the change.
   *
   * @param slice is a Ljjb.toolbox.awt.chart.PieSliceIF
   * object reference of the slice maintained by this model
   * to be removed from the pie.
   * @return a boolean value indicating whether the model
   * actually contained the slice or not.
   */
  public boolean removeSlice(PieSliceIF slice)
  {
    boolean rm = pieSlices.remove(slice);

    if (rm)
      totalValue -= slice.getValue();

    return rm;
  }

}

