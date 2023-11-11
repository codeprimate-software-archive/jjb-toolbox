/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: FormComponentIF.java
 * @version v1.0
 * Date: 30 May 2001
 * Modified Date: 17 April 2002
 * @since Java 1.0
 */

package jjb.toolbox.form;

import java.awt.event.*;
import java.util.zip.*;

public interface FormComponentIF extends FocusListener
{

  /**
   * ackStateChanged is called to acknowledge that information in the
   * form component has changed, and returns it to the unflagged state.
   *
   * @see hasStateChanged
   * @see setStateChanged
   */
  public void ackStateChanged();

  /**
   * clearData clears any user entered information from this form
   * component.
   */
  public void clearData();

  /**
   * getData returns the information entered by the user in this form
   * component.
   *
   * @return a Ljava.lang.Object representation the information contained
   * in this form component.
   */
  public Object getData();

  /**
   * hasStateChanged determines whether the user has modified the information
   * in this form component.
   *
   * @return a boolean value of true to indicate that the information in the form
   * component has changed, false otherwise.
   * @see acknowledgeStateChanged
   * @see setStateChanged
   */
  public boolean hasStateChanged();

  /**
   * init initializes the form component's state, and sets any data with the
   * specified config object.
   *
   * @param config:Ljava.lang.Object value used to initialize this form
   * component.
   * @throws Ljava.util.zip.DataFormatException
   */
  public void init(Object config) throws DataFormatException;

  /**
   * isEnabled determines whether this form component's information can be
   * modified.
   *
   * @return a boolean value of true to indicate enabled, and false to indicate
   * disabled.
   */
  public boolean isEnabled();

  /**
   * setData sets information in this component with the specified data object
   * using the form model.
   *
   * @param data:Ljava.lang.Object the information to set the data of this
   * form component.
   */
  public void setData(Object data) throws DataFormatException;

  /**
   * setEnabled enables/disables this component, as determined by the tf
   * parameter.
   *
   * @param tf a boolean value indicating true to enable and false to
   * disable this form component.
   */
  public void setEnabled(boolean tf);

  /**
   * setStateChanged forces a call to hasStateChanged to return either
   * true of false depending upon the boolean parameter to this method.
   *
   * @param tf a boolean value setting the the state of the information
   * in the form component to changed (true) or unchanged (false).
   * @see hasStateChanged
   * @see acknowledgeStateChanged
   */
  public void setStateChanged(boolean tf);

}

