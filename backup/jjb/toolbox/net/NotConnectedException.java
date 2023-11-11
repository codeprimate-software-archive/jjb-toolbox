/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: NotConnectedException.java
 * @version v1.0
 * Date: 4 June 2001
 * Modification Date: 17 April 2002
 * @since Java 1.0
 * @see jjb.toolbox.net.CommException
 */

package jjb.toolbox.net;

public class NotConnectedException extends CommException
{

  /**
   * Creates an instance of the NotConnectedException class
   * with no message.
   */
  public NotConnectedException()
  {
  }

  /**
   * Creates an instance of the NotConnectedException class
   * with a description of the problem.
   *
   * @param message:Ljava.lang.String object description of the
   * problem.
   */
  public NotConnectedException(String message)
  {
    super(message);
  }

  /**
   * Creates an instance of the NotConnectedException class
   * which was thrown because of another exception specified by
   * the cause parameter.
   *
   * @param cause:Ljava.lang.Throwable object representing the
   * reason the NotConnectedException was thrown.
   */
  public NotConnectedException(Throwable cause)
  {
    super(cause);
  }

  /**
   * Creates an instance of the NotConnectedException class
   * with the default message.  This constructor signifies that
   * the NotConnectedException occurred as a result of
   * another exception being thrown.
   *
   * @param message:Ljava.lang.String object description of the
   * problem.
   * @param cause:Ljava.lang.Throwable object representing the
   * reason the NotConnectedException was thrown.
   */
  public NotConnectedException(String    message,
                               Throwable cause   )
  {
    super(message,cause);
  }

}

