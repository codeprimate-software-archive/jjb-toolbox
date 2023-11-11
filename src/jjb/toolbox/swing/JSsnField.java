/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: JSsnField.java
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

public class JSsnField extends JTextField
{

  protected static final Toolkit TOOLKIT = Toolkit.getDefaultToolkit();

  private boolean      typeOver;

  private int          keyCode;

  private final Caret  caret;

  /**
   * Creates an instance of the JSsnField class to represent social security numbers
   * in a text field of a form in an applet/application to allow user modifications.
   */
  public JSsnField()
  {
    super(7);

    typeOver = false;
    keyCode = KeyEvent.VK_UNDEFINED;
    caret = getCaret();
    setText("   -  -    ");
    //setText("123-45-6789");
    setSelectedTextColor(Color.white);
    setSelectionColor(new Color(10,36,106));
    addKeyListener(new SSNKeyListener());
  }

  /**
   * The SSNDocument class is the default document used by the JSsnField component
   * to represent social security numbers in a standard JTextField component.
   */
  private final class SSNDocument extends PlainDocument
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
     * @param a the attributes to associate with the inserted content.
     * This may be null if there are no attributes.
     * @exception BadLocationException the given insert position is not a valid
     * position within the document
     */
    public void insertString(int          offset,
                             String       str,
                             AttributeSet a      ) throws BadLocationException
    {
      //System.out.println("INSERTSTRING offset: "+offset+" str: '"+str+"'");

      if (JSsnField.this.getText().trim().length() > 10 || (offset+str.length()) > 11 || !isValidSSN(offset,str))
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
     * isValidSSN verifies that the user inputed values constitute a valid
     * social security number and format.
     *
     * @param offs is an integer value indicating the offset into document object
     * for this social security number field to validate that the content (str)
     * conforms to the structure and rules of this component concerning social
     * security numbers.
     * @param str:Ljava.lang.String content containing full/partial social security
     * number information to validate.
     */
    public boolean isValidSSN(int    offs,
                              String str  )
    {
      boolean valid = true;

      char[] chr = str.toCharArray();

      for (int index = 0, len = chr.length; index < len; index++ )
      {
        switch (offs + index)
        {
        case 3:
        case 6:
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
     * @throws Ljavax.swing.text.BadLocationException if the given insert position
     * is not a valid position within the document
     */
    public void remove(int offs,
                       int len  ) throws BadLocationException
    {
      //System.out.println("REMOVE offs: "+offs+" len: "+len);

      if (KeyEventUtil.isDigitKey(keyCode) || keyCode == KeyEvent.VK_BACK_SPACE || keyCode == KeyEvent.VK_DELETE)
      {
        super.remove(offs,len);
        shiftLeft(offs);

        if (keyCode == KeyEvent.VK_BACK_SPACE )
        {
          if (typeOver)
            setDotLeft(offs+len);
          else
            caret.setDot((len > 1 ? offs : offs));
        }
        else if (keyCode == KeyEvent.VK_DELETE)
        {
          if (typeOver)
            setDot(offs);
          else
            caret.setDot(offs);
        }
      }

      //System.out.println("SSN: '"+JSsnField.this.getText()+"'");
    }

    /**
     * shiftLeft shifts all digit characters to the left introducing a space
     * character at the end to assist the remove operation.
     *
     * @param offset is an integer value indicating an offset into the document
     * of this JSsnField component to left shift digits of the ssn.
     * @throws Ljavax.swing.text.BadLocationException if the given insert position
     * is not a valid position within the document
     */
    private void shiftLeft(int offset) throws BadLocationException
    {
      try
      {
        StringBuffer str = new StringBuffer();

        char[] ssn = StringUtil.getDigitsOnly(this.getText(offset,getLength()-offset)).toCharArray();

        for (int index = offset, i = 0; index < 11; index++)
        {
          if (index == 3 || index == 6)
            str.append("-");
          else
            str.append((i < ssn.length) ? String.valueOf(ssn[i++]) : " ");
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
     * of this JSsnField component to right shift digits of the ssn.
     * @throws Ljavax.swing.text.BadLocationException if the given insert position
     * is not a valid position within the document
     */
    private void shiftRight(int offset,
                            int len    ) throws BadLocationException
    {
      try
      {
        StringBuffer str = new StringBuffer();

        char[] ssn = StringUtil.getDigitsOnly(this.getText(offset,(11-len)-offset)).toCharArray();

        for (int index = offset+1, i = 0; index < 11; index++)
        {
          if (index == 3 || index == 6)
            str.append("-");
          else
            str.append((i < ssn.length) ? String.valueOf(ssn[i++]) : " ");
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
   * The SSNKeyListener is used by the JSsnField component to track key events
   * targeted at editing and traversing the social security number field component.
   */
  public final class SSNKeyListener extends KeyAdapter
  {

    private final Document doc = getDocument();

    /**
     * Invoked when a key has been pressed.
     */
    public void keyPressed(KeyEvent e)
    {
      int dot = caret.getDot();

      switch (keyCode = e.getKeyCode())
      {
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
          dot = 1;
      case KeyEvent.VK_LEFT:
        if (typeOver)
        {
          setDotLeft(dot);
          e.consume();
        }

        break;
      case KeyEvent.VK_END:
        if (typeOver)
          dot = 11;
      case KeyEvent.VK_RIGHT:
        if (typeOver)
        {
          setDotRight(dot);
          e.consume();
        }

        break;
      default:
        if (KeyEventUtil.isDigitKey(keyCode) && !typeOver && (dot == 3 || dot == 6))
          caret.setDot(++dot);
      }
    }

  } // End of Class

  /**
   * Creates the default implementation of the model to be used at construction
   * if one isn't explicitly given. An instance of PhoneNumberDocument is returned.
   *
   * @return a Ljavax.swing.text.Document model implementation for this phone number
   * field, a basic text field component, using the PhoneNumberDocument model.
   */
  protected Document createDefaultModel()
  {
    return new SSNDocument();
  }

  /**
   * setDot sets the position of the caret for type overs to be the 
   * current dot position.
   *
   * @param dot is an integer index representing the offset into the
   * document for this ssn field component.
   */
  private void setDot(int dot)
  {
    if (dot == 3 || dot == 6 || dot == 11)
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
   * document for this ssn field component.
   */
  private void setDotLeft(int dot)
  {
    if (dot == 1)
    {
      setSelectionStart(0);
      setSelectionEnd(1);
    }
    else if (dot == 5 || dot == 8)
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
   * document for this ssn field component.
   */
  private void setDotRight(int dot)
  {
    if (dot == 11)
    {
      setSelectionStart(10);
      setSelectionEnd(11);
    }
    else if (dot == 3 || dot == 6)
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

