/**
 * TextMultiFormatter.java (c) 2002.4.17
 *
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.2.1
 * @see jjb.toolbox.swing.text.FormatSchema
 * @since Java 2
 */

package jjb.toolbox.swing.text;

import javax.swing.text.Document;

public class TextMultiFormatter implements FormatSchema
{

  private final FormatSchema schema1;
  private final FormatSchema schema2;

  /**
   * Creates an instance of the TextMultiFormatter class to chain
   * FormatSchema objects together in order to perform independent
   * mutations and validations of text input to JFormattedField
   * components.
   *
   * @parma schema1:Ljjb.toolbox.swing.text.FormatSchema1 object
   * text formatter/validater.
   * @parma schema2:Ljjb.toolbox.swing.text.FormatSchema1 object
   * text formatter/validater.
   */
  public TextMultiFormatter(FormatSchema schema1,
                            FormatSchema schema2 ) {
    this.schema1 = schema1;
    this.schema2 = schema2;
  }

  /**
   * add associates the newSchema FormatSchema object to the schema
   * FormatSchema object providing neither is null.
   *
   * @parma schema:Ljjb.toolbox.swing.text.FormatSchema1 object
   * text formatter/validater.
   * @parma newSchema:Ljjb.toolbox.swing.text.FormatSchema1 object
   * text formatter/validater to link with schema.
   */
  public static FormatSchema add(FormatSchema schema,
                                 FormatSchema newSchema) {
    if (schema == null)
      return newSchema;
    if (newSchema == null)
      return schema;

    return new TextMultiFormatter(schema,newSchema);
  }

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
    return schema2.format(doc,offset,schema1.format(doc,offset,text));
  }

  /**
   * remove removes the specified FormatSchema object, oldSchema, from the chain
   * of ForamtSchemaIF objects.
   *
   * @parma oldSchema:Ljjb.toolbox.swing.text.FormatSchema1 object
   * text formatter/validater.
   */
  private FormatSchema remove(FormatSchema oldSchema) {
    if (oldSchema == schema1)
      return schema2;
    if (oldSchema == schema2)
      return schema1;

    FormatSchema a2 = remove(schema1,oldSchema);
    FormatSchema b2 = remove(schema2,oldSchema);

    if (a2 == schema1 && b2 == schema2)
      return this; // The filter is not here.

    return add(a2,b2);
  }

  /*
   * remove removes the specified FormatSchema object, oldSchema, from the chain
   * of ForamtSchemaIF objects referenced by schema.
   *
   * @parma schema:Ljjb.toolbox.swing.text.FormatSchema1 object
   * text formatter/validater pointing to the foramt chain.
   * @parma oldSchema:Ljjb.toolbox.swing.text.FormatSchema1 object
   * text formatter/validater to dereference from schema.
   */
  public static FormatSchema remove(FormatSchema schema,
                                    FormatSchema oldSchema) {
    if (schema == oldSchema || schema == null)
      return null;

    if (schema instanceof TextMultiFormatter)
      return ((TextMultiFormatter) schema).remove(oldSchema);

    return schema; // The schema is not here.
  }

}

