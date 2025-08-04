/**
 * DefaultEquator (c)2002.10.2
 *
 * Default implementation of the Equator interface.
 * Calls the Object.equals method on the object
 * paremeters passed into the areEqual method in
 * order to test for equality.
 *
 * Copyright (c) 2002, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.2.21
 * @see Equator
 */

package jjb.toolbox.util;

public final class DefaultEquator implements Equator {

  private static final Equator INSTANCE = new DefaultEquator();

  /**
   * Default private constructor to enforce the
   * non-instantiability property of the
   * Singleton instance.
   */
  private DefaultEquator() {
  }

  /**
   * default implementation of the areEqual method
   * testing for equality by calling obj1.equals(obj2).
   */  
  public boolean areEqual(Object obj1,
                          Object obj2 ) {
    return (obj1 == null ? obj2 == null : obj1.equals(obj2));
  }

  /**
   * getInstance returns the single instance of the
   * DefaultEquator class to it's caller.
   */
  public static final Equator getInstance() {
    return INSTANCE;
  }

}

