/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @auther John J. Blum
 * File: NoSuchCommManagerException.java
 * @version v1.0
 * Date: 22 April 2001
 * Modification Date: 17 April 2002
 * @since Java 1.0
 * @see jjb.toolbox.net.CommException
 */

package jjb.toolbox.net;

public class NoSuchCommManagerException extends CommException
{

  /**
   * Creates an instance of the NoSuchCommManagerException class
   * with no message.
   */
  public NoSuchCommManagerException()
  {
  }

  /**
   * Creates an instance of the NoSuchCommManagerException class
   * with a description of the problem.
   *
   * @param message:Ljava.lang.String object description of the
   * problem.
   */
  public NoSuchCommManagerException(String message)
  {
    super(message);
  }

  /**
   * Creates an instance of the NoSuchCommManagerException class
   * which was thrown because of another exception specified by
   * the cause parameter.
   *
   * @param cause:Ljava.lang.Throwable object representing the
   * reason the NoSuchCommManagerException was thrown.
   */
  public NoSuchCommManagerException(Throwable cause)
  {
    super(cause);
  }

  /**
   * Creates an instance of the NoSuchCommManagerException class
   * with the default message.  This constructor signifies that
   * the NoSuchCommManagerException occurred as a result of
   * another exception being thrown.
   *
   * @param message:Ljava.lang.String object description of the
   * problem.
   * @param cause:Ljava.lang.Throwable object representing the
   * reason the NoSuchCommManagerException was thrown.
   */
  public NoSuchCommManagerException(String    message,
                                    Throwable cause   )
  {
    super(message,cause);
  }

}

