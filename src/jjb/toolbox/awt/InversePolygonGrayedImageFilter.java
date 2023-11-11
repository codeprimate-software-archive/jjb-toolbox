/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: InversePolygonGrayedImageFilter.java
 * @version v1.0
 * Date: 26 August 2001
 * Modifcation Date: 17 April 2002
 * @since Java 1.0
 */

package jjb.toolbox.awt;

import java.awt.Polygon;
import java.awt.image.RGBImageFilter;

public class InversePolygonGrayedImageFilter extends RGBImageFilter
{

  private boolean  fillColored = true;

  private Polygon  boundary;

  /**
   * Constructs an instance of the InversePolygonGrayedImageFilter class
   * to gray out either within, or outside of a polygonial region.
   *
   * @param boundary:Ljava.awt.Polygon the region with which color should
   * be applied or grayed.
   */
  public InversePolygonGrayedImageFilter(Polygon boundary)
  {
    this.boundary = boundary;
  }

  /**
   * Constructs an instance of the InversePolygonGrayedImageFilter class
   * to gray out either within, or outside of a polygonial region.
   *
   * @param boundary:Ljava.awt.Polygon the region with which color should
   * be applied or grayed.
   * @param fillColored is a boolean value indicating true for the area
   * inside the polygon to be colored, false otherwise.
   */
  public InversePolygonGrayedImageFilter(Polygon boundary,
                                         boolean fillColored)
  {
    this.fillColored = true;
    this.boundary = boundary;
  }

  /**
   * filterRGB method converts color pixels to grayscale if and only if
   * the point, defined by x y, are not contained within the bourdary
   * polygon. The algorithm matches the NTSC specification.
   *
   * @param x is the x coordinate in the image space of the pixel
   * position.
   * @param y is the y coordinate in the image space of the pixel
   * position.
   * @param pixel is the current pixel color as a 4 byte integer
   * value.
   * @return an integer 4 byte value for the new color of the
   * pixel at x,y.
   */
  public int filterRGB(int x,
                       int y,
                       int pixel)
  {
    if (boundary.contains(x,y) && fillColored)
      return pixel;

    // Get the average RGB intensity.
    int red   = (pixel & 0x00ff0000) >> 16;
    int green = (pixel & 0x0000ff00) >> 8;
    int blue  = pixel & 0x000000ff;
    int luma  = (int) (0.199 * red + 0.387 * green + 0.014 * blue);

    // Return the luma value as the value for each RGB component.
    // Note the Alpha (transparency) is always set to max (not transparent).
    return (0xff << 24) | (luma << 16) | (luma << 8) | luma;
  }

}

