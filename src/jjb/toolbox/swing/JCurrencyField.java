/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: JCurrencyField.java
 * @version v1.0
 * Date: 5 December 2001
 * Modification Date: 17 April 2002
 * @since Java 2
 */

package jjb.toolbox.swing;

import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

public class JCurrencyField extends JTextField
{

  protected static final Toolkit TOOLKIT = Toolkit.getDefaultToolkit();

  /**
   * Creates a new instance of the JCurrencyField class component to represent
   * currency in US dollars.
   */
  public JCurrencyField()
  {
    this("$");
  }

  /**
   * Creates a new instance of the JCurrencyField class component to represent
   * currency in US dollars initialized to the default monetary value.
   *
   * @param currency:Ljava.lang.String representation of the monetary value
   * to set the currency field component to.
   */
  public JCurrencyField(String currency)
  {
    super(8);
    setText(currency);
    getCaret().setDot(1);
    addKeyListener(new CurrencyKeyListener());
  }

  /**
   * The CurrencyDocument class is used by the JCurrencyField component to format the 
   * JTextField component as a currency input field.
   */
  private final class CurrencyDocument extends PlainDocument
  {

    private boolean containsDecimal = false;

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
    public void insertString(int          offs,
                             String       str,
                             AttributeSet a    ) throws BadLocationException
    {
      if (!isValidCurrency(offs,str))
        TOOLKIT.beep();
      else
      {
        super.insertString(offs,str,a);
        containsDecimal = (this.getText(0,getLength()).indexOf(".") > -1);
      }
    }

    /**
     * isValidCurrency determines whether the String object being inserted into
     * this currency field with the current currency value will maintain a valid
     * monetary value.
     *
     * @param offs is an integer offset into the document.
     * @param str:Ljava.lang.String value to insert into the document providing
     * validation criteria are met.
     * @throws Ljavax.swing.text.BadLocationException if the insert position is
     * not a valid position within the document.
     */
    private boolean isValidCurrency(int    offs,
                                    String str  ) throws BadLocationException
    {
      boolean valid = true;

      int index = -1;

      // Check Number of Digits After Decimal Point
      if (containsDecimal && (index = this.getText(0,getLength()).indexOf(".")) < offs)
      {
        if ((this.getText(index+1,(getLength() - (index+1))).length() + str.length()) > 2)
          return false;
      }

      // Decimal Point Check
      if (((index = str.indexOf(".")) > -1) && containsDecimal)
        return false;
      else
      {
        int lastIndex = str.lastIndexOf(".");

        if (lastIndex != index)
          return false;

        if (index > -1 && str.substring(index+1).length() > 2)
          return false;
      }

      // Check str Contents
      for (int indx = 0, len = str.length(); indx < len; indx++)
      {
        char c = str.charAt(indx);

        switch (indx+offs)
        {
        case 0:
          valid &= (c == '$' && (this.getText(0,getLength()).indexOf("$") == -1));
          break;
        case 1:
          boolean negated = this.getText(1,1).equals("-");

          valid &= ((c == '-' && !negated) || (c == '.') || (Character.isDigit(c) && !negated));
          break;
        default:
          valid &= (Character.isDigit(c) || (c == '.'));
        }

        if (!valid)
          return false;
      }

      return valid;
    }

    /**
     * Removes some content from the document.
     * Removing content causes a write lock to be held while the
     * actual changes are taking place.  Observers are notified
     * of the change on the thread that called this method.
     * <p>
     * This method is thread safe, although most Swing methods
     * are not. Please see
     * <A HREF="http://java.sun.com/products/jfc/swingdoc-archive/threads.html">Threads
     * and Swing</A> for more information.
     *
     * @param offs the starting offset >= 0
     * @param len the number of characters to remove >= 0
     * @exception BadLocationException  the given remove position is not a valid
     *   position within the document
     * @see Document#remove
     */
    public void remove(int offs,
                       int len  ) throws BadLocationException
    {
      if (offs == 0)
        return;

      super.remove(offs,len);
      containsDecimal = (this.getText(0,getLength()).indexOf(".") > -1);
    }

  } // End of Class

  /**
   * The CurrencyKeyListener is used by the JCurrencyField component to
   * track key events targeted at editing and traversing the currency field
   * component.
   */
  public final class CurrencyKeyListener extends KeyAdapter
  {

    private final Caret caret = getCaret();

    /**
     * Invoked when a key has been pressed.
     */
    public void keyPressed(KeyEvent e)
    {
      switch (e.getKeyCode())
      {
      case KeyEvent.VK_HOME:
        caret.setDot(1);
        e.consume();
        break;
      case KeyEvent.VK_LEFT:
        if (caret.getDot() == 1)
          e.consume();
      }
    }

  } // End of Class

  /**
   * Creates the default implementation of the model to be used at construction if one isn't
   * explicitly given. An instance of CurrencyDocument is returned.
   *
   * @return the default Ljavax.swing.text.Document object used to house the content for
   * this currency field component.
   */
  protected Document createDefaultModel()
  {
    return new CurrencyDocument();
  }

  /**
   * getCurrency returns a BigDecimal representation of the monetary value
   * contained in this currency field component.
   *
   * @return a Ljava.math.BigDecimal object containing the currency value
   * represented by this currency field component.
   */
  public BigDecimal getCurrency()
  {
    return new BigDecimal(getText().substring(1));
  }

  /**
   * The overridden setText method sets the contents of this text field component
   * to the specified currency value.
   *
   * @param text:Ljava.lang.String value specifying the currency value to populate
   * this text field component.
   */
  public void setText(String text)
  {
    if (!text.trim().startsWith("$"))
      text = "$" + text.trim();

    super.setText(text);
  }

}

