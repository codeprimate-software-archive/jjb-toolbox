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

public class ConditionVariable {

  private final Mutex lock;

  public ConditionVariable() {
    this(new Mutex());
  }

  public ConditionVariable(final Mutex lock) {
    this.lock = lock;
  }

  public void cvWait()
      throws InterruptedException {
    cvTimedWait(lock,0);
  }

  public void cvWait(final Mutex lock)
      throws InterruptedException {
    cvTimedWait(lock,0);
  }

  public void cvWait(final long milliseconds)
      throws InterruptedException {
    cvTimedWait(lock,milliseconds);
  }

  public void cvTimedWait(final Mutex lock,
                          final long milliseconds)
      throws InterruptedException {
    int i = 0;
    InterruptedException iex = null;

    synchronized (this) {
      // You must own the lock in order to use this method.
      if (lock.getMutexOwner() != Thread.currentThread()) {
        throw new IllegalMonitorStateException("Current Thread not owner!");
      }

      // Release the lock (completely).
      while (lock.getMutexOwner() == Thread.currentThread()) {
        i++;
        lock.freeMutex();
      }

      // Use wait() method.
      try {
        if (milliseconds == 0)
          wait();
        else
          wait(milliseconds);
      }
      catch (InterruptedException e) {
        iex = e;
      }
    }

    // Obtain the lock (return to original state).
    for ( ; i > 0; i--)
      lock.getMutex();

    if (iex != null)
      throw iex;
  }

  public void cvSignal() {
    cvSignal(lock);
  }

  public synchronized void cvSignal(final Mutex lock) {
    // You must own the lock in order to use this method.
    if (lock.getMutexOwner() != Thread.currentThread()) {
      throw new IllegalMonitorStateException("Current Thread not owner!");
    }
    notify();
  }

  public void cvBroadcast() {
    cvBroadcast(lock);
  }

  public synchronized void cvBroadcast(final Mutex lock) {
    // You must own the lock in order to use this method.
    if (lock.getMutexOwner() != Thread.currentThread()) {
      throw new IllegalMonitorStateException("Current Thread not owner!");
    }
    notifyAll();
  }

}

