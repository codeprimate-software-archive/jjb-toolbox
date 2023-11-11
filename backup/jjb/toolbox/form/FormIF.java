/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: FormIF.java
 * @version v1.0
 * Date: 30 May 2001
 * Modification Date: 17 April 2002
 * @since Java 1.0
 */

package jjb.toolbox.form;

public interface FormIF
{

  /**
   * getFormModel returns the form model for a component which implements
   * this interface and contains a form.
   *
   * @return a Ljjb.toolbox.form.FormModelIF object for the form of this
   * implementing class.
   */
  public FormModelIF getFormModel();

  /**
   * setFormModel sets the form model for the class implementing this
   * interface, containing a form.
   *
   * @param fm:Ljjb.toolbox.form.FormModelIF object which acts as the
   * form model for the form contained in the implementing class.
   */
  public void setFormModel(FormModelIF fm);

}

