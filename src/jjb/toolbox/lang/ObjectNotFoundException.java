/**
 * ObjectNotFoundException.java (c) 2002.12.12
 *
 * Copyright (c) 2002, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.2.21
 * @see java.lang.ClassNotFoundException
 * @see jjb.toolbox.util.NestedException
 */

package jjb.toolbox.lang;

import jjb.toolbox.util.NestedException;

public class ObjectNotFoundException extends NestedException {

  /**
   * Creates an instance of the ObjectNotFoundException class
   * with no message.
   */
  public ObjectNotFoundException() {
  }

  /**
   * Creates an instance of the ObjectNotFoundException class
   * with a description of the problem.
   *
   * @param message java.lang.String object description of the
   * problem.
   */
  public ObjectNotFoundException(String message) {
    super(message);
  }

  /**
   * Creates an instance of the ObjectNotFoundException class
   * which was thrown because of another exception specified by
   * the cause parameter.
   *
   * @param cause java.lang.Throwable object representing the
   * reason the ObjectNotFoundException was thrown.
   */
  public ObjectNotFoundException(Throwable cause) {
    super(cause);
  }

  /**
   * Creates an instance of the ObjectNotFoundException class
   * with the default message.  This constructor signifies that
   * the ObjectNotFoundException occurred as a result of
   * another exception being thrown.
   *
   * @param message java.lang.String object description of the
   * problem.
   * @param cause java.lang.Throwable object representing the
   * reason the ObjectNotFoundException was thrown.
   */
  public ObjectNotFoundException(String message,
                                 Throwable cause) {
    super(message,cause);
  }

}

