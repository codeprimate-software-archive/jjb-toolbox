/**
 * Copyright (c) 2002, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: CommException.java
 * @version v1.0
 * Date: 24 February 2002
 * Modification Date: 17 April 2002
 * @since Java 2
 * @see jjb.toolbox.util.MaskedException
 */

package jjb.toolbox.net;

import jjb.toolbox.util.MaskedException;

public class CommException extends MaskedException
{

  /**
   * Creates an instance of the CommException class with no
   * message.
   */
  public CommException()
  {
  }

  /**
   * Creates an instance of the CommException class with a
   * description of the problem.
   *
   * @param message:Ljava.lang.String object description of the
   * problem.
   */
  public CommException(String message)
  {
    super(message);
  }

  /**
   * Creates an instance of the CommException class which was
   * thrown because of another exception specified by the cause
   * parameter.
   *
   * @param cause:Ljava.lang.Throwable object representing the
   * reason the CommException was thrown.
   */
  public CommException(Throwable cause)
  {
    super(cause);
  }

  /**
   * Creates an instance of the CommException class with the default
   * message.  This constructor signifies that the CommException
   * occurred as a result of another exception being thrown.
   *
   * @param message:Ljava.lang.String object description of the
   * problem.
   * @param cause:Ljava.lang.Throwable object representing the
   * reason the CommException was thrown.
   */
  public CommException(String    message,
                       Throwable cause   )
  {
    super(message,cause);
  }

}

