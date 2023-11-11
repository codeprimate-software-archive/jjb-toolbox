/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: EFrame.java
 * @version v1.0
 * Date: 17 April 2001
 * Modification Date: 18 July 2002
 * @since Java 2
 */

package jjb.toolbox.swing;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GridBagConstraints;
import java.awt.Point;
import javax.swing.JFrame;
import jjb.toolbox.awt.LayoutManagerUtil;
import jjb.toolbox.awt.WindowUtil;
import jjb.toolbox.form.FormIF;
import jjb.toolbox.form.FormModelIF;

public abstract class EFrame extends JFrame implements FormIF
{

  private FormModelIF          formModel;

  protected GridBagConstraints gbc;

  /**
   * Constructs a new instance of Frame that is initially invisible.
   */
  public EFrame()
  {
  }

  /**
   * Create a Frame with the specified GraphicsConfiguration of a screen device.
   *
   * @param gc:Ljava.awt.GraphicsConfiguration of the target screen device. If gc is
   * null, the system default GraphicsConfiguration is assumed.
   */
  public EFrame(GraphicsConfiguration gc)
  {
    super(gc);
  }

  /**
   * Constructs a new, initially invisible Frame object with the specified title.
   *
   * @param title:Ljava.lang.String the title to be displayed in the frame's border.
   * A null value is treated as an empty string, "".
   */
  public EFrame(String title)
  {
    super(title);
  }

  /**
   * Constructs a new, initially invisible Frame object with the specified title
   * and a GraphicsConfiguration.
   *
   * @param title:Ljava.lang.String the title to be displayed in the frame's border.
   * A null value is treated as an empty string, "".
   * @param gc:Ljava.awt.GraphicsConfiguration of the target screen device. If gc is
   * null, the system default GraphicsConfiguration is assumed.
   */
  public EFrame(String                title,
                GraphicsConfiguration gc    )
  {
    super(title,gc);
  }

  /**
   * add adds the specified component to this CFrame container object
   * using a java.awt.GridBagLayout manager.
   *
   * @param c is a Ljava.awt.Componet object to add to this CFrame
   * container object.
   * @param x is an integer value indicating the cell at the left
   * of the component's display area, where the leftmost cell has
   * gridx=0.
   * @param y is an integer value indicating the cell at the top
   * of the component's display area, where the topmost cell has
   * gridy=0.
   */
  protected void add(Component c,
                     int       x,
                     int       y)
  {
    add(c,x,y,1,1,100,100,GridBagConstraints.NONE,GridBagConstraints.CENTER);
  }

  /**
   * add adds the specified component to this CFrame container object
   * using a java.awt.GridBagLayout manager.
   *
   * @param c is a Ljava.awt.Componet object to add to this CFrame
   * container object.
   * @param x is an integer value indicating the cell at the left
   * of the component's display area, where the leftmost cell has
   * gridx=0.
   * @param y is an integer value indicating the cell at the top
   * of the component's display area, where the topmost cell has
   * gridy=0.
   * @param fill is an integer field is used when the component's
   * display area is larger than the component's requested size.
   * @param anchor is used to determine which part of the cell the
   * component should be placed.  The field is used when the component
   * is smaller than its display area. Possible values include: CENTER,
   * NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST and
   * NORTHWEST.
   */
  protected void _add(Component c,
                      int       x,
                      int       y,
                      int       fill,
                      int       anchor)
  {
    add(c,x,y,1,1,100,100,fill,anchor);
  }

  /**
   * add adds the specified component to this CFrame container object
   * using a java.awt.GridBagLayout manager.
   *
   * @param c is a Ljava.awt.Componet object to add to this CFrame
   * container object.
   * @param x is an integer value indicating the cell at the left
   * of the component's display area, where the leftmost cell has
   * gridx=0.
   * @param y is an integer value indicating the cell at the top
   * of the component's display area, where the topmost cell has
   * gridy=0.
   * @param w is an integer value specifying the number of
   * cells in a row for the component's display area.
   * @param h is an integer value specifying the number of
   * cells in a column for the component's display area.
   */
  protected void add(Component c,
                     int       x,
                     int       y,
                     int       w,
                     int       h)
  {
    add(c,x,y,w,h,100,100,GridBagConstraints.NONE,GridBagConstraints.CENTER);
  }

  /**
   * add adds the specified component to this CFrame container object
   * using a java.awt.GridBagLayout manager.
   *
   * @param c is a Ljava.awt.Componet object to add to this CFrame
   * container object.
   * @param x is an integer value indicating the cell at the left
   * of the component's display area, where the leftmost cell has
   * gridx=0.
   * @param y is an integer value indicating the cell at the top
   * of the component's display area, where the topmost cell has
   * gridy=0.
   * @param w is an integer value specifying the number of
   * cells in a row for the component's display area.
   * @param h is an integer value specifying the number of
   * cells in a column for the component's display area.
   * @param fill is an integer field is used when the component's
   * display area is larger than the component's requested size.
   * @param anchor is used to determine which part of the cell the
   * component should be placed.  The field is used when the component
   * is smaller than its display area. Possible values include: CENTER,
   * NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST and
   * NORTHWEST.
   */
  protected void _add(Component c,
                      int       x,
                      int       y,
                      int       w,
                      int       h,
                      int       fill,
                      int       anchor)
  {
    add(c,x,y,w,h,100,100,fill,anchor);
  }

  /**
   * add adds the specified component to this CFrame container object
   * using a java.awt.GridBagLayout manager.
   *
   * @param c is a Ljava.awt.Componet object to add to this CFrame
   * container object.
   * @param x is an integer value indicating the cell at the left
   * of the component's display area, where the leftmost cell has
   * gridx=0.
   * @param y is an integer value indicating the cell at the top
   * of the component's display area, where the topmost cell has
   * gridy=0.
   * @param w is an integer value specifying the number of
   * cells in a row for the component's display area.
   * @param h is an integer value specifying the number of
   * cells in a column for the component's display area.
   * @param wx is an integer value specifying how to distribute
   * extra horizontal space.
   * @param wy is an integer value specifying how to distribute
   * extra vertical space.
   */
  protected void add(Component c,
                     int       x,
                     int       y,
                     int       w,
                     int       h,
                     int       wx,
                     int       wy)
  {
    add(c,x,y,w,h,wx,wy,GridBagConstraints.NONE,GridBagConstraints.CENTER);
  }

  /**
   * add adds the specified component to this CFrame container object
   * using a java.awt.GridBagLayout manager.
   *
   * @param c is a Ljava.awt.Componet object to add to this CFrame
   * container object.
   * @param x is an integer value indicating the cell at the left
   * of the component's display area, where the leftmost cell has
   * gridx=0.
   * @param y is an integer value indicating the cell at the top
   * of the component's display area, where the topmost cell has
   * gridy=0.
   * @param w is an integer value specifying the number of
   * cells in a row for the component's display area.
   * @param h is an integer value specifying the number of
   * cells in a column for the component's display area.
   * @param wx is an integer value specifying how to distribute
   * extra horizontal space.
   * @param wy is an integer value specifying how to distribute
   * extra vertical space.
   * @param fill is an integer field is used when the component's
   * display area is larger than the component's requested size.
   * @param anchor is used to determine which part of the cell the
   * component should be placed.  The field is used when the component
   * is smaller than its display area. Possible values include: CENTER,
   * NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST and
   * NORTHWEST.
   */
  protected void add(Component c,
                     int       x,
                     int       y,
                     int       w,
                     int       h,
                     int       wx,
                     int       wy,
                     int       fill,
                     int       anchor)
  {
    if (gbc == null)
      gbc = new GridBagConstraints();

    LayoutManagerUtil.setConstraints(gbc,x,y,w,h,wx,wy,fill,anchor);
    add(c,gbc);
  }

  /**
   * getDialogLocation centers a dialog frame about the application window
   * on the desktop screen.
   *
   * @param appSize is a Ljava.awt.Dimension object specifying the size of
   * the dialog window.
   * @return a Ljava.awt.Point location of the top left-corner of the
   * dialog box on the destkop, which will be centered about the
   * application window.
   * @deprecated should use the WindowUtil.getDialogLocation method.
   */
  public Point getDialogLocation(Dimension dialogSize)
  {
    return WindowUtil.getDialogLocation(this,dialogSize);
  }

  /**
   * getFormModel returns the form model for a component which implements
   * this interface and contains a form.
   *
   * @return a Ljjb.toolbox.form.FormModelIF object for the form of this
   * implementing class.
   */
  public FormModelIF getFormModel()
  {
    return formModel;
  }

  /**
   * setFormModel sets the form model for the class implementing this
   * interface, containing a form.
   *
   * @param fm:Ljjb.toolbox.form.FormModelIF object which acts as the
   * form model for the form contained in the implementing class.
   */
  public void setFormModel(FormModelIF fm)
  {
    formModel = fm;
  }

}

