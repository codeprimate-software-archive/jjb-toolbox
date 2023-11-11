/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: HTTPCommManager.java
 * @version v1.0
 * Date: 3 June 2001
 * Modification Date: 17 April 2002
 * @since Java 1.0
 */

package jjb.toolbox.net.http;

import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import jjb.toolbox.net.AbstractCommManager;
import jjb.toolbox.net.CommException;
import jjb.toolbox.net.NotConnectedException;
import jjb.toolbox.net.RequestResponse;

public class HTTPCommManager extends AbstractCommManager
{

  private boolean            doInput         = true;
  private boolean            doOutput        = false;
  private boolean            useCaches       = false;
  private boolean            userInteraction = false;

  private HttpURLConnection  comm;

  private Map                requestProperties;

  private URL                url;

  /**
   * Creates an instance of the HTTPCommManager class in order
   * send HTTP requests.
   */
  public HTTPCommManager()
  {
    this(null);
  }

  /**
   * Contructs a new HTTPCommManager object using the specified
   * URL to refer to the remote host/service (usually a Web
   * Server listening for HTTP request on port 80).
   *
   * @param url:Ljava.net.URL uniform resource locator.
   */
  public HTTPCommManager(URL url)
  {
    this.url = url;
    requestProperties = new HashMap();
  }

  /**
   * allowUserInteraction allows the user to be queried for
   * passwords. Note that the HttpURLConnection itself has
   * no facilities for executing such a query.  The query
   * must be carried out by an external program such as a
   * browser or browser plugin.
   *
   * @param userInteraction is a boolean value indicating
   * true if the  user can be queried for a password.
   */
  public void allowUserInteraction(boolean userInteraction)
  {
    this.userInteraction = userInteraction;
  }

  /**
   * connect connects to the remote host/service as referred
   * to by the URL.
   */
  public void connect() throws CommException
  {
    connect(url);
  }

  /**
   * connect opens a connection to the specified resource
   * (host/service) using the URL object.
   *
   * @param url:Ljava.net.URL uniform resource locator.
   */
  public void connect(URL url) throws CommException
  {
    try
    {
      if (url == null)
        throw new MalformedURLException("\""+url+"\" is not a valid URL.");

      String protocol = url.getProtocol();

      if (!protocol.toLowerCase().equals("http"))
        throw new IOException(protocol+" protocol is an invalid protocol for the HTTPCommManager object.");

      disconnect();
      comm = (HttpURLConnection) url.openConnection();
      comm.setAllowUserInteraction(userInteraction);
      comm.setDoInput(doInput);
      comm.setDoOutput(doOutput);
      comm.setUseCaches(useCaches);

      Object key = null;

      for (Iterator i = requestProperties.keySet().iterator(); i.hasNext(); )
        comm.setRequestProperty((String) (key = i.next()),(String) requestProperties.get(key));

      //comm.connect();
    }
    catch (Exception e)
    {
      throw new CommException(e);
    }
  }

  /**
   * disconnect closes the connection to the remote
   * host/service referred to by the URL.
   */
  public void disconnect() throws CommException
  {
    comm.disconnect();
    comm = null;
  }

  /**
   * getHttpConnection returns the HttpURLConnection object
   * managed by this HTTPCommManager object to communicate
   * with the remote host/service.
   *
   * @return a Ljava.net.HttpURLConnection object used to
   * send http request on behalf of the client.
   */
  public HttpURLConnection getHttpConnection()
  {
    return comm;
  }

  /**
   * getInputStream returns an input stream object to read
   * content sent by the remote host/service.
   *
   * @return a Ljava.io.InputStream object to read content
   * from the input stream.
   */
  public InputStream getInputStream() throws CommException
  {
    try
    {
      if (comm == null)
        throw new NotConnectedException("Connection to the remote object has not been established.");

      return comm.getInputStream();
    }
    catch (IOException ioe)
    {
      throw new CommException(ioe);
    }
  }

  /**
   * getOutputStream returns the output stream object to
   * send content to the remote host/service.
   *
   * @return a Ljava.io.OutputStream object to send content
   * over the wire to the remote host/service.
   */
  public OutputStream getOutputStream() throws CommException
  {
    try
    {
      if (comm == null)
        throw new NotConnectedException("Connection to the remote object has not been established.");

      return comm.getOutputStream();
    }
    catch (IOException ioe)
    {
      throw new CommException(ioe);
    }
  }

  /**
   * getURL returns the url for which this CommManagerIF
   * object uses to refer/connect to the remote host/service.
   *
   * @return a Ljava.net.URL uniform resource locator.
   */
  public URL getURL()
  {
    return url;
  }

  /**
   * listen opens a server socket connection on the specified
   * port to listen for client requests.  listen is an
   * unsupported operation in this class.
   *
   * @param port is an integer specifying the port on which to
   * listen for client request.
   * @throws Ljjb.toolbox.util.UnsupportedOperationException.
   */
  public boolean listen(int port) throws CommException
  {
    throw new UnsupportedOperationException("Operation is not supported at this time!");
  }

  /**
   * listen opens a server socket connection on the specified
   * port to listen for client requests for a specified number
   * of milliseconds before the operation times out.  listen
   * is an unsupported operation in this class.
   *
   * @param port is an integer specifying the port on which to
   * listen for client request.
   * @param timeout is an integer value specifying the number
   * of milliseconds before the operation times out.
   * @throws Ljjb.toolbox.util.UnsupportedOperationException.
   */
  public boolean listen(int port,
                        int timeout) throws CommException
  {
    throw new UnsupportedOperationException("Operation is not supported at this time!");
  }

  /**
   * receive receives a object from the remote host/service.
   *
   * @return a Ljava.lang.Object encapsulating the content
   * sent by the remote host/service to this client.
   */
  public Object receive() throws CommException
  {
    try
    {
      setDoInput(true);

      if (comm == null)
        connect();

      ObjectInputStream	in = new ObjectInputStream(comm.getInputStream());

      Object obj = in.readObject();

      in.close();
      disconnect();

      return obj;
    }
    // Catch ClassNotFoundException
    // Catch IOException
    catch (Exception e)
    {
      throw new CommException(e);
    }
  }

  /**
   * send sends a Serializable object to the remote
   * host/service.
   *
   * @param obj:Ljava.io.Serializable object to send
   * over the wire to the remote host/service.
   */
  public void send(Serializable obj) throws CommException
  {
    try
    {
      setDoOutput(true);
      setRequestProperty("Content-Type","java-internal/"+(obj == null ? null : obj.getClass().getName()));

      if (comm == null)
        connect();

      ObjectOutputStream out = new ObjectOutputStream(comm.getOutputStream());

      out.writeObject(obj);
      out.flush();
      out.close();
      disconnect();
    }
    catch (IOException ioe)
    {
      throw new CommException(ioe);
    }
  }

  /**
   * sendGETRequest sends a GET request to the remote
   * host/service referred to by the URL object, appended
   * with the specified query String.
   *
   * @param query:Ljava.util.Properties object containing
   * the name/value pairs for the query String.
   * @return a Ljava.lang.Object representation of the
   * content sent by the server to this client.
   */
  public Object sendGETRequest(Properties query) throws Exception
  {
    Object returnValue = null;

    if (url == null)
      throw new MalformedURLException("\""+url+"\" is not a valid URL.");

    URL _url	= new URL(url.toExternalForm()+(query == null ? "" : "?"+toURLEncodedString(query)));

    setDoOutput(true);
    connect(_url);

    if (doInput)
    {
      ObjectInputStream in = new ObjectInputStream(comm.getInputStream());

      returnValue = in.readObject();
      in.close();
      disconnect();
    }

		return returnValue;
  }

  /**
   * sendPOSTRequest sends a POST request to the remote
   * host/service using the specified RequestResponse object
   * to encapsulate request information and to receive the
   * server's response.
   *
   * @param reqResp:Ljjb.toolbox.comm.RequestResponse object
   * encapsulating client request and server response
   * information.
   * @return a Ljava.lang.Object of the content received by
   * the client contained in the RequestResponse object.
   */
  public Object sendPOSTRequest(RequestResponse reqResp) throws Exception
  {
    Object returnValue = null;

    setDoOutput(true);
    setRequestProperty("Content-Type","java-internal/"+reqResp.getClass().getName());

    if (comm == null)
      connect();

    ObjectOutputStream out = new ObjectOutputStream(comm.getOutputStream());

    out.writeObject(reqResp);
    out.flush();
    out.close();

    if (doInput)
    {
      ObjectInputStream	in = new ObjectInputStream(comm.getInputStream());

      reqResp = (RequestResponse) in.readObject();
      returnValue = reqResp.getReturnValue();
      in.close();
      disconnect();
    }

    return returnValue;
  }

  /**
   * setDoInput sets whether the HTTPCommManager object can
   * receive information from the remote host/service.
   *
   * @param doInput is a boolean value indicating true if
   * the HTTP connection can receive content using the
   * InputStream, false otherwise.
   */
  public void setDoInput(boolean doInput)
  {
    this.doInput = doInput;
  }

  /**
   * setDoOutput sets whether the HTTCommManager object can send
   * information over the wire to the remote host/service.
   *
   * @param doOutput is a boolean value indicating true if this
   * http connection can send content using the OutputStream,
   * false otherwise.
   */
  public void setDoOutput(boolean doOutput)
  {
    this.doOutput = doOutput;
  }

  /**
   * setRequestProperty sets a request property.  The key/value
   * pair must be meaningful for the request type.
   *
   * @param key:Ljava.lang.String representing the request key,
   * usually a request header (i.e. Content-Type).
   * @param value:Ljava.lang.String representing the value for
   * the corresponding request header key (i.e. "text/html").
   */
  private void setRequestProperty(String key,
                                  String value)
  {
    requestProperties.put(key,value);
  }

  /**
   * setURL sets the url to the remote host/service to which
   * this CommManagerIF object will communicate.
   *
   * @param url:Ljava.net.URL uniform resource locator to the
   * remote host/service.
   */
  public void setURL(URL url)
  {
    this.url = url;
  }

  /**
   * setUseCaches sets whether the http connection should cache
   * the content received from the remote host/service for quick
   * reference on subsequent duplicate request.  Note that this
   * method only applies in contextes where caches are
   * implemented, such as Web Browsers for Applets.
   *
   * @param useCaches is a boolean value indicating true is
   * requests should be cached.
   */
  public void setUseCaches(boolean useCaches)
  {
    this.useCaches = useCaches;
  }

  /**
   * toURLEncodedString converts a Properties object to an URL-encoded
   * query String of the form: name=value&...
   *
   * @param args:Ljava.util.Properties is the list of arguments to form
   * the query.
   * @return a Ljava.lang.String URL encoded query of the property
   * arguments.
   */
	private String toURLEncodedString(Properties args)
  {
    if (args == null)
      return "";

    StringBuffer query = new StringBuffer();

    Enumeration names = args.propertyNames();

    String name = null;

    while (names.hasMoreElements())
    {
      name = names.nextElement().toString();
      query.append(name).append("=").append((String) args.getProperty(name));

      if (names.hasMoreElements())
        query.append("&");
    }

    return URLEncoder.encode(query.toString());
	}

}

