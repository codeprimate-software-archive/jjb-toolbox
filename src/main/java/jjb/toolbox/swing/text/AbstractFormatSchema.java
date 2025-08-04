/**
 * AbstractFormatSchema.java (c) 2002.4.17
 *
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.2.1
 * @since Java 2
 */

package jjb.toolbox.swing.text;

import javax.swing.text.Document;

public abstract class AbstractFormatSchema implements FormatSchema {

  /**
   * Used by subclasses to create an instance of the AbstractFormatSchema
   * class to call common functionality when implementing schemas to 
   * format and validate text inputed into JFormattedField components.
   *
   * @param schema:Ljjb.toolbox.swing.text.FormatSchemaIF object which is
   * part of the schema chain associated with this schema object for
   * formatting and validing text input to JFormattedField components.
   */
  public AbstractFormatSchema() {
  }

}

