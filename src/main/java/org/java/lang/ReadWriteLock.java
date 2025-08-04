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

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

class ReadWriteNode {
  static final int READER = 0;
  static final int WRITER = 1;

  int nAcquires;
  int state;

  Thread t;
  
  ReadWriteNode(final Thread t,
                final int state) {
    this.t = t;
    this.state = state;
    nAcquires = 0;
  }
}

public class ReadWriteLock {
  
  private List waiters;
    
  public ReadWriteLock() {
    waiters = new Vector();
  }

  private int firstWriter() {
    int index = 0;
    for (Iterator it = waiters.iterator(); it.hasNext(); index++) {
      final ReadWriteNode node = (ReadWriteNode) it.next();
      if (node.state == ReadWriteNode.WRITER)
        return index;
    }
    return Integer.MAX_VALUE;
  }

  private int getIndex(Thread t) {
    int index = 0;
    for (Iterator it = waiters.iterator(); it.hasNext(); index++) {
      if (((ReadWriteNode) it.next()).t == t)
        return index;
    }
    return -1;
  }

  public synchronized void lockRead() {
    ReadWriteNode node = null; 
    Thread me = Thread.currentThread();
    int index = getIndex(me);
    
    if (index == -1) {
      node = new ReadWriteNode(me, ReadWriteNode.READER);
      waiters.add(node);
    }
    else
      node = (ReadWriteNode) waiters.get(index);
    
    while (getIndex(me) > firstWriter()) {
      try {
        wait();
      }
      catch (InterruptedException ignore) {
      }
    }

    node.nAcquires++;
  }
  
  public synchronized void lockWrite() {
    ReadWriteNode node = null;
    Thread me = Thread.currentThread();
    int index = getIndex(me);
    
    if (index == -1) {
      node = new ReadWriteNode(me, ReadWriteNode.WRITER);
      waiters.add(node);
    }
    else {
      node = (ReadWriteNode) waiters.get(index);
      
      if (node.state == ReadWriteNode.READER)
        throw new IllegalStateException("Upgrade Lock!");
      
      node.state = ReadWriteNode.WRITER;
    }
    
    while (getIndex(me) != 0) {
      try {
        wait();
      }
      catch (InterruptedException ignore) {
      }
    }
    
    node.nAcquires++;
  }

  public synchronized void unlock() {
    ReadWriteNode node = null;
    Thread me = Thread.currentThread();
    int index = getIndex(me);
    
    if (index > firstWriter())
      throw new IllegalStateException("Lock not held!");
    
    node = (ReadWriteNode) waiters.get(index);
    node.nAcquires--;
    
    if (node.nAcquires == 0) {
      waiters.remove(node);
      notifyAll();
    }
  }

}

