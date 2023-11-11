/*
 * StreamNetFile.java (c) 2001.9.29
 *
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.3.5
 */

package jjb.toolbox.net;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class StreamNetFile {

  public static final long KB = 1024;
  public static final long MB = 1048576;

  private static final long printSize = MB;

  /**
   * Constructs a StreamNetFile object to transfer a file from a remote
   * Server to the localhost.
   */
  public StreamNetFile() {
  }

  /**
   * Transfers a file from the specified location to a location
   * on the localhost specified by the client of this program.
   *
   * @param fromLocation java.net.URL object representing the remote location
   * of the file to transfer
   * @param toLocation java.lang.String is the specified location on the
   * localhost to place the file.
   * @throws a java.lang.IOException if the stream is corrupted during the 
   * file transfer.
   */
  public static void streamFile(URL fromLocation,
                                String toLocation)
      throws IOException {
    URLConnection connection = fromLocation.openConnection();

    BufferedInputStream stream = new BufferedInputStream(connection.getInputStream());

    FileOutputStream fos = new FileOutputStream(toLocation);

    int bufferSize = 64 * (int) KB;
    int len = -1;

    long totalBytes = 0;

    byte[] buffer = new byte[bufferSize];

    while ((len = stream.read(buffer,0,bufferSize)) != -1) {
      fos.write(buffer,0,len);

      totalBytes += len;

      if (totalBytes % printSize == 0)
        System.out.println("Downloaded "+totalBytes+" bytes.");
    }

    fos.flush();
    fos.close();
    stream.close();
  }

  /**
   * main is the executable method of this program.
   *
   * @param args java.lang.String array containing the arguments to this
   * program.
   * Note: that arg[0] contains the URL of the remote file to transfer and
   * arg[1] contains the destination of the file on the localhost.
   */
  public static void main(String[] args) throws Exception {
    streamFile(new URL(args[0]),args[1]);
  }

}

