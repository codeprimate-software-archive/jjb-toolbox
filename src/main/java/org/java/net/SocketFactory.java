/**
 * Copyright (c) 2001, Sun Microsystems, Inc.
 * ALL RIGHTS RESERVED
 *
 * This class was taken from the Core Java 2 Volume II - Advanced
 * Features book, Prentice Hall, 2000
 * ISBN 0-13-081934-4
 * Chapter 3 - Networking
 *
 * @author Cay S. Horstmann & Gary Cornell
 * Modified By: John J. Blum
 * File: SocketFactory.java
 * @version v1.0.1
 * Date: 15 August 2001
 * Modification Date: 19 March 2002
 * @since Java 1.0
 */

package org.java.net;

import java.io.IOException;
import java.net.Socket;

public class SocketFactory implements Runnable
{
  
  private int     port;

  private Socket  socket;

  private String  host;

  /**
   * Creates an instance of the SocketFactory class by specifying
   * the host to connect to and the port on which to establish
   * the connection.
   *
   * @param host:Ljava.lang.String remote machine in which to
   * connect specified as either the IP address or the domain
   * name.
   * @param port an integer number of the port on which the
   * remote machine is listening for connections.
   */
  public SocketFactory(String host,
                       int    port )
  {
    this.host = host;
    this.port = port;
  }

  /**
   * getSocket returns the socket for which the caller tried to
   * create.
   *
   * @return a Ljava.net.Socket object for the connection
   * between the client and server.
   */
  public Socket getSocket()
  {
    return socket;
  }

  /**
   * openSocket tries to connect to the remote host in the
   * specified number of milliseconds (timeout).
   *
   * @param host:Ljava.lang.String remote machine in which to
   * connect specified as either the IP address or the domain
   * name.
   * @param port an integer number of the port on which the
   * remote machine is listening for connections.
   * @param timeout is the length in milliseconds to wait for
   * the remote host connection to timeout.
   */
  public static Socket openSocket(String host,
                                  int    port,
                                  int    timeout) throws InterruptedException
  {
    final SocketFactory socketOpener = new SocketFactory(host,port);

    final Thread t = new Thread(socketOpener);

    t.start();
    t.join(timeout);

    return socketOpener.getSocket();
  }

  /**
   * run is invoked by the Thread created in
   * SocketOpener.openSocket in order to establish a connection
   * to a remote host in a specified number of milliseconds.
   */
  public void run()
  {
    try
    {
      socket = new Socket(host,port);
    }
    catch (IOException ioe)
    {
      socket = null;
    }
  }

}

