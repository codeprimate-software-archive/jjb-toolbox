/*
 * ProtocolCapture.java (c) 2003.3.8
 *
 * Copyright (c) 2003, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.3.23
 * @since Java 1.4
 */

package jjb.toolbox.net;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;

public class ProtocolCapture {

  private static final int BUFFER_SIZE = 1024;
  private static final int TIMEOUT = 30000; // 30 seconds.

  /**
   * This method starts a Server listening on the specified port
   * waiting for a client request.  Once a client has connected
   * the information transfered is captured and formatted and
   * the request is returned.
   */
  public static final byte[] captureRequest(final int port)
      throws IOException {
    final ServerSocket server = new ServerSocket(port);
    final Socket client = server.accept();

    final ByteArrayOutputStream requestBuffer = new ByteArrayOutputStream(BUFFER_SIZE);
    final BufferedInputStream reader = new BufferedInputStream(client.getInputStream());

    byte[] buffer = new byte[BUFFER_SIZE];
    int len = BUFFER_SIZE;
    
    while (len >= BUFFER_SIZE) {
      len = reader.read(buffer,0,BUFFER_SIZE);
      requestBuffer.write(buffer,0,len);
    }

    requestBuffer.close(); // has no effect.
    reader.close();
    client.close();
    server.close();

    return requestBuffer.toByteArray();
  }

  /**
   * This method opens a client socket and sends a well formatted
   * request to a server @ address listening on the specified port
   * number.  The servers's response is then captured, formatted
   * in human readable form and returned from this method.
   */
  public static final byte[] captureResponse(final String address,
                                             final int port       )
      throws IOException {
    return captureResponse(InetAddress.getByName(address),port,captureRequest(port));
  }
  
  public static final byte[] captureResponse(final InetAddress ip,
                                             final int port,
                                             final byte[] request )
      throws IOException {
    final SocketAddress address = new InetSocketAddress(ip,port);
    final Socket client = new Socket();

    try {
      client.connect(address,TIMEOUT);

      final BufferedInputStream in =
        new BufferedInputStream(client.getInputStream());
      final BufferedOutputStream out =
        new BufferedOutputStream(client.getOutputStream());

      out.write(request,0,request.length);
      out.flush();

      final ByteArrayOutputStream responseBuffer =
        new ByteArrayOutputStream(BUFFER_SIZE);
      byte[] buffer = new byte[BUFFER_SIZE];
      int len = BUFFER_SIZE;
      
      while (len >= BUFFER_SIZE) {
        len = in.read(buffer,0,BUFFER_SIZE);
        responseBuffer.write(buffer,0,len);
      }

      responseBuffer.close();
      in.close();
      out.close();
      client.close();
      
      return responseBuffer.toByteArray();
    }
    catch (SocketTimeoutException e) {
      System.err.println("Timeout expired; unable to connect after " 
        + TIMEOUT + " seconds!");
    }
    
    return null;
  }

  /**
   * If used from the command-line, the method outputs a the help
   * information.
   */
  private static final void usage() {
    System.out.println("\n\n> java jjb.toolbox.net.ProtocolRequestCapture <port>\n");
  }

  /**
   * Ensures that the port number is a valid numerical value.
   */
  private static final void validate(final int port) {
    if (port < 1) {
      throw new IllegalArgumentException(port 
        + " is not a valid port number.  The port must be greater than 1.");
    }
  }

  /**
   * Main executable method of this program.
   */
  public static void main(String[] args) throws Exception {
    if (args.length == 0) {
      usage();
      System.exit(0);
    }

    int port = -1;
    try {
      port = Integer.parseInt(args[0]);
    }
    catch (NumberFormatException nfe) {
      System.err.println(args[0] + " is not a valid port!");
      System.exit(0);
    }

    validate(port);
    System.out.println("\n"+captureRequest(port));
  }

}

