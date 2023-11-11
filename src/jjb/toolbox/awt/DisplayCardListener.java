/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * Written @ Education Logistics, Inc.
 *
 * @auther John J. Blum
 * File: DisplayCardListener.java
 * @version v1.0
 * Date: 17 April 2001
 * Modification Date: 17 April 2002
 * @since Java 1.0
 */

package jjb.toolbox.awt;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DisplayCardListener implements ActionListener
{

  protected Container  container;

  protected String     cardName;

  /**
   * Constructs a new DisplayCardListener object to listen to card change events
   * in a container using the CardLayout manager.
   *
   * @param container:Ljava.awt.Container the container using a Card Layout Manager.
   * @param cardName:Ljava.lang.String the name of the card to switch to.
   */
  public DisplayCardListener(Container  container,
                             String     cardName  )
  {
    this.container = container;
    this.cardName = cardName;
  }

  /**
   * actionPerformed is called by the event source for which this listener
   * registered itself with by calling the addActionListener method, passing
   * a ActionEvent object representing the event.  The event source sends an
   * ActionEvent to this object notifying that the user has requested a change
   * in the user interface to display the card specified by "cardName" in the 
   * container specified by "container".
   *
   * @param ae:Ljava.awt.event.ActionEvent the action event object fired by the event
   * source.
   */
  public void actionPerformed(ActionEvent ae)
  {
    CardLayout cardlayoutmanager = (CardLayout) container.getLayout();

    cardlayoutmanager.show(container,cardName);
  }

}

