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

public class ThreadPool {

  private int nObjects = 0;

  private boolean terminated = false;
  
  private ConditionVariable cvAvailable;
  private ConditionVariable cvEmpty;
  
  private List objects;

  private Mutex cvFlag;
  
  private ThreadPoolThread[] poolThreads;

  private class ThreadPoolRequest {
    private final Object lock;
    private final Runnable task; // target
    
    private ThreadPoolRequest(final Runnable task,
                              final Object lock   ) {
      this.task = task;
      this.lock = lock;
    }
  }
  
  private class ThreadPoolThread extends Thread {
    private ThreadPool pool; // parent
    private volatile boolean shouldRun = true;
    
    private ThreadPoolThread(final ThreadPool pool,
                             final int i           ) {
      super("ThreadPoolThread " + i);
      this.pool = pool;
    }
    
    public void run() {
      ThreadPoolRequest request = null;
      
      while (shouldRun) {
        try {
          pool.cvFlag.getMutex();

          while (request == null && shouldRun) {
            try {
              request = (ThreadPoolRequest) pool.objects.get(0);
            }
            catch (ArrayIndexOutOfBoundsException e) {
              request = null;
            }
            catch (ClassCastException e) {
              System.err.println("Error: Invalid Data!");
              e.printStackTrace(System.err);
            }

            if (request == null) {
              try {
                pool.cvAvailable.cvWait();
              }
              catch (InterruptedException e) {
                return;
              }
            }
          }
        }
        finally {
          pool.cvFlag.freeMutex();
        }

        if (!shouldRun)
          return;

        request.task.run();

        try {
          pool.cvFlag.getMutex();
          nObjects--;
          if (nObjects == 0)
            pool.cvEmpty.cvSignal();
        }
        finally {
          pool.cvFlag.freeMutex();
        }
        
        if (request.lock != null) {
          synchronized (request.lock) {
            request.lock.notify();
          }
        }
        
        request = null;
      }
    }
  }
  
  public ThreadPool(final int n) {
    cvFlag = new Mutex();
    cvAvailable = new ConditionVariable(cvFlag);
    cvEmpty = new ConditionVariable(cvFlag);
    objects = new Vector();
    poolThreads = new ThreadPoolThread[n];
    
    for (int i = n; --i >= 0; ) {
      poolThreads[i] = new ThreadPoolThread(this, i);
      poolThreads[i].start();
    }
  }
  
  private void add(final Runnable task,
                   final Object lock   ) {
    try {
      cvFlag.getMutex();
      
      if (terminated)
        throw new IllegalStateException("The ThreadPool has shutdown!");
      
      objects.add(new ThreadPoolRequest(task, lock));
      cvAvailable.cvSignal();
    }
    finally {
      cvFlag.freeMutex();
    }
  }
  
  public void addRequest(final Runnable task) {
    add(task, null);
  }
  
  public void addRequestAndWait(final Runnable task)
      throws InterruptedException {
    final Object lock = new Object();
    synchronized (lock) {
      add(task, lock);
      lock.wait();
    }
  }
  
  public void waitForAll(final boolean terminate)
      throws InterruptedException {
    try {
      cvFlag.getMutex();
      
      while (nObjects != 0)
        cvEmpty.cvWait();
      
      if (terminate) {
        for (int i = poolThreads.length; --i >= 0; )
          poolThreads[i].shouldRun = false;
        cvAvailable.cvBroadcast();
        terminated = true;
      }
    }
    finally {
      cvFlag.freeMutex();
    }
  }
  
  public void waitForAll() throws InterruptedException {
    waitForAll(false);
  }
  
}

