/**
 * Copyright (c) 2002, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: AbstractCommManager.java
 * @version v1.0
 * Date: 24 February 2002
 * Modification Date: 17 April 2002
 * @since Java 2
 * @see jjb.toolbox.net.CommManagerIF
 */

package jjb.toolbox.net;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.Observable;

public abstract class AbstractCommManager extends Observable implements CommManagerIF
{

  protected InputStream   inStream;

  protected OutputStream  outStream;

  protected Socket        socket;

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
  public void connect() throws CommException
  {
    throw new UnsupportedOperationException("Operation is not supported.");
  }

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
  public void disconnect() throws CommException
  {
    throw new UnsupportedOperationException("Operation is not supported.");
  }

  /**
   * getInputStream returns the input stream object for this connection.
   *
   * @return Ljava.io.InputStream
   * throws Ljjb.toolbox.net.CommException if a communication problem
   * occurs.
   * @throws Ljava.lang.UnsupportedOperationException if this
   * operation is not supported, or not implemented.
   */
  public InputStream getInputStream() throws CommException
  {
    return inStream;
  }

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
  public OutputStream getOutputStream() throws CommException
  {
    return outStream;
  }

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
  public boolean listen(int port) throws CommException
  {
    throw new UnsupportedOperationException("Operation is not supported.");
  }

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
                        int timeout) throws CommException
  {
    throw new UnsupportedOperationException("Operation is not supported.");
  }

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
  public Object receive() throws CommException
  {
    throw new UnsupportedOperationException("Operation is not supported.");
  }

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
  public void send(Serializable message) throws CommException
  {
    throw new UnsupportedOperationException("Operation is not supported.");
  }

}

