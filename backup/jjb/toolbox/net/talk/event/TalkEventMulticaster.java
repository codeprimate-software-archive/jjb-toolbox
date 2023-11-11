/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: TalkEventMulticaster.java
 * @version v1.0
 * Date: 23 August 2001
 * Modification Date: 17 April 2002
 * @since Java 1.0
 * @see Ljava.awt.AWTEventMulticaster
 */

package jjb.toolbox.net.talk.event;

import java.awt.AWTEventMulticaster;
import java.util.EventListener;

public class TalkEventMulticaster extends AWTEventMulticaster implements TalkListener
{

  /**
   * Constructs an instance of the TalkEventMulticaster object to broadcast
   * TalkEvents to multiple TalkListeners.
   *
   * @param a:Ljava.awt.event.EventListener object listening for TalkEvents.
   * @param b:Ljava.awt.event.EventListener object listening for TalkEvents.
   */
  public TalkEventMulticaster(EventListener a,
                              EventListener b )
  {
    super(a,b);
  }

  /**
   * Adds talk-listener-a with talk-listener-b and returns the
   * resulting multicast listener.
   *
   * @param a:Ljjb.toolbox.comm.talk.event.TalkListener talk-listener-a
   * @param b:Ljjb.toolbox.comm.talk.event.TalkListener the talk-listener being added.
   * @return the Ljjb.toolbox.comm.talk.event.TalkListener object.
   */
  public static TalkListener add(TalkListener a,
                                 TalkListener b )
  {
    return (TalkEventMulticaster) addInternal(a,b);
  }

  /**
   * Removes the old talk-listener from talk-listener-l and
   * returns the resulting multicast listener.
   *
   * @param l:Ljjb.toolbox.comm.talk.event.TalkListener talk-listener-l
   * @param oldl:Ljjb.toolbox.comm.talk.event.TalkListener the talk-listener being removed
   * @return the Ljjb.toolbox.comm.talk.event.TalkListener object.
   */
  public static TalkListener remove(TalkListener l,
                                    TalkListener oldl)
  {
    return (TalkListener) removeInternal(l,oldl);
  }

  /**
   * talkingPerformed is called when a talk event is fired by the 
   * TalkManager object when communicating and receiving data from
   * the remote host.
   *
   * @param te:Ljjb.toolbox.comm.talk.event.TalkEvent object encapsulating
   * the talk event information.
   */
  public void talkingPerformed(TalkEvent te)
  {
    ((TalkListener) a).talkingPerformed(te);
    ((TalkListener) b).talkingPerformed(te);
  }
}

