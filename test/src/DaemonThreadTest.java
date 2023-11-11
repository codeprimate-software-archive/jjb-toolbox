/**
 * DaemonThreadTest.java (c) 2003.2.26
 *
 * Copyright (c) 2003, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.2.26
 */

public class DaemonThreadTest {

  private static final class DaemonThread implements Runnable {
    public void run() {
      int count = 1;
      while (true) {
        System.out.println("DaemonThread: "+count++);
        try {
          Thread.sleep(1000);
        }
        catch (InterruptedException e) {
        }
      }
    }
  }
  
  private static final class UserThread implements Runnable {
    public void run() {
      int count = 1;
      while (count < 20) {
        System.out.println("UserThread: "+count++);
        try {
          Thread.sleep(1000);
        }
        catch (InterruptedException e) {
        }
      }
    }
  }
  
  public static void main(String[] args) {
    final Thread dt = new Thread(new DaemonThread());
    dt.setDaemon(true);
    dt.start();
    
    final Thread ut = new Thread(new UserThread());
    ut.start();

    System.out.println("Sleeping...");
    try {
      Thread.sleep(5000);
    }
    catch (InterruptedException e) {
    }
    System.out.println("Done Sleeping!");
  }

}

