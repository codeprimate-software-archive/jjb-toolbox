/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: FormCheckbox.java
 * @version v1.0
 * Date: 30 May 2001
 * Modification Date: 17 April 2002
 * @since Java 1.0
 */

package jjb.toolbox.form;

import java.awt.*;
import java.awt.event.*;
import java.util.zip.*;

public class FormCheckbox extends Checkbox implements FormComponentIF
{

  private boolean currentState = false;

  /**
   * Creates a Checkbox with no label.
   */
  public FormCheckbox() {}

  /**
   * Creates a Checkbox with the specified label.
   *
   * @param label:Ljava.lang.String is the label text for this
   * gui component.
   */
  public FormCheckbox(String label)
  {
    super(label);
  }

  /**
   * Creates a Checkbox with the specified label and sets the
   * specified state.
   *
   * @param label:Ljava.lang.String is the label text for this
   * gui component.
   * @param state is a boolean value indicating whether the checkbox
   * is initially checked or not.
   */
  public FormCheckbox(String  label,
                      boolean state )
  {
    super(label,state);
  }

  /**
   * Creates a Checkbox with the specified label, in the specified
   * CheckboxGroup, and set to the specified state.
   *
   * @param label:Ljava.lang.String is the label text for this
   * gui component.
   * @param state is a boolean value indicating whether the checkbox
   * is initially checked or not.
   * @param group:Ljava.awt.CheckboxGroup used to associated this Checkbox
   * component with other Checkboxes.
   */
  public FormCheckbox(String        label,
                      boolean       state,
                      CheckboxGroup group )
  {
    super(label,state,group);
  }

  /**
   * Constructs a Checkbox with the specified label, set to the specified state,
   * and in the specified CheckboxGroup.
   *
   * @param label:Ljava.lang.String is the label text for this
   * gui component.
   * @param group:Ljava.awt.CheckboxGroup used to associated this Checkbox
   * component with other Checkboxes.
   * @param state is a boolean value indicating whether the checkbox
   * is initially checked or not.
   */
  public FormCheckbox(String        label,
                      CheckboxGroup group,
                      boolean       state )
  {
    super(label,group,state);
  }

  /**
   * ackStateChanged is called by the form model to acknowledge that the
   * state of this component has changed.
   */
  public void ackStateChanged()
  {
    currentState = getState();
  }

  /**
   * clearData removes information from the form component.
   */
  public void clearData()
  {
    setState(currentState = false);
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
    return new Boolean(getState());
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
    return (currentState != getState());
  }
  
  /**
   * init is called to initialize this component.
   *
   * @param config:Ljava.lang.Object object used to configure this
   * form component.
   */
  public void init(Object config) throws DataFormatException {}

  /**
   * setData sets information in this component with the specified data object
   * using the form model.
   *
   * @param data:Ljava.lang.Object the information to set the data of this
   * form component.
   */
  public void setData(Object data) throws DataFormatException
  {
    if (!(data instanceof Boolean))
      throw new DataFormatException("The information to this component must be of type Boolean.");

    setState(currentState = ((Boolean) data).booleanValue());
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
    if (tf)
      currentState = !getState();
    else
      currentState = getState();
  }

}

