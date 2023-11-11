/**
 * DigitsOnlyFormatSchema.java (c) 2002.4.17
 *
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.2.1
 * @see jjb.toolbox.swing.text.FormatSchema
 * @see jjb.toolbox.swing.text.AbstractFormatSchema
 * @since Java 2
 */

package jjb.toolbox.swing.text;

import javax.swing.text.Document;
import jjb.toolbox.util.StringUtil;

public class DigitsOnlyFormatSchema extends AbstractFormatSchema {

  /**
   * format verifies the input text to be inserted into the JTextField
   * component has a valid format and performs any other mutations on the
   * text.
   *
   * @param doc:Ljavax.swing.text.Document object model of the JTextField
   * using this schema, containing the content of the field.
   * @param offset is an integer value specifying the offset into the
   * document.
   * @param text:Ljava.lang.String object containing the text to format and
   * valid before inserting into the JTextField component.
   * @throws Ljjb.toolbox.swing.text.IllegalTextFormatException if the text
   * format is not valid input to the textfield component.
   */
  public String format(Document doc,
                       int offset,
                       String text  )
      throws InvalidTextFormatException {
    if (!StringUtil.isDigitsOnly(text))
      throw new InvalidTextFormatException("The text must only consist of digits 0 through 9.");
    return text;
  }

}

