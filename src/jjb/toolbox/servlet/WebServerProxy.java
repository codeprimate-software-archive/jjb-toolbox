/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: WebServerProxy.java
 * @version v1.0
 * Date: 30 August 2001
 * Modification Date: 15 April 2003
 * @since Java 2
 */

package jjb.toolbox.servlet;

import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class WebServerProxy extends HttpServlet
{

  /**
   * doGet handles GET method request from a HTTP client such as a web browser or a
   * Java applet.  doGet processes the request by calling the handleRequest method.
   *
   * @param req:Ljavax.servlet.http.HttpServletRequest object encapsulates the request
   * made by the client to the HTTP server.
   * @param resp:Ljavax.servlet.http.HttpServletResponse object encapsulates the server's
   * response to the client's request.
   * @throws Ljava.io.IOException
   * @throws Ljavax.servlet.ServletException
   */
  public void doGet(HttpServletRequest  req,
                    HttpServletResponse resp) throws IOException, ServletException
  {
    handleRequest(req,resp);
  }

  /**
   * doPost handles POST method request from a HTTP client such as a web browser or a
   * Java applet.  doPost processes the request by calling the handleRequest method.
   *
   * @param req:Ljavax.servlet.http.HttpServletRequest object encapsulates the request
   * made by the client to the HTTP server.
   * @param resp:Ljavax.servlet.http.HttpServletResponse object encapsulates the server's
   * response to the client's request.
   * @throws Ljava.io.IOException
   * @throws Ljavax.servlet.ServletException
   */
  public void doPost(HttpServletRequest  req,
                     HttpServletResponse resp) throws IOException, ServletException
  {
    handleRequest(req,resp);
  }

  /**
   * handleRequest handles both GET/POST method request from a HTTP client such as a web
   * browser or a Java applet.
   *
   * @param req:Ljavax.servlet.http.HttpServletRequest object encapsulates the request
   * made by the client to the HTTP server.
   * @param resp:Ljavax.servlet.http.HttpServletResponse object encapsulates the server's
   * response to the client's request.
   * @throws Ljava.io.IOException
   * @throws Ljavax.servlet.ServletException
   */
  public void handleRequest(HttpServletRequest  req,
                            HttpServletResponse resp) throws IOException, ServletException
  {
    OutputStream out = resp.getOutputStream();

    String urlStr = req.getParameter("url");

    if (urlStr == null)
      return;

    try
    {
      URL url = new URL(URLDecoder.decode(urlStr, req.getCharacterEncoding()));

      URLConnection connection = url.openConnection();

      connection.setUseCaches(false);

      InputStream in = connection.getInputStream();

      byte[] buffer = new byte[4096];

      int len = -1;

      while ((len = in.read(buffer,0,buffer.length)) > -1)
        out.write(buffer,0,len);

      out.flush();
    }
    catch (MalformedURLException murle)
    {
      OutputStreamWriter writer = new OutputStreamWriter(out);

      String message = "<p><h1> The URL "+urlStr+" is not valid. </h1></p>";

      writer.write(message,0,message.length());
      writer.flush();
    }
    catch (UnsupportedEncodingException e) {
      OutputStreamWriter writer = new OutputStreamWriter(out);

      String message = "<p><h1> The Character Encoding '" + req.getCharacterEncoding()
        + " is not valid. </h1></p>";

      writer.write(message,0,message.length());
      writer.flush();
    }
    catch (IOException ioe)
    {
      OutputStreamWriter writer = new OutputStreamWriter(out);

      String message = "<p><h1> This service is currently not available, please try back again later. </h1></p>";

      writer.write(message,0,message.length());
      writer.flush();
    }
  }

  /**
   * init initializes and configures this Servlet.
   *
   * @param config:Ljavax.servlet.ServletConfig object used to initialize this Servlet
   * for service.
   */
  public void init(ServletConfig config)
  {
  }

}

