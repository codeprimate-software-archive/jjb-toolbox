/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: JPhoneNumberField.java
 * @version v1.0
 * Date: 12 November 2001
 * Modification Date: 17 April 2002
 * @since Java 2
 */

package jjb.toolbox.swing;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import jjb.toolbox.awt.event.KeyEventUtil;
import jjb.toolbox.util.StringUtil;

public class JPhoneNumberField extends JTextField
{

  protected static final Toolkit TOOLKIT = Toolkit.getDefaultToolkit();

  private boolean      typeOver;

  private int          keyCode;

  private final Caret  caret;

  /**
   * Creates an instance of the JPhoneNumberField class to represent phone numbers
   * in a text field of a form in a applet/application to allow user modifications.
   */
  public JPhoneNumberField()
  {
    super(8);

    keyCode = KeyEvent.VK_UNDEFINED;
    caret = getCaret();
    setText("(   )   -    ");
    caret.setDot(1);
    setSelectedTextColor(Color.white);
    setSelectionColor(new Color(10,36,106));
    addKeyListener(new PhoneNumberKeyListener());
  }

  /**
   * The PhoneNumberDocument class is the default document used by the JPhoneNumberField
   * component to represent phone numbers in a standard JTextField component.
   */
  private final class PhoneNumberDocument extends PlainDocument
  {

    /**
     * Inserts a string of content.  This will cause a DocumentEvent
     * of type DocumentEvent.EventType.INSERT to be sent to the
     * registered DocumentListers, unless an exception is thrown.
     *
     * @param offset is an integer value offset into the document to insert
     * the content >= 0.  All positions that track change at or after the
     * given location will move.
     * @param str the string to insert
     * @param a the attributes to associate with the inserted content.  This may
     * be null if there are no attributes.
     * @throws Ljavax.swing.text.BadLocationException the given insert position
     * is not a valid position within the document
     */
    public void insertString(int          offset,
                             String       str,
                             AttributeSet a      ) throws BadLocationException
    {
      //System.out.println("insertString offset: "+offset+" str: '"+str+"'");

      if (JPhoneNumberField.this.getText().trim().length() > 12 || (offset+str.length()) > 13 || !isValidPhone(offset,str))
        TOOLKIT.beep();
      else
      {
        if (KeyEventUtil.isDigitKey(keyCode))
          shiftRight(offset,str.length());

        super.insertString(offset,str,a);

        if (KeyEventUtil.isDigitKey(keyCode))
        {
          if (typeOver)
            setDotRight(offset+str.length());
          else
            caret.setDot(offset+str.length());
        }
      }
    }

    /**
     * isValidPhone verifies that the user inputed values constitute a valid
     * phone number and format.
     *
     * @param offs is an integer value indicating the offset into document object
     * for this phone number field to validate that the content (str) conforms
     * the structure and rules of this component concerning phone numbers.
     * @param str:Ljava.lang.String content containing full/partial phone number
     * information to validate.
     */
    public boolean isValidPhone(int    offs,
                                String str  )
    {
      boolean valid = true;

      char[] chr = str.toCharArray();

      for (int index = 0, len = chr.length; index < len; index++ )
      {
        switch (offs + index)
        {
        case 0:
          valid &= (chr[index] == '(');
          break;
        case 4:
          valid &= (chr[index] == ')');
          break;
        case 8:
          valid &= (chr[index] == '-');
          break;
        default:
          valid &= (Character.isDigit(chr[index]) || Character.isWhitespace(chr[index]));
        }

        if (!valid)
          return false;
      }

      return true;
    }

    /**
     * remove removes a portion of the content of the document.  This will cause a
     * DocumentEvent of type DocumentEvent.EventType.REMOVE to be sent to the
     * registered DocumentListeners, unless an exception is thrown.
     *
     * @param offs the offset from the begining >= 0
     * @param len the number of characters to remove >= 0
     */
    public void remove(int offs,
                       int len  ) throws BadLocationException
    {
      //System.out.println("remove offs: "+offs+" len: "+len);

      if (KeyEventUtil.isDigitKey(keyCode) || keyCode == KeyEvent.VK_BACK_SPACE || keyCode == KeyEvent.VK_DELETE)
      {
        if (offs == 0)  // prevent the '(' character from being removed!  The KeyEvent was not consumed!  Can we BUG!! in Java.
          return;

        super.remove(offs,len);
        shiftLeft(offs);

        if (keyCode == KeyEvent.VK_BACK_SPACE )
        {
          if (typeOver)
            setDotLeft(offs+len);
          else
            caret.setDot(offs);
        }
        else if (keyCode == KeyEvent.VK_DELETE)
        {
          if (typeOver)
            setDot(offs);
          else
            caret.setDot(offs);
        }
      }
    }

    /**
     * shiftLeft shifts all digit characters to the left introducing a space
     * character at the end to assist the remove operation.
     *
     * @param offset is an integer value indicating an offset into the document
     * of this JPhoneNumberField component to left shift digits of the phone number.
     * @throws Ljavax.swing.text.BadLocationException if the given insert position
     * is not a valid position within the document
     */
    private void shiftLeft(int offset) throws BadLocationException
    {
      try
      {
        StringBuffer str = new StringBuffer();

        char[] phoneNumber = StringUtil.getDigitsOnly(this.getText(offset,getLength()-offset)).toCharArray();

        for (int index = offset, i = 0; index < 13; index++)
        {
          if (index == 4)
            str.append(")");
          else if (index == 8)
            str.append("-");
          else
            str.append((i < phoneNumber.length) ? String.valueOf(phoneNumber[i++]) : " ");
        }

        super.remove(offset,getLength()-offset);
        super.insertString(offset,str.toString(),null);
      }
      catch (BadLocationException ignore)
      {
        //ignore.printStackTrace();
        throw ignore;
      }
    }

    /**
     * shiftRight shifts all digit characters to the right removing a space
     * character at the end to assist the insertString operation.
     *
     * @param offset is an integer value indicating an offset into the document
     * of this JPhoneNumberField component to right shift digits of the phone number.
     * @throws Ljavax.swing.text.BadLocationException if the given insert position
     * is not a valid position within the document
     */
    private void shiftRight(int offset,
                            int len    ) throws BadLocationException
    {
      try
      {
        StringBuffer str = new StringBuffer();

        char[] phoneNumber = StringUtil.getDigitsOnly(this.getText(offset,(13-len)-offset)).toCharArray();

        for (int index = offset+1, i = 0; index < 13; index++)
        {
          if (index == 4)
            str.append(")");
          else if (index == 8)
            str.append("-");
          else
            str.append((i < phoneNumber.length) ? String.valueOf(phoneNumber[i++]) : " ");
        }

        super.remove(offset,getLength()-offset);
        super.insertString(offset,str.toString(),null);
      }
      catch (BadLocationException ignore)
      {
        //ignore.printStackTrace();
        throw ignore;
      }
    }

  } // End of Class

  /**
   * The PhoneNumberKeyListener is used by the JPhoneNumberField component to
   * track key events targeted at editing and traversing the phone number field
   * component.
   */
  public final class PhoneNumberKeyListener extends KeyAdapter
  {

    private final Caret    caret = getCaret();

    private final Document doc   = getDocument();

    /**
     * Invoked when a key has been pressed.
     */
    public void keyPressed(KeyEvent e)
    {
      int dot = caret.getDot();

      switch (keyCode = e.getKeyCode())
      {
      case KeyEvent.VK_BACK_SPACE:
        if ((dot == 2 && typeOver) || (dot == 1 && !typeOver))
          e.consume();

        break;
      case KeyEvent.VK_INSERT:
        typeOver = !typeOver;

        if (typeOver)
          setDot(dot);
        else
          caret.setDot(dot-1);

        e.consume();
        break;
      case KeyEvent.VK_HOME:
        if (typeOver)
          setDot(1);
        else
          caret.setDot(1);

        e.consume();
        break;
      case KeyEvent.VK_LEFT:
        if (typeOver)
        {
          setDotLeft(dot);
          e.consume();
        }
        else
        {
          if (dot == 1)
            e.consume();
        }

        break;
      case KeyEvent.VK_END:
        if (typeOver)
          dot = 13;
      case KeyEvent.VK_RIGHT:
        if (typeOver)
        {
          setDotRight(dot);
          e.consume();
        }

        break;
      default:
        if (KeyEventUtil.isDigitKey(keyCode) && !typeOver && (dot == 4 || dot == 8))
          caret.setDot(++dot);
      }
    }

  } // End of Class

  /**
   * Creates the default implementation of the model to be used at construction
   * if one isn't explicitly given.  An instance of PhoneNumberDocument is returned.
   *
   * @return a Ljavax.swing.text.Document model implementation for this phone number
   * field, a basic text field component, using the PhoneNumberDocument model.
   */
  protected Document createDefaultModel()
  {
    return new PhoneNumberDocument();
  }

  /**
   * setDot sets the position of the caret for type overs to be the 
   * current dot position.
   *
   * @param dot is an integer index representing the offset into the
   * document for this phone number field component.
   */
  private void setDot(int dot)
  {
    if (dot == 4 || dot == 8 || dot == 13)
    {
      setSelectionStart(dot - 1);
      setSelectionEnd(dot);
    }
    else
    {
      setSelectionStart(dot);
      setSelectionEnd(dot + 1);
    }
  }

  /**
   * setDotLeft sets the position of the caret for type overs to the
   * left of the current dot position.
   *
   * @param dot is an integer index representing the offset into the
   * document for this phone number field component.
   */
  private void setDotLeft(int dot)
  {
    if (dot <= 2)
    {
      setSelectionStart(1);
      setSelectionEnd(2);
    }
    else if (dot == 6 || dot == 10)
    {
      setSelectionStart(--dot - 2);
      setSelectionEnd(dot - 1);
    }
    else
    {
      setSelectionStart(dot - 2);
      setSelectionEnd(dot - 1);
    }
  }

  /**
   * setDotRight sets the position of the caret for type overs to the
   * right of the current dot position.
   *
   * @param dot is an integer index representing the offset into the
   * document for this phone number field component.
   */
  private void setDotRight(int dot)
  {
    if (dot == 13)
    {
      setSelectionStart(12);
      setSelectionEnd(13);
    }
    else if (dot == 4 || dot == 8)
    {
      setSelectionStart(++dot);
      setSelectionEnd(dot + 1);
    }
    else
    {
      setSelectionStart(dot);
      setSelectionEnd(dot + 1);
    }
  }

}

