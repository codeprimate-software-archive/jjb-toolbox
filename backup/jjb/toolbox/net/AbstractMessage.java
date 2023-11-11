/**
 * Copyright (c) 2002, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: AbstractMessage.java
 * @version v1.0
 * Date: 25 February 2002
 * Modification Date: 17 April 2002
 * @since Java 2
 * @see jjb.toolbox.net.MessageIF
 */

package jjb.toolbox.net;

import java.util.Date;

public abstract class AbstractMessage implements MessageIF
{
  protected int       priority;

  protected Date      timeStamp;

  protected Object    message;

  protected String    contactName;
  protected String    fromAddress;
  protected String    subject;
  protected String    toAddress;
  protected String    username;

  protected String[]  carbonCopies;

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
    throw new UnsupportedOperationException("Operation is not supported.");
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
    throw new UnsupportedOperationException("Operation is not supported.");
  }

  /**
   * getContactName returns the name of the person who sent
   * this message object.
   *
   * @return a Ljava.lang.String object of the sender of this
   * message object.
   * @throws Ljava.lang.UnsupportedOperationException if this
   * operation is not supported.
   */
  public String getContactName()
  {
    return contactName;
  }

  /**
   * getFromAddress returns the address/email address of the
   * sender of this message object.
   *
   * @return a Ljava.lang.String object representation of the
   * address or e-mail address.
   */
  public String getFromAddress()
  {
    return fromAddress;
  }

  /**
   * getMessage returns the body of the message.
   *
   * @return a Ljava.lang.Object representation of the body
   * of this message object.
   */
  public Object getMessage()
  {
    return message;
  }

  /**
   * getPriority returns the priority of this message, hence
   * the message's importance (urgent).
   *
   * @return an integer value specifying the level of
   * importance, where 1 represents the highest priority and
   * 10 specifies the lowest priority.
   * @throws Ljava.lang.UnsupportedOperationException if this
   * operation is not supported.
   */
  public int getPriority()
  {
    return priority;
  }

  /**
   * getSubject returns the subject of this message object,
   * usually a short explanation/summary of the body.
   *
   * @return a Ljava.lang.String containing the subject of
   * the message.
   */
  public String getSubject()
  {
    return subject;
  }

  /**
   * getTimeStamp returns the time that the sender sent
   * this message.
   *
   * @return a Ljava.util.Date object representing the time
   * that the user sent this message.
   * @throws Ljava.lang.UnsupportedOperationException if this
   * operation is not supported.
   */
  public Date getTimeStamp()
  {
    return timeStamp;
  }

  /**
   * getToAddress returns the recipient's address/email
   * address.
   *
   * @return a Ljava.lang.String object of the recipient's
   * address.
   */
  public String getToAddress()
  {
    return toAddress;
  }

  /**
   * getUsername is the alias/handle of the person sending
   * the message.
   *
   * @return a Ljava.lang.String object of the person's
   * alias, or handle in order to identify the person if the
   * contact name is not specified.
   * @throws Ljava.lang.UnsupportedOperationException if this
   * operation is not supported.
   */
  public String getUsername()
  {
    return username;
  }

  /**
   * notifySenderMessageRead notifies the person who sent the
   * message that the recipient read his/her message.
   *
   * @throws Ljava.lang.UnsupportedOperationException if this
   * operation is not supported.
   */
  public void notifySenderMessageRead()
  {
  }

  /**
   * sendCarbonCopies sends potential other people copies of
   * this message.
   *
   * @param copies is a [Ljava.lang.String object of
   * address/email addresses of people who should be cc'ed
   * this message.
   * @throws Ljava.lang.UnsupportedOperationException if this
   * operation is not supported.
   */
  public void sendCarbonCopies(String[] copies)
  {
    carbonCopies = copies;
  }

  /**
   * setContactName sets the contact name of the person
   * sending this message object.  This is also represents the
   * name of the person to which replies of the message will
   * be sent.
   *
   * @param contactName is a Ljava.lang.String object
   * containing the sender's natural name.
   * @throws Ljava.lang.UnsupportedOperationException if this
   * operation is not supported.
   */
  public void setContactName(String contactName)
  {
    this.contactName = contactName;
  }

  /**
   * setFromAddress sets the sender's address/email address.
   *
   * @param from is a Ljava.lang.String object containing the
   * sender's address or email address.
   */
  public void setFromAddress(String from)
  {
    fromAddress = from;
  }

  /**
   * setMessage sets the body of the message object.
   * Usually this is a textual message of some predefined
   * format.
   *
   * @param message:Ljava.lang.Object containing the
   * contents of the message body.
   */
  public void setMessage(Object message)
  {
    this.message = message;
  }

  /**
   * setPriority sets the priority of this message, the
   * importance of this message (urgency).
   *
   * @param priority is an integer value specifying the
   * importance of this message, where 1 represents
   * immediate attention and 10 represents non-urgent
   * messages.
   * @throws Ljava.lang.UnsupportedOperationException if this
   * operation is not supported.
   */
  public void setPriority(int priority)
  {
    this.priority = priority;
  }

  /**
   * setSubject sets the message subject, a short summary
   * about the purpose of the message.
   *
   * @param subject is a Ljava.lang.String containing the
   * subject content.
   */
  public void setSubject(String subject)
  {
    this.subject = subject;
  }

  /**
   * setTimeStamp sets the date at which this message is
   * created.  Note that the sender should not be setting
   * this value, rather the message dispatcher should send
   * this message prior to sending this message.
   *
   * @param timeStamp is a Ljava.util.Date containing the
   * time at which this message was sent.
   * @throws Ljava.lang.UnsupportedOperationException if this
   * operation is not supported.
   */
  public void setTimeStamp(Date timeStamp)
  {
    this.timeStamp = timeStamp;
  }

  /**
   * setToAddress sets the recipients address/email addres.
   *
   * @param to is a Ljava.lang.String containing the
   * recipient's address or email address.
   */
  public void setToAddress(String to)
  {
    toAddress = to;
  }

  /**
   * setUsername sets the alias or handle of the user sending
   * the message.  Note that the user should be the one setting
   * the alias, and the system should call this method only if
   * a contact name or alias was not specified.  The system
   * should then set the username to be the handle of the email
   * address.
   *
   * @param username is a Ljava.lang.String containing the
   * sender's alias or handle.
   * @throws Ljava.lang.UnsupportedOperationException if this
   * operation is not supported.
   */
  public void setUsername(String username)
  {
    this.username = username;
  }

}

