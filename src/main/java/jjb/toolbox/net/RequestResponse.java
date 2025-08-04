/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: RequestResponse.java
 * @version v1.0
 * Date: 30 May 2001
 * Modification Date: 17 April 2002
 * @since Java 2
 */

package jjb.toolbox.net;

public class RequestResponse implements java.io.Serializable
{

  private Class[]   parameterTypes = null;

  private Object[]  arguments      = null;

  private Object    returnValue    = null;

  private String    methodName;

  /**
   * Creates an instance of the RequestResponse class.  The
   * RequestResponse object is used to communicate a client's
   * request to the server, and for the server to return it's
   * response back to the client.
   */
  public RequestResponse()
  {
  }

  /**
   * This constructor is used by the client app to form a
   * request to the server app.  The client specifies the
   * name of the server's service and arguments to pass
   * the service.
   *
   * @param methodName is a Ljava.lang.String object specifying
   * the name of the service to invoke on the server.
   * @param arguments is a [Ljava.lang.Object array containing
   * parameter values to pass to the server's service.
   */
  public RequestResponse(String   methodName,
                         Object[] arguments  )
  {
    setMethodName(methodName);

    if (arguments != null)
      validateArgs(arguments);
  }

  /**
   * This constructor is used by the server app to return a
   * response to the client app which sent the request.  The
   * server encapsulates it's response in this object as an
   * answer to the client's request.
   *
   * @param returnValue is a Ljava.lang.Object used to
   * encapsulate any information for the server's response to
   * the client's request.
   */
  public RequestResponse(Object returnValue)
  {
    setReturnValue(returnValue);
  }

  /**
   * getArguments returns the actual values passed to the
   * server's service.
   *
   * @return a [Ljava.lang.Object array containing the
   * values of the parameters to the server's service.
   */
  public Object[] getArguments()
  {
    return arguments;
  }

  /**
   * getMethodName returns the name of the service (remote
   * method call) to invoke on the server.
   *
   * @return Ljava.lang.String of the server service name.
   */
  public String getMethodName()
  {
    return methodName;
  }

  /**
   * getParameterTypes returns the number, order, and types
   * of the arguments to the service.
   *
   * @return a [Ljava.lang.Class array containing type
   * information for the arguments to the server service.
   */
  public Class[] getParameterTypes()
  {
    return parameterTypes;
  }

  /**
   * getReturnValue returns the response by the server as a
   * result of invoking it's service (methodName) and
   * processing the client's request.
   *
   * @return a Ljava.lang.Object representation of the server's
   * response.
   * @throws Ljava.lang.Exception
   */
  public Object getReturnValue() throws Exception
  {
    if (returnValue instanceof Exception)
      throw (Exception) returnValue;

    return returnValue;
  }

  /**
   * setArguments sets the values of the parameters to pass to
   * the server service when invoked.
   *
   * @param arguments:[Ljava.lang.Object array containing the
   * values of the parameters to pass the server service.
   */
  public void setArguments(Object[] arguments)
  {
    if (arguments != null)
      validateArgs(arguments);
  }

  /**
   * setMethodName sets the name of the service to invoke on
   * the remote server.
   *
   * @param methodName:Ljava.lang.String is the name of the
   * servers service (remote method call).
   * @throws Ljava.lang.NullPointerException
   */
  public void setMethodName(String methodName)
  {
    if (methodName == null)
      throw new NullPointerException("Method name determines client request and cannot be null!");

    this.methodName = methodName;
  }

  /**
   * setReturnValue sets the response returned by the server
   * app.
   *
   * @param retVal:Ljava.lang.Object representation of the
   * server's response.
   */  
  public void setReturnValue(Object retVal)
  {
    returnValue = retVal;
  }

  /**
   * validateArgs is called by the constructor to get the
   * data types of the arguments passed by the client app.
   *
   * @param arguments:[Ljava.lang.Object an array of values
   * passed to the remote object to carry out it's service.
   */
  private void validateArgs(Object[] arguments)
  {
    parameterTypes = new Class[arguments.length];

    for (int index = parameterTypes.length; --index >= 0; )
      parameterTypes[index] = arguments[index].getClass();

    this.arguments = arguments;
  }

  /**
   * validateArguments is called by the constructor to get
   * the data types of the arguments passed by the client
   * app.
   *
   * @param arguments:[Ljava.lang.Object an array of values
   * passed to the remote object to carry out it's service.
   * @throws Ljava.lang.IllegalArgumentException
   * @deprecated
   * @see validateArgs
   */
  private void validateArguments(Object[] arguments)
  {
    parameterTypes = new Class[arguments.length];

    for (int index = 0; index < arguments.length; index++)
    {
      Object arg = arguments[index];

      if (arg instanceof Boolean)            parameterTypes[index] = Boolean.class;
      else if (arg instanceof Boolean[])     parameterTypes[index] = Boolean[].class;
      else if (arg instanceof boolean[])     parameterTypes[index] = boolean[].class;
      else if (arg instanceof Byte)          parameterTypes[index] = Byte.class;
      else if (arg instanceof Byte[])        parameterTypes[index] = Byte[].class;
      else if (arg instanceof byte[])        parameterTypes[index] = byte[].class;
      else if (arg instanceof Character)     parameterTypes[index] = Character.class;
      else if (arg instanceof Character[])   parameterTypes[index] = Character[].class;
      else if (arg instanceof char[])        parameterTypes[index] = char[].class;
      else if (arg instanceof Double)        parameterTypes[index] = Double.class;
      else if (arg instanceof Double[])      parameterTypes[index] = Double[].class;
      else if (arg instanceof double[])      parameterTypes[index] = double[].class;
      else if (arg instanceof Float)         parameterTypes[index] = Float.class;
      else if (arg instanceof Float[])       parameterTypes[index] = Float[].class;
      else if (arg instanceof float[])       parameterTypes[index] = float[].class;
      else if (arg instanceof Integer)       parameterTypes[index] = Integer.class;
      else if (arg instanceof Integer[])     parameterTypes[index] = Integer[].class;
      else if (arg instanceof int[])         parameterTypes[index] = int[].class;
      else if (arg instanceof Long)          parameterTypes[index] = Long.class;
      else if (arg instanceof Long[])        parameterTypes[index] = Long[].class;
      else if (arg instanceof long[])        parameterTypes[index] = long[].class;
      else if (arg instanceof Short)         parameterTypes[index] = Short.class;
      else if (arg instanceof Short[])       parameterTypes[index] = Short[].class;
      else if (arg instanceof short[])       parameterTypes[index] = short[].class;
      else if (arg instanceof String)        parameterTypes[index] = String.class;
      else if (arg instanceof String[])      parameterTypes[index] = String[].class;
      else if (arg instanceof Object[])      parameterTypes[index] = Object[].class;
      else if (arg instanceof Object)        parameterTypes[index] = Object.class;
      else
        throw new IllegalArgumentException("Unsupported Type: "+arg.getClass().toString());
    }

    this.arguments = arguments;
  }

}

