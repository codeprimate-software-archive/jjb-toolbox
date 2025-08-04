/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * This class (component) extends the javax.swing.JButton component in the
 * Swing library (JFC, javax.swing.*) to implement a borderless button.
 * Written @ Education Logistics, Inc.
 *
 * @author John J. Blum
 * File: EButton.java
 * @version v1.0
 * Date: 17 April 2001
 * Modification Date: 14 October 2002
 * @since Java 2
 */

package jjb.toolbox.swing;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;

public class EButton extends JButton
{

  /**
   * Default constructor.  Creates a button with no set text
   * or icon.
   */
  public EButton()
  {
    init();
  }

  /**
   * Creates a button where properties are taken from the
   * Action supplied.
   *
   * @param a is a Ljavax.swing.Action object defining the
   * behavior, or action, of this button when clicked.
   */
  public EButton(Action a)
  {
    super(a);
    init();
  }

  /**
   * Creates a button with an icon.
   *
   * @param icon is a Ljava.awt.Icon image for the button.
   */
  public EButton(Icon icon)
  {
    super(icon);
    init();
  }

  /**
   * Creates a button with text.
   *
   * @param text is a Ljava.lang.String of text displayed
   * on the button.
   */
  public EButton(String text)
  {
    super(text);
    init();
  }

  /**
   * Creates a button with initial text and an icon.
   *
   * @param icon is a Ljava.awt.Icon image for the button.
   * @param text is a Ljava.lang.String of text displayed
   * on the button.
   */
  public EButton(String text,
                 Icon   icon )
  {
    super(text,icon);
    init();
  }

  private final class FocusHandler extends FocusAdapter
  {
    /**
     * focusLost method captures the FocusEvent when the button
     * loses focus.  The method sets the borderPainted property
     * of the button to false.
     *
     * @param fe is a Ljava.awt.event.FocusEvent object capturing
     * the focus lost event in order to set the button's border
     * to empty.
     */
    public void focusLost(FocusEvent fe)
    {
      if (isEnabled())
        setBorderPainted(false);
    }
  }

  private final class MouseHandler extends MouseAdapter
  {
    /**
     * If enabled, paint the button's border.
     *
     * @param me is the mouse event.
     */
    public void mouseEntered(MouseEvent me)
    {
      if (isEnabled())
        setBorderPainted(true);
    }

    /**
     * If enabled, do not paint the border when
     * the button is painted.
     *
     * @param me is the mouse event.
     */
    public void mouseExited(MouseEvent me)
    {
      if (isEnabled())
        setBorderPainted(false);
    }

    /**
     * If enabled, set the button's border to
     * lowered bevel border.
     *
     * @param me is the mouse event.
     */
    public void mousePressed(MouseEvent me)
    {
      if (isEnabled())
        setBorder(BorderFactory.createLoweredBevelBorder());
    }

    /**
     * If enabled, sets the button's border to
     * raised bevel border.
     *
     * @param me is the mouse event.
     */
    public void mouseReleased(MouseEvent me)
    {
      if (isEnabled())
        setBorder(BorderFactory.createRaisedBevelBorder());
    }
  }

  /**
   * init initializes this button with event handlers and
   * it's initial border.
   */
  private void init()
  {
    addFocusListener(new FocusHandler());
    addMouseListener(new MouseHandler());
    setBorder(BorderFactory.createRaisedBevelBorder());
    setBorderPainted(false);
  }

  /**
   * setEnabled is called to enable or disable this button
   * component.
   *
   * @param enable is a boolean value of indicating true
   * for enabled, false otherwise.
   */
  public void setEnabled(boolean enable)
  {
    setBorderPainted(false);
    super.setEnabled(enable);
  }

}

