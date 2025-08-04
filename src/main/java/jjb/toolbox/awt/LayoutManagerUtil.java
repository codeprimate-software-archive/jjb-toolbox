/**
 * Copyright (c) 2002, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: LayoutManagerUtil.java
 * @version v1.0
 * Date: 23 February 2002
 * Modification Date: 2 June 2002
 * @since Java 1.0
 */

package jjb.toolbox.awt;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class LayoutManagerUtil
{

  /**
   * Default private constructor preventing instances or subclasses
   * of the BorderFactory class from being created, supporting
   * non-instantiability.
   */
  private LayoutManagerUtil()
  {
  }

  /**
   * getVerticalFlowLayout returns a VerticalFlowLayout manager with
   * center alignment and 5 pixel gaps on the vertical & horizontal
   * spacing between components.
   *
   * @return an instance of the jjb.toolbox.awt.VerticalFlowLayout
   * manager.
   */
  public static final VerticalFlowLayout getVerticalFlowLayout()
  {
    return new VerticalFlowLayout();
  }

  /**
   * getVerticalFlowLayout returns a VerticalFlowLayoutManager with
   * the specified alignment and 5 pixel gaps on the vertical &
   * horizontal spacing between components.
   *
   * @param align is an integer value specifying the alignment of
   * components within their defined space (CENTER, LEFT, RIGHT).
   * @return an instance of the jjb.toolbox.awt.VerticalFlowLayout
   * manager.
   */
  public static final VerticalFlowLayout getVerticalFlowLayout(int align)
  {
    return new VerticalFlowLayout(align);
  }

  /**
   * getVerticalFlowLayout returns a VerticalFlowLayoutManager with
   * the specified alignment, horizontalGap, and VerticalGap betweeen
   * components in the panel with the VerticalFlowLayout.
   *
   * @param align is an integer value specifying the alignment of
   * components within their defined space (CENTER, LEFT, RIGHT).
   * @param horizontalGap is an int value specifying the number of
   * pixels between components horizontally.
   * @param verticalGap is an int value specifying the number of
   * pixels between components vertically.
   * @return an instance of the jjb.toolbox.awt.VerticalFlowLayout
   * manager.
   */
  public static final VerticalFlowLayout getVerticalFlowLayout(int align,
                                                               int horizontalGap,
                                                               int verticalGap   )
  {
    return new VerticalFlowLayout(align,horizontalGap,verticalGap);
  }

  /**
   * setConstraints are used to set the properties of the GridBagConstraints
   * object used in determining the layout of components in a container object.
   *
   * @param gbc is a Ljvaa.awt.GridBagConstraints variable containing layout
   * constraints.
   * @param gridx is an integer value indicating the cell at the left
   * of the component's display area, where the leftmost cell has
   * gridx=0.
   * @param gridy is an integer value indicating the cell at the top
   * of the component's display area, where the topmost cell has
   * gridy=0.
   */
  public static final void setConstraints(GridBagConstraints gbc,
                                          int                gridx,
                                          int                gridy)
  {
    setConstraints(gbc,gridx,gridy,1,1,100,100,GridBagConstraints.NONE,GridBagConstraints.CENTER,gbc.insets,0,0);
  }

  /**
   * setConstraints are used to set the properties of the GridBagConstraints
   * object used in determining the layout of components in a container object.
   *
   * @param gbc is a Ljvaa.awt.GridBagConstraints variable containing layout
   * constraints.
   * @param gridx is an integer value indicating the cell at the left
   * of the component's display area, where the leftmost cell has
   * gridx=0.
   * @param gridy is an integer value indicating the cell at the top
   * of the component's display area, where the topmost cell has
   * gridy=0.
   * @param fill is an integer field is used when the component's display
   * area is larger than the component's requested size.
   * @param anchor is used to determine which part of the cell the
   * component should be placed.  The field is used when the component
   * is smaller than its display area. Possible values include: CENTER,
   * NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST and
   * NORTHWEST.
   */
  public static final void _setConstraints(GridBagConstraints gbc,
                                           int                gridx,
                                           int                gridy,
                                           int                fill,
                                           int                anchor)
  {
    setConstraints(gbc,gridx,gridy,1,1,100,100,fill,anchor,gbc.insets,0,0);
  }

  /**
   * setConstraints are used to set the properties of the GridBagConstraints
   * object used in determining the layout of components in a container object.
   *
   * @param gbc is a Ljvaa.awt.GridBagConstraints variable containing layout
   * constraints.
   * @param gridx is an integer value indicating the cell at the left
   * of the component's display area, where the leftmost cell has
   * gridx=0.
   * @param gridy is an integer value indicating the cell at the top
   * of the component's display area, where the topmost cell has
   * gridy=0.
   * @param gridWidth is an integer value specifying the number of
   * cells in a row for the component's display area.
   * @param gridHeight is an integer value specifying the number of
   * cells in a column for the component's display area.
   */
  public static final void setConstraints(GridBagConstraints gbc,
                                          int                gridx,
                                          int                gridy,
                                          int                gridWidth,
                                          int                gridHeight)
  {
    setConstraints(gbc,gridx,gridy,gridWidth,gridHeight,100,100,GridBagConstraints.NONE,GridBagConstraints.CENTER,gbc.insets,0,0);
  }

  /**
   * setConstraints are used to set the properties of the GridBagConstraints
   * object used in determining the layout of components in a container object.
   *
   * @param gbc is a Ljvaa.awt.GridBagConstraints variable containing layout
   * constraints.
   * @param gridx is an integer value indicating the cell at the left
   * of the component's display area, where the leftmost cell has
   * gridx=0.
   * @param gridy is an integer value indicating the cell at the top
   * of the component's display area, where the topmost cell has
   * gridy=0.
   * @param gridWidth is an integer value specifying the number of
   * cells in a row for the component's display area.
   * @param gridHeight is an integer value specifying the number of
   * cells in a column for the component's display area.
   * @param fill is an integer field is used when the component's display
   * area is larger than the component's requested size.
   * @param anchor is used to determine which part of the cell the
   * component should be placed.  The field is used when the component
   * is smaller than its display area. Possible values include: CENTER,
   * NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST and
   * NORTHWEST.
   */
  public static final void _setConstraints(GridBagConstraints gbc,
                                           int                gridx,
                                           int                gridy,
                                           int                gridWidth,
                                           int                gridHeight,
                                           int                fill,
                                           int                anchor     )
  {
    setConstraints(gbc,gridx,gridy,gridWidth,gridHeight,100,100,fill,anchor,gbc.insets,0,0);
  }

  /**
   * setConstraints are used to set the properties of the GridBagConstraints
   * object used in determining the layout of components in a container object.
   *
   * @param gbc is a Ljvaa.awt.GridBagConstraints variable containing layout
   * constraints.
   * @param gridx is an integer value indicating the cell at the left
   * of the component's display area, where the leftmost cell has
   * gridx=0.
   * @param gridy is an integer value indicating the cell at the top
   * of the component's display area, where the topmost cell has
   * gridy=0.
   * @param gridWidth is an integer value specifying the number of
   * cells in a row for the component's display area.
   * @param gridHeight is an integer value specifying the number of
   * cells in a column for the component's display area.
   * @param weightX is an integer value specifying how to distribute
   * extra horizontal space.
   * @param weightY is an integer value specifying how to distribute
   * extra vertical space.
   */
  public static final void setConstraints(GridBagConstraints gbc,
                                          int                gridx,
                                          int                gridy,
                                          int                gridWidth,
                                          int                gridHeight,
                                          int                weightX,
                                          int                weightY    )
  {
    setConstraints(gbc,gridx,gridy,gridWidth,gridHeight,weightX,weightY,GridBagConstraints.NONE,GridBagConstraints.CENTER,gbc.insets,0,0);
  }

  /**
   * setConstraints are used to set the properties of the GridBagConstraints
   * object used in determining the layout of components in a container object.
   *
   * @param gbc is a Ljvaa.awt.GridBagConstraints variable containing layout
   * constraints.
   * @param gridx is an integer value indicating the cell at the left
   * of the component's display area, where the leftmost cell has
   * gridx=0.
   * @param gridy is an integer value indicating the cell at the top
   * of the component's display area, where the topmost cell has
   * gridy=0.
   * @param gridWidth is an integer value specifying the number of
   * cells in a row for the component's display area.
   * @param gridHeight is an integer value specifying the number of
   * cells in a column for the component's display area.
   * @param weightX is an integer value specifying how to distribute
   * extra horizontal space.
   * @param weightY is an integer value specifying how to distribute
   * extra vertical space.
   * @param fill is an integer field is used when the component's display
   * area is larger than the component's requested size.
   * @param anchor is used to determine which part of the cell the
   * component should be placed.  The field is used when the component
   * is smaller than its display area. Possible values include: CENTER,
   * NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST and
   * NORTHWEST.
   */
  public static final void setConstraints(GridBagConstraints gbc,
                                          int                gridx,
                                          int                gridy,
                                          int                gridWidth,
                                          int                gridHeight,
                                          int                weightX,
                                          int                weightY,
                                          int                fill,
                                          int                anchor     )
  {
    setConstraints(gbc,gridx,gridy,gridWidth,gridHeight,weightX,weightY,fill,anchor,gbc.insets,0,0);
  }

  /**
   * setConstraints are used to set the properties of the GridBagConstraints
   * object used in determining the layout of components in a container object.
   *
   * @param gbc is a Ljvaa.awt.GridBagConstraints variable containing layout
   * constraints.
   * @param gridx is an integer value indicating the cell at the left
   * of the component's display area, where the leftmost cell has
   * gridx=0.
   * @param gridy is an integer value indicating the cell at the top
   * of the component's display area, where the topmost cell has
   * gridy=0.
   * @param gridWidth is an integer value specifying the number of
   * cells in a row for the component's display area.
   * @param gridHeight is an integer value specifying the number of
   * cells in a column for the component's display area.
   * @param weightX is an integer value specifying how to distribute
   * extra horizontal space.
   * @param weightY is an integer value specifying how to distribute
   * extra vertical space.
   * @param fill is an integer field is used when the component's display
   * area is larger than the component's requested size.
   * @param anchor is used to determine which part of the cell the
   * component should be placed.  The field is used when the component
   * is smaller than its display area. Possible values include: CENTER,
   * NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST and
   * NORTHWEST.
   * @param insets is a Ljava.awt.Insets object field specifiing the
   * external padding of the component, the minimum amount of space
   * between the component and the edges of its display area.
   */
  public static final void setConstraints(GridBagConstraints gbc,       
                                          int                gridx,     
                                          int                gridy,     
                                          int                gridWidth, 
                                          int                gridHeight,
                                          int                weightX,   
                                          int                weightY,   
                                          int                fill,      
                                          int                anchor,
                                          Insets             insets     )
  {
    setConstraints(gbc,gridx,gridy,gridWidth,gridHeight,weightX,weightY,fill,anchor,insets,0,0);
  }

  /**
   * setConstraints are used to set the properties of the GridBagConstraints
   * object used in determining the layout of components in a container object.
   *
   * @param gbc is a Ljvaa.awt.GridBagConstraints variable containing layout
   * constraints.
   * @param gridx is an integer value indicating the cell at the left
   * of the component's display area, where the leftmost cell has
   * gridx=0.
   * @param gridy is an integer value indicating the cell at the top
   * of the component's display area, where the topmost cell has
   * gridy=0.
   * @param gridWidth is an integer value specifying the number of
   * cells in a row for the component's display area.
   * @param gridHeight is an integer value specifying the number of
   * cells in a column for the component's display area.
   * @param weightX is an integer value specifying how to distribute
   * extra horizontal space.
   * @param weightY is an integer value specifying how to distribute
   * extra vertical space.
   * @param fill is an integer field is used when the component's display
   * area is larger than the component's requested size.
   * @param anchor is used to determine which part of the cell the
   * component should be placed.  The field is used when the component
   * is smaller than its display area. Possible values include: CENTER,
   * NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST and
   * NORTHWEST.
   * @param insets is a Ljava.awt.Insets object field specifiing the
   * external padding of the component, the minimum amount of space
   * between the component and the edges of its display area.
   * @param ipadx is an integer value field specifying the internal
   * padding of the component, how much space to add to the minimum
   * width of the component.
   * @param ipady is an integer value This field specifying the
   * internal padding, that is, how much space to add to the minimum
   * height of the component.
   */
  public static final void setConstraints(GridBagConstraints gbc,       
                                          int                gridx,     
                                          int                gridy,     
                                          int                gridWidth, 
                                          int                gridHeight,
                                          int                weightX,   
                                          int                weightY,   
                                          int                fill,      
                                          int                anchor,
                                          Insets             insets,
                                          int                ipadx,
                                          int                ipady      )
  {
    gbc.gridx       = gridx;
    gbc.gridy       = gridy;
    gbc.gridwidth   = gridWidth;
    gbc.gridheight  = gridHeight;
    gbc.weightx     = weightX;
    gbc.weighty     = weightY;
    gbc.fill        = fill;
    gbc.anchor      = anchor;
    gbc.insets      = insets;
    gbc.ipadx       = ipadx;
    gbc.ipady       = ipady;
  }

}

