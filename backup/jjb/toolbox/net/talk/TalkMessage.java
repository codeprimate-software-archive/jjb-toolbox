/**
 * Copyright (c) 2001 Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: TalkMessage.java
 * @version v1.0
 * Date: 22 April 2001
 * Modification Date: 17 April 2002
 * @since Java 2
 * @see jjb.toolbox.net.DefaultMessage
 */

package jjb.toolbox.net.talk;

public class TalkMessage extends jjb.toolbox.net.DefaultMessage
{

  public TalkMessage(String to,
                     String from)
  {
    super(to,from);
  }

  public TalkMessage(String to,
                     String from,
                     String subject,
                     Object message )
  {
    super(to,from,subject,message);
  }

  /**
   * getContactName returns the contactName for the sender
   * of this message.  If the contactName is not specified,
   * it defaults to the username of the individual.
   *
   * @return a Ljava.lang.String containing the contact name.
   */
  public String getContactName()
  {
    if (contactName == null)
      return username;

    return contactName;
  }

  /**
   * toString returns the message contents.
   *
   * @return a Ljava.lang.String of the message contents.
   */
  public String toString()
  {
    return message.toString();
  }

}

