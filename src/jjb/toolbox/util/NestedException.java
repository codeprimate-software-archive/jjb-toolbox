/**
 * NestedException.java (c) 2002.2.7
 *
 * Copyright (c) 2002, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.2.14
 * @see java.lang.Exception
 */

package jjb.toolbox.util;

import java.io.PrintStream;
import java.io.PrintWriter;

public class NestedException extends Exception {

  private final Throwable cause;

  /**
   * Creates an instance of the NestedException class with no
   * message.
   */
  public NestedException() {
    cause = null;
  }

  /**
   * Creates an instance of the NestedException class with a
   * description of the problem.
   *
   * @param message a java.lang.String object description of
   * the problem.
   */
  public NestedException(String message) {
    super(message);
    cause = null;
  }

  /**
   * Creates an instance of the NestedException class which was
   * thrown because of another exception specified by the cause
   * parameter.
   *
   * @param cause a java.lang.Throwable object representing the
   * reason the NestedException was thrown.
   */
  public NestedException(Throwable cause) {
    this.cause = cause;
  }

  /**
   * Creates an instance of the NestedException class with the default
   * message.  This constructor signifies that the NestedException
   * occurred as a result of another exception being thrown.
   *
   * @param message a java.lang.String description of the problem.
   * @param cause a java.lang.Throwable object representing the
   * reason the NestedException was thrown.
   */
  public NestedException(String message,
                         Throwable cause) {
    super(message);
    this.cause = cause;
  }

  /**
   * getCause returns the reason why the NestedException was thrown.
   *
   * @return a Ljava.lang.Throwable object representing the cause of
   * CacheException being thrown.
   */
  public Throwable getCause() {
    return cause;
  }

  /**
   * getMessage returns the description of the problem.
   *
   * @return a Ljava.lang.String object description of why the
   * CacheException was thrown.
   */
  public String getMessage() {
    final String message = super.getMessage();
    return (message != null ? message+"\n" : "") 
      + (cause != null ? cause.getMessage() : "");
  }

  /**
   * printStackTrace prints a stack trace of the nested method calls
   * to the source of the problem, where the NestedException was
   * thrown.
   */
  public void printStackTrace() {
    printStackTrace(System.err);
  }

  /**
   * printStackTrace prints to the PrintStream object a stack trace of
   * the nested method calls to the source of the problem, where the
   * NestedException was thrown.
   *
   * @param ps a java.io.PrintStream object in which to print the stack
   * trace.
   */
  public void printStackTrace(PrintStream ps) {
    final String message = super.getMessage();

    ps.println(getClass().getName()+": "
      +(message == null ? "No Message" : message));

    if (cause != null)
      cause.printStackTrace(ps);
  }

  /**
   * printStackTrace writes to the PrintWriter object a stack trace of
   * the nested method calls to the source of the problem, where the
   * NestedException was thrown.
   *
   * @param ps a java.io.PrintWriter object in which to write the stack
   * trace.
   */
  public void printStackTrace(PrintWriter pw) {
    final String message = super.getMessage();

    pw.println(getClass().getName()+": "
      +(message == null ? "No Message" : message));

    if (cause != null)
      cause.printStackTrace(pw);
  }

}

