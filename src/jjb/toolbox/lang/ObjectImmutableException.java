/*
 * ObjectImmutableException.java (c) 1 February 2003
 *
 * This Exception class specifies a runtime exception if
 * a programmer tries to modify a immutable object.
 *
 * Copyright (c) 2002, Code Primate
 * All Rights Reserved
 * @author John J. Blum
 * @version 2003.2.21
 * @see jjb.toolbox.util.NestedRuntimeException
 * @see jjb.toolbox.util.Mutable
 */

package jjb.toolbox.lang;

import jjb.toolbox.util.NestedRuntimeException;

public class ObjectImmutableException extends NestedRuntimeException {

  /**
   * Creates an instance of the ObjectImmutableException class
   * with no message.
   */
  public ObjectImmutableException() {
  }

  /**
   * Creates an instance of the ObjectImmutableException class
   * with a description of the problem.
   *
   * @param message java.lang.String object description of the
   * problem.
   */
  public ObjectImmutableException(String message) {
    super(message);
  }

  /**
   * Creates an instance of the ObjectImmutableException class
   * which was thrown because of another exception specified by
   * the cause parameter.
   *
   * @param cause java.lang.Throwable object representing the
   * reason the ObjectImmutableException was thrown.
   */
  public ObjectImmutableException(Throwable cause) {
    super(cause);
  }

  /**
   * Creates an instance of the ObjectImmutableException class
   * with the default message.  This constructor signifies that
   * the ObjectImmutableException occurred as a result of
   * another exception being thrown.
   *
   * @param message java.lang.String object description of the
   * problem.
   * @param cause java.lang.Throwable object representing the
   * reason the ObjectImmutableException was thrown.
   */
  public ObjectImmutableException(String message,
                                  Throwable cause) {
    super(message,cause);
  }

}

