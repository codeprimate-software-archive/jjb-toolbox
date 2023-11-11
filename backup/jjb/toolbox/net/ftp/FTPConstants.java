/**
 * Copyright (c) 2001, JB Innovations
 * ALL RIGHTS RESERVED
 *
 * @author John J. Blum
 * File: FTPConstants.java
 * @version v1.0
 * Date: 14 December 2001
 * Modification Date: 14 December 2001
 */

package jjb.toolbox.net;

public interface FTPConstants
{

  // Commom FTP Commands
  public static final String ABOR = "ABOR";  // abort a file transfer 
  public static final String CWD  = "CWD";   // change working directory 
  public static final String DELE = "DELE";  // delete a remote file 
  public static final String LIST = "LIST";  // list remote files 
  public static final String MDTM = "MDTM";  // return the modification time of a file
  public static final String MKD  = "MKD";   // make a remote directory 
  public static final String NLST = "NLST";  // name list of remote directory 
  public static final String PASS = "PASS";  // send password 
  public static final String PASV = "PASV";  // enter passive mode 
  public static final String PORT = "PORT";  // open a data port 
  public static final String PWD  = "PWD";   // print working directory 
  public static final String QUIT = "QUIT";  // terminate the connection 
  public static final String RETR = "RETR";  // retrieve a remote file 
  public static final String RMD  = "RMD";   // remove a remote directory 
  public static final String RNFR = "RNFR";  // rename from 
  public static final String RNTO = "RNTO";  // rename to
  public static final String SITE = "SITE";  // site-specific commands 
  public static final String SIZE = "SIZE";  // return the size of a file 
  public static final String STOR = "STOR";  // store a file on the remote host 
  public static final String TYPE = "TYPE";  // set transfer type
  public static final String USER = "USER";  // send username 

  // Less Common FTP Commands
  public static final String ACCT = "ACCT";  // send account information 
  public static final String APPE = "APPE";  // append to a remote file 
  public static final String CDUP = "CDUP";  // CWD to the parent of the current directory 
  public static final String HELP = "HELP";  // return help on using the server 
  public static final String MODE = "MODE";  // set transfer mode 
  public static final String NOOP = "NOOP";  // do nothing 
  public static final String REIN = "REIN";  // reinitialize the connection 
  public static final String STAT = "STAT";  // return server status 
  public static final String STOU = "STOU";  // store a file uniquely 
  public static final String STRU = "STRU";  // set file transfer structure 
  public static final String SYST = "SYST";  // return system type 

  public static final String EOL  = "\r\n";

  // TYPE Transfer Types
  public static final String TYPE_ASCII  = "A";
  public static final String TYPE_EBCDIC = "E";
  public static final String TYPE_IMAGE  = "I"; // binary data
  public static final String TYPE_LOCAL  = "L";

  public static final int PORT_MULTIPLIER = 256;

}

