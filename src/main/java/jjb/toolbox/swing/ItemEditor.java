/*
 * ItemEditor.java (c) 2002.5.12
 *
 * Copyright (c) 2002, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version v2003.3.7
 */

package jjb.toolbox.swing;

import javax.swing.JComponent;

public interface ItemEditor {

  /**
   * Returns a graphical user interface used by
   * an user to modify an object that is Editable.
   *
   * @return a javax.swing.JComponent object containing
   * the user interface.
   * @see getUI
   */
  public JComponent getGUI();

  /**
   * Returns user interface object used to by an
   * user to modidy an object that is Editable.  The
   * difference between getUI and getGUI is that this
   * method is not graphical; more like a command-line
   * interface.
   *
   * @return java.lang.Object used to modify the Editable
   * object.
   * @see getGUI
   */
  public Object getUI();

}

