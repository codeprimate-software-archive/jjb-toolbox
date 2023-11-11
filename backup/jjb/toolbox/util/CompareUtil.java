/**
 * CompareUtil.java (c) 2002.4.17
 *
 * The CompareUtil class is used to determine equality and inequality of
 * two Objects.  All Java wrapper classes except File are supported.
 * The purpose of this class is to provide a means of comparing Objects
 * in the Java 1.1 API.  Objects in the Java 2 API are comparable by
 * nature given they implement the Comparable interface.
 *
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.2.9
 * @see java.util.Comparator
 * @since Java 2
 */

package jjb.toolbox.util;

import java.math.*;
import java.util.*;
import jjb.toolbox.lang.ComparableIF;

public class CompareUtil implements ComparatorIF
{

  private static ComparatorIF instance;

  /**
   * Default Constructor.
   * This class is implemented using the Singleton Creational Design Pattern.
   * Only one instance of this class will occur in memory.
   */
  private CompareUtil() {
  }

  /**
   * compare allows to Objects of the same type to be compared for equality
   * and inequality.
   *
   * @param obj1 the Object being compared. Determines if this
   * Object is less than, equal to, or greater than Object 2.
   * @param obj2 the Object compared with Object 1 to determine
   * equality or inequality with Object 1.
   * @return an integer value of less than 0 to state less than Object 2,
   * 0 if equal to Object 2, or greater than 0 if greater than Object 2.
   */
  public int compare(Object obj1,
                     Object obj2 ) {
    if (obj1 instanceof Comparable)
      return ((Comparable) obj1).compareTo(obj2);

    if (obj1 instanceof ComparableIF)
      return ((ComparableIF) obj1).compareTo(obj2);

    if (obj1 instanceof BigDecimal)
      return ((BigDecimal) obj1).compareTo(obj2);

    if (obj1 instanceof BigInteger)
      return ((BigInteger) obj1).compareTo(obj2);

    if (obj1 instanceof String)
      return ((String) obj1).compareTo((String) obj2);

    if (obj1 instanceof Character) {
      int c1 = (int) ((Character) obj1).charValue();
      int c2 = (int) ((Character) obj2).charValue();
      return c1 - c2;
    }

    if (obj1 instanceof Byte) {
      byte b1 = ((Byte) obj1).byteValue();
      byte b2 = ((Byte) obj2).byteValue();
      return b1 - b2;
    }

    if (obj1 instanceof Short) {
      short s1 = ((Short) obj1).shortValue();
      short s2 = ((Integer) obj2).shortValue();
      return s1 - s2;
    }

    if (obj1 instanceof Integer) {
      int i1 = ((Integer) obj1).intValue();
      int i2 = ((Integer) obj2).intValue();
      return i1 - i2;
    }

    if (obj1 instanceof Long) {
      long l1 = ((Long) obj1).longValue();
      long l2 = ((Long) obj2).longValue();
      return l1 - l2;
    }
  
    if (obj1 instanceof Float) {
      float f1 = ((Float) obj1).floatValue();
      float f2 = ((Float) obj2).floatValue();
      return f1 - f2;
    }

    if (obj1 instanceof Double) {
      double d1 = ((Double) obj1).doubleValue();
      double d2 = ((Double) obj2).doubleValue();
      return d1 - d2;
    }

    if (obj1 instanceof Date) {
      long dte1 = ((Date) obj1).getTime();
      long dte2 = ((Date) obj2).getTime();
      return dte1 - dte2;
    }

    return 0;
  }

  /**
   * equals performs the equal operation as defined by the equals method
   * of the Ljava.lang.Object with specified object parameter.
   *
   * @param obj is a java.lang.Object used to compare for equality with
   * this Comparator.
   */
  public boolean equals(Object obj) {
    return super.equals(obj);
  }

  /**
   * getInstance returns an instance of this class.
   *
   * @return a Ljjb.toolbox.util.ComparatorIF object.
   */
  public static ComparatorIF getInstance() {
    if (instance == null)
      instance = new CompareUtil();
    return instance;
  }

}

