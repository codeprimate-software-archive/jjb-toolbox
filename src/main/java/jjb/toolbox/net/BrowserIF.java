/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: BrowserIF.java
 * @version v1.0
 * Date: 15 April 2001
 * Modified Date: 17 April 2002
 * @since Java 1.0
 * @deprecated see the javax.swing.event.HyperlinkListener class.
*/

package jjb.toolbox.net;

public interface BrowserIF
{

  /**
   * gotToURL redirects the web browser to the specified url.
   */
  public void goToURL(java.net.URL address);

}

