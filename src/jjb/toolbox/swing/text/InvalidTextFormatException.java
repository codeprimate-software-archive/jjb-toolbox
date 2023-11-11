/**
 * InvalidTextFormatException.java (c) 2002.4.17
 *
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.2.21
 * @since Java 2
 * @see jjb.toolbox.util.NestedException
 */

package jjb.toolbox.swing.text;

import jjb.toolbox.util.NestedException;

public class InvalidTextFormatException extends NestedException {

  /**
   * Creates an instance of the InvalidTextFormatException class
   * with no message.
   */
  public InvalidTextFormatException() {
  }

  /**
   * Creates an instance of the InvalidTextFormatException class
   * with a description of the problem.
   *
   * @param message java.lang.String object description of the
   * problem.
   */
  public InvalidTextFormatException(String message) {
    super(message);
  }

  /**
   * Creates an instance of the InvalidTextFormatException class
   * which was thrown because of another exception specified by
   * the cause parameter.
   *
   * @param cause java.lang.Throwable object representing the
   * reason the InvalidTextFormatException was thrown.
   */
  public InvalidTextFormatException(Throwable cause) {
    super(cause);
  }

  /**
   * Creates an instance of the InvalidTextFormatException class
   * with the default message.  This constructor signifies that
   * the InvalidTextFormatException occurred as a result of
   * another exception being thrown.
   *
   * @param message java.lang.String object description of the
   * problem.
   * @param cause java.lang.Throwable object representing the
   * reason the InvalidTextFormatException was thrown.
   */
  public InvalidTextFormatException(String message,
                                    Throwable cause ) {
    super(message,cause);
  }

}

