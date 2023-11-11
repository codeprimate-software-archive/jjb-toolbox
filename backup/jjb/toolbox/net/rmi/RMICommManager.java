/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: RMICommManager.java
 * @version v1.0
 * Date: 3 June 2001
 * Modification Date: 17 April 2002
 * @since Java 1.0
 * @see jjb.toolbox.net.CommManagerIF
 * @see jjb.toolbox.net.AbstractCommManager
 */

package jjb.toolbox.net.rmi;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import jjb.toolbox.net.AbstractCommManager;
import jjb.toolbox.net.CommException;
import jjb.toolbox.net.RequestResponse;

public class RMICommManager extends AbstractCommManager
{

  private ServerInterface  server;

  private String           hostName;
  private String           portNumber;
  private String           daemon;

  /**
   * Constructs a new instance of the RMICommManager class
   * with the specified host name and daemon name of the
   * process on the remote host.  This constructor defaults
   * the port number to 1099, corresponding to the default
   * port for the RMI registry service.
   *
   * @param hostName:Ljava.lang.String the name of the
   * remote host.
   * @param daemon:Ljava.lang.String the name of the server
   * process running on the remote host.
   */
  public RMICommManager(String hostName,
                        String daemon   )
  {
    this(hostName,"1099",daemon);
  }

  /**
   * Constructs a new instance of the RMICommManager class
   * with the specified host name, port number and daemon
   * name of the process on the remote host.
   *
   * @param hostName:Ljava.lang.String the name of the
   * remote host.
   * @param portNumber:Ljava.lang.String the port number
   * used by the client and host to communicate.
   * @param daemon:Ljava.lang.String the name of the
   * server process running on the remote host.
   * @throws Ljava.lang.NullPointerException is the host
   * parameter is null.
   */
  public RMICommManager(String hostName,
                        String portNumber,
                        String daemon     )
  {
    if (hostName == null)
      throw new NullPointerException("The host of the remote object must be specified.");

    this.hostName = hostName;

    try
    {
      Integer.parseInt(portNumber);
    }
    catch (NumberFormatException nfe)
    {
      System.out.println(portNumber+" is an invalid port number, defaulting to 1099.");
      portNumber  = "1099";
    }

    if (daemon == null)
      throw new NullPointerException("The daemon of the remote server object cannot be null.");

    this.daemon   = daemon;
  }

  /**
   * connect connects to the remote daemon service running
   * on the remote host using the specified port number.
   * The stub and skeleton files are transfered over the
   * netword and used by the client to invoke
   * methods/service on the remote object/service.
   *
   * @throws Ljava.net.MalformedURLException
   * @throws Ljava.rmi.NotBoundException
   * @throws Ljava.rmi.RemoteException
   */
  public void connect() throws CommException
  {
    try
    {
      // Instantiate a SecurityManager object to manage connection & transaction
      // priveleges to the remote server object.
      if (System.getSecurityManager() == null)
        System.setSecurityManager(new RMISecurityManager());

      // Create a reference to the remote server object.
      server = (ServerInterface) Naming.lookup("rmi://"+hostName+":"+portNumber+"/"+daemon);
    }
    // Catch Ljava.net.MalformedURLException
    // Catch Ljava.rmi.NotBoundException
    // Catch Ljava.rmi.RemoteException
    catch (Exception e)
    {
      throw new CommException(e);
    }
  }

  /**
   * disconnect closes the connection between the client
   * and the remote host.
   */
  public void disconnect() throws CommException
  {
    server =  null;
  }

  /**
   * getInputStream returns the input stream for the connection
   * between client and remote host.  This operation is
   * unsupported.
   *
   * @return a Ljava.io.InputStream object to receive information
   * from the remote host.
   * @throws Ljava.lang..UnsupportedOperationException
   */
  public InputStream getInputStream() throws CommException
  {
    throw new UnsupportedOperationException("Operation is not supported.");
  }

  /**
   * getOutputStream returns the output stream for the connection
   * between the client and remote host.  This operation is
   * unsupported.
   *
   * @return a Ljava.io.OutputStream object to send information
   * from the client the remote host.
   * @throws Ljava.lang.UnsupportedOperationException
   */
  public OutputStream getOutputStream() throws CommException
  {
    throw new UnsupportedOperationException("Operation is not supported.");
  }

  /**
   * service method makes a server request for a service
   * defined by methodName argument, passing the arguments
   * to the service for the server to carry out it's given
   * task.  The method then returns the server's response
   * represented as an Object, or throws Exception if the
   * service failed to complete it's task.
   *
   * @param methodName:Ljava.lang.String name of the
   * service (method) to invoke on the server running on
   * the remote host.
   * @param arguments:[Ljava.lang.Object contains values
   * to parameters to pass to the server's service.
   * @return a Ljava.lang.Object response of the server's
   * response.
   * @throws Ljava.lang.Exception
   */
  public Object service(String   methodName,
                        Object[] arguments  ) throws Exception
  {
    final RequestResponse reqresp = new RequestResponse(methodName,arguments);

    Method serverMethod = server.getClass().getMethod(reqresp.getMethodName(),reqresp.getParameterTypes());

    return serverMethod.invoke(server,reqresp.getArguments());
  }

}

