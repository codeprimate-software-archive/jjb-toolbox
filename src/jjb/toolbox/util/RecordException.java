/**
 * RecordException.java (c) 2002.4.17
 *
 * Copyright (c) 2002, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.2.21
 * @see jjb.toolbox.util.NestedException;
 */

package jjb.toolbox.util;

public class RecordException extends NestedException
{

  /**
   * Creates an instance of the RecordException class with no
   * message.
   */
  public RecordException() {
  }

  /**
   * Creates an instance of the RecordException class with a
   * description of the problem.
   *
   * @param message java.lang.String object description of the
   * problem.
   */
  public RecordException(String message) {
    super(message);
  }

  /**
   * Creates an instance of the RecordException class which was
   * thrown because of another exception specified by the cause
   * parameter.
   *
   * @param cause java.lang.Throwable object representing the
   * reason the RecordException was thrown.
   */
  public RecordException(Throwable cause) {
    super(cause);
  }

  /**
   * Creates an instance of the RecordException class with the
   * default message.  This constructor signifies that the
   * RecordException occurred as a result of another exception
   * being thrown.
   *
   * @param message java.lang.String object description of the
   * problem.
   * @param cause java.lang.Throwable object representing the
   * reason the RecordException was thrown.
   */
  public RecordException(String message,
                           Throwable cause) {
    super(message,cause);
  }

}

