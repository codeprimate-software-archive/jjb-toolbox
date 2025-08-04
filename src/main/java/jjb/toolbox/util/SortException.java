/**
 * SortException.java (c) 2003.2.9
 *
 * Copyright (c) 2003, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.2.21
 * @see jjb.toolbox.util.NestedException
 */

package jjb.toolbox.util;

public class SortException extends NestedException {

  /**
   * Creates an instance of the SortException class with no
   * message.
   */
  public SortException() {
  }

  /**
   * Creates an instance of the SortException class with a
   * description of the problem.
   *
   * @param message java.lang.String object description of the
   * problem.
   */
  public SortException(String message) {
    super(message);
  }

  /**
   * Creates an instance of the SortException class which was
   * thrown because of another exception specified by the cause
   * parameter.
   *
   * @param cause java.lang.Throwable object representing the
   * reason the SortException was thrown.
   */
  public SortException(Throwable cause) {
    super(cause);
  }

  /**
   * Creates an instance of the SortException class with the
   * default message.  This constructor signifies that the
   * SortException occurred as a result of another exception
   * being thrown.
   *
   * @param message java.lang.String object description of the
   * problem.
   * @param cause java.lang.Throwable object representing the
   * reason the SortException was thrown.
   */
  public SortException(String message,
                       Throwable cause) {
    super(message,cause);
  }

}

