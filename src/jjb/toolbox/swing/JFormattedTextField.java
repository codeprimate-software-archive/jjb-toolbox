/**
 * JFormattedTextField.java (c) 2002.7.20
 *
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.2.1
 * @see javax.swing.JTextField
 * @see jjb.toolbox.swing.text.FormatSchema
 * @since Java 2
 */

package jjb.toolbox.swing;

import java.awt.Toolkit;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import jjb.toolbox.swing.text.DefaultFormatSchema;
import jjb.toolbox.swing.text.FormatSchema;
import jjb.toolbox.swing.text.InvalidTextFormatException;

public class JFormattedTextField extends JTextField {

  private static final Toolkit TOOLKIT = Toolkit.getDefaultToolkit();

  private final FormatSchema  schema;

  /**
   * Creates an instance of the JFormattedField class component with the
   * DefaultFormatSchema to validate input into this textfield component.
   */
  public JFormattedTextField() {
    this(null);
  }

  /**
   * Creates an instance of the JFormattedField class component initialized
   * with the FormatSchema object to format and validate text input to 
   * this textfield component.
   *
   * @param schema:Ljjb.toolbox.swing.text.FormatSchema object specifying 
   * the format and validation scheme used when inserting text into this
   * textfield component.
   */
  public JFormattedTextField(FormatSchema schema) {
    this.schema = (schema == null ? new DefaultFormatSchema() : schema);
  }

  public final class SchemaDocument extends PlainDocument {

    /**
     * Inserts some content into the document.
     * Inserting content causes a write lock to be held while the
     * actual changes are taking place, followed by notification
     * to the observers on the thread that grabbed the write lock.
     * <p>
     * This method is thread safe, although most Swing methods
     * are not. Please see
     * <A HREF="http://java.sun.com/products/jfc/swingdoc-archive/threads.html">Threads
     * and Swing</A> for more information.
     *
     * @param offs the starting offset >= 0
     * @param str the string to insert; does nothing with null/empty strings
     * @param a the attributes for the inserted content
     * @exception BadLocationException  the given insert position is not a valid
     *   position within the document
     * @see Document#insertString
     */
    public void insertString(int offs,
                             String str,
                             AttributeSet a)
        throws BadLocationException {
      try
      {
        super.insertString(offs,schema.format(this,offs,str),a);
      }
      catch (InvalidTextFormatException itf)
      {
        TOOLKIT.beep();
      }
    }
  }

  /**
   * Creates the default implementation of the model to be used at construction if one isn't
   * explicitly given. An instance of SchemaDocument is returned.
   *
   * @return the default Ljavax.swing.text.Document object used to house the content for
   * this formatted field component.
   */
  protected Document createDefaultModel() {
    return new SchemaDocument();
  }

}

