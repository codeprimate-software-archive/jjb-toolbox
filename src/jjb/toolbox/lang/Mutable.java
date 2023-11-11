/*
 * Mutable.java (c) 1 February 2003
 *
 * The Mutable interface when implemented by a class,
 * distinguishes it's instances of being modifiable
 * or read-only objects.
 *
 * This interface specifies one method that classes
 * implementing this interface must define, isMutable.
 * This method determines whether instances of the
 * class implementing this interface are modifiable
 * or read-only.  The corresponding setMutable method
 * was not declared in this interface so that the
 * implementer could restrict access to the method
 * with the specified modifier (public,
 * package-private, protected, or private).
 *
 * Copyright (c) 2003, Code Primate
 * All Rights Reserved
 * @author John J. Blum
 * @version 2003.8.17
 * @see jjb.toolbox.lang.MutableConstants
 */
 
package jjb.toolbox.lang;

public interface Mutable extends MutableConstants {

  /**
   * Determines whether the object instance whose class
   * implements this interface is modifiable or read-only.
   *
   * @return boolean value indicating whether this object
   * is modifiable or read-only.
   */
  public boolean isMutable();

}

