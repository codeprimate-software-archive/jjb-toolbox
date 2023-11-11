/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: FormFrame.java
 * @version v1.0
 * Date: 30 May 2001
 * Modification Date: 17 April 2002
 * @since Java 1.0
 */

package jjb.toolbox.form;

import java.awt.*;
import jjb.toolbox.awt.CFrame;

public abstract class FormFrame extends CFrame implements FormIF
{

  protected FormModelIF formModel;

  /**
   * Constructs a new instance of CFrame that is initially invisible.
   */
  public FormFrame() { super(); }

  /**
   * Create a Frame with the specified GraphicsConfiguration of a screen device.
   *
   * @param gc:Ljava.awt.GraphicsConfiguration
   */
  public FormFrame(GraphicsConfiguration gc) { super(gc); }

  /**
   * Constructs a new, initially invisible Frame object with the specified title.
   * 
   * @param title:Ljava.lang.String title of this frame.
   */
  public FormFrame(String title) { super(title); }

  /**
   * Constructs a new, initially invisible Frame object with the specified title
   * and a GraphicsConfiguration.
   *
   * @param title:Ljava.lang.String title of this frame.
   * @param gc:Ljava.awt.GraphicsConfiguration
   */
  public FormFrame(String                title,
                   GraphicsConfiguration gc    )
  {
    super(title,gc);
  }

  /**
   * getDataModel is declared by the FormIF and must be implemented by this class
   * to return the DataModel for this class, which is a Frame containing a form.
   *
   * @return com.ing.na.cdi.elf.FormModelIF the data model associated with
   * the form contained within this frame.
   */
  public FormModelIF getFormModel()
  {
    return formModel;
  }

  /**
   * setDataModel is declared by the FormIF and must be implemented by this class
   * to allow a caller of this method to set the DataModel for this class in which the
   * form will use to store it's information.
   *
   * @param fm:Ljjb.toolbox.form.FormModelIF the data model specified by
   * the user of this object to be used for this form to manage data.
   */
  public void setFormModel(FormModelIF fm)
  {
    formModel = fm;
  }

  /**
   * constructNewFormChoice method creates a new FormChoice component with the
   * specified sizes.  Note that this technique is necessary to work with component
   * sizing in the AWT.  In the AWT, certain layout managers mixed with particular
   * panels causes the components setSize method to get ignored, even though the
   * layout manager's layout policies may state otherwise.  AWT certainly is
   * not as robust as the SWING architecture.  Another form of proof that windows
   * sucks!!!
   *
   * @param formChoiceSize:Ljava.awt.Dimension the preferred, minimum and maximum
   * size of the form component.
   * @return a Ljjb.toolbox.form.FormChoice component.
   */
  protected FormChoice constructNewFormChoice(final Dimension formChoiceSize)
  {
    return new FormChoice()
    {
      public Dimension getMaximumSize()   { return formChoiceSize; }
      public Dimension getPreferredSize() { return formChoiceSize; }
      public Dimension getMinimumSize()   { return formChoiceSize; }
    };
  }

  /**
   * constructNewFormTextArea creates a new FormTextArea component with the initial
   * text, number of rows and cols, and scrollbar policy.
   *
   * @param text:Ljava.lang.String the initial contents of the FormTextArea component.
   * @param rows the number of visible text rows.
   * @param cols the number of visible cols (characters).
   * @param scrollbars the scrollbar policy for this FormTextArea component.
   * @return a Ljjb.toolbox.form.FormTextArea component.
   */
  protected FormTextArea constructNewFormTextArea(String  text,
                                                  int     rows,
                                                  int     cols,
                                                  int     scrollbars)
  {
    return new FormTextArea(text,rows,cols,scrollbars);
  }

  /**
   * constructNewFormTextField method creates a new FormTextField component with the
   * specified size, initial text and number of columns visible in the user interface.
   *
   * @param formTextFieldSize:Ljava.awt.Dimension the preferred, maximum, and minimum
   * size that this component should be.
   * @return a Ljjb.toolbox.form.FormTextField component.
   */
  protected FormTextField constructNewFormTextField(final Dimension  formTextFieldSize,
                                                    String           text,
                                                    int              numColumns        )
  {
    return new FormTextField(text,numColumns)
    {
      public Dimension getMaximumSize()   { return formTextFieldSize; }
      public Dimension getPreferredSize() { return formTextFieldSize; }
      public Dimension getMinimumSize()   { return formTextFieldSize; }
    };
  }

}

