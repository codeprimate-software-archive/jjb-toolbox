/**
 * Copyright (c) 2002, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: PieChartModelIF.java
 * @version v1.0
 * Date: 12 March 2002
 * Modification Date: 17 April 2002
 * @since Java 2
 */

package jjb.toolbox.awt.chart;

import java.awt.Color;
import java.util.Iterator;

public interface PieChartModelIF
{

  public void addSlice(double value);

  public void addSlice(double value,
                       String label );

  public void addSlice(double value,
                       String label,
                       Color  c     );

  public int getNumberOfSlices();

  public PieSliceIF getSlice(int index);

  public Iterator getSlices();

  public boolean removeSlice(PieSliceIF slice);

}

