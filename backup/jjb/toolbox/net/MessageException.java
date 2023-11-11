/**
 * Copyright (c) 2002, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: MessageException.java
 * @version v1.0
 * Date: 26 February 2002
 * Modification Date: 17 April 2002
 * @since Java 2
 * @see jjb.toolbox.util.MaskedException
 */

package jjb.toolbox.net;

public class MessageException extends jjb.toolbox.util.MaskedException
{

  /**
   * Creates an instance of the MessageException class with no
   * message.
   */
  public MessageException()
  {
  }

  /**
   * Creates an instance of the MessageException class with a
   * description of the problem.
   *
   * @param message:Ljava.lang.String object description of the
   * problem.
   */
  public MessageException(String message)
  {
    super(message);
  }

  /**
   * Creates an instance of the MessageException class which was
   * thrown because of another exception specified by the cause
   * parameter.
   *
   * @param cause:Ljava.lang.Throwable object representing the
   * reason the MessageException was thrown.
   */
  public MessageException(Throwable cause)
  {
    super(cause);
  }

  /**
   * Creates an instance of the MessageException class with the
   * default message.  This constructor signifies that the
   * MessageException occurred as a result of another exception
   * being thrown.
   *
   * @param message:Ljava.lang.String object description of the
   * problem.
   * @param cause:Ljava.lang.Throwable object representing the
   * reason the MessageException was thrown.
   */
  public MessageException(String    message,
                       Throwable cause   )
  {
    super(message,cause);
  }

}

