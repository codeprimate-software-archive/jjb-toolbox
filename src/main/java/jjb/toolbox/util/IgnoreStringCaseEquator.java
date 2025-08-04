/**
 * IgnoreStringCaseEquator.java 2003.1.15
 *
 * The StringIgnoreCaseEquator is an implemenation of the Equator
 * interface that expects String arguments as it's parameters to
 * the areEqual method, and compares the Strings for equality
 * ignoring case.
 *
 * Copyright (c) 2003, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.2.21
 * @see Equator
 */

package jjb.toolbox.util;

public final class IgnoreStringCaseEquator implements Equator {

  private static final IgnoreStringCaseEquator INSTANCE =
    new IgnoreStringCaseEquator();

  /**
   * Default private constructor to enforce the
   * non-instantiability property of a
   * Singleton instance.
   */
  private IgnoreStringCaseEquator() {
  }

  /**
   * Compares the two objects for equality.  The implementation of this
   * method assumes that the object parameters are of type String, and
   * performs the cast accordingly.  The method then proceeds to perform
   * the comparison by using the equalsIgnoreCase on the first String
   * Object parameter.
   *
   * @param obj1 is the first String used in the comparison. 
   * @param obj2 is the second String used in the comparison.
   * @return a boolean indicating whether the two String objects are
   * equal, ignoring their case.
   * @throws java.lang.ClassCastException if the Object parameters are
   * not String types.
   */
  public boolean areEqual(Object obj1,
  		                    Object obj2 ) {
    final String str1 = (String) obj1;
    final String str2 = (String) obj2;
    return (str1 == null ? str2 == null : str1.equalsIgnoreCase(str2));
  }

  /**
   * Factory method for returning the single and only instance of
   * this Equator type.
   */
  public static final IgnoreStringCaseEquator getInstance() {
    return INSTANCE;
  }

}

