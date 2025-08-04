/**
 * Copyright (c) 2001, Sun Microsystems, Inc.
 * ALL RIGHTS RESERVED
 *
 * This class was taken from the Core Java 2 Volume II - Advanced
 * Features book, Prentice Hall, 2000
 * ISBN 0-13-081934-4
 * Chapter 7 - Advanced AWT
 *
 * @author Cay S. Horstmann & Gary Cornell
 * Modified By: John J. Blum
 * File: MimeClipboard.java
 * @version v1.0
 * Date: 19 March 2002
 * Modification Date: 19 March 2002
 * @since Java 2
 */

package org.java.awt.datatransfer;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.java.io.Base64InputStream;
import org.java.io.Base64OutputStream;

public class MimeClipboard extends Clipboard
{

  private Clipboard clip;

  public MimeClipboard(Clipboard cb)
  {
    super("MIME/" + cb.getName());
    clip = cb;
  }

  public synchronized void setContents(Transferable   contents,
                                       ClipboardOwner owner    )
  {
    if (contents instanceof SerializableSelection)
    {
      try
      {
        DataFlavor flavor = SerializableSelection.serializableFlavor;

        Serializable obj = (Serializable) contents.getTransferData(flavor);

        String enc    = encode(obj);
        String header = "Content-type: "
                        + flavor.getMimeType()
                        + "\nContent-length: "
                        + enc.length() + "\n\n";

        StringSelection selection = new StringSelection(header + enc);

        clip.setContents(selection, owner);
      }
      catch (UnsupportedFlavorException ignore)
      {
      }
      catch (IOException ignore)
      {
      }
    }
    else
      clip.setContents(contents, owner);
  }

  public synchronized Transferable getContents(Object requestor)
  {
    Transferable contents = clip.getContents(requestor);

    if (contents instanceof StringSelection)
    {
      String data = null;

      try
      {
        data = (String) contents.getTransferData(DataFlavor.stringFlavor);
      }
      catch (UnsupportedFlavorException e)
      {
        return contents;
      }
      catch (IOException e)
      {
        return contents;
      }

      if (!data.startsWith("Content-type: "))
        return contents;

      int start = -1;

      // skip three newlines
      for (int i = 0; i < 3; i++)
      {
        start = data.indexOf('\n', start + 1);

        if (start < 0)
          return contents;
      }

      Serializable obj = decode(data.substring(start));

      SerializableSelection selection = new SerializableSelection(obj);

      return selection;
    }
    else
      return contents;
  }

  public static String encode(Serializable obj)
  {
    ByteArrayOutputStream bOut = new ByteArrayOutputStream();

    try
    {
      Base64OutputStream b64Out = new Base64OutputStream(bOut);

      ObjectOutputStream out = new ObjectOutputStream(b64Out);

      out.writeObject(obj);
      out.close();

      return bOut.toString("8859_1");
    }
    catch (IOException exception)
    {
      return null;
    }
  }

  public static Serializable decode(String s)
  {
    try
    {
      byte[] bytes = s.getBytes("8859_1");

      ByteArrayInputStream bIn = new ByteArrayInputStream(bytes);

      Base64InputStream b64In = new Base64InputStream(bIn);

      ObjectInputStream in = new ObjectInputStream(b64In);

      Object obj = in.readObject();

      in.close();

      return(Serializable)obj;
    }
    catch (Exception e)
    {
      return null;
    }
  }

}

