/**
 * Copyright (c) 2001, Sun Microsystems, Inc.
 * ALL RIGHTS RESERVED
 *
 * This class was taken from the Core Java 2 Volume II - Advanced
 * Features book, Prentice Hall, 2000
 * ISBN 0-13-081934-4
 * Chapter 9 - Security
 *
 * @author Cay S. Horstmann & Gary Cornell
 * Modified By: John J. Blum
 * File: Bitmap.java
 * @version 1.00 1999-10-23
 * Date: 19 March 2002
 * Modification Date: 19 March 2002
 * @since Java 2
 */

package org.java.security;

import java.io.*;
import java.security.*;
import java.security.cert.*;
import java.util.*;
import sun.security.x509.X509CertInfo;
import sun.security.x509.X509CertImpl;
import sun.security.x509.X500Name;
import sun.security.x509.CertificateIssuerName;

public class CertificateSigner
{

  public static void main(String[] args)
  {
    String ksname = null;  // the keystore name
    String alias = null;   // the private key alias
    String inname = null;  // the input file name
    String outname = null; // the output file name

    for (int i = 0; i < args.length; i += 2)
    {
      if (args[i].equals("-keystore"))
        ksname = args[i + 1];
      else if (args[i].equals("-alias"))
        alias = args[i + 1];
      else if (args[i].equals("-infile"))
        inname = args[i + 1];
      else if (args[i].equals("-outfile"))
        outname = args[i + 1];
      else usage();
    }

    if (ksname == null || alias == null || inname == null || outname == null)
      usage();

    try
    {
      PushbackReader console = new PushbackReader(new InputStreamReader(System.in));

      KeyStore store = KeyStore.getInstance("JKS", "SUN");

      InputStream in = new FileInputStream(ksname);

      char[] password = readPassword(console, "Keystore password");

      store.load(in, password);
      Arrays.fill(password, ' ');
      in.close();

      char[] keyPassword = readPassword(console, "Key password for " + alias);

      PrivateKey issuerPrivateKey = (PrivateKey)store.getKey(alias, keyPassword);

      Arrays.fill(keyPassword, ' ');

      if (issuerPrivateKey == null)
        error("No such private key");

      in = new FileInputStream(inname);

      CertificateFactory factory = CertificateFactory.getInstance("X.509");


      X509Certificate inCert = (X509Certificate)factory.generateCertificate(in);

      in.close();

      byte[] inCertBytes = inCert.getTBSCertificate();

      X509Certificate issuerCert = (X509Certificate)store.getCertificate(alias);

      Principal issuer = issuerCert.getSubjectDN();

      String issuerSigAlg = issuerCert.getSigAlgName();

      FileOutputStream out = new FileOutputStream(outname);

      X509CertInfo info = new X509CertInfo(inCertBytes);

      info.set(X509CertInfo.ISSUER,
               new CertificateIssuerName((X500Name)issuer));

      X509CertImpl outCert = new X509CertImpl(info);

      outCert.sign(issuerPrivateKey, issuerSigAlg);
      outCert.derEncode(out);

      out.close();
    }
    catch (Exception exception)
    {
      System.out.println(exception);
    }
  }

  public static char[] readPassword(PushbackReader in,
                                    String         prompt) throws IOException
  {
    System.out.print(prompt + ": ");
    System.out.flush();

    final int MAX_PASSWORD_LENGTH = 100;

    int length = 0;

    char[] buffer = new char[MAX_PASSWORD_LENGTH];

    while (true)
    {
      int ch = in.read();

      if (ch == '\r' || ch == '\n' || ch == -1 || length == MAX_PASSWORD_LENGTH)
      {
        if (ch == '\r') // handle DOS "\r\n" line ends
        {
          ch = in.read();

          if (ch != '\n' && ch != -1)
            in.unread(ch);
        }
        char[] password = new char[length];

        System.arraycopy(buffer, 0, password, 0, length);
        Arrays.fill(buffer, ' ');

        return password;
      }
      else
      {
        buffer[length] = (char)ch;
        length++;
      }
    }
  }

  public static void error(String message)
  {
    System.out.println(message);
    System.exit(1);
  }

  public static void usage()
  {
    System.out.println("Usage: java CertificateSigner"
                        + " -keystore keyStore -alias issuerKeyAlias"
                        + " -infile inputFile -outfile outputFile");
    System.exit(1);
  }

}
