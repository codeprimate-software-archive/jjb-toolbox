/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: JPercentField.java
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

public class JPercentField extends JTextField
{

  protected static final Toolkit TOOLKIT = Toolkit.getDefaultToolkit();

  /**
   * Creates an instance of the JPercentField class to represent percentages
   * in a text field of a form in an applet/application to allow user
   * modifications.
   */
  public JPercentField()
  {
    this("%");
  }

  /**
   * Creates an instance of the JPercentField class to represent percentages
   * in a text field of a form in an applet/application to allow user
   * modifications, initialized to the default percentage
   *
   * @param percentage:Ljava.lang.String representation of the default percentage
   * to initialize this percent field component.
   */
  public JPercentField(String percentage)
  {
    super(8);
    setText(percentage);
    addKeyListener(new PercentKeyListener());
  }

  /**
   * The PercentDocument class is used by the JPercentageField component to format
   * the JTextField component as a percentage input field.
   */
  private final class PercentDocument extends PlainDocument
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
      if (!isValidPercent(offs,str))
        TOOLKIT.beep();
      else
      {
        super.insertString(offs,str,a);
        containsDecimal = (this.getText(0,getLength()).indexOf(".") > -1);
      }
    }

    /**
     * isValidPercent determines whether the String object being inserted into
     * this percent field with the current percent value will maintain a valid
     * percentage.
     *
     * @param offs is an integer offset into the document.
     * @param str:Ljava.lang.String value to insert into the document providing
     * validation criteria are met.
     * @throws Ljavax.swing.text.BadLocationException if the insert position is
     * not a valid position within the document.
     */
    private boolean isValidPercent(int    offs,
                                   String str  ) throws BadLocationException
    {
      boolean valid = true;

      int index = -1;

      // Decimal Point Check
      if (((index = str.indexOf(".")) > -1) && containsDecimal)
        return false;
      else
      {
        int lastIndex = str.lastIndexOf(".");

        if (lastIndex != index)
          return false;
      }

      // Percent Field Initialization Check
      if (getLength() == 0 && str.equals("%"))
        return true;

      // Check str Contents
      for (int indx = 0, len = str.length(); indx < len; indx++)
      {
        char c = str.charAt(indx);

        switch (indx+offs)
        {
        case 0:
          boolean negated = this.getText(0,1).equals("-");

          valid &= ((c == '-' && !negated) || (c == '.') || (Character.isDigit(c) && !negated));
          break;
        default:
          valid &= ((c == '.') || Character.isDigit(c) || (c == '%' && this.getText(0,getLength()).indexOf("%") == -1 && indx == (len-1)));
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
      if (offs == (getLength() - 1) && len > 0)
        return;

      super.remove(offs,len);
      containsDecimal = (this.getText(0,getLength()).indexOf(".") > -1);
    }

  } // End of Class

  /**
   * The PercentKeyListener is used by the JPercentField component to
   * track key events targeted at editing and traversing the percentage
   * field component.
   */
  public final class PercentKeyListener extends KeyAdapter
  {

    private final Caret    caret = getCaret();

    private final Document doc   = getDocument();

    /**
     * Invoked when a key has been pressed.
     */
    public void keyPressed(KeyEvent e)
    {
      switch (e.getKeyCode())
      {
      case KeyEvent.VK_END:
        caret.setDot(doc.getLength()-1);
        e.consume();
        break;
      case KeyEvent.VK_RIGHT:
        if (caret.getDot() == (doc.getLength() - 1))
          e.consume();
      }
    }

  } // End of Class

  /**
   * Creates the default implementation of the model to be used at construction if one isn't
   * explicitly given. An instance of PercentDocument is returned.
   *
   * @return the default Ljavax.swing.text.Document object used to house the content for
   * this currency field component.
   */
  protected Document createDefaultModel()
  {
    return new PercentDocument();
  }

  /**
   * getPercentage returns a BigDecimal object containing the decimal value of the
   * percentage represented by this percent field.
   *
   * @return a Ljava.math.BigDecimal value containing the percentage as a fractional
   * decimal value.
   */
  public BigDecimal getPercentage()
  {
    String percentStr = getText();

    BigDecimal percent = new BigDecimal(percentStr.substring(0,percentStr.length()-1));

    return percent.divide(new BigDecimal(100.0),BigDecimal.ROUND_HALF_EVEN);
  }

  /**
   * The overridden setText method sets the contents of this text field component
   * to the specified percentage value.
   *
   * @param text:Ljava.lang.String value specifying the percentage value to populate
   * this text field component.
   */
  public void setText(String text)
  {
    if (!text.trim().endsWith("%"))
      text = text.trim() + "%";

    super.setText(text);
  }

}

