/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: CButton.java
 * @version v1.0
 * Date: 14 March 2001
 * Modification Date: 17 April 2002
 * @since Java 1.0
 */

package jjb.toolbox.awt;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

public class CButton extends Button
{

  private double           iconWidth,
                           iconHeight;

  private final Dimension  buttonSize;

  private Image            icon;

  private final Point      topLeft;

  /**
   * Creates an instance of the CButton UI component.
   */
  public CButton()
  {
    iconWidth = 0;
    iconHeight = 0;
    buttonSize = new Dimension(0,0);
    icon = null;
    topLeft = new Point(0,0);
  }

  /**
   * Creates an instance of the CButton UI component with the given
   * label.
   *
   * @param label:Ljava.lang.String containing the text used to the label
   * this button component.
   */
  public CButton(String label)
  {
    super(label);

    iconWidth = 0;
    iconHeight = 0;
    buttonSize = new Dimension(0,0);
    icon = null;
    topLeft = new Point(0,0);
  }

  /**
   * Creates an instance of the CButton UI component with the given
   * image icon.
   *
   * @param icon:Ljava.awt.Image object representing the icon to draw
   * on the face of this button component.
   */
  public CButton(Image icon)
  {
    iconWidth = 0;
    iconHeight = 0;
    buttonSize = new Dimension(0,0);
    this.icon = icon;
    topLeft = new Point(0,0);
  }

  /**
   * calcIconSize scales the size of the icon on the button face if the
   * icon's dimensions are larger than the surface area of the button.
   */
  private void calcIconSize()
  {
    final Dimension buttonSize = getSize();

    double buttonWidth  = buttonSize.width - 8;
    double buttonHeight = buttonSize.height - 8;

    iconWidth  = icon.getWidth(this);
    iconHeight = icon.getHeight(this);

    if (iconWidth > buttonWidth || iconHeight > buttonHeight)
    {
      if ((iconWidth / buttonWidth) > (iconHeight / buttonHeight))
      {
        iconHeight = buttonWidth * (iconHeight / iconWidth);
        iconWidth = buttonWidth;
      }
      else
      {
        iconWidth = buttonHeight * (iconWidth / iconHeight);
        iconHeight = buttonHeight;
      }
    }

    topLeft.x = (int) ((buttonSize.width / 2) - (iconWidth / 2));
    topLeft.y = (int) ((buttonSize.height / 2) - (iconHeight / 2));

    //System.out.println("Top Left Point: "+topLeft);
    //System.out.println("Icon Width: "+iconWidth+" Icon Height: "+iconHeight);
  }

  /**
   * getIcon returns the image painted on the face of this button component.
   *
   * @return a Ljava.awt.Image representation of the button's icon.
   */
  public Image getIcon()
  {
    return icon;
  }

  /**
   * setIcon sets the image for the icon displayed on the button component's
   * face.
   *
   * @param icon:Ljava.awt.Image representation of the icon display on this
   * button component.
   */
  public void setIcon(Image icon)
  {
    this.icon = icon;
  }

  /**
   * paint method called by the Graphics Context to paint the button UI
   * on screen.
   *
   * @param g:Ljava.awt.Graphics object used to paint the face of the
   * button component.
   */
  public void paint(Graphics g)
  {
    super.paint(g);

    if (icon != null && !buttonSize.equals(getSize()))
    {
      calcIconSize();
      g.drawImage(icon,topLeft.x,topLeft.y,(int) iconWidth,(int) iconHeight,null);
    }
  }

}

