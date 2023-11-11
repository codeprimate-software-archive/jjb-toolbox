/**
 * Copyright (c) 2001, JB Innovations
 * ALL RIGHTS RESERVED
 *
 * @author John J. Blum
 * File: JFTP.java
 * @version beta1.0
 * Date: 14 December 2001
 * Modification Date: 20 December 2001
 */

package jjb.toolbox.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.InterruptedIOException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public final class JFTP
{

  private static final boolean DEBUG = false;

  private static final String PROMPT = "\n>";

  private int             port1,
                          port2;

  private BufferedReader  ftpServerReader;

  private BufferedWriter  ftpServerWriter;

  private InetAddress     localHost;

  private Socket          ftpServerConnection;

  private final String    localHostIP;

  public JFTP()
  {
    localHostIP = null;
  }

  public JFTP(String ipAddress) throws UnknownHostException
  {
    port1 = 16;
    port2 = 150;
    localHost = InetAddress.getLocalHost();

    final StringTokenizer ipParser = new StringTokenizer(localHost.getHostAddress(),".");

    StringBuffer ipStr = new StringBuffer();

    for ( ; ipParser.hasMoreTokens(); )
      ipStr.append(ipParser.nextElement().toString()).append(",");

    localHostIP = ipStr.toString();

    runCommandLine(ipAddress);
  }

  /**
   * connect
   */
  public String connect(String ipAddress) throws IOException, UnknownHostException
  {
    ftpServerConnection = new Socket(ipAddress,21);
    ftpServerConnection.setSoTimeout(5000);
    ftpServerReader = new BufferedReader(new InputStreamReader(ftpServerConnection.getInputStream()));
    ftpServerWriter = new BufferedWriter(new OutputStreamWriter(ftpServerConnection.getOutputStream()));

    try
    {
      return ftpServerReader.readLine();
    }
    catch (InterruptedIOException ignore)
    {
    }

    return null;
  }

  /**
   * list
   */
  public List list() throws IOException
  {
    final List dir = new ArrayList();

    final ServerSocket server = new ServerSocket(port1*FTPConstants.PORT_MULTIPLIER+port2);

    System.out.println(port(port1,port2++));
    ftpServerWriter.write(FTPConstants.LIST+FTPConstants.EOL);
    ftpServerWriter.flush();

    final Socket connection = server.accept();

    final BufferedReader dirReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

    String file = null;

    while ((file = dirReader.readLine()) != null)
      dir.add(file);

    dirReader.close();
    connection.close();
    server.close();

    /**try
    {
      final StringBuffer ftpServerResponse = new StringBuffer();

      String msg = null;

      while ((msg = ftpServerReader.readLine()) != null)
        ftpServerResponse.append(msg).append("\n");

      System.out.println(ftpServerResponse.toString());
    }
    catch (InterruptedIOException ignore)
    {
      ignore.printStackTrace();
    }*/

    return dir;
  }

  /**
   * login
   */
  private void login(BufferedReader commandLineReader) throws IOException
  {
    String username = null;
    String response = null;

    System.out.print("Enter your username: ");

    if ((response = user(username = commandLineReader.readLine())) != null)
      System.out.println("\n"+response);

    System.out.print("Enter "+username+"'s password: ");

    if ((response = pass(commandLineReader.readLine())) != null)
      System.out.println("\n"+response);
  }

  /**
   * pass
   */
  public String pass(String password) throws IOException
  {
    ftpServerWriter.write(FTPConstants.PASS+" "+password+FTPConstants.EOL);
    ftpServerWriter.flush();

    try
    {
      return ftpServerReader.readLine();
    }
    catch (InterruptedIOException ignore)
    {
    }

    return null;
  }

  /**
   * port
   */
  public String port(int port1,
                     int port2 ) throws IOException
  {
    this.port1 = port1;
    this.port2 = port2;

    StringBuffer ipPort = new StringBuffer();

    ipPort.append(localHostIP);
    ipPort.append(String.valueOf(port1));
    ipPort.append(",");
    ipPort.append(String.valueOf(port2));

    if (DEBUG)
      System.out.println("Port Command: "+FTPConstants.PORT+" "+ipPort.toString()+FTPConstants.EOL);

    ftpServerWriter.write(FTPConstants.PORT+" "+ipPort.toString()+FTPConstants.EOL);
    ftpServerWriter.flush();

    try
    {
      return ftpServerReader.readLine();
    }
    catch (InterruptedIOException ignore)
    {
    }

    return null;
  }

  /**
   * printCredits
   */
  private static final void printCredits()
  {
    System.out.println("\nJFTP v1.0, Copyright (c) 2001, JB Innovations");
    System.out.println("All Rights Reserved\n");
  }

  /**
   * printList
   */
  public static final void printList(List list)
  {
    System.out.println("\n");

    for (Iterator iter = list.iterator(); iter.hasNext(); )
      System.out.println(iter.next().toString());
  }

  public String pwd() throws IOException
  {
    ftpServerWriter.write(FTPConstants.PWD+FTPConstants.EOL);
    ftpServerWriter.flush();

    try
    {
      return ftpServerReader.readLine();
    }
    catch (InterruptedIOException ignore)
    {
    }

    return null;
  }

  /**
   * quit
   */
  public void quit() throws IOException
  {
    ftpServerWriter.write(FTPConstants.QUIT+FTPConstants.EOL);
    ftpServerWriter.flush();
    ftpServerWriter.close();
    ftpServerReader.close();
    ftpServerConnection.close();
  }

  /**
   * runCommandLine
   */
  private void runCommandLine(String ipAddress)
  {
    try
    {
      String temp = null;

      printCredits();

      if ((temp = connect(ipAddress)) != null)
        System.out.println(temp);

      final BufferedReader commandLineReader = new BufferedReader(new InputStreamReader(System.in));

      login(commandLineReader);
      System.out.print(PROMPT);

      int index = -1;

      String command = null;
      String param  = null;

      while (!(command = commandLineReader.readLine()).equalsIgnoreCase("quit"))
      {
        index = command.indexOf(" ");
        command = command.substring(0,(index > -1 ? index : command.length()));

        if (index > -1)
          param = command.substring(index+1);

        if (command.equalsIgnoreCase(FTPConstants.USER))
          System.out.println(((temp = user(param)) != null ? "\n"+temp : ""));
        else if (command.equalsIgnoreCase(FTPConstants.PASS))
          System.out.println(((temp = pass(param)) != null ? "\n"+temp : ""));
        else if (command.equalsIgnoreCase(FTPConstants.LIST))
          printList(list());
        else if (command.equalsIgnoreCase(FTPConstants.PWD))
          System.out.println(pwd());

        System.out.print(PROMPT);
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      try
      {
        quit();
      }
      catch (IOException ioe)
      {
        System.out.println("Error: could not log out of FTP Server!  "+ioe.getMessage());
        ioe.printStackTrace();
      }
    }
  }

  /**
   * usage prints program help information.
   */
  private static final void usage()
  {
    printCredits();
    System.out.println("usage: java jjb.toolbox.net.JFTP <IP Address of FTP Server>\n");
    System.out.println("options:");
    System.out.println("\t -? -help               print this help message\n");
  }

  /**
   * user
   */
  public String user(String username) throws IOException
  {
    ftpServerWriter.write(FTPConstants.USER+" "+username+FTPConstants.EOL);
    ftpServerWriter.flush();

    try
    {
      return ftpServerReader.readLine();
    }
    catch (InterruptedIOException ignore)
    {
    }

    return null;
  }

  /**
   * main executable method for the JFTP application.
   */
  public static void main(String[] args) throws Exception
  {
    if (args.length == 0 || args[0].equals("-?") || args[0].equalsIgnoreCase("-help"))
      usage();

    new JFTP(args[0]);
  }

}

