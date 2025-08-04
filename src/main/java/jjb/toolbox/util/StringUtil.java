/**
 * StringUtil.java (c) 2002.4.22
 *
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.2.21
 * @since Java 1.0
 */

package jjb.toolbox.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class StringUtil 
{

  private static final String[] _SPACES =
  {
    "", // 0
    " ",
    "  ",
    "   ",
    "    ",
    "     ", // 5
    "      ",
    "       ",
    "        ",
    "         ",
    "          ", // 10
    "           ",
    "            ",
    "             ",
    "              ",
    "               ", // 15
    "                ",
    "                 ",
    "                  ",
    "                   ",
    "                    ", // 20
    "                     ",
    "                      ",
    "                       ",
    "                        ",
    "                         ",
    "                          ", // 25
    "                           ",
    "                            ",
    "                             ",
    "                              ",
    "                               ", // 30
    "                                ",
    "                                 ",
    "                                  ",
    "                                   ",
    "                                    ", // 35
    "                                     ",
    "                                      ",
    "                                       ",
    "                                        ",
    "                                         ", // 40
    "                                          ",
    "                                           ",
    "                                            ",
    "                                             ",
    "                                              ", // 45
    "                                               ",
    "                                                ",
    "                                                 ",
    "                                                  ",
    "                                                   " // 50
  };

  public static final List SPACES = Collections.unmodifiableList(Arrays.asList(_SPACES));

  private static final String[] _NEW_LINES = 
  {
    "",
    "\n",
    "\n\n",
    "\n\n\n",
    "\n\n\n\n",
    "\n\n\n\n\n"
  };

  public static final List NEW_LINES = Collections.unmodifiableList(Arrays.asList(_NEW_LINES));

  /**
   * Constructor used by subclasses to create an instance of
   * the StringUtil class to add other String operations.
   */
  protected StringUtil()
  {
  }

  /**
   * cotainsDigit returns a boolean value indicating if the
   * specified String conatins at least one digit (numeric
   * character).
   *
   * @param str Ljava.lang.String value used in determining
   * if there exists a digit.
   * @return a boolean value of true if the String parameter
   * contains at least one digit, false otherwise.
   * @throws Ljava.lang.NullPointerException if the String
   * parameter is null.
   */
  public static boolean containsDigit(String str)
  {
    if (isEmpty(str))
      return false;

    char[] chars = str.toCharArray();

    for (int index = chars.length; --index >= 0; )
    {
      if (Character.isDigit(chars[index]))
        return true;
    }

    return false;
  }

  /**
   * containsLetter returns a boolean value indicating if the
   * specified String conatins at least one letter.
   *
   * @param str Ljava.lang.String value used in determining
   * if there exists a plain letter.
   * @return a boolean value of true if the String parameter
   * contains at least one letter, false otherwise.
   * @throws Ljava.lang.NullPointerException if the String
   * parameter is null.
   */
  public static boolean containsLetter(String str)
  {
    if (isEmpty(str))
      return false;

    char[] chars = str.toCharArray();

    for (int index = chars.length; --index >= 0; )
    {
      if (Character.isLetter(chars[index]))
        return true;
    }

    return false;
  }

  /**
   * containsLowerCaseLetter returns a boolean value
   * indicating if the specified String contains at
   * least one lower case letter.
   *
   * @param str Ljava.lang.String value used in determining
   * if there exists a lower case letter.
   * @return a boolean value of true if the String parameter
   * contains at least one lower case letter, false otherwise.
   * @throws Ljava.lang.NullPointerException if the String
   * parameter is null.
   */
  public static boolean containsLowerCaseLetter(String str)
  {
    if (isEmpty(str))
      return false;

    char[] chars = str.toCharArray();

    for (int index = chars.length; --index >= 0; )
    {
      if (Character.isLetter(chars[index]) && Character.isLowerCase(chars[index]))
        return true;
    }

    return false;
  }

  /**
   * containsUpperCaseLetter returns a boolean value
   * indicating if the specified String contains at
   * least one upper case letter.
   *
   * @param str Ljava.lang.String value used in determining
   * if there exists a upper case letter.
   * @return a boolean value of true if the String parameter
   * contains at least one upper case letter, false otherwise.
   * @throws Ljava.lang.NullPointerException if the String
   * parameter is null.
   */
  public static boolean containsUpperCaseLetter(String str)
  {
    if (isEmpty(str))
      return false;

    char[] chars = str.toCharArray();

    for (int index = chars.length; --index >= 0; )
    {
      if (Character.isLetter(chars[index]) && Character.isUpperCase(chars[index]))
        return true;
    }

    return false;
  }

  /**
   * countDigits counts the number of digits (numeric
   * characters) contained within the specified String.
   *
   * @param str Ljava.lang.String value used in totaling
   * the number of digit characters contained.
   * @return a integer value indicating the number of
   * digits contained withing the specified String, or
   * 0 if there are none.
   * @throws Ljava.lang.NullPointerException if the String
   * parameter is null.
   */
  public static int countDigits(String str)
  {
    if (isEmpty(str))
      return 0;

    char[] chars = str.toCharArray();

    int count = 0;

    for (int index = chars.length; --index >= 0; )
    {
      if (Character.isDigit(chars[index]))
        count++;
    }

    return count;
  }

  /**
   * countLetters counts the number of letters contained
   * within the specified String.
   *
   * @param str Ljava.lang.String value used in totaling
   * the number of letters contained.
   * @return a integer value indicating the number of
   * letters contained withing the specified String, or
   * 0 if there are none.
   * @throws Ljava.lang.NullPointerException if the String
   * parameter is null.
   */
  public static int countLetters(String str)
  {
    if (isEmpty(str))
      return 0;

    char[] chars = str.toCharArray();

    int count = 0;

    for (int index = chars.length; --index >= 0; )
    {
      if (Character.isLetter(chars[index]))
        count++;
    }

    return count;
  }

  /**
   * countLowerCaseLetters counts the number of lower case
   * letters contained within the specified String.
   *
   * @param str Ljava.lang.String value used in totaling
   * the number of lower case letters contained.
   * @return a integer value indicating the number of
   * lower case letters contained withing the specified
   * String, or 0 if there are none.
   * @throws Ljava.lang.NullPointerException if the String
   * parameter is null.
   */
  public static int countLowerCaseLetters(String str)
  {
    if (isEmpty(str))
      return 0;

    char[] chars = str.toCharArray();

    int count = 0;

    for (int index = chars.length; --index >= 0; )
    {
      if (Character.isLetter(chars[index]) && Character.isLowerCase(chars[index]))
        count++;
    }

    return count;
  }

  /**
   * countLowerCaseLetters counts the number of upper case
   * letters contained within the specified String.
   *
   * @param str Ljava.lang.String value used in totaling
   * the number of upper case letters contained.
   * @return a integer value indicating the number of
   * upper case letters contained withing the specified
   * String, or 0 if there are none.
   * @throws Ljava.lang.NullPointerException if the String
   * parameter is null.
   */
  public static int countUpperCaseLetters(String str)
  {
    if (isEmpty(str))
      return 0;

    char[] chars = str.toCharArray();

    int count = 0;

    for (int index = chars.length; --index >= 0; )
    {
      if (Character.isLetter(chars[index]) && Character.isUpperCase(chars[index]))
        count++;
    }

    return count;
  }

  /**
   * getDigitsOnly returns a String containing only the
   * digits from the original String parameter or an empty
   * String if the String parameter contains no digits.
   *
   * @param str:Ljava.lang.String arguments containg the
   * digits to extract.
   * @return a Ljava.lang.String containing only the digits
   * of the String parameter or an empty String if the
   * String parameter contains not digits.
   * @throws Ljava.lang.NullPointerException if the String
   * parameter is null.
   */
  public static String getDigitsOnly(String str)
  {
    if (isEmpty(str))
      return "";

    StringBuffer strBuf = new StringBuffer();

    char[] chars = str.toCharArray();

    char c = ' ';

    for (int index = 0, len = chars.length; index < len; index++)
    {
      if (Character.isDigit(chars[index]))
        strBuf.append(chars[index]);
    }

    return strBuf.toString();
  }

  /**
   * getLettersOnly returns a String containing only the
   * letters from the original String parameter or an
   * empty String if the String parameter contains no
   * letters.
   *
   * @param str Ljava.lang.String parameter containg the
   * letters to extract.
   * @return a Ljava.lang.String containing only the
   * letters of the String parameter or an empty String
   * if the String parameter does not contain letters.
   * @throws Ljava.lang.NullPointerException if the String
   * parameter is null.
   */
  public static String getLettersOnly(String str)
  {
    if (isEmpty(str))
      return "";

    StringBuffer strBuf = new StringBuffer();

    char[] chars = str.toCharArray();

    char c = ' ';

    for (int index = 0, len = chars.length; index < len; index++)
    {
      if (Character.isLetter(chars[index]))
        strBuf.append(chars[index]);
    }

    return strBuf.toString();
  }

  /**
   * isDigitsOnly determines if the following String
   * parameter only contains characters that are numeric
   * digits between 0 and 9.
   *
   * @param str:Ljava.lang.String argument in question
   * for digits only check.
   * @return a boolean value of true if the String
   * parameter only contains digits, false otherwise.
   * @throws Ljava.lang.NullPointerException if the
   * String parameter is null.
   */
  public static boolean isDigitsOnly(String str)
  {
    if (isEmpty(str))
      return false;

    char[] chars = str.toCharArray();

    for (int index = chars.length; --index >= 0; )
    {
      if (!Character.isDigit(chars[index]))
        return false;
    }

    return true;
  }

  /**
   * isLettersOnly determines if the following String parameter
   * only contains letters between a and z and A and Z.
   *
   * @param str Ljava.lang.String parameter used in determing
   * the 
   * @return a boolean value of true if the String parameter
   * only contains characters, false otherwise.
   * @throws Ljava.lang.NullPointerException if the String
   * parameter is null.
   */
  public static boolean isLettersOnly(String str)
  {
    if (isEmpty(str))
      return false;

    char[] chars = str.toCharArray();

    for (int index = chars.length; --index >= 0; )
    {
      if (!Character.isLetter(chars[index]))
        return false;
    }

    return true;
  }

  /**
   * hasWhitespace returns a boolean valud indicating
   * if the String parameter contains white space
   * characters, determined by calling the
   * Character.isWhitespace method on all characters
   * constituting the String until a whitespace
   * character is found.
   *
   * @param str is the Ljava.lang.String value being
   * tested for whitespace characters.
   * @return a boolean value of true if at least one
   * of the characters in this String is a whitespace.
   * @throws Ljava.lang.NullPointerException if the
   * String parameter is null.
   */
  public static boolean hasWhitespace(String str)
  {
    if (isEmpty(str))
      return false;

    char[] chars = str.toCharArray();

    for (int index = chars.length; --index >= 0; )
    {
      if (Character.isWhitespace(chars[index]))
          return true;
    }

    return false;
  }

  /**
   * isEmpty returns a boolean indicating whether the
   * argument String is empty, containing no characters.
   *
   * @param str is a Ljava.lang.String argument being
   * tested for emptiness.
   * @return a boolean value of true if the String
   * argument contains no characters, false if the
   * String contains characters.
   * @throws Ljava.lang.NullPointerException if the
   * String parameter is null.
   */
  public static boolean isEmpty(String str)
  {
    return str.trim().equals("");
  }

  /**
   * replace replaces all "from" occurences of the
   * specified String in the "str" parameter to the
   * "to" parameter.
   *
   * @param str:Ljava.lang.String the String object in
   * which to replace "from" String occurrences to "to"
   * Strings.
   * @param from:Ljava.lang.String the String to replace.
   * @param to:Ljava.lang.String the String to replace
   * the from String with.
   * @return a Ljava.lang.String object of the
   * replacement.
   * @throws Ljava.lang.NullPointerException if the
   * String parameter is null.
   */
  public static String replace(String str,
                               String from,
                               String to   )
  {
    if (isEmpty(str))
      return str;

    int index      = 0;
    int fromLength = from.length();

    String temp = null;

    while ((index = str.indexOf(from,index)) != -1)
    {
      temp = str.substring(0,index);
      temp += to;
      temp += str.substring(index+fromLength);
      str = temp;
    }

    return str;
  }

  /**
   * Returns a String will all extra white space between
   * tokens in a String taken out and replaced with a
   * single space.
   *
   * @param theString which is a String object containing
   * 1 or more spaces between tokens in the String.
   * @return a new String containing only single spaces
   * between tokens in the String parameter.
   * @throws java.lang.NullPointerException if the the
   * String parameter is null.
   */
  public static String singleSpaceTokens(String theString)
  {
    if (theString == null)
      throw new NullPointerException("Unable to parse a null String!");
 
    final StringTokenizer parser = new StringTokenizer(theString);
    final StringBuffer buffer = new StringBuffer();
 
    while (parser.hasMoreTokens())
      buffer.append(parser.nextToken()).append(" ");
 
    return buffer.toString();
  }
 
  /**
   * trimLeadingZeros removes any leading zeros from the
   * String representation of the number to prevent the
   * value of the number from being interpreted as an
   * octal value.  Note that this method will first call
   * trim() on the String parameter to only work with
   * digits.
   *
   * @param str:Ljava.lang.String representation of an
   * integer value containing leading zeros.
   * @return a Ljava.lang.String representation of the
   * integer parameter value without leading zeros.
   * @throws Ljava.lang.NumberFormatException if the
   * value represented by the String is not an integer
   * value.
   * @throws Ljava.lang.NullPointerException if the
   * String parameter is null.
   */
  public static String trimLeadingZeros(String str)
  {
    if (isEmpty(str))
      return str;

    Integer.parseInt(str = str.trim());

    StringBuffer newStr = new StringBuffer();

    boolean trimmed = false;

    char[] digits = str.toCharArray();

    for (int index = 0, len = digits.length; index < len; index++)
    {
      if (digits[index] == '0' && !trimmed)
        continue;

      newStr.append(digits[index]);
      trimmed = true;
    }

    return newStr.toString();
  }

  /**
   * Shortens the specified to be no larger than the maxLength
   * parameter.
   */
  public static final String truncate(final String theString,
                                      final int maxLength    ) {
    return (theString == null ? null 
      : theString.substring(0,Math.min(maxLength,theString.length())));
  }

}

