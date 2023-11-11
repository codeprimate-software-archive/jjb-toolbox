/**
 * Copyright (c) 2002, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: DefaultMessage.java
 * @version v1.0
 * Date: 25 February 2002
 * Modification Date: 17 April 2002
 * @since Java 2
 * @see jjb.toolbox.net.MessageIF
 * @see jjb.toolbox.net.AbstractMessage
 */

package jjb.toolbox.net;

import java.util.ArrayList;
import java.util.List;

public class DefaultMessage extends AbstractMessage
{

  private final List attachments;

  /**
   * Creates an instance of the DefaultMessage class to
   * the specified individual from the client wich
   * created this object.
   *
   * @param from is a Ljava.lang.String specifying the
   * sender of this message object.
   * @param to is a Ljava.lang.String specifying the 
   * recipient of this message object.
   */
  public DefaultMessage(String from,
                        String to   )
  {
    this(from,to,null,null);
  }

  /**
   * Creates an instance of the DefaultMessage class to
   * send a message to the person specified by the "to"
   * parameter by the sender specified by the "from"
   * parameter.
   *
   * @param from is a Ljava.lang.String specifying the
   * sender of this message object.
   * @param to is a Ljava.lang.String specifying the 
   * recipient of this message object.
   * @param subject is a Ljava.lang.String object
   * containing a short description of the message's
   * purpose.
   * @param message is a Ljava.lang.Ojbect containing
   * the message contents.
   */
  public DefaultMessage(String from,
                        String to,
                        String subject,
                        Object message )
  {
    if (from == null)
      throw new NullPointerException("The \"from\" address cannot be null.");

    if (to == null)
      throw new NullPointerException("The \"to\" address cannot be null.");

    setFromAddress(from);
    setToAddress(to);
    setSubject(subject);
    setMessage(message);
    attachments = new ArrayList();
  }

  /**
   * addAttachment includes an object or file with this message
   * object.
   *
   * @param attachment is a Ljava.lang.Object encapsulating the
   * data for the attachment to this message.
   * @throws Ljava.lang.UnsupportedOperationException if this
   * operation is not supported.
   */
  public void addAttachment(Object attachment)
  {
    if (attachment == null)
      return;

    attachments.add(attachment);
  }

  /**
   * getAttachements returns an array of attachment objects for
   * this message object.
   *
   * @return a [Ljava.lang.Object array of the message
   * attachements or null if this message does not contain any
   * attachments.
   * @throws Ljava.lang.UnsupportedOperationException if this
   * operation is not supported.
   */
  public Object[] getAttachments()
  {
    return attachments.toArray();
  }

}

