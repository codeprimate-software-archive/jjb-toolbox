/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: RMIServlet.java
 * @version v1.0
 * Date: 10 June 2001
 * Modification Date: 17 April 2002
 * @since Java 2
 */

package jjb.toolbox.servlet;

import java.io.*;
import java.lang.reflect.*;
import javax.servlet.*;
import javax.servlet.http.*;
import jjb.toolbox.net.RequestResponse;
import jjb.toolbox.util.UserException;

public class RMIServlet extends HttpServlet
{

  private PrintWriter  logFile;

  /**
   * doPost handles an HTTP POST method request sent by a client to this
   * Servlet.
   *
   * @param request:Ljavax.servlet.http.HttpServletRequest object encapsulating
   * information about the client's request.
   * @param response:Ljavax.servlet.http.HttpServletResponse object encapsulating
   * the information (response) to send back to the client on behalf of the client's
   * request.
   * @throws Ljava.io.IOException
   * @throws Ljavax.servlet.ServletException
   */
  public void doPost(HttpServletRequest  request, 
                     HttpServletResponse response) throws IOException, ServletException
  {
    RequestResponse reqresp = null;

    try
    {
      ObjectInputStream objInStream = new ObjectInputStream(request.getInputStream());

      reqresp = (RequestResponse) objInStream.readObject();

      Method servletMethod = this.getClass().getMethod(reqresp.getMethodName(),reqresp.getParameterTypes());

      reqresp.setReturnValue(servletMethod.invoke(this,reqresp.getArguments()));
    }
    catch (InvocationTargetException ite)
    {
      Throwable texc = ite.getTargetException();

      if (texc instanceof UserException)
        reqresp.setReturnValue(texc);
      else if (logFile != null)
        ite.printStackTrace(logFile);
    }
    catch (ClassNotFoundException cnfe)
    {
      if (logFile != null)
        cnfe.printStackTrace(logFile);
    }
    catch (NoSuchMethodException nsme)
    {
      if (logFile != null)
        nsme.printStackTrace(logFile);
    }
    catch (IllegalAccessException iae)
    {
      if (logFile != null)
        iae.printStackTrace(logFile);
    }
    finally
    {
      response.setContentType("java-internal/"+reqresp.getClass().getName());

      ObjectOutputStream objOutStream = new ObjectOutputStream(response.getOutputStream());

      objOutStream.writeObject(reqresp);
      objOutStream.flush();
      objOutStream.close();
    }
  }

}

