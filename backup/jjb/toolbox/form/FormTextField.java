/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: FormTextField.java
 * @version v1.0
 * Date: 30 May 2001
 * Modification Date: 17 April 2002
 * @since Java 1.0
 */

package jjb.toolbox.form;

import java.awt.*;
import java.awt.event.*;
import java.util.zip.*;

public class FormTextField extends TextField implements FormComponentIF
{

  private boolean stateChanged  = false;

  private String  currentText   = "";

  /**
   * Constructs a new text field.
   */
  public FormTextField()
  {
    this(null,50);
  }

  /**
   * Constructs a new empty text field with the specified
   * number of columns.
   *
   * @param numColumns specifies the number of visible columns
   * displayed.
   */
  public FormTextField(int numColumns)
  {
    this(null,numColumns);
  }

  /**
   * Constructs a new text field initialized with the specified
   * text.
   *
   * @param text:Ljava.lang.String object used to set the content
   * of this text field component.
   */
  public FormTextField(String text)
  {
    this(text,50);
  }

  /**
   * Constructs a new text field initialized with the specified
   * text to be displayed, and wide enough to hold the specified
   * number of columns.
   *
   * @param text:Ljava.lang.String object used to set the content
   * of this text field component.
   * @param numColumns specifies the number of visible columns
   * displayed.
   */
  public FormTextField(String  text,
                       int     numColumns)
  {
    super(text,numColumns);
  }

  /**
   * ackStateChanged is called by the form model to acknowledge that the
   * state of this component has changed.
   */
  public void ackStateChanged()
  {
    currentText = getText();
    stateChanged = false;
  }

  /**
   * clearData removes information from the form component.
   */
  public void clearData()
  {
    setText("");
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
    return getText();
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
    return (stateChanged || !currentText.equals(getText()));
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
    setText((String) data);
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

  /**
   * This overridden setText method sets the state of this object and
   * flags the information as unchanged.  setText then calls it's parent's
   * setText method for default behavior.
   *
   * @param text:Ljava.lang.String object used to set the content
   * of this text field component.
   */
  public void setText(String text)
  {
    currentText = text;

    super.setText(text);
  }

}

