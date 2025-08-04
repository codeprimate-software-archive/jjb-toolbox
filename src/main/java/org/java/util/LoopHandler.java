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

package org.java.util;

public abstract class LoopHandler implements Runnable {

  protected int currLoop;
  protected int endLoop;
  protected int numThreads;
  protected int startLoop;

  protected Thread[] lookupThreads;
  
  public LoopHandler(final int start,
                     final int end,
                     final int threads) {
    startLoop = start;
    endLoop = end;
    numThreads = threads;
    lookupThreads = new Thread[numThreads];
  }
  
  protected final class LoopRange {
    public int start;
    public int end;
  }

  protected synchronized LoopRange getLoopRange() {
    if (currLoop >= endLoop)
      return null;
    
    final LoopRange range = new LoopRange();
    range.start = currLoop;
    currLoop += (endLoop - startLoop) / numThreads + 1;
    range.end = (currLoop < endLoop ? currLoop : endLoop);
    return range;
  }
  
  public abstract void executeLoopBody(final int start,
                                       final int end   );
                                       
  public void processLoop() {
    for (int index = lookupThreads.length; --index >= 0; ) {
      lookupThreads[index] = new Thread(this);
      lookupThreads[index].start();
    }
    
    for (int index = lookupThreads.length; --index >= 0; ) {
      try {
        lookupThreads[index].join();
      }
      catch (InterruptedException ignore) {
      }
    }
  }
  
  public void run() {
     for (LoopRange range = getLoopRange(); range != null; )
       executeLoopBody(range.start, range.end);
  }

}

