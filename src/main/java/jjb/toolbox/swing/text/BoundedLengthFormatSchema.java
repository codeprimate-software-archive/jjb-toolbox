/**
 * BoundedLengthFormatSchema.java (c) 2002.4.17
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

public class BoundedLengthFormatSchema extends AbstractFormatSchema {

  private final int maxLength;

  /**
   * Creates an instance of the BoundedLengthFormatSchema class to 
   * restrict the length of the text in the JFormattedField to the 
   * specified maxLength.
   *
   * @param maxLength is a integer value specifying the maximum text
   * length that can be inserted into the JFormattedField component.
   */
  public BoundedLengthFormatSchema(int maxLength) {
    this.maxLength = maxLength;
  }

  /**
   * format verifies the length of the text to be inserted into the
   * JFormattedField plus the fields current text length does not
   * exceed the max length.
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
    if ((text.length() + doc.getLength()) > maxLength)
      throw new InvalidTextFormatException("The length of the text to be inserted plus the current text length exceeds the maximum length of "+maxLength);
    return text;
  }

}

