/**
 * Copyable.java (c) 2002.5.12
 *
 * This class is meant to replace the Cloneable interface
 * in forcing classes that implement this interface to
 * obey it's contract of implementing the copy operation.
 * Classes that implement the Cloneable interface are not
 * required to implement a clone operation since the
 * interface does not have a clone method.  Cloneable is
 * merely a marker interface.
 *
 * Copyright (c) 2002, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.2.1
 * @see java.lang.Cloneable
 * @since Java 1.0
 */

package jjb.toolbox.lang;

public interface Copyable {

  /**
   * copy provides an exact duplicate of the object whose
   * class implements this interface.  Look to the class's
   * implementation to determine whether the copy is "deep"
   * or "shallow".
   *
   * @return a java.lang.Object clone of the object whose
   * class implements this interface.
   */
  public Object copy();

}

