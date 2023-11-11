/**
 * Copyright (c) 2001, JB Innovations
 * ALL RIGHTS RESERVED
 *
 * The JNumberOnlyField component displays signed numbers in base 10.
 *
 * @author John J. Blum
 * File: JNumberOnlyField.java
 * @version v1.0
 * Date: 5 December 2001
 * Modification Date: 5 December 2001
 * @since Java 2
 */

package jjb.toolbox.swing;

import java.awt.Toolkit;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

public class JNumberOnlyField extends JTextField
{

  public static final RadixType BINARY      = new RadixType(2);
  public static final RadixType OCATAL      = new RadixType(8);
  public static final RadixType DECIMAL     = new RadixType(10);
  public static final RadixType HEXIDECIMAL = new RadixType(16);

  protected static final Toolkit TOOLKIT = Toolkit.getDefaultToolkit();

  private final BigDecimal  minValue,
                            maxValue;

  private final RadixType   radix;

  /**
   * Creates a new instance of the JNumberOnlyField class component to work with and
   * display numeric values.
   */
  public JNumberOnlyField()
  {
    this(new Integer(0),DECIMAL,Double.MIN_VALUE,Double.MAX_VALUE);
  }

  /**
   * Creates a new instance of the JNumberOnlyField class component initialized to
   * the given Number.
   *
   * @param num:Ljava.lang.Number object containing the numeric value to display in
   * this field of the form containing this component.
   */
  public JNumberOnlyField(Number num)
  {
    this(num,DECIMAL,Double.MIN_VALUE,Double.MAX_VALUE);
  }

  /**
   * Creates a new instance of the JNumberOnlyField class component initialized to
   * the given Number.
   *
   * @param num:Ljava.lang.Number object containing the numeric value to display in
   * this field of the form containing this component.
   * @param base:Ljjb.toolbox.swing.JNumberOnlyField.RadixType object specifying the
   * base to display and work with the numbers in.
   */
  public JNumberOnlyField(Number num,
                          RadixType base)
  {
    this(num,base,Double.MIN_VALUE,Double.MAX_VALUE);
  }

  /**
   * Creates a new instance of the JNumberOnlyField class component initialized to
   * the given Number.
   *
   * @param num:Ljava.lang.Number object containing the numeric value to display in
   * this field of the form containing this component.
   * @param base:Ljjb.toolbox.swing.JNumberOnlyField.RadixType object specifying the
   * base to display and work with the numbers in.
   */
  public JNumberOnlyField(Number    num,
                          RadixType base,
                          double    minValue,
                          double    maxValue )
  {
    final Number numCopy = (Number) num.clone();  // Defensive Copy

    final boolean _isWholeNumber = isWholeNumber(numCopy);

    final double numValue = num.doubleValue();

    if (!_isWholeNumber && base != DECIMAL)
      throw new IllegalArgumentException("Float-point numbers cannot be represented in "+base+".");

    if (numValue < minValue || numValue > maxValue)
      throw new IllegalArgumentException("The

    if (_isWholeNumber && !(numCopy instanceof BigInteger))
      numCopy = new BigInteger(numCopy.toString());

    minValue = new BigDecimal(String.valueOf(minValue));
    maxValue = new BigDecimal(String.valueOf(maxValue));

    if (_isWholeNumber)
      setText(((BigInteger) numCopy).toString(base.getRadix()));
    else
      setText(numCopy);
  }

  /**
   * The RadixType class serves as a type-safe enumeration for defining types.
   */
  private final class RadixType
  {

    private final int radix;

    /**
     * Creates an instance of the RadixType class with the specified radix integer
     * value.  Valid radixes for the Java Platform are between 2 and 32.
     *
     * @param radix is an integer value between 2 and 32 inclusive specifying the
     * base of number.
     */
    public RadixType(int radix)
    {
      this.radix = radix;
    }

    /**
     * getRadix returns the value of the radix specified by an instance of the
     * RadixType class.
     *
     * @return a integer value representing the radix (base) of a number as
     * specified by this RadixType instance.
     */
    public int getRadix()
    {
      return radix;
    }

    /**
     * getNumberPrefix returns the 
     */
    public String getNumberPrefix()
    {
      switch (radix)
      {
      case 8:
        return "0";
      case 16:
        return "0x";
      default:
        return "";
      }
    }

    /**
     * toString determines a String description of the radix.
     *
     * @return a Ljava.lang.String description of the radix.
     */
    public String toString()
    {
      switch (radix)
      {
      case 2:
        return "binary";
      case 8:
        return "octal";
      case 10:
        return "decimal";
      case 16:
        return "hexidecimal";
      }
    }

  } // End of Class

  /**
   * The NumberDocument class is used by the JNumberOnlyField component to format the 
   * JTextField component as a numeric input field validating numbers as binary, octal,
   * decimal and hexidecimal.  Special types of numeric fields can also be validated 
   * numerically such as Currency and Percentage.
   */
  private final class NumberDocument extends PlainDocument
  {

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
      if (!isValidNumber(str))
        TOOLKIT.beep();
      else
        super.insertString(offs,str,a);
    }

    /**
     * isValidNumber
     */
    private boolean isValidNumber(int    offs,
                                  String str  )
    {
      if (radix == BINARY)
        return isValidBinary(str);
      else if (radix == OCTAL)
        return isValidOctal(str);
      else if (radix == DECIMAL)
        return isValidDecimal(offs,str);
      else
        return isValidHexidecimal(str);
    }

    /**
     * isValidBinary
     */
    private boolean isValidBinary(int    offs,
                                  String str  )
    {
      for (int index = str.length; --index >= 0; )
      {
        char zeroOne = str.charAt(index);

        if (zeroOne != '1' && zeroOne != '0')
          return false;
      }

      String currentNumber = getText(0,getLength());
      String newNumber     = currentNumber.substring(0,offs) + str + currentNumber.substring(offs);

      BigDecimal bigNumber = new BigDecimal(newNumber,2);
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
                       int len) throws BadLocationException
    {
      if (offs < 2 && radix == HEXIDECIMAL)
        return;

      if (offs < 1 && radix == OCTAL)
        return;

      super.remove(offs,len);
    }

  } // End of Class

  /**
   * isWhole number determines whether the number is a non-fractional value such as
   * Integer, Long, BigInteger, Short, or Byte.
   *
   * @param num:Ljava.lang.Number object used to determine if it is a whole number
   * or not.
   * @return a boolean value of the true if the Number is whole, false otherwise.
   */
  protected final boolean isWholeNumber(Number num)
  {
    if (num instanceof Integer)
      return true;
    else if (num instanceof Long)
      return true;
    else if (num instanceof BigInteger)
      return true;
    else if (num instanceof Short)
      return true;
    else if (num instanceof Byte)
      return true;

    return false;
  }

  /**
   * isFloatingPoint number determines whether the number is a fractional value such
   * as Double, Float or BigDecimal.
   *
   * @param num:Ljava.lang.Number object used to determine if it is a floating-point
   * number or not.
   * @return a boolean value of the true if the Number is a floating-point, false
   * otherwise.
   */
  protected final boolean isFloatingPoint(Number num)
  {
    if (num instanceof Double)
      return true;
    else if (num instanceof Float)
      return true;
    else if (num instanceof BigDecimal)
      return true;

    return false;
  }

}

