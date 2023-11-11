/**
 * Copyright (c) 2001 Code Primate
 * All Rights Reserved
 *
 * The MessageAdapter class integrates the MessageIF class
 * objects of the jjb.toolbox.net package into the Java
 * Mail API.
 *
 * @author John J. Blum
 * File: MessageAdapter.java
 * @version v1.0
 * Date: 22 April 2001
 * Modification Date: 17 April 2002
 * @since Java 1.0
 * @see jjb.toolbox.net.MessageIF
 * @see javax.mail.Message
 * @deprecated do not even ask!
 */

package jjb.toolbox.net.mail;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Enumeration;
import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.search.SearchTerm;
import jjb.toolbox.net.MessageException;
import jjb.toolbox.net.MessageIF;

public class MessageAdapter extends Message implements MessageIF
{

  private int            priority;

  private final Message  email;

  /**
   * Creates an instance of the MessageAdapter class to house
   * a Ljavax.mail.Message object, for which calls in the
   * MessageIF interface are translated.
   */
  public MessageAdapter(Message email)
  {
    if (email == null)
      throw new NullPointerException("The Message object passed to this constructor cannot be null.");

    priority = MessageIF.NORMAL_PRIORITY;
    this.email = email;
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
    return null;
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
  public String getContactName() throws MessageException
  {
    return getFromAddress();
  }

  /**
   * getFromAddress returns the address/email address of the
   * sender of this message object.
   *
   * @return a Ljava.lang.String object representation of the
   * address or e-mail address.
   */
  public String getFromAddress() throws MessageException
  {
    try
    {
      return email.getFrom().toString();
    }
    // Catch Ljavax.mail.MessagingException
    catch (Exception e)
    {
      throw new MessageException(e);
    }
  }

  /**
   * getMessage returns the body of the message.
   *
   * @return a Ljava.lang.Object representation of the body
   * of this message object.
   */
  public Object getMessage() throws MessageException
  {
    try
    {
      return email.getContent();
    }
    // Catch Ljavax.mail.MessagingException
    catch (Exception e)
    {
      throw new MessageException(e);
    }
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
    try
    {
      return email.getSubject();
    }
    // Catch Ljavax.mail.MessagingException
    catch (Exception e)
    {
    }

    return null;
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
  public Date getTimeStamp() throws MessageException
  {
    try
    {
      return email.getSentDate();
    }
    // Catch Ljavax.mail.MessagingException
    catch (Exception e)
    {
      throw new MessageException(e);
    }
  }

  /**
   * getToAddress returns the recipient's address/email
   * address.
   *
   * @return a Ljava.lang.String object of the recipient's
   * address.
   */
  public String getToAddress() throws MessageException
  {
    try
    {
      final StringBuffer buffer = new StringBuffer();

      final Address[] addr = email.getAllRecipients();

      for (int index = addr.length; --index >=0; )
      {
        buffer.append(addr[index].toString());

        if (index > 0)
          buffer.append(MessageIF.EMAIL_ADDRESS_DELIMETER);
      }

      return buffer.toString();
    }
    // Catch Ljavax.mail.MessagingException
    catch (Exception e)
    {
      throw new MessageException(e);
    }
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
  public String getUsername() throws MessageException
  {
    return getFromAddress();
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
    throw new UnsupportedOperationException("Operation is not supported in the MessageAdapter.");
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
  public void sendCarbonCopies(String[] copies) throws MessageException
  {
    try
    {
      if (copies == null || copies.length == 0)
        return;

      final Address[] addr = new Address[copies.length];

      for (int index = addr.length; --index >= 0; )
        addr[index] = new InternetAddress(copies[index]);

      email.addRecipients(Message.RecipientType.CC,addr);
    }
    // Catch Ljavax.mail.MessageException
    // Catch Ljavax.mail.internet.AddressException
    catch (Exception e)
    {
      throw new MessageException(e);
    }
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
    throw new UnsupportedOperationException("Operation not supported in the Ljavax.mail.Message type.");
  }

  /**
   * setFromAddress sets the sender's address/email address.
   *
   * @param from is a Ljava.lang.String object containing the
   * sender's address or email address.
   */
  public void setFromAddress(String from) throws MessageException
  {
    if (from == null)
      throw new NullPointerException("The from address cannot be null.");

    try
    {
      email.setFrom(new InternetAddress(from));
    }
    // Catch Ljavax.mail.MessagingException
    catch (Exception e)
    {
      throw new MessageException(e);
    }
  }

  /**
   * setMessage sets the body of the message object.
   * Usually this is a textual message of some predefined
   * format.
   *
   * @param message:Ljava.lang.Object containing the
   * contents of the message body.
   */
  public void setMessage(Object message) throws MessageException 
  {
    try
    {
      if (message instanceof String)
        email.setText(message.toString());
      else
        email.setContent(message,"application/"+message.getClass().getName());
    }
    // Catch Ljavax.mail.MessagingException
    catch (Exception e)
    {
      throw new MessageException(e);
    }
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
    if (priority < MessageIF.LOW_PRIORITY ||
        priority > MessageIF.HIGH_PRIORITY)
      throw new IllegalArgumentException("The Priority level may only be set between "+MessageIF.LOW_PRIORITY+" and "+MessageIF.HIGH_PRIORITY);

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
    try
    {
      email.setSubject(subject);
    }
    catch (Exception e)
    {
    }
  }

  /**
   * setTimeStamp sets the date at which this message is
   * created.  Note that the sender should not be setting
   * this value, rather the message dispatcher should set
   * this time prior to sending this message.
   *
   * @param timeStamp is a Ljava.util.Date containing the
   * time at which this message was sent.
   * @throws Ljava.lang.UnsupportedOperationException if this
   * operation is not supported.
   */
  public void setTimeStamp(Date timeStamp) throws MessageException
  {
    try
    {
      email.setSentDate(timeStamp);
    }
    // Catch Ljavax.mail.IllegalWritingException
    // Catch Ljavax.mail.MessagingException
    catch (Exception e)
    {
      throw new MessageException(e);
    }
  }

  /**
   * setToAddress sets the recipients address/email addres.
   *
   * @param to is a Ljava.lang.String containing the
   * recipient's address or email address.
   */
  public void setToAddress(String to) throws MessageException
  {
    if (to == null)
      throw new NullPointerException("The to address of this message object cannot be null.");

    try
    {
      email.setRecipient(Message.RecipientType.TO,new InternetAddress(to));
    }
    // Catch Ljavax.mail.MessagingException
    // Catch Ljavax.mail.internet.AddressException
    catch (Exception e)
    {
      throw new MessageException(e);
    }
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
    throw new UnsupportedOperationException("Operation not supported in the Ljavax.mail.Message type.");
  }

  /**
   * Add these addresses to the existing "From" attribute.
   */
  public void addFrom(Address[] addresses) throws MessagingException
  {
    email.addFrom(addresses);
  }

  /**
   * Add this value to the existing values for this header_name.
   */
  public void addHeader(String header,
                        String value  ) throws MessagingException
  {
    email.addHeader(header,value);
  }

  /**
   * Add this recipient address to the existing ones of the
   * given type.
   */
  public void addRecipient(Message.RecipientType type,
                           Address               address) throws MessagingException
  {
    email.addRecipient(type,address);
  }

  /**
   * Add these recipient addresses to the existing ones of
   * the given type.
   */
  public void addRecipients(Message.RecipientType type,
                            Address[]             addresses) throws MessagingException
  {
    email.addRecipients(type,addresses);
  }

  /**
   * Return all the headers from this part as an Enumeration
   * of Header objects.
   */
  public Enumeration getAllHeaders() throws MessagingException
  {
    return email.getAllHeaders();
  }

  /**
   * Get all the recipient addresses for the message.
   */
  public Address[] getAllRecipients() throws MessagingException
  {
    return email.getAllRecipients();
  }

  /**
   * Return the content as a Java object.
   */
  public Object getContent() throws IOException, MessagingException
  {
    return email.getContent();
  }

  /**
   * Returns the Content-Type of the content of this part.
   */
  public String getContentType() throws MessagingException
  {
    return email.getContentType();
  }

  /**
   * Return a DataHandler for the content within this part.
   */
  public DataHandler getDataHandler() throws MessagingException
  {
    return email.getDataHandler();
  }

  /**
   * Return a description String for this part.
   */
  public String getDescription() throws MessagingException
  {
    return email.getDescription();
  }

  /**
   * Return the disposition of this part.
   */
  public String getDisposition() throws MessagingException
  {
    return email.getDisposition();
  }

  /**
   * Get the filename associated with this part, if possible.
   */
  public String getFileName() throws MessagingException
  {
    return email.getFileName();
  }

  /**
   * Returns a Flags object containing the flags for this
   * message.
   */
  public Flags getFlags() throws MessagingException
  {
    return email.getFlags();
  }

  /**
   * Get the folder from which this message was obtained.
   */
  public Folder getFolder()
  {
    return email.getFolder();
  }

  /**
   * Returns a Flags object containing the flags for this
   * message.
   */
  public Address[] getFrom() throws MessagingException
  {
    return email.getFrom();
  }

  /**
   * Get all the headers for this header name.
   */
  public String[] getHeader(String header) throws MessagingException
  {
    return email.getHeader(header);
  }

  /**
   * Return an input stream for this part's "content".
   */
  public InputStream getInputStream() throws IOException, MessagingException
  {
    return email.getInputStream();
  }

  /**
   * Return the number of lines in the content of this part.
   */
  public int getLineCount() throws MessagingException
  {
    return email.getLineCount();
  }

  /**
   * Return matching headers from this part as an Enumeration
   * of Header objects.
   */
  public Enumeration getMatchingHeaders(String[] headers) throws MessagingException
  {
    return email.getMatchingHeaders(headers);
  }

  /**
   * Get the Message number for this Message.
   */
  public int getMessageNumber()
  {
    return email.getMessageNumber();
  }

  /**
   * Return non-matching headers from this envelope as an
   * Enumeration of Header objects.
   */
  public Enumeration getNonMatchingHeaders(String[] headers) throws MessagingException
  {
    return email.getNonMatchingHeaders(headers);
  }

  /**
   * Get the date this message was received.
   */
  public Date getReceivedDate() throws MessagingException
  {
    return email.getReceivedDate();
  }

  /**
   * Get all the recipient addresses of the given type.
   */
  public Address[] getRecipients(Message.RecipientType type) throws MessagingException
  {
    return email.getRecipients(type);
  }

  /**
   * Get the addresses to which replies should be directed.
   */
  public Address[] getReplyTo() throws MessagingException
  {
    return email.getReplyTo();
  }

  /**
   * Get the date this message was sent.
   */
  public Date getSentDate() throws MessagingException
  {
    return email.getSentDate();
  }

  /**
   * Return the size of the content of this part in bytes.
   */
  public int getSize() throws MessagingException
  {
    return email.getSize();
  }

  /**
   * Checks whether this message is expunged.
   */
  public boolean isExpunged()
  {
    return email.isExpunged();
  }

  /**
   * Is this Part of the specified MIME type? This method
   * compares only the primaryType and subType.
   */
  public boolean isMimeType(String mimeType) throws MessagingException
  {
    return email.isMimeType(mimeType);
  }

  /**
   * Check whether the flag specified in the flag argument
   * is set in this message.
   */
  public boolean isSet(Flags.Flag flag) throws MessagingException
  {
    return email.isSet(flag);
  }

  /**
   * Apply the specified Search criterion to this message.
   */
  public boolean match(SearchTerm term) throws MessagingException
  {
    return email.match(term);
  }

  /**
   * Remove all headers with this name.
   */
  public void removeHeader(String header) throws MessagingException
  {
    email.removeHeader(header);
  }

  /**
   * Get a new Message suitable for a reply to this message.
   */
  public Message reply(boolean replyToAll) throws MessagingException
  {
    return email.reply(replyToAll);
  }

  /**
   * Save any changes made to this message into the
   * message-store when the containing folder is closed, if
   * the message is contained in a folder.
   */
  public void saveChanges() throws MessagingException
  {
    email.saveChanges();
  }

  /**
   * This method sets the given Multipart object as this
   * message's content.
   */
  public void setContent(Multipart mp) throws MessagingException
  {
    email.setContent(mp);
  }

  /**
   * A convenience method for setting this part's content.
   */
  public void setContent(Object obj,
                         String type) throws MessagingException
  {
    email.setContent(obj,type);
  }

  /**
   * This method provides the mechanism to set this part's
   * content.
   */
  public void setDataHandler(DataHandler dh) throws MessagingException
  {
    email.setDataHandler(dh);
  }

  /**
   * Set a description String for this part.
   */
  public void setDescription(String description) throws MessagingException
  {
    email.setDescription(description);
  }

  /**
   * Set the disposition of this part.
   */
  public void setDisposition(String disposition) throws MessagingException
  {
    email.setDisposition(disposition);
  }

  /**
   * Sets the expunged flag for this Message.
   */
  protected void setExpunged(boolean expunged)
  {
  }

  /**
   * Set the filename associated with this part, if possible.
   */
  public void setFileName(String filename) throws MessagingException
  {
    email.setFileName(filename);
  }

  /**
   * Set the specified flag on this message to the specified
   * value.
   */
  public void setFlag(Flags.Flag flag,
                      boolean    set  ) throws MessagingException
  {
    email.setFlag(flag,set);
  }

  /**
   * Set the specified flags on this message to the specified
   * value.
   */
  public void setFlags(Flags   flags,
                       boolean set   ) throws MessagingException
  {
    email.setFlags(flags,set);
  }

  /**
   * Set the "From" attribute in this Message.
   */
  public void setFrom() throws MessagingException
  {
    email.setFrom();
  }

  /**
   * Set the "From" attribute in this Message.
   */
  public void setFrom(Address from) throws MessagingException
  {
    email.setFrom(from);
  }

  /**
   * Set the value for this header_name.
   */
  public void setHeader(String header,
                        String value  ) throws MessagingException
  {
    email.setHeader(header,value);
  }

  /**
   * Set the Message number for this Message.
   */
  protected void setMessageNumber(int messageNumber)
  {
  }

  /**
   * Set the recipient address.
   */
  public void setRecipient(Message.RecipientType type,
                           Address               address) throws MessagingException
  {
    email.setRecipient(type,address);
  }

  /**
   * Set the recipient addresses.
   */
  public void setRecipients(Message.RecipientType type,
                            Address[]             addresses) throws MessagingException
  {
    email.setRecipients(type,addresses);
  }

  /**
   * Set the addresses to which replies should be directed.
   */
  public void setReplyTo(Address[] addresses) throws MessagingException
  {
    email.setReplyTo(addresses);
  }

  /**
   * Set the sent date of this message.
   */
  public void setSentDate(Date sentDate) throws MessagingException
  {
    email.setSentDate(sentDate);
  }

  /**
   * A convenience method that sets the given String as this
   * part's content with a MIME type of "text/plain".
   */
  public void setText(String text) throws MessagingException
  {
    email.setText(text);
  }

  /**
   * Output a bytestream for this Part.
   */
  public void writeTo(OutputStream os) throws IOException, MessagingException
  {
    email.writeTo(os);
  }

}

