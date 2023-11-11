/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: FormIntegerField.java
 * @version v1.0
 * Date: 30 May 2001
 * Modification Date: 17 April 2002
 * @since Java 1.0
 */

package jjb.toolbox.form;

import java.awt.*;
import java.awt.event.*;
import java.util.zip.*;

public class FormIntegerField extends FormTextField
{

  private String currentNumber = "";

  /**
   * Creates a new IntegerField component.
   */
  public FormIntegerField()
  {
    this(null,50);
  }

  /**
   * Constructs a new, empty Integer field with the specified
   * number of columns.
   *
   * @param numColumns sets the number of visible columns in the
   * user interface for this field.
   */
  public FormIntegerField(int numColumns)
  {
    this(null,numColumns);
  }

  /**
   * Constructs a new Integer field initialized with the specified
   * integer value.
   *
   * #param number:Ljava.lang.Integer object containing the integer
   * value to set the fields contents.
   */
  public FormIntegerField(Integer number)
  {
    this(number,50);
  }

  /**
   * Constructs a new Integer field initialized with the specified
   * integer value to be displayed, and wide enough to hold the specified
   * number of columns.
   *
   * #param number:Ljava.lang.Integer object containing the integer
   * value to set the fields contents.
   * @param numColumns sets the number of visible columns in the
   * user interface for this field.
   */
  public FormIntegerField(Integer number,
                          int     numColumns)
  {
    super((number == null ? "" : number.toString()),numColumns);
  }

  /**
   * getData returns the information contained in this component that
   * was entered by the user.
   *
   * @return a Ljava.lang.Object representation of the information contained
   * in the form component.
   */
  public Object getData()  
  {
    try
    {
      return new Integer(getText().trim());
    }
    catch (NumberFormatException ignore)
    {
      return null;
    }
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
    if (!(data instanceof Integer))
      throw new DataFormatException("FormIntegerField.setData(): expecting a value of type Integer, type recieved: "+data.getClass());

    super.setData(data.toString());
  }

}

