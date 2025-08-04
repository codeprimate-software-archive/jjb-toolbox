/**
 * UserException.java (c) 2002.4.17
 *
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.2.21
 * @see jjb.toolbox.util.NestedException
 */

package jjb.toolbox.util;

public class UserException extends NestedException {

  /**
   * Creates an instance of the UserException class with no
   * message.
   */
  public UserException() {
  }

  /**
   * Creates an instance of the UserException class with a
   * description of the problem.
   *
   * @param message java.lang.String object description of
   * the problem.
   */
  public UserException(String message) {
    super(message);
  }

  /**
   * Creates an instance of the UserException class which was
   * thrown because of another exception specified by the cause
   * parameter.
   *
   * @param cause java.lang.Throwable object representing the
   * reason the UserException was thrown.
   */
  public UserException(Throwable cause) {
    super(cause);
  }

  /**
   * Creates an instance of the UserException class with the
   * default message.  This constructor signifies that the
   * UserException occurred as a result of another exception
   * being thrown.
   *
   * @param message java.lang.String object description of the
   * problem.
   * @param cause java.lang.Throwable object representing the
   * reason the UserException was thrown.
   */
  public UserException(String message,
                       Throwable cause) {
    super(message,cause);
  }

}

