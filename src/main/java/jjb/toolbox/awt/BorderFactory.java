/**
 * BorderFactory.java (c) 2002.2.23
 *
 * Copyright (c) 2002, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.2.21
 */

package jjb.toolbox.awt;

import java.awt.Component;
import java.awt.Panel;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public final class BorderFactory {

  private static final GridBagConstraints gbc = new GridBagConstraints();

  public static final Insets EMPTY_BORDER_INSETS5 = new Insets(5,5,5,5);

  /**
   * Default private constructor preventing instances or subclasses
   * of the BorderFactory class from being created, enforcing
   * non-instantiability.
   */
  private BorderFactory() {
  }

  /**
   * createEmptyBorder creates an empty border around the outside of
   * the component parameter.  This implementation uses the default insets,
   * emptyBorderInsets, as defined by the GUIInterface interface.
   *
   * @param comp is a java.awt.Component which to create an empty border
   * around.
   * @return a java.awt.Panel containing the Component parameter in a
   * padded border panel.
   */
  public static final Panel createEmptyBorder(Component comp) {
    return createEmptyBorder(comp,EMPTY_BORDER_INSETS5);
  }

  /**
   * createEmptyBorder creates an empty border around the outside of
   * the component parameter.  This implementation uses the default insets,
   * emptyBorderInsets, as defined by the GUIInterface interface.
   *
   * @param comp is a java.awt.Component which to create an empty border
   * around.
   * @param insets is a java.awt.Insets object specifying the padding
   * around the top, left, bottom and right edges, constituting the
   * border of the component.
   * @return is a java.awt.Panel containing the Component parameter in a
   * padded border panel.
   */
  public static final Panel createEmptyBorder(Component comp,
                                              Insets padding ) {
    final Panel borderPanel = new Panel(new GridBagLayout());

    gbc.insets = padding;
    LayoutManagerUtil.setConstraints(gbc,0,0,1,1,100,100,
      GridBagConstraints.BOTH,GridBagConstraints.CENTER);
    borderPanel.add(comp,gbc);

    return borderPanel;
  }

}

