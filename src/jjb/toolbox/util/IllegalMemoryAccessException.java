/**
 * IllegalMemoryAccessException.java (c) 2002.4.17
 *
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.2.21
 * @see jjb.toolbox.util.NestedException
 */

package jjb.toolbox.util;

public class IllegalMemoryAccessException extends NestedException {

  /**
   * Creates an instance of the CacheException class with no
   * message.
   */
  public IllegalMemoryAccessException() {
  }

  /**
   * Creates an instance of the IllegalMemoryAccessException class
   * with a description of the problem.
   *
   * @param message java.lang.String object description of the
   * problem.
   */
  public IllegalMemoryAccessException(String message) {
    super(message);
  }

  /**
   * Creates an instance of the IllegalMemoryAccessException class
   * which was thrown because of another exception specified by
   * the cause parameter.
   *
   * @param cause java.lang.Throwable object representing the
   * reason the IllegalMemoryAccessException was thrown.
   */
  public IllegalMemoryAccessException(Throwable cause) {
    super(cause);
  }

  /**
   * Creates an instance of the IllegalMemoryAccessException class
   * with the default message.  This constructor signifies that
   * the IllegalMemoryAccessException occurred as a result of
   * another exception being thrown.
   *
   * @param message java.lang.String object description of the
   * problem.
   * @param cause java.lang.Throwable object representing the
   * reason the IllegalMemoryAccessException was thrown.
   */
  public IllegalMemoryAccessException(String message,
                                      Throwable cause) {
    super(message,cause);
  }

}

