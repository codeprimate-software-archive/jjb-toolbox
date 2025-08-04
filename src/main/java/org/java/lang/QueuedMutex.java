/*
 *  Copyright (c) 1997-1999 Scott Oaks and Henry Wong. All Rights Reserved.
 *
 *  Permission to use, copy, modify, and distribute this software
 *  and its documentation for NON-COMMERCIAL purposes and
 *  without fee is hereby granted.
 *
 *  This sample source code is provided for example only,
 *  on an unsupported, as-is basis.
 *
 *  AUTHOR MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 *  THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 *  TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 *  PARTICULAR PURPOSE, OR NON-INFRINGEMENT. AUTHOR SHALL NOT BE LIABLE FOR
 *  ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 *  DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 *
 *  THIS SOFTWARE IS NOT DESIGNED OR INTENDED FOR USE OR RESALE AS ON-LINE
 *  CONTROL EQUIPMENT IN HAZARDOUS ENVIRONMENTS REQUIRING FAIL-SAFE
 *  PERFORMANCE, SUCH AS IN THE OPERATION OF NUCLEAR FACILITIES, AIRCRAFT
 *  NAVIGATION OR COMMUNICATION SYSTEMS, AIR TRAFFIC CONTROL, DIRECT LIFE
 *  SUPPORT MACHINES, OR WEAPONS SYSTEMS, IN WHICH THE FAILURE OF THE
 *  SOFTWARE COULD LEAD DIRECTLY TO DEATH, PERSONAL INJURY, OR SEVERE
 *  PHYSICAL OR ENVIRONMENTAL DAMAGE ("HIGH RISK ACTIVITIES").  AUTHOR
 *  SPECIFICALLY DISCLAIMS ANY EXPRESS OR IMPLIED WARRANTY OF FITNESS FOR
 *  HIGH RISK ACTIVITIES.
 */

package org.java.lang;

import java.util.List;
import java.util.Vector;

public class QueuedMutex extends Mutex {

  protected List waiters;

  public QueuedMutex() {
    waiters = new Vector();
  }
  
  public synchronized void freeMutex() {
    if (Thread.currentThread() != mutexOwner)
      throw new IllegalArgumentException("QueuedMutex not held!");
    
    if (nestedCount == 0) {
      waiters.remove(0);
      notifyAll();
      mutexOwner = null;
    }
    else
      nestedCount--;
  }

  public synchronized void getMutex() {
    Thread me = Thread.currentThread();
    
    if (me == mutexOwner) {
      nestedCount++;
      return;
    }
    
    waiters.add(me);
    
    while ((Thread) waiters.get(0) != me) {
      try {
        wait();
      }
      catch (InterruptedException ignore) {
      }
    }
    
    mutexOwner = me;
    nestedCount = 0;
  }
  
  public synchronized boolean tryGetMutex() {
    if (waiters.size() != 0 && mutexOwner != Thread.currentThread())
      return false;
    getMutex();
    return true;
  }

}

