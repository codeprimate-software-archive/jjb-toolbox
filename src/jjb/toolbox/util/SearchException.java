/*
 * SearchException.java (c) 2003.3.5
 *
 * Copyright (c) 2003, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.3.5
 */

package jjb.toolbox.util;

public class SearchException extends NestedException {

  /**
   * Creates an instance of the SearchException class with no
   * message.
   */
  public SearchException() {
  }

  /**
   * Creates an instance of the SearchException class with a
   * description of the problem.
   *
   * @param message java.lang.String object description of the
   * problem.
   */
  public SearchException(String message) {
    super(message);
  }

  /**
   * Creates an instance of the SearchException class which was
   * thrown because of another exception specified by the cause
   * parameter.
   *
   * @param cause java.lang.Throwable object representing the
   * reason the SearchException was thrown.
   */
  public SearchException(Throwable cause) {
    super(cause);
  }

  /**
   * Creates an instance of the SearchException class with the
   * default message.  This constructor signifies that the
   * SearchException occurred as a result of another exception
   * being thrown.
   *
   * @param message java.lang.String object description of the
   * problem.
   * @param cause java.lang.Throwable object representing the
   * reason the SearchException was thrown.
   */
  public SearchException(String message,
                         Throwable cause) {
    super(message,cause);
  }

}

