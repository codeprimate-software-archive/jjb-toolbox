/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * Note that class was commented by SlickEdit using the
 * Java API comments.
 *
 * @author John J. Blum
 * File: TalkEvent.java
 * @version v1.0
 * Date: 23 August 2001
 * Modification Date: 17 April 2002
 * @since Java 1.0
 * @see Ljava.awt.AWTEvent
 */

package jjb.toolbox.net.talk.event;

public class TalkEvent extends java.awt.AWTEvent
{

  /**
   * Constructs a new instance of the TalkEvent class
   * specifying the event category with the ID and the
   * object source which originated this event object.
   *
   * @param ID is an integer category description of
   * the TalkEvent.
   * @param source:Ljava.lang.Object is the object
   * source, or originator, of this TalkEvent object.
   */
  public TalkEvent(int    id,
                   Object source)
  {
    super(source,id);
  }

}

