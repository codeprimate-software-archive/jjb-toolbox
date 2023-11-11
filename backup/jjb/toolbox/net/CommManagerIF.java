/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @auther John J. Blum
 * File: CommManagerIF.java
 * @version v1.0
 * Date: 17 April 2001
 * Modification Date: 17 April 2002
 * @since Java 1.0
 */

package jjb.toolbox.net;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

public interface CommManagerIF
{

  /**
   * connect is used by a client to open a TCP/IP connection between
   * a pair of applications with intentions not defined.  This default
   * implementation does nothing since protocols like HTTP are
   * stateless, and do not require persistent connections.
   * 
   * throws Ljjb.toolbox.net.CommException if a communication problem
   * occurs.
   * @throws Ljava.lang.UnsupportedOperationException if this
   * operation is not supported, or not implemented.
   */
  public void connect() throws CommException;

  /**
   * disconnect closes any connection to a remote host.  The default
   * implementation throws an UnsupportedOperationException since
   * protocols like HTTP are stateless, and do not require persistent
   * connections.
   *
   * throws Ljjb.toolbox.net.CommException if a communication problem
   * occurs.
   * @throws Ljava.lang.UnsupportedOperationException if this
   * operation is not supported, or not implemented.
   */
  public void disconnect() throws CommException;

  /**
   * getInputStream returns the input stream object for this connection.
   *
   * @return Ljava.io.InputStream
   * throws Ljjb.toolbox.net.CommException if a communication problem
   * occurs.
   * @throws Ljava.lang.UnsupportedOperationException if this
   * operation is not supported, or not implemented.
   */
  public InputStream getInputStream() throws CommException;

  /**
   * getOutputStream returns the output stream object for this
   * connection.
   *
   * @return Ljava.io.OutputStream
   * throws Ljjb.toolbox.net.CommException if a communication problem
   * occurs.
   * @throws Ljava.lang.UnsupportedOperationException if this
   * operation is not supported, or not implemented.
   */
  public OutputStream getOutputStream() throws CommException;

  /**
   * listen sets this communication manager up as a server listening
   * on a specified port for client's request to connect.
   *
   * @param port an integer specifying the port number in which to
   * listen for client connections.
   * @return boolean to indicate a succesful connection.
   * throws Ljjb.toolbox.net.CommException if a communication problem
   * occurs.
   * @throws Ljava.lang.UnsupportedOperationException if this
   * operation is not supported, or not implemented.
   */
  public boolean listen(int port) throws CommException;

  /**
   * The overloaded listen method sets this communication manager up
   * as a server listening on a specified port for client's request
   * to connect and does so for only a specified amount of time.
   *
   * @param port an integer specifying the port number in which to
   * listen for client connections.
   * @param timeout an integer specifying the number of milliseconds
   * to time out the server's wait to listen for connections.
   * @return boolean to indicate a succesful connection.
   * throws Ljava.lang.CommException
   * throws Ljjb.toolbox.net.CommException if a communication problem
   * occurs.
   * @throws Ljava.lang.UnsupportedOperationException if this
   * operation is not supported, or not implemented.
   */
  public boolean listen(int port,
                        int timeout) throws CommException;

  /**
   * receive is called in an asychronous form of communication
   * to receive Serializable objects from the remote object over the
   * established connection.
   *
   * @return Ljava.lang.Object from the remote object (server).
   * throws Ljjb.toolbox.net.CommException if a communication problem
   * occurs.
   * @throws Ljava.lang.UnsupportedOperationException if this
   * operation is not supported, or implemented.
   */
  public Object receive() throws CommException;

  /**
   * send sends a Serializable object to a remote object over the
   * established connection.
   *
   * @param message:Ljjb.toolbox.comm.MessageIF the message to send.
   * throws Ljjb.toolbox.net.CommException if a communication problem
   * occurs.
   * @throws Ljava.lang.UnsupportedOperationException if this
   * operation is not supported, or not implemented.
   */
  public void send(Serializable obj) throws CommException;

}

