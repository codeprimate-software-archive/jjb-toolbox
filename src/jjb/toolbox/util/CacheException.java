/**
 * CacheException.java (c) 2002.1.18
 *
 * Copyright (c) 2002, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.2.21
 */

package jjb.toolbox.util;

import java.io.PrintStream;
import java.io.PrintWriter;

public class CacheException extends NestedException {

  /**
   * Creates an instance of the CacheException class with no
   * message.
   */
  public CacheException() {
  }

  /**
   * Creates an instance of the CacheException class with a
   * description of the problem.
   *
   * @param message java.lang.String object description of the
   * problem.
   */
  public CacheException(String message) {
    super(message);
  }

  /**
   * Creates an instance of the CacheException class which was
   * thrown because of another exception specified by the cause
   * parameter.
   *
   * @param cause java.lang.Throwable object representing the
   * reason the CacheException was thrown.
   */
  public CacheException(Throwable cause) {
    super(cause);
  }

  /**
   * Creates an instance of the CacheException class with the
   * default message.  This constructor signifies that the
   * CacheException occurred as a result of another exception
   * being thrown.
   *
   * @param message java.lang.String object description of the
   * problem.
   * @param cause java.lang.Throwable object representing the
   * reason the CacheException was thrown.
   */
  public CacheException(String message,
                        Throwable cause) {
    super(message,cause);
  }

}

