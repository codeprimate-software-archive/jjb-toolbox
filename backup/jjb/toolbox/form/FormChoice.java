/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: FormChoice.java
 * @version v1.0
 * Date: 30 May 2001
 * Modification Date: 17 April 2002
 * @since Java 1.0
 */

package jjb.toolbox.form;

import java.awt.*;
import java.awt.event.*;
import java.util.zip.*;

public class FormChoice extends Choice implements FormComponentIF
{

  private boolean   stateChanged  = false;

  private Dimension size          = new Dimension(150,21);

  private String    currentText   = null;

  /**
   * Creates a new choice menu.
   */
  public FormChoice()
  {
    this(null);
  }

  /**
   * Creates a new FormChoice menu component with the specified physical
   * dimension in pixels.
   *
   * @param dim:Ljava.awt.Dimension object to represent the width and height
   * of this component in pixels in the user interface.
   */
  public FormChoice(Dimension dim)
  {
    setSize((size = (dim == null ? size : dim)));

    addItemListener(new ItemListener()
    {
      public void itemStateChanged(ItemEvent ie)
      {
        if (ie.getStateChange() == ItemEvent.SELECTED)
          stateChanged = true;
      }
    });
  }

  /**
   * ackStateChanged is called by the form model to acknowledge that the
   * state of this component has changed.
   */
  public void ackStateChanged()
  {
    stateChanged = false;
  }

  /**
   * clearData removes information from the form component.
   */
  public void clearData()
  {
    select(0);
    stateChanged = false;
  }

  /**
   * focusGained is called when this component receives focus in the user
   * interface.
   *
   * @param fe:Ljava.awt.event.FocusEvent object encapsulating information
   * about the focus gained event.
   */
  public void focusGained(FocusEvent fe) {}

  /**
   * focusLost is called when this component loses focus in the user interface.
   * This method is useful when performing user input validation.
   *
   * @param fe:Ljava.awt.event.FocusEvent object encapsulating information
   * about the focus lost event.
   */
  public void focusLost(FocusEvent fe) {}

  /**
   * getData returns the information contained in this component that
   * was entered by the user.
   *
   * @return a Ljava.lang.Object representation of the information contained
   * in the form component.
   */
  public Object getData()
  {
    return getSelectedItem();
  }

  /**
   * getMaximumSize returns the maximum size of the component allowed
   * for painting this widget on screen.
   *
   * @return a Ljava.awt.Dimension object representing the maximum size
   * of this component in the user interface.
   */
  public Dimension getMaximumSize()
  {
    return size;
  }

  /**
   * getMinimumSize returns the minimum size of the component allowed
   * for painting this widget on screen.
   *
   * @return a Ljava.awt.Dimension object representing the minimum size
   * of this component in the user interface.
   */
  public Dimension getMinimumSize()
  {
    return size;
  }

  /**
   * getPreferredSize returns the preferred size for which this component
   * would be most visually appealing.
   *
   * @return a Ljava.awt.Dimension object representing the preferred size of
   * this component when it is painted on screen.
   */
  public Dimension getPreferredSize()
  {
    return size;
  }

  /**
   * hasStateChanged determines whether the user has modified the information
   * contained in this form component.
   *
   * @return a boolean value indicating that whether state of this form
   * component has changed.
   */
  public boolean hasStateChanged()
  {
    String text = getSelectedItem();

    return (stateChanged || (!currentText.equals(text)));
  }

  /**
   * init is called to initialize this component.
   *
   * @param config:Ljava.lang.Object object used to configure this
   * form component.
   */
  public void init(Object config) throws DataFormatException
  {
    if (config == null)
      removeAll();
    else if (config instanceof String[])
    {
      removeAll();

      String[] items = (String[]) config;

      currentText = items[0];

      for (int index = 0; index < items.length; index++)
        addItem(items[index]);
    }
    else
      throw new DataFormatException(config.getClass().getName()+" is an invalid data format for the FormChoice component.");
  }

  /**
   * setData sets information in this component with the specified data object
   * using the form model.
   *
   * @param data:Ljava.lang.Object the information to set the data of this
   * form component.
   */
  public void setData(Object data) throws DataFormatException
  {
    if (data instanceof String)
      select((currentText = data.toString()));
    else
      throw new DataFormatException("The data items for FormChoice must be of type String."); 

    stateChanged = false;
  }

  /**
   * setStateChanged sets the state of this form component to the specified
   * boolean parameter.
   *
   * @param tf is a boolean value indicating the state for which this form
   * component should be set.
   */
  public void setStateChanged(boolean tf)
  {
    stateChanged = tf;
  }

}

