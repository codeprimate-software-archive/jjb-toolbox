/**
 * Copyright (c) 2002, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: PieSliceIF.java
 * @version v1.0
 * Date: 12 March 2002
 * Modification Date: 17 April 2002
 * @since Java 2
 */

package jjb.toolbox.awt.chart;

import java.awt.Color;

public interface PieSliceIF
{

  public Color getColor();

  public String getLabel();

  public double getPercent();

  public double getValue();

  public void setColor(Color c);

  public void setLabel(String label);

  public void setValue(double value);

}

