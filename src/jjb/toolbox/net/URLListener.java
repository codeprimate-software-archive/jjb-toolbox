/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: URLListener.java
 * @version v1.0
 * Date: 22 April 2001
 * Modification Date: 17 April 2002
 * @since Java 1.0
 * @deprecated see the javax.swing.event.HyperlinkListener class.
 */

package jjb.toolbox.net;

import java.awt.Cursor;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

public class URLListener extends MouseAdapter
{

  private final BrowserIF   browser;

  private final Cursor      defaultCursor   = new Cursor(Cursor.DEFAULT_CURSOR);
  private final Cursor      handCursor      = new Cursor(Cursor.HAND_CURSOR);

  private final URL         address;

  private final Window      container;

  /**
   * Creates a new instance of the URLListener class to handle
   * hyperlinks embeded in applications/applets.
   *
   * @param container:Ljava.awt.Window the window containing
   * the URL for which the mouse icon will change.
   * @param browser:Ljjb.toolbox.net.BrowserIF the object
   * implementing the BrowserIF interface, which is capable of
   * redirecting the browser to a new Internet resource.
   * @param address:Ljava.net.URL the uniform resource locator
   * object.
   */
  public URLListener(Window    container,
                     BrowserIF browser,
                     URL       address   )
  {
    this.container = container;
    this.browser = browser;
    this.address = address;
  }

  /**
   * mouseEntered is called when the user moves his/her mouse
   * over the hyperlink object in the UI.  The method then
   * proceeds in setting the mouse cursor to a pointing hand.
   *
   * @param me:Ljava.awt.event.MouseEvent encapsulating
   * information about the event - mouse entered.
   */
  public void mouseEntered(MouseEvent me)
  {
    container.setCursor(handCursor);
  }

  /**
   * mouseExited is called when the user moves the mouse
   * outside of the hyperlink object area in the UI.  the
   * method then sets the mouse back to the default, arrow.
   *
   * @param me:Ljava.awt.event.MouseEvent encapsulating
   * information about the event - mouse exited.
   */
  public void mouseExited(MouseEvent me)
  {
    container.setCursor(defaultCursor);
  }

  /**
   * mousePressed triggers the hyperlink and causes the
   * BrowserIF object to retrieve the resource (content) at
   * the specified URL.
   *
   * @param me:Ljava.awt.event.MouseEvent encapsulating
   * information about the event - mouse pressed.
   */
  public void mousePressed(MouseEvent me)
  {
    browser.goToURL(address);
  }

}

