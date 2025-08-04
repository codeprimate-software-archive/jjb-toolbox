/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * Modeled after the Ljava.awt.FlowLayout manager class in the Java 1.0 API.
 * Note, that most of the routines are implemented by software engineers at
 * Sun Microsystems with only slight modifications.
 * 
 * Also, this layout could be accomplished if the Ljava.awt.ComponentOrientation
 * class could be extended with an anonymous class such as the following: 
 * 
 *  ComponentOrientation co = new ComponentOrientation()
 *  {
 *    public boolean isHorizontal() { return false; }
 *    public boolean isLeftToRight() { return true; }
 *  };
 *
 * Note, that this ComponentOrientation specifies the components in a container
 * are to be layed out Top To Bottom, Left To Right.  Then, by calling the containers
 * setComponentOrientation method and passing the new ComponentOrientation object to
 * the method as follows:
 *
 *  setComponentOrientation(co);
 *
 * components will have a Top To Botton, Left To Right orientation within their
 * respective containers.
 *
 * In Java 2, this layout could be accomplished using a Box Layout, or a JToolbar with
 * vertical orientation.
 *
 * @author John J. Blum
 * File: VerticalFlowLayout.java
 * @version v1.0
 * Date: 22 April 2001
 * Modification Date: 17 April 2002
 * @since Java 1.0
 */

package jjb.toolbox.awt;

import java.awt.*;
import java.io.*;

public class VerticalFlowLayout implements LayoutManager, Serializable
{

  public static final int     TOP      = 0,
                              BOTTOM   = 1,
                              CENTER   = 2,
                              LEADING	 = 3,
                              TRAILING = 4;

  private int                 align,
                              newAlign,
                              hgap,
                              vgap;

  /**
   * Constructs a new VerticalFlowLayout with a centered alignment
   * and a default 5-unit horizontal and vertical gap.
   */
  public VerticalFlowLayout()
  {
    this(CENTER,5,5);
  }

  /**
   * Constructs a new VerticalFlowLayout with the specified alignment
   * and a default 5-unit horizontal and vertical gap.
   *
   * @param align the specified alignment of the components in this container,
   * (LEFT, RIGHT, CENTER).
   */
  public VerticalFlowLayout(int align)
  {
    this(align,5,5);
  }

  /**
   * Creates a new VerticalFlowLayout manager with the indicated alignment
   * and the indicated horizontal and vertical gaps.
   *
   * @param align the specified alignment of the components in this container,
   * (LEFT, RIGHT, CENTER).
   * @param horizontalGap the amount of space to the left and right of each
   * component.
   * @param verticalGap the amount of space to the top and bottom of each
   * component. 
   */
  public VerticalFlowLayout(int align,
                            int horizontalGap,
                            int verticalGap   )
  {
    hgap = horizontalGap;
    vgap = verticalGap;
    setAlignment(align);
  }

  /**
   * Adds the specified component to the layout. Not used by this class.
   *
   * @param name:Ljava.lang.String the name of the component.
   * @param comp:Ljava.awt.Component the component to be added.
   */
  public void addLayoutComponent(String    name,
                                 Component comp)
  {
  }

  /**
   * Gets the alignment for this layout manager.
   * Possible values are <code>VerticalFlowLayout.LEFT</code>,
   * <code>VerticalFlowLayout.RIGHT</code>, or <code>VerticalFlowLayout.CENTER</code>.
   *
   * @return the alignment value for this layout.
   * @see Ljjb.toolbox.awt.VerticalFlowLayout#setAlignment
   */
  public int getAlignment()
  {
    return align;
  }

  /**
   * getHgap returns the horizontal (to the left and right) gap between components.
   *
   * @return the horizontal gap between components.
   * @see Ljjb.toolbox.awt.VerticalFlowLayout#setHgap
   */
  public int getHgap()
  {
    return hgap;
  }

  /**
   * getVgap returns the vertical gap between components.
   *
   * @return the vertical (the top and bottom) gap between components.
   * @see Ljjb.toolbox.awt.VerticalFlowLayout#setVgap
   */
  public int getVgap()
  {
    return vgap;
  }

  /**
   * layoutContainer lays out the container. This method lets each
   * component take its preferred size by reshaping the components
   * in the target container in order to satisfy the constraints of
   * this <code>VerticalFlowLayout</code> object.
   *
   * @param target:Ljava.awt.Container the specified container being laid out.
   * @see Ljava.awt.Container
   * @see Ljava.awt.Container#doLayout
   */
  public void layoutContainer(Container target)
  {
    synchronized(target.getTreeLock())
    {
      boolean ltr = target.getComponentOrientation().isLeftToRight();

      Insets insets = target.getInsets();

      int nComponents = target.getComponentCount(),
          maxHeight   = target.getSize().height - (insets.top + insets.bottom + vgap * 2),
          x           = insets.left + hgap,
          y           = 0,
          rowWidth    = 0,
          start       = 0;

      Component component = null;

      Dimension componentDim = null;

      for (int index = 0; index < nComponents; index++)
      {
        component = target.getComponent(index);

        if (component.isVisible())
        {
          componentDim = component.getPreferredSize();
          component.setSize(componentDim.width,componentDim.height);

          if ((y + componentDim.height) <= maxHeight)
          {
            if (y > 0)
              y += vgap;

            y += componentDim.height;
            rowWidth = Math.max(rowWidth,componentDim.width);
          }
          else
          {
            moveComponents(target,            // container
                           x,                 // x
                           insets.top + vgap, // y
                           rowWidth,          // width
                           maxHeight - y,     // height
                           start,             // compStart
                           index,             // compEnd
                           ltr);              // ltr (component orientation)
            y = componentDim.height;
            x += rowWidth + hgap;
            rowWidth = componentDim.width;
            start = index;
          }
        }
      }

      moveComponents(target,x,(insets.top + vgap),rowWidth,(maxHeight - y),start,nComponents,ltr);
    }
  }

  /**
   * minimumLayoutSize returns the minimum dimensions needed to layout the
   * components contained in the specified target container.
   *
   * @param target:Ljava.awt.Container the container which needs to be laid out.
   * @return a Ljava.awt.Dimension object of the minimum dimensions to lay out
   * the subcomponents of the specified container.
   * @see Ljjb.toolbox.awt.VerticalFlowLayout#preferredLayoutSize
   * @see Ljava.awt.Container
   * @see Ljava.awt.Container#doLayout
   */
  public Dimension minimumLayoutSize(Container target)
  {
    synchronized (target.getTreeLock())
    {
      Dimension containerDim = new Dimension(0,0);

      int nComponents = target.getComponentCount();

      for (int i = nComponents; --i >= 0; )
      {
        Component component = target.getComponent(i);

        if (component.isVisible())
        {
          Dimension componentDim = component.getMinimumSize();

          containerDim.width = Math.max(containerDim.width,componentDim.width);

          if (i > 0)
            containerDim.height += vgap;

          containerDim.height += componentDim.height;
        }
      }

      Insets insets = target.getInsets();

      containerDim.width += insets.left + insets.right + hgap * 2;
      containerDim.height += insets.top + insets.bottom + vgap * 2;

      return containerDim;
    }
  }

  /**
   * moveComponents centers the elements in the specified row, if there
   * is any slack.
   * 
   * @param target:Ljava.awt.Container the component which needs to be
   * moved
   * @param x the x coordinate
   * @param y the y coordinate
   * @param width the width dimensions
   * @param height the height dimensions
   * @param rowStart the beginning of the row
   * @param rowEnd the the ending of the row
   * @param ltr is left to right if true.
   */
  private void moveComponents(Container target,
                              int       x,
                              int       y,
                              int       width,
                              int       height,
                              int       compStart,
                              int       compEnd,
                              boolean   ltr       )
  {
    synchronized (target.getTreeLock())
    {
      switch (newAlign)
      {
      case TOP:
        y += ltr ? 0 : height;
        break;
      case CENTER:
        y += height / 2;
        break;
      case BOTTOM:
        y += ltr ? height : 0;
      case LEADING:
        break;
      case TRAILING:
        y += height;
        break;
      }

      Component component = null;

      Dimension targetSize    = target.getSize(),
                componentSize = null;

      for (int index = compStart; index < compEnd; index++)
      {
        component = target.getComponent(index);
        componentSize = component.getSize();

        if (component.isVisible())
        {
          if (ltr)
            component.setLocation(x + (width - componentSize.width) / 2,y);
          else
            component.setLocation(x + (width - componentSize.width) / 2,targetSize.height - y - componentSize.height);

          y += componentSize.height * vgap;
        }
      }
    }
  }

  /**
   * preferredLayoutSize returns the preferred dimensions for this layout
   * given the components in the specified target container.
   *
   * @param target:Ljava.awt.Container the container which needs to be laid
   * out.
   * @return a Ljava.awt.Dimension of the preferred dimensions to lay out
   * the subcomponents of the
   * specified container.   
   * @see Ljava.awt.Container
   * @see Ljjb.toolbox.awt.VerticalFlowLayout#minimumLayoutSize
   * @see Ljava.awt.Container#getPreferredSize
   */
  public Dimension preferredLayoutSize(Container target)
  {
    synchronized (target.getTreeLock())
    {
      Dimension containerDim = new Dimension(0,0);

      int nComponents = target.getComponentCount();

      for (int i = nComponents ; --i >= 0; )
      {
        Component component = target.getComponent(i);

        if (component.isVisible())
        {
          Dimension componentDim = component.getPreferredSize();

          containerDim.width = Math.max(containerDim.width,componentDim.width);

          if (i > 0)
            containerDim.height += vgap;

          containerDim.height += componentDim.height;
        }
      }

      Insets insets = target.getInsets();

      containerDim.width += insets.left + insets.right + hgap * 2;
      containerDim.height += insets.top + insets.bottom + vgap * 2;

      return containerDim;
    }
  }

  /**
   * Removes the specified component from the layout. Not used by
   * this class.
   *
   * @param comp:Ljava.awt.Component the component to remove.
   * @see Ljava.awt.Container#removeAll
   */
  public void removeLayoutComponent(Component comp)
  {
  }

  /**
   * Sets the alignment for this layout manager.
   * Possible values are <code>VerticalFlowLayout.LEFT</code>,
   * <code>VerticalFlowLayout.RIGHT</code>, and <code>VerticalFlowLayout.CENTER</code>.
   *
   * @param align the alignment value.
   * @see Ljjb.toolbox.awt.VerticalFlowLayout#getAlignment()
   */
  public void setAlignment(int align)
  {
    this.newAlign = align;

    // this.align is used only for serialization compatibility,
    // so set it to a value compatible with the 1.1 version
    // of the class

    switch (align)
    {
    case LEADING:
      this.align = TOP;
      break;
    case TRAILING:
      this.align = BOTTOM;
      break;
    default:
      this.align = align;
      break;
    }
  }

  /**
   * setHgap sets the horizontal gap between components.
   * 
   * @param horizontalGap the horizontal gap between components.
   * @see Ljjb.toolbox.awt.VerticalFlowLayout#getHgap
   */
  public void setHgap(int horizontalGap)
  {
    hgap = horizontalGap;
  }

  /**
   * setVgap sets the vertical gap between components.
   *
   * @param verticalGap the vertical gap between components
   * @see Ljjb.toolbox.awt.VerticalFlowLayout#getVgap
   */
  public void setVgap(int verticalGap)
  {
    this.vgap = verticalGap;
  }

  /**
   * toString returns a string representation of this <code>VerticalFlowLayout</code>
   * object and its properties.
   *
   * @return a Ljava.lang.String representation of this layout manager.
   */
  public String toString()
  {
    String str = null;

    switch (align)
    {
    case TOP:
      str = ",align=left";
      break;
    case CENTER:
      str = ",align=center";
      break;
    case BOTTOM:
      str = ",align=right";
      break;
    case LEADING:
      str = ",align=leading";
      break;
    case TRAILING:
      str = ",align=trailing";
      break;
    }

    return getClass().getName() + "[hgap=" + hgap + ",vgap=" + vgap + str + "]";
  }

}

