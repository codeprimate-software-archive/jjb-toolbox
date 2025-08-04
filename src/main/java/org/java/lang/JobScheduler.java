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

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class JobScheduler implements Runnable {

  public static final int ONCE = 1;
  public static final int FOREVER = -1;
  public static final long HOURLY = 60 * 60 * 1000;
  public static final long DAILY = 24 * HOURLY;
  public static final long WEEKLY = 7 * DAILY;
  public static final long MONTHLY = -1;
  public static final long YEARLY = -2;

  private DaemonLock dlock = new DaemonLock();

  private List jobs = new Vector(100);
  
  private ThreadPool pool;
  
  private class JobNode {
    private int count;
    private long interval;
    private Date executeAt;
    private Runnable job;
  }
  
  public JobScheduler(final int poolSize) {
    pool = (poolSize > 0) ? new ThreadPool(poolSize) : null;
    Thread js = new Thread(this);
    js.setDaemon(true);
    js.start();
  }
  
  private synchronized void addJob(final JobNode job) {
    dlock.acquire();
    jobs.add(job);
    notify();
  }
  
  private synchronized void removeJob(final Runnable job) {
    for (Iterator it = jobs.iterator(); it.hasNext(); ) {
      if (((JobNode) it.next()).job == job) {
        jobs.remove(job);
        dlock.release();
        break;
      }
    }
  }
  
  private JobNode updateJobNode(final JobNode jobNode) {
    final Calendar cal = Calendar.getInstance();
    cal.setTime(jobNode.executeAt);
    
    if (jobNode.interval == MONTHLY) {
      cal.add(Calendar.MONTH, 1);
      jobNode.executeAt = cal.getTime();
    }
    else if (jobNode.interval == YEARLY) {
      cal.add(Calendar.YEAR, 1);
      jobNode.executeAt = cal.getTime();
    }
    else {
      jobNode.executeAt = new Date(jobNode.executeAt.getTime() 
        + jobNode.interval);
    }
    
    jobNode.count = (jobNode.count == FOREVER) ? FOREVER 
      : jobNode.count - 1;
      
    return (jobNode.count != 0) ? jobNode : null;
  }
  
  private synchronized long runJobs() {
    long minDiff = Long.MAX_VALUE;
    long now = System.currentTimeMillis();
    
    for (int i = 0, size = jobs.size(); i < size; ) {
      final JobNode jobNode = (JobNode) jobs.get(i);
      
      if (jobNode.executeAt.getTime() <= now) {
        if (pool != null)
          pool.addRequest(jobNode.job);
        else {
          Thread jt = new Thread(jobNode.job);
          jt.setDaemon(false);
          jt.start();
        }
        
        if (updateJobNode(jobNode) == null) {
          jobs.remove(jobNode);
          dlock.release();
        }
      }
      else {
        long diff = jobNode.executeAt.getTime() - now;
        minDiff = Math.min(diff, minDiff);
        i++;        
      }
    }
    
    return minDiff;
  }
  
  public synchronized void run() {
    while (true) {
      long waitTime = runJobs();
      try {
        wait(waitTime);
      }
      catch (InterruptedException e) {
      }
    }
  }
  
  public void execute(final Runnable job) {
    executeIn(job, 0);
  }
  
  public void executeIn(final Runnable job,
                        final long millis  ) {
    executeInAndRepeat(job, millis, 1000, ONCE);
  }
  
  public void executeInAndRepeat(final Runnable job,
                                 final long millis,
                                 final long repeat  ) {
    executeInAndRepeat(job, millis, repeat, FOREVER);
  }
  
  public void executeInAndRepeat(final Runnable job,
                                 final long millis,
                                 final long repeat,
                                 final int count    ) {
    executeAtAndRepeat(job, new Date(System.currentTimeMillis() + millis),
      repeat, count);
  }
  
  public void executeAt(final Runnable job,
                        final Date when    ) {
    executeAtAndRepeat(job, when, 1000, ONCE);
  }
  
  public void executeAtAndRepeat(final Runnable job,
                                 final Date when,
                                 final long repeat  ) {
    executeAtAndRepeat(job, when, repeat, FOREVER);
  }
  
  public void executeAtAndRepeat(final Runnable job,
                                 final Date when,
                                 final long repeat,
                                 final int count    ) {
    final JobNode jobNode = new JobNode();
    jobNode.job = job;
    jobNode.executeAt = when;
    jobNode.interval = repeat;
    jobNode.count = count;
    addJob(jobNode);
  }
  
  public void cancel(final Runnable job) {
    removeJob(job);
  }

}

