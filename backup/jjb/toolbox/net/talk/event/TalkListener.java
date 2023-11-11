/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: TalkListener.java
 * @version v1.0
 * Date: 23 August 2001
 * Modification Date: 17 April 2002
 * @since Java 1.0
 * @see Ljava.util.EventListener
 */

package jjb.toolbox.net.talk.event;

public interface TalkListener extends java.util.EventListener
{

  /**
   * talkingPerformed is called when a talk event is fired
   * by the TalkManager object when communicating and
   * receiving data from the remote host.
   *
   * @param te:Ljjb.toolbox.comm.talk.event.TalkEvent object
   * encapsulating the talk event information.
   */
  public void talkingPerformed(TalkEvent te);

}

