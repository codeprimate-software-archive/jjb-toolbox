/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @auther John J. Blum
 * File: TalkCommManager.java
 * @version v1.0
 * Date: 22 April 2001
 * Modification Date: 11 July 2002
 * @since Java 1.0
 * @see jjb.toolbox.net.CommManagerIF
 * @see jjb.toolbox.net.AbstractCommManager
 */

package jjb.toolbox.net.talk;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observer;
import jjb.toolbox.net.AbstractCommManager;
import jjb.toolbox.net.CommException;
import jjb.toolbox.net.MessageIF;
import jjb.toolbox.net.talk.event.TalkEventMulticaster;
import jjb.toolbox.net.talk.event.TalkListener;

public class TalkCommManager extends AbstractCommManager implements Runnable
{

  private boolean             CONNECTED     = false;

  private ObjectInputStream   objInStream   = null;

  private ObjectOutputStream  objOutStream  = null;

  private ServerSocket        server        = null;

  private TalkListener        talkListener  = null;

  /**
   * Creates a new instance of the TalkCommManager class to
   * conduct Point To Point communication (or one-on-one
   * chat).
   */
  public TalkCommManager()
  {
  }

  /**
   * Creates a new instance of the TalkCommManager class to
   * conduct Point To Point communication (or one-on-one
   * chat).  Also setup an observer to observe this
   * TalkCommManager object.
   *
   * @param observer:Ljava.util.Observer object which
   * observes the talk manager for events, or network
   * communication activity.
   */
  public TalkCommManager(Observer observer)
  {
    if (observer != null)
      addObserver(observer);
  }

  /**
   * addTalkListener adds the specified event TalkListener
   * to this object listen for TalkEvents.
   *
   * @param tl:Ljjb.toolbox.comm.talk.event.TalkListener
   * object to add to the list of listeners for this
   * CommManagerIF object.
   */
  public synchronized void addTalkListener(TalkListener tl)
  {
    if (tl == null)
      return;

    talkListener = TalkEventMulticaster.add(talkListener,tl);
  }

  /**
   * connect attempts to establish a connection between a
   * pair of applications using a client socket to connect
   * with a server socket listening on the remote host
   * running an server application.
   *
   * @param host:Ljava.lang.String the name of the remote
   * host, or it's IP address.
   * @param port:int the port on which the remote host
   * application is listening.
   */
  public void connect(String  host,
                      int     port ) throws CommException
  {
    try
    {
      if (socket != null)
        throw new IOException("Connection already established!\n  Call disconnect, and try again.");

      InetAddress IP = InetAddress.getByName(host);

      socket = new Socket(IP,port);
      objInStream = new ObjectInputStream(inStream = socket.getInputStream());
      objOutStream = new ObjectOutputStream(outStream = socket.getOutputStream());
      CONNECTED = true;
      new Thread(this).start();
    }
    // Catch IOException
    catch (Exception e)
    {
      throw new CommException(e);
    }
  }

  /**
   * disconnect attempts to close a connection between this
   * application and an application running on a remote host.
   */
  public void disconnect() throws CommException
  {
    try
    {
      CONNECTED = false;
      objInStream.close();
      objOutStream.close();
      inStream.close();
      outStream.close();
      socket.close();
      objInStream = null;
      objOutStream = null;
      inStream = null;
      outStream = null;
      socket = null;
    }
    // Catch IOException
    catch (Exception e)
    {
      throw new CommException(e);
    }
  }

  /**
   * listen sets up a server socket to listen for incoming
   * client's request to establish a connection on the given
   * port.  This overloaded method calls the two-argument
   * listen method by passing the client-defined port and
   * setting the timeout to 0 (infinite timeout).
   *
   * @param port is an integer value specifying a port on
   * which to listen for client connections.
   * @return a boolean of true to indicate a succesful
   * connection, false otherwise.
   */
  public boolean listen(int port) throws CommException
  {
    return listen(port,0);
  }

  /**
   * listen sets up a server socket to listen for incoming
   * client's request to establish a connection.
   *
   * @param port is an integer value specifying a port on
   * which to listen for client connections.
   * @param timeout is an integer specifying the timeout,
   * in milliseconds.
   * @return a boolean of true to indicate a succesful
   * connection, false otherwise.
   */
  public boolean listen(int port,
                        int timeout) throws CommException
  {
    try
    {
      if (server == null)
        server = new ServerSocket(port);

      if (timeout < 0)
        timeout = 0;

      server.setSoTimeout(timeout);
      socket = server.accept();  // blocks for specified "timeout" and then throws java.io.InterruptedIOException
      objInStream = new ObjectInputStream(inStream = socket.getInputStream());
      objOutStream = new ObjectOutputStream(outStream = socket.getOutputStream());
      CONNECTED = true;
      new Thread(this).start();

      return (socket != null);
    }
    // Catch Ljava.lang.InterruptedException
    catch (Exception e)
    {
      throw new CommException(e);
    }
  }

  /**
   * receive is called to receive information passed to
   * this application by the server application running
   * on the remote host.
   *
   * @return a Ljava.lang.Object of the data sent by the
   * server to this client application.
   */
  public Object receive() throws CommException
  { 
    try
    {
      return (MessageIF) objInStream.readObject();
    }
    // Catch Ljava.lang.ClassNotFoundException
    catch (Exception e)
    {
      // ridiculous, this should never happen since I wrote the class!
      throw new CommException(e);
    }
  }

  /**
   * removeTalkListener removes the specified event
   * listener from this object, therefore, it will no
   * longer receive talk events.
   *
   * @param tl:Ljjb.toolbox.comm.talk.event.TalkListener
   * object to remove from the list of listeners.
   */
  public synchronized void removeTalkListener(TalkListener tl)
  {
    if (tl == null)
      return;

    talkListener = TalkEventMulticaster.remove(talkListener,tl);
  }

  /**
   * run is invoked from a Thread created by the connect
   * & listen methods.  It is tasked with recieving
   * messages from the remote host application and
   * notifying any observers.
   */
  public void run()
  {
    while (CONNECTED)
    {
      try
      {
        MessageIF message = (MessageIF) receive();

        if (message == null) continue;

        setChanged();
        notifyObservers(message);
      }
      catch (Exception ignore)
      {
        // does this mean a collision?
      }
    }
  }

  /**
   * send pipes a message to the application running on
   * the remote host.
   *
   * @param message:Ljava.io.Serializable object to send
   * to the server application running on the remote host.
   */
  public void send(Serializable message) throws CommException
  {
    try
    {
      objOutStream.writeObject(message);
    }
    // Catch Ljava.io.IOException
    catch (Exception e)
    {
      throw new CommException(e);
    }
  }

}

